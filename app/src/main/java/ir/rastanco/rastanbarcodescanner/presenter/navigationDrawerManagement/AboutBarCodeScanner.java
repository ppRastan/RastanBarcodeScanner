package ir.rastanco.rastanbarcodescanner.presenter.navigationDrawerManagement;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;

import ir.rastanco.rastanbarcodescanner.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutBarCodeScanner extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_bar_code_scanner);

    }
    //set font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
         }
    }


