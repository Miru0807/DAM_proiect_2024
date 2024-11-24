package ro.ase.tema2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

// Declarația bazei de date Room
@Database(entities = {Recipe.class, Category.class}, version = 2) // Versiunea bazei de date
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance; // Singleton pentru baza de date

    // DAO-urile declarate
    public abstract RecipeDao recipeDao();
    public abstract CategoryDao categoryDao();

    // Metoda pentru obținerea instanței singleton
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "database_name" // Numele bazei de date
                    )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()// Permite recrearea bazei de date dacă schema se schimbă
                    .build();
        }
        return instance;
    }

    // Exemplar de migrație pentru versiunea 1 -> 2
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Exemplu: adaugă o coloană nouă în tabelul recipes
            database.execSQL("ALTER TABLE recipes ADD COLUMN new_column_name TEXT");
        }
    };
}
