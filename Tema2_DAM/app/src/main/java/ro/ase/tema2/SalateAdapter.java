package ro.ase.tema2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SalateAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> salateList;

    public SalateAdapter(Context context, ArrayList<String> salateList) {
        this.context = context;
        this.salateList = salateList;
    }

    @Override
    public int getCount() {
        return salateList.size();
    }

    @Override
    public Object getItem(int position) {
        return salateList.get(position);
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

        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        String salata = salateList.get(position);

        // Aplică regulile de formatare
        // Regula 1: Transformă textul în majuscule
        textView.setText(salata.toUpperCase());

        // Regula 2: Setează culoarea textului la verde dacă salata conține "varză"
        if (salata.toLowerCase().contains("varză")) {
            textView.setTextColor(Color.GREEN);
        } else {
            textView.setTextColor(Color.BLACK);
        }

        // Regula 3: Setează textul în bold pentru pozițiile impare
        if (position % 2 == 1) {
            textView.setTypeface(null, Typeface.BOLD);
        } else {
            textView.setTypeface(null, Typeface.NORMAL);
        }

        return convertView;
    }
}
