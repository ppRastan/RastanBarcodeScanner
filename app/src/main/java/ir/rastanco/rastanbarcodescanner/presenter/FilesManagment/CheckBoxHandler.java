package ir.rastanco.rastanbarcodescanner.presenter.FilesManagment;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.rastanco.rastanbarcodescanner.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CheckBoxHandler extends ListActivity {



    private ArrayList<String> files;
    private ImageButton toolbarShareButton;
    private ImageButton toolbarDeleteButton;
    private TextView selectAllCheckBoxesTextView;
    private ImageButton toolbarFinishedChecking;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_handler);
        this.setAdapterAndFillListView();
        this.addToolbarActionListener();
        this.manageClickListeners();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    private void addToolbarActionListener() {

        toolbarFinishedChecking = (ImageButton)findViewById(R.id.appbar_barcode_displayer_check_btn);
        toolbarShareButton = (ImageButton)findViewById(R.id.checkbox_content_layout_share_btn);
        toolbarDeleteButton = (ImageButton)findViewById(R.id.checkbox_content_layout_delete_btn);
        toolbarShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"share button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        toolbarDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        toolbarFinishedChecking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CheckBoxHandler.this,MainActivity.class));
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
        files = new ArrayList<String>();
        files.add("file 1");
        files.add("file 2");
        files.add("file 3");
        files.add("file 4");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, files);
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