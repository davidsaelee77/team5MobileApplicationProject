package edu.uw.tcss450.griffin.ui.chat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * @author David Salee & Tyler Lorella
 * @version May 2020
 */
public class ChatRoom implements Serializable {


    //    private final long mMemberID;
//    private final String mUserName;
//    private final String mFirstName;
//    private final String mLastName;
//    private final long mChatRoomID;
    /** 
     * String of email. 
     */
    private final String email;
    /**
     * Integer of rows. 
     */
    private final int rowCount;

//
//    public ChatRoom(long mMemberID, String userName, String mFirstName, String mLastName, long mChatRoomID) {
//
//        this.mMemberID = mMemberID;
//        this.mUserName = userName;
//        this.mFirstName = mFirstName;
//        this.mLastName = mLastName;
//        this.mChatRoomID = mChatRoomID;
//
//    }

    /**
     * Constructor that instantiates fields. 
     * @param json JSONObject containing email.
     * @param count Integer of rows. 
     * @throws Exception
     */
    public ChatRoom(JSONObject json, int count) throws Exception {

        rowCount = count;
        email = json.getString("email");


//        mMemberID = json.getLong("memberid");
//        mUserName = json.getString("username");
//        mFirstName = json.getString("firstname");
//        mLastName = json.getString("lastname");
//        mChatRoomID = json.getLong("chatroomid");
    }


//    public String getFirstName() {
//        return mFirstName;
//    }
//
//    public String getLastName() {
//        return mLastName;
//    }
//
//    public String getUserName() {
//        return mUserName;
//    }
//
//    public long getMemberID() {
//        return mMemberID;
//    }
//
//    public long getChatRoomID() {
//        return mChatRoomID;
//    }

/**
 * Returns row count.
 * @return Amount of rows. 
 */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Returns the email.
     * @return String of email.
     */
    public String getEmail() {
        return email;
    }


}


