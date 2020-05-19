package edu.uw.tcss450.griffin.ui.chat;

import org.json.JSONObject;

import java.io.Serializable;


public class ChatRoom implements Serializable {


    private final long mMemberID;
    private final String mUserName;
    private final String mFirstName;
    private final String mLastName;
    private final long mChatRoomID;


    public ChatRoom(long mMemberID, String userName, String mFirstName, String mLastName, long mChatRoomID) {

        this.mMemberID = mMemberID;
        this.mUserName = userName;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mChatRoomID = mChatRoomID;

    }

    public ChatRoom(JSONObject json) throws Exception {
        mMemberID = json.getLong("memberid");
        mUserName = json.getString("username");
        mFirstName = json.getString("firstname");
        mLastName = json.getString("lastname");
        mChatRoomID = json.getLong("chatroomid");
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

    public long getMemberID() {
        return mMemberID;
    }

    public long getChatRoomID() {
        return mChatRoomID;
    }

}


