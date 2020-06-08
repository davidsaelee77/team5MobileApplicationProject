package edu.uw.tcss450.griffin.ui.contacts;

import org.json.JSONObject;

import java.io.Serializable;
/**
 * @author David Salee
 * @version May 2020
 */
public class Contacts implements Serializable {
/**
 * String of member id. 
 */
    private final String mMemberID;
    /**
     * String of username.
     */
    private final String mUserName;
    /**
     * String of first name. 
     */
    private final String mFirstName;
    /**
     * String of last name. 
     */
    private final String mLastName;
    //private final String alphabet;

    /**
     * Constructor that intantiates fields. 
     * @param mMemberID
     * @param userName
     * @param mFirstName
     * @param mLastName
     */
    public Contacts(String mMemberID, String userName, String mFirstName, String mLastName) {

        this.mMemberID = mMemberID;
        this.mUserName = userName;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
    }

    /**
     * Constructor that instantiates fields from a JSONObject. 
     */
    public Contacts(JSONObject json) throws Exception {
        mMemberID = json.getInt("memberid") + "";
        mUserName = json.getString("username");
        mFirstName = json.getString("firstname");
        mLastName = json.getString("lastname");
    }

    /**
     * Returns first name. 
     */
    public String getFirstName() {
        return mFirstName;
    }

    /**
     * Returns last name. 
     */
    public String getLastName() {
        return mLastName;
    }

    /**
     * Returns username
     * @return
     */
    public String getUserName() {
        return mUserName;
    }

    /**
     * Returns member id. 
     */
    public String getMemberID() {return mMemberID;}

}
