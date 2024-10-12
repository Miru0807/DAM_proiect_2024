package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText recipeNameEditText, ingredientsEditText, instructionsEditText;
    private Spinner categorySpinner;
    private Switch vegetarianSwitch;
    private CheckBox glutenFreeCheckBox;
    private RatingBar difficultyRatingBar;
    private SeekBar spicinessSeekBar;
    private Button saveRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);


        recipeNameEditText = findViewById(R.id.recipeNameEditText);
        ingredientsEditText = findViewById(R.id.ingredientsEditText);
        instructionsEditText = findViewById(R.id.instructionsEditText);
        categorySpinner = findViewById(R.id.categorySpinner);
        vegetarianSwitch = findViewById(R.id.vegetarianSwitch);
        glutenFreeCheckBox = findViewById(R.id.glutenFreeCheckBox);
        difficultyRatingBar = findViewById(R.id.difficultyRatingBar);
        spicinessSeekBar = findViewById(R.id.spicinessSeekBar);
        saveRecipeButton = findViewById(R.id.saveRecipeButton);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.recipe_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);


        saveRecipeButton.setOnClickListener(v -> {
            String recipeName = recipeNameEditText.getText().toString();
            String ingredients = ingredientsEditText.getText().toString();
            String instructions = instructionsEditText.getText().toString();
            String category = categorySpinner.getSelectedItem().toString();
            boolean isVegetarian = vegetarianSwitch.isChecked();
            boolean isGlutenFree = glutenFreeCheckBox.isChecked();
            float difficulty = difficultyRatingBar.getRating();
            int spiciness = spicinessSeekBar.getProgress();

            if (recipeName.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
                Toast.makeText(AddRecipeActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            } else {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("recipeName", recipeName);
                resultIntent.putExtra("ingredients", ingredients);
                resultIntent.putExtra("instructions", instructions);
                resultIntent.putExtra("category", category);
                resultIntent.putExtra("isVegetarian", isVegetarian);
                resultIntent.putExtra("isGlutenFree", isGlutenFree);
                resultIntent.putExtra("difficulty", difficulty);
                resultIntent.putExtra("spiciness", spiciness);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
