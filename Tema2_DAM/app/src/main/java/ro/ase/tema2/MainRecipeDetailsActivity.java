package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainRecipeDetailsActivity extends AppCompatActivity {
    private TextView tvRecipeName, tvIngredients, tvInstructions;
    private Button btnBackToMain;
    private String sourceActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipe_details);

        tvRecipeName = findViewById(R.id.tvRecipeName);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvInstructions = findViewById(R.id.tvInstructions);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String recipeName = extras.getString("recipeName");
            String ingredients = extras.getString("ingredients");
            String instructions = extras.getString("instructions");
            sourceActivity = extras.getString("sourceActivity");

            tvRecipeName.setText(recipeName);
            tvIngredients.setText("Ingrediente: " + ingredients);
            tvInstructions.setText("Instrucțiuni: " + instructions);
        }

        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(MainRecipeDetailsActivity.this, AperitiveActivity.class);
            startActivity(intent);
            finish();
        });

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
