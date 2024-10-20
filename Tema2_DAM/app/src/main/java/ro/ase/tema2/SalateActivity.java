package ro.ase.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class SalateActivity extends AppCompatActivity {
    private ListView lvSalate;
    private Button btnBackToMain;
    private ArrayList<String> salateList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salate);

        // Inițializarea elementelor UI
        lvSalate = findViewById(R.id.lvSalate);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        // Popularea ListView cu rețete de salate
        salateList = new ArrayList<>();
        salateList.add("Salată 1 - Salată Caesar");
        salateList.add("Salată 2 - Salată de roșii cu brânză");
        salateList.add("Salată 3 - Salată de vinete");
        salateList.add("Salată 4 - Salată de cartofi");
        salateList.add("Salată 5 - Salată de varză");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, salateList);
        lvSalate.setAdapter(adapter);

        // Setează listener pentru butonul "Back"
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(SalateActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Opțional, pentru a închide activitatea curentă
        });

        // Setează listener pentru elementele din ListView
        lvSalate.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(SalateActivity.this, MainRecipeDetailsActivity.class);

            String recipeName = salateList.get(position);
            String ingredients = "";
            String instructions = "";

            // Setează ingredientele și instrucțiunile pentru fiecare salată
            switch (position) {
                case 0:
                    ingredients = "Salată verde, Pui, Crutoane, Parmezan, Sos Caesar";
                    instructions = "1. Amestecă salata cu puiul...\n2. Adaugă crutoanele și sosul...";
                    break;
                case 1:
                    ingredients = "Roșii, Brânză, Ulei de măsline, Sare, Piper";
                    instructions = "1. Taie roșiile...\n2. Adaugă brânza și uleiul de măsline...";
                    break;
                case 2:
                    ingredients = "Vinete, Ceapă, Ulei, Sare";
                    instructions = "1. Coace vinetele...\n2. Amestecă cu ceapa și uleiul...";
                    break;
                case 3:
                    ingredients = "Cartofi, Ceapă verde, Ulei de măsline, Sare";
                    instructions = "1. Fierbe cartofii...\n2. Taie ceapa și amestecă...";
                    break;
                case 4:
                    ingredients = "Varză, Ulei, Sare, Oțet";
                    instructions = "1. Taie varza fin...\n2. Amestecă cu ulei și oțet...";
                    break;
            }

            // Adaugă datele în intent
            intent.putExtra("recipeName", recipeName);
            intent.putExtra("ingredients", ingredients);
            intent.putExtra("instructions", instructions);

            // Adaugă activitatea sursă în intent
            intent.putExtra("sourceActivity", "SalateActivity");

            // Pornește activitatea pentru detalii
            startActivity(intent);
        });
    }
}
