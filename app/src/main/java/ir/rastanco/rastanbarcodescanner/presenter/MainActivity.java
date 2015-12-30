package ir.rastanco.rastanbarcodescanner.presenter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
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

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.ArrayList;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.dataModel.DataBaseHandler;
import ir.rastanco.rastanbarcodescanner.dataModel.FileInfo;

/*
created by parisaRashidi  on 94/9/27
 this is MainActivity for RastanBarcodeScanner witch handele toolbars actions
 */
public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    private DataBaseHandler dbHandler;
    private ArrayList<FileInfo> allFileInfo;
    private String state = "default";
    private boolean sort_mode = true ;
    private Toolbar toolbar;
    private ImageButton sortFiles;
    private ImageButton change_image_sort;
    private Button showFiles;
    private ImageButton checkBox_toolbar;
    private FrameLayout container;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private TextView simple_empty_database_textView;
    private LinearLayout temp_toolbar_checkbox_handler;
    private LinearLayout main_toolbar;

    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }


        dbHandler=new DataBaseHandler(this);
        allFileInfo=new ArrayList<FileInfo>();
        allFileInfo=dbHandler.selectAllFileInfo();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.DKGRAY));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iBarcodeReader = new Intent(MainActivity.this, BarcodeReadingActivity.class);
                startActivity(iBarcodeReader);
                //here
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        simple_empty_database_textView = (TextView)findViewById(R.id.check_db_state_textView);
        checkBox_toolbar = (ImageButton) findViewById(R.id.checkbox_toolbar);
        sortFiles = (ImageButton) findViewById(R.id.sort_toolbar);
        showFiles = (Button) findViewById(R.id.allfiles_toolbar);
        temp_toolbar_checkbox_handler = (LinearLayout)findViewById(R.id.temp_linear_checkbox);
        main_toolbar = (LinearLayout)findViewById(R.id.main_toolbar);
        Bundle bundle=new Bundle();
        bundle.putSerializable("allFileInfo", allFileInfo);
        //mainFragmentHandler.setArguments(bundle);
        if(dbHandler.emptyDB())
        {
            simple_empty_database_textView.setVisibility(View.VISIBLE);

        }

        //else
        //{
          //  simple_empty_database_textView.setVisibility(View.GONE);

//        }


        sortFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeAllViewsInLayout();
                if(sort_mode == true){
                    change_image_sort = (ImageButton) findViewById(R.id.sort_toolbar);
                    change_image_sort.setImageResource(R.drawable.ic_sort_a_to_z);

                    sort_mode = false ;

                }
                else if(sort_mode == false){

                    change_image_sort.setImageResource(R.drawable.ic_sort_icon);
                    sort_mode = true;
                }
                            }
        });

        checkBox_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_toolbar.setVisibility(View.INVISIBLE);
                temp_toolbar_checkbox_handler.setVisibility(View.VISIBLE);
                simple_empty_database_textView.setVisibility(View.GONE);

            }
        });


        showFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeAllViewsInLayout();
                switch (state) {

                    case "default": {
                        showFiles.setText(getResources().getString(R.string.showExcelFilesOnly));
                        state = "displayTextFilesOnly";
                        break;
                    }
                    case "displayExcelFilesOnly": {
                        showFiles.setText(getResources().getString(R.string.allfiles));
                        state = "default";
                        break;
                    }
                    case "displayTextFilesOnly": {
                        showFiles.setText(getResources().getString(R.string.showTextFilesOnly));
                        state = "displayExcelFilesOnly";
                        break;
                    }
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_my_files) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
                                                                                                                                           } else if (id == R.id.nav_have_sent_files) {
            //checkListViewAdapterState.equals("displaySentFilesInListView");

        } else if (id == R.id.nav_send) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "lets try our barcodeScanner");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ir.rastanco.rastanbarcodescanner.presenter/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);*/
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       /* Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ir.rastanco.rastanbarcodescanner.presenter/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();*/
    }

}
