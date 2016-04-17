package ir.rastanco.rastanbarcodescanner.Utility;

import android.content.Context;

/**
 * Created by parisa
 * this class helps you to use contexts in all activities by instantiating.
 */
public class Configuration {

    private static Configuration config=new Configuration();

    public static Configuration getConfig(){
        if (config!= null){
            return config;
        }
        else return new Configuration();

    }

    public static Context activityContext;
}