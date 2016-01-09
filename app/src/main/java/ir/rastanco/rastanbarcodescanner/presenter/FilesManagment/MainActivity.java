package ir.rastanco.rastanbarcodescanner.presenter.FilesManagment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.dataModel.DataBaseHandler;
import ir.rastanco.rastanbarcodescanner.dataModel.FileInfo;
import ir.rastanco.rastanbarcodescanner.presenter.BarcodeReading.BarcodeReadingActivity;

/*
created by parisaRashidi  on 94/9/27
 */
public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    private GoogleApiClient client;
    private Toolbar actionBar;
    private TextView simple_empty_database_textView;
    private Intent sendIntent;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragmentHandler mainFragmentHandler;
    private Typeface font ;
    private DataBaseHandler dbHandler;
    private int exitSafeCounter = 0;
    //private ArrayList<FileInfo> allFileInfo;
    private String state = "default";
    private boolean sort_mode = true ;
    private ImageButton sortFiles;
    private ImageButton change_image_sort;
    private Button showFiles;
    private ImageButton checkBox_toolbar;
    private final String defaultMode= "default";
    private final String displayExcelFilesOnly = "displayExcelFilesOnly";
    private final String displayTextFilesOnly = "displayTextFilesOnly";
    private FrameLayout fragmentContainer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        this.setActionBar();
        this.supportDrawable();
        this.addToolbar();
        this.supportFab();
        this.setMainFragmentAndCheckDataBaseState();
        this.setFont();
    }

    private void setMainFragmentAndCheckDataBaseState() {

        fragmentContainer = (FrameLayout)findViewById(R.id.fragment_container);
        mainFragmentHandler = new FragmentHandler();
//        dbHandler = new DataBaseHandler(this);
        simple_empty_database_textView = (TextView)findViewById(R.id.check_db_state_textView);
//               if(dbHandler.emptyDB())
//                         {
//                              simple_empty_database_textView.setVisibility(View.VISIBLE);
//                              simple_empty_database_textView.setTypeface(font);
//
//                         }

//                              else
        //                                {
        simple_empty_database_textView.setVisibility(View.GONE);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, mainFragmentHandler);
        fragmentTransaction.commit();


        //                              }

    }

    private void addToolbar()
    {
        checkBox_toolbar = (ImageButton) findViewById(R.id.checkbox_toolbar);
        sortFiles = (ImageButton) findViewById(R.id.sort_toolbar);
        showFiles = (Button)findViewById(R.id.allfiles_toolbar);
        sortFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //container.removeAllViewsInLayout();
                if(sort_mode == true){
                    change_image_sort = (ImageButton)findViewById(R.id.sort_toolbar);
                    change_image_sort.setImageResource(R.mipmap.ic_sort_a_to_z);

                    sort_mode = false ;

                }
                else if(sort_mode == false){

                    change_image_sort.setImageResource(R.mipmap.ic_sort);
                    sort_mode = true;
                }
            }
        });

        checkBox_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  if(dbHandler.emptyDB())
//                         {
//                              simple_empty_database_textView.setVisibility(View.VISIBLE);
//                              simple_empty_database_textView.setTypeface(font);
//
//                         }

//                              else
                //                                {
                startActivity(new Intent(MainActivity.this, CheckBoxHandler.class));
            //}
            }
        });


        showFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (state) {

                    case defaultMode: {
                        showFiles.setText(getResources().getString(R.string.showExcelFilesOnly));
                        state = displayTextFilesOnly;
                        break;
                    }
                    case displayExcelFilesOnly: {
                        showFiles.setText(getResources().getString(R.string.allfiles));
                        state = defaultMode;
                        break;
                    }
                    case displayTextFilesOnly: {
                        showFiles.setText(getResources().getString(R.string.showTextFilesOnly));
                        state = displayExcelFilesOnly;
                        break;
                    }
                }
            }
        });
    }

    private void supportDrawable() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, actionBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }




    private void setActionBar() {

        actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);


    }

    private void supportFab() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.DKGRAY));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iBarcodeReader = new Intent(MainActivity.this, BarcodeReadingActivity.class);
                startActivity(iBarcodeReader);
            }
        });
    }

    private void setFont(){
        font  = Typeface.createFromAsset(getAssets(), "yekan_font.ttf");
    }


    @Override
    public void onBackPressed() {

        exitSafeCounter++;
        if (exitSafeCounter == 1) {
            Toast.makeText(MainActivity.this,getResources().getString(R.string.sure_to_exit),
                    Toast.LENGTH_SHORT).show();
        } else if(exitSafeCounter >1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_my_files) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));

        }
        else if (id == R.id.nav_send)
        {
            sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.barcode_scanner));
            sendIntent.putExtra(Intent.EXTRA_TEXT, "http://cafebazaar.ir/app/?id=com.Arvand.HundredPercent");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        }
        else if(id==R.id.nav_have_sent_files)
        {


        }
        else if(id == R.id.nav_about){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
