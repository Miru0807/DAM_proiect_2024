package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainRecipeDetailsActivity extends AppCompatActivity {
    private ListView lvRecipeDetails;
    private Button btnBackToMain;
    private String sourceActivity;
    private ArrayList<String> recipeDetailsList;
    private RecipeDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipe_details);

        lvRecipeDetails = findViewById(R.id.lvRecipeDetails);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        // Populează lista de detalii ale rețetei
        recipeDetailsList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String recipeName = extras.getString("recipeName");
            String ingredients = extras.getString("ingredients");
            String instructions = extras.getString("instructions");
            sourceActivity = extras.getString("sourceActivity");

            // Adaugă detalii în lista pentru afișare
            recipeDetailsList.add(recipeName);
            recipeDetailsList.add(ingredients);
            recipeDetailsList.add(instructions);
        }

        // Inițializează și setează adapterul pentru ListView
        adapter = new RecipeDetailsAdapter(this, recipeDetailsList);
        lvRecipeDetails.setAdapter(adapter);

        // Setează listener pentru butonul "Back"
        btnBackToMain.setOnClickListener(v -> {
            Intent intent;
            // Verifică activitatea sursă și redirecționează în funcție de aceasta
            switch (sourceActivity) {
                case "ReteteRapideActivity":
                    intent = new Intent(MainRecipeDetailsActivity.this, ReteteRapideActivity.class);
                    break;
                case "ReteteVegetarieneActivity":
                    intent = new Intent(MainRecipeDetailsActivity.this, ReteteVegetarieneActivity.class);
                    break;
                case "SalateActivity":
                    intent = new Intent(MainRecipeDetailsActivity.this, SalateActivity.class);
                    break;
                case "BauturiActivity":
                    intent = new Intent(MainRecipeDetailsActivity.this, BauturiActivity.class);
                    break;
                case "RetetePrincipaleActivity":
                    intent = new Intent(MainRecipeDetailsActivity.this, RetetePrincipaleActivity.class);
                    break;
                case "AperitiveActivity":
                    intent = new Intent(MainRecipeDetailsActivity.this, AperitiveActivity.class);
                    break;
                case "DeserturiActivity":
                    intent = new Intent(MainRecipeDetailsActivity.this, DeserturiActivity.class);
                    break;
                default:
                    intent = new Intent(MainRecipeDetailsActivity.this, MainActivity.class); // fallback la MainActivity
                    break;
            }
            startActivity(intent);
            finish(); // Opțional, închide activitatea curentă
        });
    }
}
