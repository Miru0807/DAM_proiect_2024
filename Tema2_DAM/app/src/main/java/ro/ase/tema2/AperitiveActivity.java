package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AperitiveActivity extends AppCompatActivity {
    private ListView lvAperitive;
    private Button btnBackToMain;
    private Button btnAddRecipe;
    private ArrayList<Recipe> aperitiveList;
    private ArrayAdapter<String> adapter;

    private static final int REQUEST_CODE_ADD_RECIPE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aperitive);

        lvAperitive = findViewById(R.id.lvAperitive);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnAddRecipe = findViewById(R.id.btnAddRecipe);

        aperitiveList = new ArrayList<>();
        aperitiveList.add(new Recipe("Aperitiv 1 - Bruschette", "Pâine, Roșii, Ulei de măsline, Busuioc", "1. Taie roșiile...\n2. Pregătește pâinea..."));
        aperitiveList.add(new Recipe("Aperitiv 2 - Ouă umplute", "Ouă, Maioneză, Muștar", "1. Fierbe ouăle...\n2. Umple ouăle cu amestecul..."));

        // Adapter doar pentru numele rețetelor
        ArrayList<String> recipeNames = new ArrayList<>();
        for (Recipe recipe : aperitiveList) {
            recipeNames.add(recipe.getName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeNames);
        lvAperitive.setAdapter(adapter);

        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(AperitiveActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(AperitiveActivity.this, AddRecipeDetailsActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_RECIPE);
        });

        lvAperitive.setOnItemClickListener((parent, view, position, id) -> {
            Recipe selectedRecipe = aperitiveList.get(position);
            Intent intent = new Intent(AperitiveActivity.this, MainRecipeDetailsActivity.class);
            intent.putExtra("recipeName", selectedRecipe.getName());
            intent.putExtra("ingredients", selectedRecipe.getIngredients());
            intent.putExtra("instructions", selectedRecipe.getInstructions());
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_RECIPE && resultCode == RESULT_OK && data != null) {
            String newRecipeName = data.getStringExtra("recipeName");
            String newIngredients = data.getStringExtra("ingredients");
            String newInstructions = data.getStringExtra("instructions");

            // Creează un obiect Recipe pentru noua rețetă și adaugă-l în listă
            Recipe newRecipe = new Recipe(newRecipeName, newIngredients, newInstructions);
            aperitiveList.add(newRecipe);

            // Actualizează lista afișată
            adapter.add(newRecipe.getName());
            adapter.notifyDataSetChanged();
        }
    }
}
