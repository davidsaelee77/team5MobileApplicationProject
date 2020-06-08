package edu.uw.tcss450.griffin.util;

import android.app.Activity;

import edu.uw.tcss450.griffin.MainActivity;
import edu.uw.tcss450.griffin.R;

public class Utils {
    private static int sTheme;
    public final static int UW_THEME = 0;
    public final static int SONICS_THEME = 1;
    public final static int BLUEGREY_THEME = 2;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     *
     * @param activity
     * @param theme
     */
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.recreate();
    }

    /**
     * Set the theme of the activity, according to the configuration.
     *
     * @param activity
     */
    public static void onActivityCreateSetTheme(MainActivity activity) {
        switch (sTheme) {
            default:
            case UW_THEME:

                activity.getTheme().applyStyle(R.style.AppTheme, true);
                break;
            case SONICS_THEME:
                activity.getTheme().applyStyle(R.style.SonicsTheme, true);
                break;
            case BLUEGREY_THEME:
                activity.getTheme().applyStyle(R.style.BluegreyTheme, true);
                break;
        }
    }
}