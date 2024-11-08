package ro.ase.tema2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchTheme, switchNotifications, switchLanguage;
    private Button btnBackToPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize switches
        switchTheme = findViewById(R.id.switchTheme);
        switchNotifications = findViewById(R.id.switchNotifications);
        switchLanguage = findViewById(R.id.switchLanguage);
        btnBackToPrevious = findViewById(R.id.btnBackToPrevious);

        // Get SharedPreferences
        SharedPreferences prefs = getSharedPreferences("AppConfig", MODE_PRIVATE);

        // Load the saved settings into the switches
        switchTheme.setChecked(prefs.getBoolean("darkTheme", false));
        switchNotifications.setChecked(prefs.getBoolean("notificationsEnabled", true));
        switchLanguage.setChecked(prefs.getBoolean("languageEnglish", true));

        // Set listeners to update SharedPreferences when switches are toggled
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("darkTheme", isChecked);
            editor.apply();

            // Restart ReteteRapideActivity to apply the new theme
            Intent intent = new Intent(SettingsActivity.this, ReteteRapideActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("notificationsEnabled", isChecked);
            editor.apply();
        });

        switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("languageEnglish", isChecked);
            editor.apply();
        });

        // Set listener for Back button
        btnBackToPrevious.setOnClickListener(v -> finish()); // Close this activity and return to the previous one
    }
}
