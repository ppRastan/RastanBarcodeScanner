package ir.rastanco.rastanbarcodescanner.presenter.BarcodeReading;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagment.MainActivity;

/*
created by shayeste
 */
//Todo for this page : save all barcode aray list and counter number so that users don't miss them when they go to next activity
public class BarcodeReadingActivity extends Activity {

    private Camera camera;
    private CameraPreview cameraPreview;
    private Handler autoFocusHandler;

    private ImageButton okButton;
    private ImageView cameraButton;
    private ImageScanner scanner;
    private TextView txtCounterScan;

    private int counterScan;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    private ArrayList<Barcode> barcodesList;
    private ImageButton selectAreaBtn;
    private boolean noArea = false;
    static {
        System.loadLibrary("iconv");
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reading);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.createPage();

    }



    private void createPage() {


        barcodesList = new ArrayList<Barcode>();

        this.initializeCamera();
        this.createFooterToolbar();

        new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void createFooterToolbar() {
        cameraButton = (ImageButton) findViewById(R.id.imgb_rScan);
        okButton =(ImageButton)findViewById(R.id.imgb_Check);

        //For counter
        counterScan=0;
        txtCounterScan=(TextView)findViewById(R.id.txt_counterScan);
        txtCounterScan.setText(Integer.toString(counterScan));
        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    camera.startPreview();
                    camera.setPreviewCallback(previewCb);
                    previewing = true;
                    camera.autoFocus(autoFocusCB);
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                okButton.setImageResource(R.mipmap.ic_green_check_mark);
                Bundle bundle = new Bundle();
                bundle.putSerializable("barcodesList", barcodesList);
                Intent iDisplayBarcode = new Intent(BarcodeReadingActivity.this, BarcodesListDisplayerActivity.class);
                iDisplayBarcode.putExtras(bundle);
                //TODO uncommnet this if statement so that user cant continue whit no item scaned
                              //if (counterScan>0)
                               startActivity(iDisplayBarcode);
               // else
                //{
                  //  Toast.makeText(getApplicationContext(),getResources().getString(R.id.no_item_scaned),Toast.LENGTH_SHORT).show();
                //}
            }
        });
        selectAreaBtn = (ImageButton)findViewById(R.id.image_area);
        selectAreaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noArea == false){

                    selectAreaBtn.setImageResource(R.mipmap.ic_green_area);
                    Toast.makeText(getApplicationContext(),"dfhfh",Toast.LENGTH_SHORT).show();
                    noArea = true;
                    //TODO display area here
                }
                else if(noArea){
                    selectAreaBtn.setImageResource(R.mipmap.ic_area);
                    noArea = false;
                    //TODO exit area mode
                }

            }
        });
    }

    private void initializeCamera() {

        autoFocusHandler = new Handler();
        camera = getCameraInstance();

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        cameraPreview = new CameraPreview(this, camera, previewCb, autoFocusCB);
        final FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
        preview.addView(cameraPreview);
    }

    @Override
    public void onBackPressed() {

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
            Log.v("Camera", "Camera Not Available!");
        }
        return c;
    }

    private void releaseCamera() {
       try{
           if (camera != null) {
               previewing = false;
               camera.setPreviewCallback(null);
               camera.release();
               camera = null;
           }
               }catch(Exception exception){
                Log.v("Camera", "Error in Releasing Camera");
             }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                camera.autoFocus(autoFocusCB);
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
                BarcodeReadingActivity.this.camera.setPreviewCallback(null);
                BarcodeReadingActivity.this.camera.stopPreview();
                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {
                    barcodeScanned = true;
                    Barcode aBarcode = new Barcode();
                    aBarcode.setContent(sym.getData());
                    aBarcode.setFormat(sym.getType());
                    barcodesList.add(aBarcode);
                }
                counterScan++;
                txtCounterScan.setText(Integer.toString(counterScan));
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