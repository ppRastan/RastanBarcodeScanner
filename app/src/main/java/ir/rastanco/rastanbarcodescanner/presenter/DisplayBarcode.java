package ir.rastanco.rastanbarcodescanner.presenter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import Utility.BarcodeDisplayerArrayAdapter;
import ir.rastanco.rastanbarcodescanner.R;

/*
created by parisaRashidi  on 94/9/27
this class displays all barcode that scanded
you can remove theme and impact them to a folder

 */
public class DisplayBarcode extends AppCompatActivity {
    private Button save_barcodes_btn ;
    private ListView barcodeDisplayerListView ;
    private ArrayList<String> barcodeDisplayerArraylist;
    private ArrayAdapter<String> barcodeDisplayerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_barcode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.DKGRAY));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iBarcodeReader = new Intent(DisplayBarcode.this, BarcodeReadingActivity.class);
                startActivity(iBarcodeReader);
            }
        });
        barcodeDisplayerArraylist = new ArrayList<String>();
        save_barcodes_btn = (Button)findViewById(R.id.button_save);
        save_barcodes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        barcodeDisplayerListView = (ListView)findViewById(R.id.barcode_displayer_listview);
        fillBarcodeDisplayerListViewDynamicly();
        barcodeDisplayerAdapter = new BarcodeDisplayerArrayAdapter(this,barcodeDisplayerArraylist);
        barcodeDisplayerListView.setAdapter(barcodeDisplayerAdapter);
        barcodeDisplayerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {


            }
        });
    }

    private void fillBarcodeDisplayerListViewDynamicly() {
        //TODO fill this method by scanned barcodes
        //barcodeDisplayerArraylist.addAll();
        barcodeDisplayerArraylist.add("barcode 1");
        barcodeDisplayerArraylist.add("barcode 2");
        barcodeDisplayerArraylist.add("barcode 3");
        barcodeDisplayerArraylist.add("barcode 4");
        barcodeDisplayerArraylist.add("barcode 5");
        barcodeDisplayerArraylist.add("barcode 6");
        barcodeDisplayerArraylist.add("barcode 7");
        barcodeDisplayerArraylist.add("barcode 8");
        barcodeDisplayerArraylist.add("barcode 9");
        barcodeDisplayerArraylist.add("barcode 10");

    }

}
