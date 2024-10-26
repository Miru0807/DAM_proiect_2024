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
    private ArrayList<String> aperitiveList;
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
        aperitiveList.add("Aperitiv 1 - Bruschette");
        aperitiveList.add("Aperitiv 2 - Ouă umplute");
        aperitiveList.add("Aperitiv 3 - Salată de vinete");
        aperitiveList.add("Aperitiv 4 - Rulouri cu șuncă");
        aperitiveList.add("Aperitiv 5 - Platou de brânzeturi");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, aperitiveList);
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
            Intent intent = new Intent(AperitiveActivity.this, MainRecipeDetailsActivity.class);

            String recipeName = aperitiveList.get(position);
            String ingredients = "";
            String instructions = "";

            // Setează ingredientele și instrucțiunile pentru fiecare aperitiv
            switch (position) {
                case 0:
                    ingredients = "Pâine, Roșii, Ulei de măsline, Busuioc";
                    instructions = "1. Taie roșiile...\n2. Pregătește pâinea...";
                    break;
                case 1:
                    ingredients = "Ouă, Maioneză, Muștar";
                    instructions = "1. Fierbe ouăle...\n2. Umple ouăle cu amestecul...";
                    break;
                case 2:
                    ingredients = "Vinete, Ceapă, Ulei";
                    instructions = "1. Coace vinetele...\n2. Amestecă cu ceapă...";
                    break;
                case 3:
                    ingredients = "Șuncă, Cremă de brânză, Castraveți";
                    instructions = "1. Rulează șunca cu crema de brânză...\n2. Adaugă castraveți...";
                    break;
                case 4:
                    ingredients = "Brânzeturi diverse, Struguri, Măsline";
                    instructions = "1. Așază brânzeturile pe un platou...\n2. Adaugă struguri și măsline...";
                    break;
            }

            // Adaugă datele în intent
            intent.putExtra("recipeName", recipeName);
            intent.putExtra("ingredients", ingredients);
            intent.putExtra("instructions", instructions);
            // Adaugă activitatea sursă în intent
            intent.putExtra("sourceActivity", "AperitiveActivity");

            // Pornește activitatea pentru detalii
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_RECIPE && resultCode == RESULT_OK && data != null) {
            // Preia datele trimise de AddRecipeDetailsActivity
            String newRecipeName = data.getStringExtra("recipeName");
            aperitiveList.add(newRecipeName); // Adaugă rețeta nouă în listă
            adapter.notifyDataSetChanged(); // Actualizează ListView-ul
        }
    }
}
