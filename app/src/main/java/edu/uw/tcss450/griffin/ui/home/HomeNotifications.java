package edu.uw.tcss450.griffin.ui.home;

import java.io.Serializable;

public class HomeNotifications implements Serializable {


    private final int mNotifications;

    public HomeNotifications(int notifications) {

        this.mNotifications = notifications;
    }

    public int getNotifications() {
        return mNotifications;
    }


}
