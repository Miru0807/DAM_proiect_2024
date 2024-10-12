package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView recipesListView;
    private Button addRecipeButton;
    private ArrayList<String> recipesList;
    private ArrayAdapter<String> adapter;

    private ImageView recipeImageView;
    private CheckBox vegetarianCheckBox;
    private RadioButton easyRadioButton;
    private SeekBar difficultySeekBar;

    private boolean filterVegetarian = false;  // variabilă pentru filtrarea vegetariană
    private boolean filterEasy = false;  // variabilă pentru filtrarea după dificultate
    private int currentDifficulty = 0; // Dificultatea curentă selectată

    private ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String newRecipe = data.getStringExtra("recipeName");
                        String ingredients = data.getStringExtra("ingredients");
                        String instructions = data.getStringExtra("instructions");
                        String category = data.getStringExtra("category");
                        boolean isVegetarian = data.getBooleanExtra("isVegetarian", false);
                        boolean isGlutenFree = data.getBooleanExtra("isGlutenFree", false);
                        float difficulty = data.getFloatExtra("difficulty", 0);
                        int spiciness = data.getIntExtra("spiciness", 0);

                        recipesList.add(newRecipe + " - " + category + " (Vegetarian: " + isVegetarian + ", Gluten Free: " + isGlutenFree + ", Difficulty: " + difficulty + ")");
                        adapter.notifyDataSetChanged();

                        Toast.makeText(MainActivity.this, "Recipe '" + newRecipe + "' added!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesListView = findViewById(R.id.recipesListView);
        addRecipeButton = findViewById(R.id.addRecipeButton);
        recipeImageView = findViewById(R.id.recipeImageView);
        vegetarianCheckBox = findViewById(R.id.vegetarianCheckBox);
        easyRadioButton = findViewById(R.id.easyRadioButton);
        difficultySeekBar = findViewById(R.id.difficultySeekBar);

        recipesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipesList);
        recipesListView.setAdapter(adapter);

        addRecipeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
            startForResult.launch(intent);
        });

        recipesListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
            intent.putExtra("recipeName", recipesList.get(position));
            startActivity(intent);
        });

        vegetarianCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            filterVegetarian = isChecked;
            if (isChecked) {
                Toast.makeText(MainActivity.this, "Filter: Vegetarian Recipes Only", Toast.LENGTH_SHORT).show();
                filterRecipes();
            } else {
                Toast.makeText(MainActivity.this, "Filter: All Recipes", Toast.LENGTH_SHORT).show();
                filterRecipes();
            }
        });

        easyRadioButton.setOnClickListener(v -> {
            filterEasy = easyRadioButton.isChecked();
            if (filterEasy) {
                Toast.makeText(MainActivity.this, "Filter: Easy Recipes", Toast.LENGTH_SHORT).show();
                filterRecipes();
            }
        });

        difficultySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentDifficulty = progress;
                Toast.makeText(MainActivity.this, "Selected Difficulty: " + currentDifficulty, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "Adjusting Difficulty...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "Filter: Difficulty " + currentDifficulty + " applied", Toast.LENGTH_SHORT).show();
                filterRecipes();
            }
        });
    }

    private void filterRecipes() {
        ArrayList<String> filteredRecipes = new ArrayList<>();

        for (String recipe : recipesList) {
            if (filterVegetarian && !recipe.contains("Vegetarian: true")) {
                continue;
            }
            if (filterEasy && recipe.contains("Difficulty: Hard")) {
                continue;
            }
            if (recipe.contains("Difficulty: " + currentDifficulty)) {
                filteredRecipes.add(recipe);
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredRecipes);
        recipesListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
