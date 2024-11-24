package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class DeserturiActivity extends AppCompatActivity {
    private ListView lvDeserturi;
    private Button btnBackToMain;
    private ArrayList<Recipe> deserturiList;
    private RecipeAdapter adapter;

    private Button btnAddRecipe;

    private static final int REQUEST_CODE_ADD_RECIPE = 1;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = AppDatabase.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deserturi);

        // Inițializarea elementelor UI
        lvDeserturi = findViewById(R.id.lvDeserturi);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnAddRecipe = findViewById(R.id.btnAddRecipe);
        deserturiList = (ArrayList<Recipe>) db.recipeDao().getAllByCategory(2);

        adapter = new RecipeAdapter(this, deserturiList);
        lvDeserturi.setAdapter(adapter);

        // Setează listener pentru butonul "Back"
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(DeserturiActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Opțional, pentru a închide activitatea curentă
        });

        btnAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(DeserturiActivity.this, AddRecipeDetailsActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_RECIPE);
        });

        // Setează listener pentru elementele din ListView
        lvDeserturi.setOnItemClickListener((parent, view, position, id) -> {
            Recipe selectedRecipe = deserturiList.get(position);
            Intent intent = new Intent(DeserturiActivity.this, MainRecipeDetailsActivity.class);
            intent.putExtra("recipeName", selectedRecipe.getName());
            intent.putExtra("ingredients", selectedRecipe.getIngredients());
            intent.putExtra("instructions", selectedRecipe.getInstructions());
            startActivity(intent);


            // Pornește activitatea pentru detalii
            startActivity(intent);
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        RecipeDao recipeDao = db.recipeDao();
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_RECIPE && resultCode == RESULT_OK && data != null) {
            String newRecipeName = data.getStringExtra("recipeName");
            String newIngredients = data.getStringExtra("ingredients");
            String newInstructions = data.getStringExtra("instructions");
            int newCategory = 2;

            if (db.categoryDao().getCategoryById(newCategory) == null) {
                db.categoryDao().insertCategory(new Category("Deserturi")); // Adaugă categoria dacă nu există
            }
            // Creează și adaugă rețeta nouă în adapter
            Recipe newRecipe = new Recipe(newRecipeName, newIngredients, newInstructions, newCategory);
            recipeDao.insertAll(newRecipe);
            adapter.addRecipe(newRecipe); // Metodă custom din adapter care sortează și notifică
        }
    }
}
