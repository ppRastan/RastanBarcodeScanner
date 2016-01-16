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

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagment.MainActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutBarCodeScanner extends Activity {

    private ImageButton checkBtn;
    private TextView textAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_bar_code_scanner);
        this.addToolbar();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    private void addToolbar() {
        checkBtn = (ImageButton)findViewById(R.id.about_app_check_btn);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBtn.setImageResource(R.mipmap.ic_green_check_mark);
                startActivity(new Intent(AboutBarCodeScanner.this, MainActivity.class));
            }
        });
    }

}
