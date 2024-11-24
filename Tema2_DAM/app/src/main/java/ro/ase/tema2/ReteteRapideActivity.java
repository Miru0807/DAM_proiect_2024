package ro.ase.tema2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ReteteRapideActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;
    private static final int REQUEST_CODE_SETTINGS = 3;

    private ListView lvReteteRapide;
    private Button btnBackToMain, btnAddRecipe, btnSettings;
    private  ArrayList<Recipe> rapideList;
    private RecipeAdapter adapter;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply settings before setting the content view
        applySettings();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retete_rapide);

        // Initialize UI elements
        lvReteteRapide = findViewById(R.id.lvReteteRapide);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnAddRecipe = findViewById(R.id.btnAddRecipe);
        btnSettings = findViewById(R.id.btnSettings);

        // Populate ListView with sample recipes
        rapideList = (ArrayList<Recipe>) db.recipeDao().getAllByCategory(3);

        adapter = new RecipeAdapter(this, rapideList);
        lvReteteRapide.setAdapter(adapter);

        // Set listener for "Back" button
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(ReteteRapideActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Set listener for "Add Recipe" button
        btnAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(ReteteRapideActivity.this, AddEditRecipeActivity.class);
            intent.putExtra("isEditMode", false);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });

        // Set listener for "Settings" button
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(ReteteRapideActivity.this, SettingsActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SETTINGS);
        });

        // Set listener for items in the ListView
        lvReteteRapide.setOnItemClickListener((parent, view, position, id) -> {
            // Show dialog for choosing between view and edit
            AlertDialog.Builder builder = new AlertDialog.Builder(ReteteRapideActivity.this);
            builder.setTitle("Alege acțiunea")
                    .setMessage("Dorești să vizualizezi sau să editezi această rețetă?")
                    .setPositiveButton("Vizualizează", (dialog, which) -> {
                        // Intent for viewing recipe details
                        Intent viewIntent = new Intent(ReteteRapideActivity.this, MainRecipeDetailsActivity.class);


                        Recipe selectedRecipe = rapideList.get(position);
                        Intent intent = new Intent(ReteteRapideActivity.this, MainRecipeDetailsActivity.class);
                        intent.putExtra("recipeName", selectedRecipe.getName());
                        intent.putExtra("ingredients", selectedRecipe.getIngredients());
                        intent.putExtra("instructions", selectedRecipe.getInstructions());
                        startActivity(intent);

                        viewIntent.putExtra("sourceActivity", "ReteteRapideActivity");

                        startActivity(viewIntent);
                    })
                    .setNegativeButton("Editează", (dialog, which) -> {
                        Intent editIntent = new Intent(ReteteRapideActivity.this, AddEditRecipeActivity.class);
                        editIntent.putExtra("isEditMode", true);
                        editIntent.putExtra("recipeName", (CharSequence) rapideList.get(position));
                        editIntent.putExtra("position", position);

                        startActivityForResult(editIntent, REQUEST_CODE_EDIT);
                    })
                    .setNeutralButton("Anulează", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    // Apply settings based on SharedPreferences
    private void applySettings() {
        SharedPreferences prefs = getSharedPreferences("AppConfig", MODE_PRIVATE);
        boolean darkTheme = prefs.getBoolean("darkTheme", false);

        // Apply theme based on darkTheme preference
        if (darkTheme) {
            setTheme(R.style.DarkTheme); // Ensure DarkTheme is defined in styles.xml
        } else {
            setTheme(R.style.LightTheme); // Ensure LightTheme is defined in styles.xml
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RecipeDao recipeDao = db.recipeDao();
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE_ADD) {
                String newRecipeName = data.getStringExtra("recipeName");
                String newIngredients = data.getStringExtra("ingredients");
                String newInstructions = data.getStringExtra("instructions");
                int newCategory = 3;

                if (db.categoryDao().getCategoryById(newCategory) == null) {
                    db.categoryDao().insertCategory(new Category("Rapide")); // Adaugă categoria dacă nu există
                }
                // Creează și adaugă rețeta nouă în adapter
                Recipe newRecipe = new Recipe(newRecipeName, newIngredients, newInstructions, newCategory);
                recipeDao.insertAll(newRecipe);
                adapter.addRecipe(newRecipe); // Metodă custom din adapter care sortează și notifică
            } else if (requestCode == REQUEST_CODE_EDIT) {
                int position = data.getIntExtra("position", -1);
                String updatedRecipeName = data.getStringExtra("recipeName");
                if (position != -1 && updatedRecipeName != null) {
//                    rapideList.set(position, updatedRecipeName);
                    adapter.notifyDataSetChanged();
                }
            } else if (requestCode == REQUEST_CODE_SETTINGS) {
                // Reload settings when returning from SettingsActivity
                recreate(); // Recreates the activity to apply settings
            }
        }
    }
}
