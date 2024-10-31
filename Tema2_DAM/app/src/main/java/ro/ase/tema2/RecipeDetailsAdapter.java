package ro.ase.tema2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RecipeDetailsAdapter extends BaseAdapter {
    private Context context;
    private List<String> detailsList;

    public RecipeDetailsAdapter(Context context, List<String> detailsList) {
        this.context = context;
        this.detailsList = detailsList;
    }

    @Override
    public int getCount() {
        return detailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return detailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        String detail = detailsList.get(position);

        // Aplică reguli de formatare
        if (position == 0) {
            // Regula 1: Titlul rețetei, setat cu font mare și îngroșat
            textView.setTextSize(24);
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
        } else if (position == 1) {
            // Regula 2: Titlul secțiunii de ingrediente
            textView.setText("Ingrediente: " + detail);
            textView.setTextSize(18);
            textView.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        } else if (position == 2) {
            // Regula 3: Titlul secțiunii de instrucțiuni
            textView.setText("Instrucțiuni: " + detail);
            textView.setTextSize(18);
            textView.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        }

        return convertView;
    }
}
