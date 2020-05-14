package edu.uw.tcss450.team5tcss450client.ui.contacts;

import java.util.Date;

public class ContactList {


        private final String mFirstName;
        private final String mLastName;
        //private final String mUserName;


    public ContactList(String mFirstName, String mLastName) {

            this.mFirstName = mFirstName;
            this.mLastName = mLastName;
        }



    public String getmFirstName() {
        return mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }


}
