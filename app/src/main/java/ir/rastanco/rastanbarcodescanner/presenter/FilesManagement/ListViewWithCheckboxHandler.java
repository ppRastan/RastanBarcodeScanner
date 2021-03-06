package ir.rastanco.rastanbarcodescanner.presenter.FilesManagement;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import ir.rastanco.rastanbarcodescanner.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListViewWithCheckboxHandler extends ListActivity {
    private ArrayList<String> files;
    private ImageButton toolbarFinishedChecking;
    private ArrayAdapter<String> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_handler);
        this.setAdapterAndFillListView();
        this.addToolbarActionListener();
        this.manageClickListeners();

    }
   //to set font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    private void addToolbarActionListener() {

        toolbarFinishedChecking = (ImageButton)findViewById(R.id.appbar_barcode_Indicative_check_btn);
        ImageButton toolbarShareButton = (ImageButton)findViewById(R.id.checkbox_content_layout_share_btn);
        ImageButton toolbarDeleteButton = (ImageButton)findViewById(R.id.checkbox_content_layout_delete_btn);
        toolbarShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"share button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        toolbarDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
                int itemCount = getListView().getCount();

                for(int i=itemCount-1; i >= 0; i--){
                    if(checkedItemPositions.get(i)){
                        adapter.remove(files.get(i));
                    }
                }
                checkedItemPositions.clear();
                adapter.notifyDataSetChanged();
            }
        });
        toolbarFinishedChecking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 toolbarFinishedChecking.setImageResource(R.mipmap.ic_green_check_mark);
                startActivity(new Intent(ListViewWithCheckboxHandler.this, MainActivity.class));
            }
        });
    }

    private void manageClickListeners() {

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

    private void setAdapterAndFillListView() {
        files = new ArrayList<>();
        files.add("file 1");
        files.add("file 2");
        files.add("file 3");
        files.add("file 4");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, files);
        getListView().setAdapter(adapter);
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