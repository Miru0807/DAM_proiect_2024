package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;
;import java.util.ArrayList;

public class ReteteVegetarieneActivity extends AppCompatActivity {
    private ListView lvReteteVegetariene;
    private Button btnBackToMain;
    private ArrayList<String> vegetarieneList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retete_vegetariene);

        // Inițializarea elementelor UI
        lvReteteVegetariene = findViewById(R.id.lvReteteVegetariene);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        // Popularea ListView cu rețete vegetariene
        vegetarieneList = new ArrayList<>();
        vegetarieneList.add("Rețetă 1 - Salată de avocado");
        vegetarieneList.add("Rețetă 2 - Tofu prăjit cu legume");
        vegetarieneList.add("Rețetă 3 - Falafel");
        vegetarieneList.add("Rețetă 4 - Supă de linte");
        vegetarieneList.add("Rețetă 5 - Mâncare de spanac");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vegetarieneList);
        lvReteteVegetariene.setAdapter(adapter);

        // Setează listener pentru butonul "Back"
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(ReteteVegetarieneActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Setează listener pentru elementele din ListView
        lvReteteVegetariene.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ReteteVegetarieneActivity.this, MainRecipeDetailsActivity.class);

            String recipeName = vegetarieneList.get(position);
            String ingredients = "";
            String instructions = "";

            // Setează ingredientele și instrucțiunile pentru fiecare rețetă
            switch (position) {
                case 0:
                    ingredients = "Avocado, Roșii, Lămâie, Ulei de măsline";
                    instructions = "1. Taie avocado...\n2. Amestecă cu roșiile și uleiul de măsline...";
                    break;
                case 1:
                    ingredients = "Tofu, Broccoli, Morcovi, Sos de soia";
                    instructions = "1. Prăjește tofu...\n2. Adaugă legumele...";
                    break;
                case 2:
                    ingredients = "Năut, Făină, Ceapă, Condimente";
                    instructions = "1. Amestecă ingredientele...\n2. Formează bile și prăjește...";
                    break;
                case 3:
                    ingredients = "Linte, Morcovi, Ceapă, Usturoi";
                    instructions = "1. Fierbe lintea...\n2. Adaugă restul ingredientelor...";
                    break;
                case 4:
                    ingredients = "Spanac, Usturoi, Sare, Piper";
                    instructions = "1. Fierbe spanacul...\n2. Călește cu usturoi...";
                    break;
            }

            // Adaugă datele în intent
            intent.putExtra("recipeName", recipeName);
            intent.putExtra("ingredients", ingredients);
            intent.putExtra("instructions", instructions);
            intent.putExtra("sourceActivity", "ReteteVegetarieneActivity");

            // Pornește activitatea pentru detalii
            startActivity(intent);
        });


    }
}
