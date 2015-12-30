package ir.rastanco.rastanbarcodescanner.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.dataModel.DataBaseHandler;
import ir.rastanco.rastanbarcodescanner.dataModel.FileInfo;

public class ChooseNameActivity extends AppCompatActivity implements OnItemSelectedListener {

    private Button btnSave;
    private ListView listFileName;
    private AutoCompleteTextView actvFileName;

    private DataBaseHandler dbHandler;
    private FileInfo fileInfoSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSave= (Button)findViewById(R.id.btn_save);
        listFileName=(ListView) findViewById(R.id.lstv_fileName);
        actvFileName=(AutoCompleteTextView)findViewById(R.id.actv_fileName);

        dbHandler=new DataBaseHandler(this);

        this.createPage();
    }

    private void createPage() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent iBarcodeReader=new Intent(ChooseNameActivity.this,BarcodeReadingActivity.class);
                startActivity(iBarcodeReader);
            }
        });

        final Spinner spinner = (Spinner) findViewById(R.id.spin_fileType);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add(".txt");
        categories.add(".xls");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        ArrayList<FileInfo> allFileInfo = new ArrayList<FileInfo>();
        allFileInfo = dbHandler.selectAllFileInfo();
        final ArrayList<String> fileName=new ArrayList<String>();
        for (int i=0;i<allFileInfo.size();i++)
            fileName.add(allFileInfo.get(i).getFileName()+allFileInfo.get(i).getFileType());
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,fileName);
        listFileName.setAdapter(listAdapter);
        actvFileName.setAdapter(listAdapter);
        actvFileName.setThreshold(1);

        listFileName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO Complete List View Item Click (open exsit file)
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
        final String currentDateAndTime = sdf.format(new Date());
        actvFileName.setHint(currentDateAndTime);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actvFileName.getText().toString().length()==0)
                    actvFileName.setText(currentDateAndTime);
                fileInfoSave = new FileInfo(actvFileName.getText().toString(),
                        spinner.getSelectedItem().toString(),
                        currentDateAndTime);
                dbHandler.insertAFileInfo(fileInfoSave);
                actvFileName.setText("");

                //what is the filename?
                //what is the fileContent?
                //TODO for Shaiste:
                //Please use this method to save your file on the external Storage of the phone!

             //   this.saveToFile(fileName, fileContent);


            }
        });
    }

    @Override
    public void onBackPressed()
    {


        //do whatever you want the 'Back' button to do
        //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
        this.startActivity(new Intent(ChooseNameActivity.this,BarcodeReadingActivity.class));
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void saveToFile(String name, String fileContent) {

        File file = null;
        File root = Environment.getExternalStorageDirectory();
        if (root.canWrite()) {
            File dir = new File(root.getAbsolutePath() + "/BarcodeScanner");
            dir.mkdirs();
            String fileName = name;
            file = new File(dir, fileName);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
     } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.write(fileContent.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
