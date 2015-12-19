package ir.rastanco.rastanbarcodescanner.presenter;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.presenter.SheredFiles;

/*
created by parisaRashidi  on 94/9/27
 this is MainActivity for RastanBarcodeScanner witch handele toolbars actions and displays stored files
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private String state = "default";
    private Toolbar toolbar;
    private ImageButton sortFiles ;
    private ImageButton change_image_sort;
    private ImageButton delete_btn;
    private ImageButton share_btn;
    private Button showFiles;
    private ImageButton checkBox_toolbar;
    private LinearLayout container;
    private LinearLayout temp_linear_for_checkbox;
    private Button select_all_checkboxes;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(getColorStateList(R.color.colorPrimary));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        temp_linear_for_checkbox = (LinearLayout)findViewById(R.id.checkbox_content_layout);
        container =  (LinearLayout)findViewById(R.id.fragment_container);
        share_btn = (ImageButton)findViewById(R.id.checkbox_content_layout_share_btn);
        delete_btn = (ImageButton)findViewById(R.id.checkbox_content_layout_delete_btn);
        select_all_checkboxes = (Button)findViewById(R.id.checkbox_content_layout_select_all);
        checkBox_toolbar = (ImageButton)findViewById(R.id.checkbox_toolbar);
        sortFiles = (ImageButton)findViewById(R.id.sort_toolbar);
        showFiles = (Button)findViewById(R.id.allfiles_toolbar);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        select_all_checkboxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        sortFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeAllViewsInLayout();
                change_image_sort = (ImageButton) findViewById(R.id.sort_toolbar);
                change_image_sort.setImageResource(R.drawable.ic_sort_a_to_z);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SheredFiles sortAToZ = new SheredFiles();
                fragmentTransaction.add(R.id.fragment_container, sortAToZ, "HELLO");
                fragmentTransaction.commit();
            }
        });


        checkBox_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_linear_for_checkbox.setVisibility(View.VISIBLE);

            }
        });


        showFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeAllViewsInLayout();
                switch (state){

                    case "default":
                    {
                        showFiles.setText(getResources().getString(R.string.showExcelFilesOnly));
                        state = "displayTextFilesOnly";
                        break;
                    }
                    case "displayExcelFilesOnly" :
                    {
                        showFiles.setText(getResources().getString(R.string.allfiles));
                        state = "default";
                        break;
                    }
                    case "displayTextFilesOnly":{
                        showFiles.setText(getResources().getString(R.string.showTextFilesOnly));
                        state = "displayExcelFilesOnly";
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_my_files)
        {
            startActivity(new Intent(MainActivity.this,MainActivity.class));
        }
        else if (id == R.id.nav_have_sent_files)
        {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SheredFiles sheredFiles = new SheredFiles();
            fragmentTransaction.add(R.id.fragment_container, sheredFiles, "HELLO");
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_send) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "lets try our barcodeScanner");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        }
        else if (id == R.id.nav_payment)
        {
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
