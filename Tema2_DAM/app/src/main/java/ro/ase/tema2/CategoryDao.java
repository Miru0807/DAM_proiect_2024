package ro.ase.tema2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface CategoryDao {

    // Inserează o categorie în tabel
    @Insert
    void insertCategory(Category category);

    // Returnează toate categoriile
    @Query("SELECT * FROM category")
    List<Category> getAllCategories();

    // Returnează o categorie specifică pe baza ID-ului
    @Query("SELECT * FROM category WHERE id = :id")
    Category getCategoryById(int id);

    // Șterge o categorie specifică
    @Delete
    void deleteCategory(Category category);

    // Șterge toate categoriile din tabel
    @Query("DELETE FROM category")
    void deleteAllCategories();
}
