package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class BauturiActivity extends AppCompatActivity {
    private ListView lvBauturi;
    private Button btnBackToMain;
    private ArrayList<String> bauturiList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bauturi);

        // Inițializarea elementelor UI
        lvBauturi = findViewById(R.id.lvBauturi);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        // Popularea ListView cu băuturi și smoothie-uri
        bauturiList = new ArrayList<>();
        bauturiList.add("Băutură 1 - Limonadă");
        bauturiList.add("Băutură 2 - Smoothie cu banane");
        bauturiList.add("Băutură 3 - Ceai verde");
        bauturiList.add("Băutură 4 - Frappé");
        bauturiList.add("Băutură 5 - Smoothie cu fructe de pădure");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bauturiList);
        lvBauturi.setAdapter(adapter);

        // Setează listener pentru butonul "Back"
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(BauturiActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Opțional, pentru a închide activitatea curentă
        });

        // Setează listener pentru elementele din ListView
        lvBauturi.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(BauturiActivity.this, MainRecipeDetailsActivity.class);

            String recipeName = bauturiList.get(position);
            String ingredients = "";
            String instructions = "";

            // Setează ingredientele și instrucțiunile pentru fiecare băutură și smoothie
            switch (position) {
                case 0:
                    ingredients = "Lămâi, Apă, Zahăr, Mentă";
                    instructions = "1. Stoarce lămâile...\n2. Amestecă apa cu zahărul și menta...";
                    break;
                case 1:
                    ingredients = "Banane, Lapte, Miere";
                    instructions = "1. Amestecă bananele cu laptele...\n2. Adaugă mierea...";
                    break;
                case 2:
                    ingredients = "Ceai verde, Miere, Lămâie";
                    instructions = "1. Fierbe apa...\n2. Adaugă ceaiul verde și mierea...";
                    break;
                case 3:
                    ingredients = "Cafea, Lapte, Gheață";
                    instructions = "1. Amestecă cafeaua cu laptele...\n2. Adaugă gheața...";
                    break;
                case 4:
                    ingredients = "Fructe de pădure, Iaurt, Miere";
                    instructions = "1. Amestecă fructele de pădure cu iaurtul...\n2. Adaugă mierea...";
                    break;
            }

            // Adaugă datele în intent
            intent.putExtra("recipeName", recipeName);
            intent.putExtra("ingredients", ingredients);
            intent.putExtra("instructions", instructions);

            // Adaugă activitatea sursă în intent
            intent.putExtra("sourceActivity", "BauturiActivity");

            // Pornește activitatea pentru detalii
            startActivity(intent);
        });
    }
}
