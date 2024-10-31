package ro.ase.tema2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    private Context context;
    private ArrayList<Recipe> recipes;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        super(context, 0, recipes);
        this.context = context;
        this.recipes = recipes;
        sortRecipesByName(); // Sortează inițial lista alfabetic
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        Recipe recipe = recipes.get(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(recipe.getName());

        return convertView;
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        sortRecipesByName();
        notifyDataSetChanged();
    }

    private void sortRecipesByName() {
        Collections.sort(recipes, Comparator.comparing(Recipe::getName));
    }
}
