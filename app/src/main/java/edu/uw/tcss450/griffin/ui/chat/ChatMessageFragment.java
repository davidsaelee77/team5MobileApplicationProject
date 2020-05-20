package edu.uw.tcss450.griffin.ui.chat;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author David Salee & Tyler Lorella
 * @version May 2020
 */
public class ChatMessageFragment implements Serializable {

    /**
     * Integer of message id. 
     */
    private final int mMessageId;
    /**
     * String of message.
     */
    private final String mMessage;
    /**
     * String of sender.
     */
    private final String mSender;
    /**
     * String of timestamp.
     */
    private final String mTimeStamp;

    /**
     * Public ChatMessage constructor that instantiates fields. 
     * @param messageId Integer of message id.
     * @param message String of message.
     * @param sender String of sender.
     * @param timeStamp String of timestamp.    
     */
    public ChatMessageFragment(int messageId, String message, String sender, String timeStamp) {
        mMessageId = messageId;
        mMessage = message;
        mSender = sender;
        mTimeStamp = timeStamp;
    }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * ChatMessage object.
     *
     * @param cmAsJson the String to be parsed into a ChatMessage Object.
     * @return a ChatMessage Object with the details contained in the JSON String.
     * @throws JSONException when cmAsString cannot be parsed into a ChatMessage.
     */
    public static ChatMessageFragment createFromJsonString(final String cmAsJson) throws JSONException {
        final JSONObject msg = new JSONObject(cmAsJson);
        return new ChatMessageFragment(msg.getInt("messageid"),
                msg.getString("message"),
                msg.getString("email"),
                msg.getString("timestamp"));
    }
           
    /**
     * Method that returns the message. 
     * @return Value of mMessage. 
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * Method that returns the sender. 
     * @return Name of sender. 
     */
    public String getSender() {
        return mSender;
    }

    /**
     * Method that returns the timestamp of the method.
     * @return Value of the message's timestamp.
     */
    public String getTimeStamp() {
        return mTimeStamp;
    }

    /**
     * Method that returns the message id.
     * @return Value of message id.
     */
    public int getMessageId() {
        return mMessageId;
    }

    /**
     * Provides equality solely based on MessageId.
     *
     * @param other the other object to check for equality
     * @return true if other message ID matches this message ID, false otherwise
     */
    @Override
    public boolean equals(@Nullable Object other) {
        boolean result = false;
        if (other instanceof ChatMessageFragment) {
            result = mMessageId == ((ChatMessageFragment) other).mMessageId;
        }
        return result;
    }
}

