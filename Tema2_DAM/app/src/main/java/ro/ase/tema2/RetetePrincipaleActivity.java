package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class RetetePrincipaleActivity extends AppCompatActivity {

    private ListView lvMainRecipes;
    private Button btnBack;
    private ArrayList<String> recipesList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retete_principale);

        // Inițializarea elementelor UI
        lvMainRecipes = findViewById(R.id.lvMainRecipes);
        btnBack = findViewById(R.id.btnBack);

        // Popularea ListView cu rețete
        recipesList = new ArrayList<>();
        recipesList.add("Friptură de pui");
        recipesList.add("Paste Carbonara");
        recipesList.add("Supă de legume");
        recipesList.add("Sarmale");
        recipesList.add("Tocăniță de cartofi");


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipesList);
        lvMainRecipes.setAdapter(adapter);

        // Setează listener pentru selectarea unei rețete
        lvMainRecipes.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(RetetePrincipaleActivity.this, MainRecipeDetailsActivity.class);

            String recipeName = recipesList.get(position);
            String ingredients = "";
            String instructions = "";

            // Setează ingredientele și instrucțiunile pentru fiecare rețetă
            switch (position) {
                case 0:
                    ingredients = "Pui, Ulei, Sare, Piper";
                    instructions = "1. Taie puiul...\n2. Prajește în ulei...";
                    break;
                case 1:
                    ingredients = "Paste, Bacon, Ouă, Parmezan";
                    instructions = "1. Fierbe pastele...\n2. Amestecă bacon cu ouă...";
                    break;
                case 2:
                    ingredients = "Legume, Sare, Piper, Apă";
                    instructions = "1. Taie legumele...\n2. Fierbe legumele...";
                    break;
                case 3:
                    ingredients = "Carne de porc, Orez, Foi de varză";
                    instructions = "1. Pregătește umplutura...\n2. Răsucește foile...";
                    break;
                case 4:
                    ingredients = "Cartofi, Ceapă, Ulei, Sare";
                    instructions = "1. Curăță cartofii...\n2. Gătește cartofii în tigaie...";
                    break;
            }

            // Adaugă datele în intent
            intent.putExtra("recipeName", recipeName);
            intent.putExtra("ingredients", ingredients);
            intent.putExtra("instructions", instructions);
            // Adaugă activitatea sursă în intent
            intent.putExtra("sourceActivity", "RetetePrincipaleActivity");
            // Pornește activitatea pentru detalii
            startActivity(intent);
        });



        // Setează listener pentru butonul de "Back"
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Revino la MainActivity
                Intent intent = new Intent(RetetePrincipaleActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Opțional, pentru a închide activitatea curentă
            }
        });
    }
}
