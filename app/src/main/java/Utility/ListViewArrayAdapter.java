package Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.rastanco.rastanbarcodescanner.R;

public class ListViewArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;
    private CheckBox checkBox_listview_item;
    private  TextView textView_listveiw_item;
    public ListViewArrayAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.listview_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.listview_item, parent, false);
        textView_listveiw_item = (TextView) rowView.findViewById(R.id.label);
        checkBox_listview_item = (CheckBox)rowView.findViewById(R.id.checkbox);
        return rowView;
    }

}