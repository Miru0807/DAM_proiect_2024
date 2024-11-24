package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class BauturiActivity extends AppCompatActivity {
    private ListView lvBauturi;
    private Button btnBackToMain;
    private Button btnAddRecipe;
    private ArrayList<Recipe> bauturiList;
    private RecipeAdapter adapter;

    private static final int REQUEST_CODE_ADD_RECIPE = 1;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = AppDatabase.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bauturi);

        lvBauturi = findViewById(R.id.lvBauturi);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnAddRecipe = findViewById(R.id.btnAddRecipe);

        bauturiList = (ArrayList<Recipe>) db.recipeDao().getAllByCategory(1);

        // Inițializează adapterul custom și setează la ListView
        adapter = new RecipeAdapter(this, bauturiList);
        lvBauturi.setAdapter(adapter);

        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(BauturiActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(BauturiActivity.this, AddRecipeDetailsActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_RECIPE);
        });

        lvBauturi.setOnItemClickListener((parent, view, position, id) -> {
            Recipe selectedRecipe = bauturiList.get(position);
            Intent intent = new Intent(BauturiActivity.this, MainRecipeDetailsActivity.class);
            intent.putExtra("recipeName", selectedRecipe.getName());
            intent.putExtra("ingredients", selectedRecipe.getIngredients());
            intent.putExtra("instructions", selectedRecipe.getInstructions());
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
            int newCategory = 1;

            if (db.categoryDao().getCategoryById(newCategory) == null) {
                db.categoryDao().insertCategory(new Category("Băuturi")); // Adaugă categoria dacă nu există
            }
            // Creează și adaugă rețeta nouă în adapter
            Recipe newRecipe = new Recipe(newRecipeName, newIngredients, newInstructions, newCategory);
            recipeDao.insertAll(newRecipe);
            adapter.addRecipe(newRecipe); // Metodă custom din adapter care sortează și notifică
        }
    }
}
