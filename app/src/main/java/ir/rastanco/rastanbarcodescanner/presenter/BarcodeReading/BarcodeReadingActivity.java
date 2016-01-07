package ir.rastanco.rastanbarcodescanner.presenter.BarcodeReading;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
public class BarcodeReadingActivity extends Activity {

    private Camera mCamera;
    private CameraPreview cameraPreview;
    private Handler autoFocusHandler;
    private ImageButton imgbCheck;
    private ImageView imgBarecode;
    private ImageScanner scanner;
    private TextView txtCounterScan;
    private int counterScan = 0;
    private boolean barcodeScanned = false;
    private boolean previewing = true;
    private ArrayList<Barcode> allBarcode;
    private FrameLayout frameCameraPreview;
    private GoogleApiClient client;
    private Context context;
    TextView scanText;
    Button scanButton;
    static {
        System.loadLibrary("iconv");
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reading);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                this.readyCamera();
                this.setToolbarOnclickListener();
                this.addGoogleClient();

    }

    private void addGoogleClient() {

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void readyCamera() {
        mCamera = getCameraInstance();
        cameraPreview = new CameraPreview(this, mCamera, previewCallback, autoFocusCallback);
        frameCameraPreview = (FrameLayout) findViewById(R.id.cameraPreview);
        frameCameraPreview.addView(cameraPreview);

    }



    private void setToolbarOnclickListener() {

        imgBarecode = (ImageButton) findViewById(R.id.imgb_rScan);
        imgbCheck=(ImageButton)findViewById(R.id.imgb_Check);
        autoFocusHandler = new Handler();
        imgBarecode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    mCamera.startPreview();
                     mCamera.setPreviewCallback(previewCallback);
                    previewing = true;
                    mCamera.autoFocus(autoFocusCallback);
                }
            }
        });

        imgbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allBarcode = new ArrayList<Barcode>();
                Bundle bundle=new Bundle();
                bundle.putSerializable("allBarcode", allBarcode);
                Intent iDisplayBarcode=new Intent(BarcodeReadingActivity.this,BarcodeDisplayer.class);
                iDisplayBarcode.putExtras(bundle);
                //if(counterScan > 0){
                    startActivity(iDisplayBarcode);
                //}

            }
        });

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
                mCamera.autoFocus(autoFocusCallback);
        }
    };

        Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            scanner = new ImageScanner();
            scanner.setConfig(0, Config.X_DENSITY, 3);
            scanner.setConfig(0, Config.Y_DENSITY, 3);
            txtCounterScan=(TextView)findViewById(R.id.txt_counterScan);
            txtCounterScan.setText(Integer.toString(counterScan));
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

                    //parisa added this code
//                    if(sym.getType() == Symbol.CODE128){
//                        sym.getData();
//                        MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.beep_ok);
//                        mediaPlayer.start();
//                    }
//                    else {
//                        MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.beep_wrong);
//                        mediaPlayer.start();
//                    }
//                    mCamera.setPreviewCallback(previewCallback);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCallback);
                    barcodeScanned = true;
                    Barcode aBarcode = new Barcode();
                    aBarcode.setContent(sym.getData());
                    aBarcode.setFormat(sym.getType());
                    allBarcode.add(aBarcode);
                }
                counterScan++;
                txtCounterScan.setText(Integer.toString(counterScan));
            }
        }
    };

    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    @Override
    public void onStart() { super.onStart(); }

    @Override
    public void onStop() { super.onStop(); }
     //method to crop Bitmap in case of use
    //this method written by parisa
      public Bitmap scaleCenterCrop(Bitmap source , int newHeight , int newWidth){

        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);


        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;
    }
}