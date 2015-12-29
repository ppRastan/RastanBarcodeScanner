package ir.rastanco.rastanbarcodescanner.presenter;

import android.app.ListActivity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.rastanco.rastanbarcodescanner.R;

public class CheckBoxManager extends ListActivity {

    private ImageButton share_btn ;
    private ImageButton delete_btn;
    private ArrayList<String> filesToSend;
    private ArrayList<String>listview_items;
    private final List<Integer> typeFile=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_manager);
        listview_items = new ArrayList<String >();
        fillListViewItemsFromDataBase();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, listview_items);
        getListView().setAdapter(adapter);
        share_btn = (ImageButton)findViewById(R.id.checkbox_content_layout_share_btn);
        delete_btn = (ImageButton)findViewById(R.id.checkbox_content_layout_delete_btn);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT,getResources().getString(R.string.share_subject));
                startActivity(Intent.createChooser(intent,getResources().getString(R.string.share_via)));
                ArrayList<Uri> files = new ArrayList<Uri>();
                for(String path : filesToSend /* List of the files you want to send */) {
                    File file = new File(path);
                    Uri uri = Uri.fromFile(file);
                    files.add(uri);

                }
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckBox chk = (CheckBox) v;
                int itemCount = getListView().getCount();
                for(int i=0 ; i < itemCount ; i++){
                    getListView().setItemChecked(i, chk.isChecked());
                }
            }
        };

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                CheckBox chk = (CheckBox) findViewById(R.id.chkAll);
                int checkedItemCount = getCheckedItemCount();

                if(getListView().getCount()==checkedItemCount)
                    chk.setChecked(true);
                else
                    chk.setChecked(false);
            }
        };

         CheckBox chkAll =  ( CheckBox ) findViewById(R.id.chkAll);

        chkAll.setOnClickListener(clickListener);
        getListView().setOnItemClickListener(itemClickListener);

    }

    private void fillListViewItemsFromDataBase() {
        listview_items.add("file 1");
        listview_items.add("file 2");
        listview_items.add("file 3");
        listview_items.add("file 4");
    }

    private int getCheckedItemCount(){
        int cnt = 0;
        SparseBooleanArray positions = getListView().getCheckedItemPositions();
        int itemCount = getListView().getCount();

        for(int i=0;i<itemCount;i++){
            if(positions.get(i))
                cnt++;
        }
        return cnt;
    }
}