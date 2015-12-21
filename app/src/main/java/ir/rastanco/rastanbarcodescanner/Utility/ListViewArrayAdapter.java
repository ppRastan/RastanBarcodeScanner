package ir.rastanco.rastanbarcodescanner.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import ir.rastanco.rastanbarcodescanner.R;

public class ListViewArrayAdapter extends ArrayAdapter<String> {
    private  Context context;
    private ArrayList<String> values;
    public ListViewArrayAdapter(Context context,ArrayList<String >values) {
        super(context, R.layout.listview_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.listview_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        CheckBox checkBox = (CheckBox)rowView.findViewById(R.id.checkbox);
        checkBox.setVisibility(View.INVISIBLE);
        textView.setText(values.get(position));
        String s = values.get(position);
        System.out.println(s);
        return rowView;
    }
}