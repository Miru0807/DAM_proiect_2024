package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ReteteRapideActivity extends AppCompatActivity {

    private ListView lvReteteRapide;
    private Button btnBackToMain;
    private ArrayList<String> rapideList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retete_rapide);

        // Inițializarea elementelor UI
        lvReteteRapide = findViewById(R.id.lvReteteRapide);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        // Popularea ListView cu rețete rapide
        rapideList = new ArrayList<>();
        rapideList.add("Rețetă 1 - Sandviș cu avocado");
        rapideList.add("Rețetă 2 - Omletă cu brânză");
        rapideList.add("Rețetă 3 - Salată rapidă de roșii");
        rapideList.add("Rețetă 4 - Pâine prăjită cu unt de arahide");
        rapideList.add("Rețetă 5 - Smoothie rapid cu fructe");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rapideList);
        lvReteteRapide.setAdapter(adapter);

        // Setează listener pentru butonul "Back"
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(ReteteRapideActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Setează listener pentru elementele din ListView
        lvReteteRapide.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ReteteRapideActivity.this, MainRecipeDetailsActivity.class);

            String recipeName = rapideList.get(position);
            String ingredients = "";
            String instructions = "";

            // Setează ingredientele și instrucțiunile pentru fiecare rețetă
            switch (position) {
                case 0:
                    ingredients = "Avocado, Pâine, Lămâie, Sare";
                    instructions = "1. Taie avocado...\n2. Întinde pe pâine...";
                    break;
                case 1:
                    ingredients = "Ouă, Brânză, Sare, Piper";
                    instructions = "1. Bate ouăle...\n2. Gătește cu brânza...";
                    break;
                case 2:
                    ingredients = "Roșii, Ulei de măsline, Oțet, Sare";
                    instructions = "1. Taie roșiile...\n2. Adaugă ulei și oțet...";
                    break;
                case 3:
                    ingredients = "Pâine, Unt de arahide, Miere";
                    instructions = "1. Prăjește pâinea...\n2. Întinde untul de arahide...";
                    break;
                case 4:
                    ingredients = "Fructe, Iaurt, Miere";
                    instructions = "1. Amestecă fructele cu iaurtul...\n2. Adaugă mierea...";
                    break;
            }

            // Adaugă datele în intent
            intent.putExtra("recipeName", recipeName);
            intent.putExtra("ingredients", ingredients);
            intent.putExtra("instructions", instructions);
            intent.putExtra("sourceActivity", "ReteteRapideActivity");

            // Pornește activitatea pentru detalii
            startActivity(intent);
        });
    }
}
