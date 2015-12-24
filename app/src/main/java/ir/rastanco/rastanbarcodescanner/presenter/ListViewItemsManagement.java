package ir.rastanco.rastanbarcodescanner.presenter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import ir.rastanco.rastanbarcodescanner.R;

/**
 * Created by ParisaRashidhi on 23/12/2015.
 */
public class ListViewItemsManagement extends Fragment {

    private CheckBox listview_checkbox;
    private View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       v = inflater.inflate(R.layout.list_item, null);
       return v;
    }
    public void CheckboxState(boolean isCheckboxToolbarChecked) {
       /* if(isCheckboxToolbarChecked == false)
        {
           listview_checkbox = (CheckBox)v.findViewById(Configuration.listItemCheckBox);
           listview_checkbox.setVisibility(View.INVISIBLE);
        }
        else if(isCheckboxToolbarChecked == true)
        {
            listview_checkbox = (CheckBox)v.findViewById(Configuration.listItemCheckBox);
            listview_checkbox.setVisibility(View.VISIBLE);
        }*/
    }
}
