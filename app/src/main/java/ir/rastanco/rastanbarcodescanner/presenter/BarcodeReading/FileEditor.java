package ir.rastanco.rastanbarcodescanner.presenter.BarcodeReading;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;

import ir.rastanco.rastanbarcodescanner.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FileEditor extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_editor);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}
