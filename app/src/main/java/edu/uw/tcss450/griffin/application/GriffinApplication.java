package edu.uw.tcss450.griffin.application;

import android.app.Application;
import android.content.Context;

public class GriffinApplication extends Application {


    private static Context context;
    public static int id = -1;
    public static int currId = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        GriffinApplication.context = this;

    }

    public static Context getContext() {

        return context;
    }


}
