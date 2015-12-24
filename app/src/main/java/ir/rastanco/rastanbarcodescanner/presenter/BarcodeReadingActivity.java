package ir.rastanco.rastanbarcodescanner.presenter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.util.ArrayList;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.dataModel.Barcode;
/*
created by shayeste
 */
public class BarcodeReadingActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

    private ImageButton imgbRScan;
    private ImageButton endScan;
    private ImageView imgBarecode;
    private ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    private ArrayList<Barcode> allBarcode;

    static {
        System.loadLibrary("iconv");
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reading);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        imgBarecode = (ImageButton) findViewById(R.id.imgb_rScan);
        endScan = (ImageButton) findViewById(R.id.imgb_end);

        allBarcode = new ArrayList<Barcode>();

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        imgBarecode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    mCamera.startPreview();
                    Toast.makeText(getBaseContext(), "آغاز اسکن ...", Toast.LENGTH_LONG).show();
                    mCamera.setPreviewCallback(previewCb);
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                }
            }
        });

        endScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                FragmentManager fragmentManager = getFragmentManager();
                bundle.putSerializable("allBarcode", allBarcode);
                BarcodeDisplayer barcodeDisplayer=new BarcodeDisplayer();
                barcodeDisplayer.setArguments(bundle);
                fragmentManager.popBackStack();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.cameraPreview, barcodeDisplayer);
                fragmentTransaction.commit();
                /*Intent intent=new Intent(BarcodeReadingActivity.this,DisplayBarcodeNumbersActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                mPreview.getHolder().removeCallback(mPreview);
                /*Intent intent = new Intent(BarcodeReadingActivity.this, ChooseNameActivity.class);
                startActivity(intent);*/
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        //do whatever you want the 'Back' button to do
        //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
        this.startActivity(new Intent(BarcodeReadingActivity.this, MainActivity.class));
    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            int result = scanner.scanImage(barcode);
            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {
                    Toast.makeText(getBaseContext(), "شماره بار کد :" + sym.getData(), Toast.LENGTH_LONG).show();
                    barcodeScanned = true;
                    Barcode aBarcode = new Barcode();
                    aBarcode.setContent(sym.getData());
                    aBarcode.setFormat(sym.getType());
                    allBarcode.add(aBarcode);
                }
            }
        }
    };

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    @Override
    public void onStart() { super.onStart(); }

    @Override
    public void onStop() { super.onStop(); }

}