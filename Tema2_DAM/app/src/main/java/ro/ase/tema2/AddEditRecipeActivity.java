package ro.ase.tema2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditRecipeActivity extends AppCompatActivity {

    private EditText etRecipeName, etIngredients, etInstructions;
    private Button btnSave;
    private boolean isEditMode;
    private int recipePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_recipe);

        etRecipeName = findViewById(R.id.etRecipeName);
        etIngredients = findViewById(R.id.etIngredients);
        etInstructions = findViewById(R.id.etInstructions);
        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);
        recipePosition = intent.getIntExtra("position", -1);

        if (isEditMode && recipePosition != -1) {
            etRecipeName.setText(intent.getStringExtra("recipeName"));
            etIngredients.setText(intent.getStringExtra("ingredients"));
            etInstructions.setText(intent.getStringExtra("instructions"));
        }

        btnSave.setOnClickListener(v -> saveRecipe());
    }

    private void saveRecipe() {
        String recipeName = etRecipeName.getText().toString();
        String ingredients = etIngredients.getText().toString();
        String instructions = etInstructions.getText().toString();

        if (recipeName.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("recipeName", recipeName);
        resultIntent.putExtra("ingredients", ingredients);
        resultIntent.putExtra("instructions", instructions);
        resultIntent.putExtra("isEditMode", isEditMode);
        resultIntent.putExtra("position", recipePosition);

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
