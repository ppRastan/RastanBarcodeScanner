package ir.rastanco.rastanbarcodescanner.presenter;

import android.app.Fragment;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.Utility.Configuration;

public class CheckBoxManager extends Fragment {

    private ImageButton share_btn ;
    private ImageButton delete_btn;
    private ArrayList<String> filesToSend;
    private ArrayList<String>listview_items;
    private ArrayAdapter<String> adapter;
    private View view;
    private ListView listveiewWithCheckbox;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_check_box_manager, null);

        listveiewWithCheckbox = (ListView)view.findViewById(R.id.list);
        listview_items = new ArrayList<String >();
        fillListViewItemsFromDataBase();
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.listview_item_with_checkbox, listview_items);
        listveiewWithCheckbox.setAdapter(adapter);

        return view;
    }//end of oncreate

    private void fillListViewItemsFromDataBase() {
        listview_items.add("file 1");
        listview_items.add("file 2");
        listview_items.add("file 3");
        listview_items.add("file 4");

    }
}