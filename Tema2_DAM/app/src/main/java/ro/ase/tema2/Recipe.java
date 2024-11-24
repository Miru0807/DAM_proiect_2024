package ro.ase.tema2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(
        foreignKeys = @ForeignKey(
                entity = Category.class, // Entitatea asociată
                parentColumns = "id",    // Coloana din tabelul `categories`
                childColumns = "categoryId", // Coloana din tabelul `recipes`
                onDelete = ForeignKey.CASCADE // Șterge rețetele dacă categoria este ștearsă
        )
)
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "ingredients")
    private String ingredients;

    @ColumnInfo(name = "instructions")
    private String instructions;

    @ColumnInfo(name = "categoryId", index = true) // Index pentru performanță
    private int categoryId;

    public Recipe(String name, String ingredients, String instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Recipe(String name, String ingredients, String instructions, int category) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.categoryId = category;
    }


    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
