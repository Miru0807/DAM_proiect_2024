package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailsActivity extends AppCompatActivity {

    private TextView recipeNameTextView, ingredientsTextView, instructionsTextView;
    private Button backButton;
    private ImageView recipeImageView;
    private RatingBar ratingBar;
    private Switch favoriteSwitch;
    private SeekBar spicyLevelSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipeNameTextView = findViewById(R.id.recipeNameTextView);
        ingredientsTextView = findViewById(R.id.ingredientsTextView);
        instructionsTextView = findViewById(R.id.instructionsTextView);
        recipeImageView = findViewById(R.id.recipeImageView);
        ratingBar = findViewById(R.id.ratingBar);
        favoriteSwitch = findViewById(R.id.favoriteSwitch);
        spicyLevelSeekBar = findViewById(R.id.spicyLevelSeekBar);
        backButton = findViewById(R.id.backButton);

        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipeName");

        recipeNameTextView.setText(recipeName);
        ingredientsTextView.setText("Ingredients for " + recipeName);
        instructionsTextView.setText("Instructions for " + recipeName);

        recipeImageView.setImageResource(R.drawable.ic_launcher_foreground);

        ratingBar.setRating(3.5f);
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                Toast.makeText(RecipeDetailsActivity.this, "New rating: " + rating, Toast.LENGTH_SHORT).show();
                System.out.println("New rating: " + rating);
            }
        });

        favoriteSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(RecipeDetailsActivity.this, "Recipe marked as favorite.", Toast.LENGTH_SHORT).show();
                System.out.println("Recipe marked as favorite.");
            } else {
                Toast.makeText(RecipeDetailsActivity.this, "Recipe unmarked as favorite.", Toast.LENGTH_SHORT).show();
                System.out.println("Recipe unmarked as favorite.");
            }
        });

        spicyLevelSeekBar.setMax(10);
        spicyLevelSeekBar.setProgress(3);
        spicyLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    Toast.makeText(RecipeDetailsActivity.this, "Spiciness level: " + progress, Toast.LENGTH_SHORT).show();
                    System.out.println("Spiciness level: " + progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(RecipeDetailsActivity.this, "Start adjusting spiciness level.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(RecipeDetailsActivity.this, "Final spiciness level: " + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> {
            Toast.makeText(RecipeDetailsActivity.this, "Returning to recipe list.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
