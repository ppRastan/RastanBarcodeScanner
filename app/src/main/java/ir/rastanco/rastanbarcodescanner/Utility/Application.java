package ir.rastanco.rastanbarcodescanner.Utility;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by ParisaRashidhi on 16/01/2016.
 */
    public class Application extends android.app.Application {
        @Override
        public void onCreate() {
            super.onCreate();
            CalligraphyConfig.initDefault("fonts/yekan_font.ttf");
        }
    }
