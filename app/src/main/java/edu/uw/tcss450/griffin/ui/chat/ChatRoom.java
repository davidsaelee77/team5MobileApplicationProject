package edu.uw.tcss450.griffin.ui.chat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;


public class ChatRoom implements Serializable {


    //    private final long mMemberID;
//    private final String mUserName;
//    private final String mFirstName;
//    private final String mLastName;
//    private final long mChatRoomID;
    private final String email;
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

    public int getRowCount() {
        return rowCount;
    }

    public String getEmail() {
        return email;
    }


}


