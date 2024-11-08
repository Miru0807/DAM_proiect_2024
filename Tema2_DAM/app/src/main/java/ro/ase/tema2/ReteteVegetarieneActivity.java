package ro.ase.tema2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ReteteVegetarieneActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;

    private ListView lvReteteVegetariene;
    private Button btnBackToMain, btnAddRecipe;
    private ArrayList<String> vegetarieneList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retete_vegetariene);

        // Inițializarea elementelor UI
        lvReteteVegetariene = findViewById(R.id.lvReteteVegetariene);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnAddRecipe = findViewById(R.id.btnAddRecipe);

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

        // Buton pentru adăugarea unei rețete noi
        btnAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(ReteteVegetarieneActivity.this, AddEditRecipeActivity.class);
            intent.putExtra("isEditMode", false);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });

        // Setează listener pentru elementele din ListView
        lvReteteVegetariene.setOnItemClickListener((parent, view, position, id) -> {
            // Afișează dialogul pentru a alege între vizualizare și editare
            AlertDialog.Builder builder = new AlertDialog.Builder(ReteteVegetarieneActivity.this);
            builder.setTitle("Alege acțiunea")
                    .setMessage("Dorești să vizualizezi sau să editezi această rețetă?")
                    .setPositiveButton("Vizualizează", (dialog, which) -> {
                        // Intent pentru vizualizarea detaliilor rețetei
                        Intent viewIntent = new Intent(ReteteVegetarieneActivity.this, MainRecipeDetailsActivity.class);

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
                        viewIntent.putExtra("recipeName", recipeName);
                        viewIntent.putExtra("ingredients", ingredients);
                        viewIntent.putExtra("instructions", instructions);
                        viewIntent.putExtra("sourceActivity", "ReteteVegetarieneActivity");

                        // Pornește activitatea pentru vizualizarea detaliilor
                        startActivity(viewIntent);
                    })
                    .setNegativeButton("Editează", (dialog, which) -> {
                        // Intent pentru editarea rețetei
                        Intent editIntent = new Intent(ReteteVegetarieneActivity.this, AddEditRecipeActivity.class);
                        editIntent.putExtra("isEditMode", true);
                        editIntent.putExtra("recipeName", vegetarieneList.get(position));
                        editIntent.putExtra("position", position);

                        // Pornește activitatea pentru editare
                        startActivityForResult(editIntent, REQUEST_CODE_EDIT);
                    })
                    .setNeutralButton("Anulează", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE_ADD) {
                // Adăugare rețetă nouă
                String newRecipeName = data.getStringExtra("recipeName");
                if (newRecipeName != null) {
                    vegetarieneList.add(newRecipeName);
                    adapter.notifyDataSetChanged();
                }
            } else if (requestCode == REQUEST_CODE_EDIT) {
                // Editare rețetă existentă
                int position = data.getIntExtra("position", -1);
                String updatedRecipeName = data.getStringExtra("recipeName");
                if (position != -1 && updatedRecipeName != null) {
                    vegetarieneList.set(position, updatedRecipeName);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
