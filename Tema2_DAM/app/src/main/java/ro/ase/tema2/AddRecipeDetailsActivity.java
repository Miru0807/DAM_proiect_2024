package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeDetailsActivity extends AppCompatActivity {
    private EditText etRecipeName;
    private EditText etIngredients;
    private EditText etInstructions;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_details);

        // Inițializare UI
        etRecipeName = findViewById(R.id.etRecipeName);
        etIngredients = findViewById(R.id.etIngredients);
        etInstructions = findViewById(R.id.etInstructions);
        btnBack = findViewById(R.id.btnBack);

        // Setează listener pentru butonul "Back"
        btnBack.setOnClickListener(v -> {
            String recipeName = etRecipeName.getText().toString().trim();
            String ingredients = etIngredients.getText().toString().trim();
            String instructions = etInstructions.getText().toString().trim();

            // Creează un intent cu datele rețetei
            Intent resultIntent = new Intent();
            resultIntent.putExtra("recipeName", recipeName);
            resultIntent.putExtra("ingredients", ingredients);
            resultIntent.putExtra("instructions", instructions);

            // Setează rezultatul și finalizează activitatea
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
