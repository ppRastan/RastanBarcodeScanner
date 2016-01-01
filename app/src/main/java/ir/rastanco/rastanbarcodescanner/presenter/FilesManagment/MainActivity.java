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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.dataModel.DataBaseHandler;
import ir.rastanco.rastanbarcodescanner.presenter.BarcodeReading.BarcodeReadingActivity;

/*
created by parisaRashidi  on 94/9/27
 this is MainActivity for RastanBarcodeScanner witch handele toolbars actions
 */
public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    private GoogleApiClient client;
    private Toolbar toolbar;
    private DataBaseHandler dbHandler;
    private TextView simple_empty_database_textView;
    private FrameLayout container;
    private Intent sendIntent;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainFragmentHandler mainFragmentHandler;
    private Typeface font ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mainFragmentHandler = new MainFragmentHandler();
        dbHandler=new DataBaseHandler(this);
        simple_empty_database_textView = (TextView)findViewById(R.id.check_db_state_textView);
        container = (FrameLayout)findViewById(R.id.fragment_container);
        font  = Typeface.createFromAsset(getAssets(), "yekan_font.ttf");
        sendIntent = new Intent();
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

//        if(dbHandler.emptyDB())
//        {
//            simple_empty_database_textView.setVisibility(View.VISIBLE);
//            simple_empty_database_textView.setTypeface(font);
//
//        }

//        else
//        {
            simple_empty_database_textView.setVisibility(View.GONE);
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, mainFragmentHandler);
            fragmentTransaction.commit();

        //}

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

        } else if (id == R.id.nav_send) {
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            sendIntent.putExtra(Intent.EXTRA_TEXT, "http://cafebazaar.ir/app/?id=com.Arvand.HundredPercent");
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


    }

    @Override
    public void onStop() {
        super.onStop();


    }

}
