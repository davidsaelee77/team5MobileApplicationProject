package edu.uw.tcss450.team5tcss450client.ui.contacts;

import org.json.JSONObject;

import java.io.Serializable;

public class Contacts implements Serializable {

    private final String mMemberID;
    private final String mUserName;
    private final String mFirstName;
    private final String mLastName;
    //private final String alphabet;


    public Contacts(String mMemberID, String userName, String mFirstName, String mLastName) {

        this.mMemberID = mMemberID;
        this.mUserName = userName;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        // this.alphabet = alphabet;
    }

    public Contacts(JSONObject json) throws Exception {
        mMemberID = json.getInt("memberid") + "";
        mUserName = json.getString("username");
        mFirstName = json.getString("firstname");
        mLastName = json.getString("lastname");
    }

    public static class Builder {

        private final String mMemberID;
        private final String mUserName;
        private final String mFirstName;
        private final String mLastName;
        //private final String alphabet;

        public Builder(String mMemberID, String userName, String mFirstName, String mLastName) {

            this.mMemberID = mMemberID;
            this.mUserName = userName;
            this.mFirstName = mFirstName;
            this.mLastName = mLastName;

            // this.alphabet = alphabet;
        }

        public Contacts build() {
            return new Contacts(this);
        }

    }

    private Contacts(final Builder builder) {

        this.mMemberID = builder.mMemberID;
        this.mUserName = builder.mUserName;
        this.mFirstName = builder.mFirstName;
        this.mLastName = builder.mLastName;
        //this.alphabet = builder.alphabet;

    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getMemberID() {return mMemberID;}

//    public String getAlphabet() {
//
//        return alphabet;
//    }


}
