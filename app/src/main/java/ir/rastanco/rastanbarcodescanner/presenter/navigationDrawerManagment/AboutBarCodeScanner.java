package ir.rastanco.rastanbarcodescanner.presenter.navigationDrawerManagment;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagment.CheckBoxHandler;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagment.MainActivity;
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


