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
    private ArrayList<String> rapideList;
    private ArrayAdapter<String> adapter;

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
        rapideList = new ArrayList<>();
        rapideList.add("Rețetă 1 - Sandviș cu avocado");
        rapideList.add("Rețetă 2 - Omletă cu brânză");
        rapideList.add("Rețetă 3 - Salată rapidă de roșii");
        rapideList.add("Rețetă 4 - Pâine prăjită cu unt de arahide");
        rapideList.add("Rețetă 5 - Smoothie rapid cu fructe");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rapideList);
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

                        String recipeName = rapideList.get(position);
                        String ingredients = "";
                        String instructions = "";

                        // Set ingredients and instructions based on recipe position
                        switch (position) {
                            case 0:
                                ingredients = "Avocado, Pâine, Lămâie, Sare";
                                instructions = "1. Taie avocado...\n2. Întinde pe pâine...";
                                break;
                            case 1:
                                ingredients = "Ouă, Brânză, Sare, Piper";
                                instructions = "1. Bate ouăle...\n2. Gătește cu brânza...";
                                break;
                            case 2:
                                ingredients = "Roșii, Ulei de măsline, Oțet, Sare";
                                instructions = "1. Taie roșiile...\n2. Adaugă ulei și oțet...";
                                break;
                            case 3:
                                ingredients = "Pâine, Unt de arahide, Miere";
                                instructions = "1. Prăjește pâinea...\n2. Întinde untul de arahide...";
                                break;
                            case 4:
                                ingredients = "Fructe, Iaurt, Miere";
                                instructions = "1. Amestecă fructele cu iaurtul...\n2. Adaugă mierea...";
                                break;
                        }

                        viewIntent.putExtra("recipeName", recipeName);
                        viewIntent.putExtra("ingredients", ingredients);
                        viewIntent.putExtra("instructions", instructions);
                        viewIntent.putExtra("sourceActivity", "ReteteRapideActivity");

                        startActivity(viewIntent);
                    })
                    .setNegativeButton("Editează", (dialog, which) -> {
                        Intent editIntent = new Intent(ReteteRapideActivity.this, AddEditRecipeActivity.class);
                        editIntent.putExtra("isEditMode", true);
                        editIntent.putExtra("recipeName", rapideList.get(position));
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

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE_ADD) {
                String newRecipeName = data.getStringExtra("recipeName");
                if (newRecipeName != null) {
                    rapideList.add(newRecipeName);
                    adapter.notifyDataSetChanged();
                }
            } else if (requestCode == REQUEST_CODE_EDIT) {
                int position = data.getIntExtra("position", -1);
                String updatedRecipeName = data.getStringExtra("recipeName");
                if (position != -1 && updatedRecipeName != null) {
                    rapideList.set(position, updatedRecipeName);
                    adapter.notifyDataSetChanged();
                }
            } else if (requestCode == REQUEST_CODE_SETTINGS) {
                // Reload settings when returning from SettingsActivity
                recreate(); // Recreates the activity to apply settings
            }
        }
    }
}
