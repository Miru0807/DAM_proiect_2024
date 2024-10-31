package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class DeserturiActivity extends AppCompatActivity {
    private ListView lvDeserturi;
    private Button btnBackToMain;
    private ArrayList<String> deserturiList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deserturi);

        // Inițializarea elementelor UI
        lvDeserturi = findViewById(R.id.lvDeserturi);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        // Popularea ListView cu deserturi
        deserturiList = new ArrayList<>();
        deserturiList.add("Desert 1 - Tiramisu");
        deserturiList.add("Desert 2 - Panna Cotta");
        deserturiList.add("Desert 3 - Clătite");
        deserturiList.add("Desert 4 - Chec");
        deserturiList.add("Desert 5 - Tort de ciocolată");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deserturiList);
        lvDeserturi.setAdapter(adapter);

        // Setează listener pentru butonul "Back"
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(DeserturiActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Opțional, pentru a închide activitatea curentă
        });

        // Setează listener pentru elementele din ListView
        lvDeserturi.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(DeserturiActivity.this, MainRecipeDetailsActivity.class);

            String recipeName = deserturiList.get(position);
            String ingredients = "";
            String instructions = "";

            // Setează ingredientele și instrucțiunile pentru fiecare desert
            switch (position) {
                case 0:
                    ingredients = "Cafea, Mascarpone, Ouă, Zahăr, Pișcoturi";
                    instructions = "1. Prepară cafeaua...\n2. Amestecă mascarpone...";
                    break;
                case 1:
                    ingredients = "Frișcă, Lapte, Zahăr, Vanilie";
                    instructions = "1. Încălzește laptele...\n2. Adaugă vanilia...";
                    break;
                case 2:
                    ingredients = "Făină, Ouă, Lapte, Unt";
                    instructions = "1. Amestecă ingredientele...\n2. Prăjește clătitele...";
                    break;
                case 3:
                    ingredients = "Făină, Zahăr, Ouă, Unt";
                    instructions = "1. Amestecă ingredientele...\n2. Coace checul...";
                    break;
                case 4:
                    ingredients = "Ciocolată, Frișcă, Ouă, Făină";
                    instructions = "1. Topeste ciocolata...\n2. Prepară compoziția...";
                    break;
            }

            // Adaugă datele în intent
            intent.putExtra("recipeName", recipeName);
            intent.putExtra("ingredients", ingredients);
            intent.putExtra("instructions", instructions);
            // Adaugă activitatea sursă în intent
            intent.putExtra("sourceActivity", "DeserturiActivity");
            // Pornește activitatea pentru detalii
            startActivity(intent);
        });
    }
}
