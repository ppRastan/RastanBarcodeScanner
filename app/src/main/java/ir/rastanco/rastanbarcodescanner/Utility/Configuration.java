package ir.rastanco.rastanbarcodescanner.Utility;

import android.content.Context;

import ir.rastanco.rastanbarcodescanner.R;

/**
 * Created by parisaRashidiNezhad on 1394/8/20
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
    public static int listItemCheckBox= R.id.listview_checkbox;
}
