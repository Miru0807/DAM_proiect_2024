package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BauturiActivity extends AppCompatActivity {
    private ListView lvBauturi;
    private Button btnBackToMain;
    private Button btnAddRecipe;
    private ArrayList<Recipe> bauturiList;
    private ArrayAdapter<String> adapter;

    private static final int REQUEST_CODE_ADD_RECIPE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bauturi);

        lvBauturi = findViewById(R.id.lvBauturi);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnAddRecipe = findViewById(R.id.btnAddRecipe); // Inițializare buton de adăugare

        bauturiList = new ArrayList<>();
        bauturiList.add(new Recipe("Băutură 1 - Limonadă", "Lămâi, Apă, Zahăr, Mentă", "1. Stoarce lămâile...\n2. Amestecă apa cu zahărul și menta..."));
        bauturiList.add(new Recipe("Băutură 2 - Smoothie cu banane", "Banane, Lapte, Miere", "1. Amestecă bananele cu laptele...\n2. Adaugă mierea..."));

        ArrayList<String> recipeNames = new ArrayList<>();
        for (Recipe recipe : bauturiList) {
            recipeNames.add(recipe.getName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeNames);
        lvBauturi.setAdapter(adapter);

        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(BauturiActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Deschide `AddRecipeDetailsActivity` pentru a adăuga o băutură nouă
        btnAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(BauturiActivity.this, AddRecipeDetailsActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_RECIPE);
        });

        // Deschide `MainRecipeDetailsActivity` pentru detaliile rețetei selectate
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
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_RECIPE && resultCode == RESULT_OK && data != null) {
            String newRecipeName = data.getStringExtra("recipeName");
            String newIngredients = data.getStringExtra("ingredients");
            String newInstructions = data.getStringExtra("instructions");

            // Creează și adaugă rețeta nouă în listă
            Recipe newRecipe = new Recipe(newRecipeName, newIngredients, newInstructions);
            bauturiList.add(newRecipe);

            // Actualizează lista afișată în `ListView`
            adapter.add(newRecipe.getName());
            adapter.notifyDataSetChanged();
        }
    }
}
