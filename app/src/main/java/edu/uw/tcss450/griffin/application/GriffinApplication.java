package edu.uw.tcss450.griffin.application;

import android.app.Application;
import android.content.Context;

/**
 * @author David Saelee
 * @version May 2020
 */
/**
 * Class is used to rerun main activity once
 * the the theme is changed.
 */
public class GriffinApplication extends Application {


    /**
     * Stores context value.
     */
    private static Context context;
    /**
     * Stores integer value.
     */
    public static int id = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        GriffinApplication.context = this;

    }
    /**
     * Method returns the value of the context.
     *
     * @return context value.
     */
    public static Context getContext() {

        return context;
    }


}
