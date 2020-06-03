package edu.uw.tcss450.griffin.services;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import org.json.JSONException;

import edu.uw.tcss450.griffin.AuthActivity;
import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.ui.chat.ChatMessageFragment;
import edu.uw.tcss450.griffin.ui.contacts.Invitation;
import me.pushy.sdk.Pushy;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;

/**
 * @author Charles Bryan
 * @version May 2020
 */
/**
 * Push functionality to retrieve and send messages
 * from the web server to client application.
 */
public class PushReceiver extends BroadcastReceiver {
    /**
     * Stores message string value.
     */
    public static final String RECEIVED_NEW_MESSAGE = "new message from pushy";

    /**
     * Stores new contact string value.
     */
    public static final String RECEIVED_NEW_CONTACT = "new contact from pushy";


    /**
     * Stores string value.
     */
    private static final String CHANNEL_ID = "1";

    private static final String TYPE_NEW_CONTACT = "contact";

    private static final String TYPE_NEW_CHATROOM = "newchatroom";

    private static final String TYPE_MESSAGE = "msg"; //TODO: Figure the type out

    @Override
    public void onReceive(final Context context, final Intent intent) {

        //the following variables are used to store the information sent from Pushy
        //In the WS, you define what gets sent. You can change it there to suit your needs
        //Then here on the Android side, decide what to do with the message you got

        //for the lab, the WS is only sending chat messages so the type will always be msg
        //for your project, the WS needs to send different types of push messages.
        //So perform logic/routing based on the "type"
        //feel free to change the key or type of values.
        String typeOfMessage = intent.getStringExtra("type");

        if (typeOfMessage.equals(TYPE_MESSAGE)) {
            handleNewMessage(context, intent);
        } else if (typeOfMessage.equals(TYPE_NEW_CONTACT)) {
            handleNewContact(context, intent);
        } else if (typeOfMessage.equals(TYPE_NEW_CHATROOM)) {

        }

    }

    private void handleNewMessage(final Context context, final Intent intent) {
        ChatMessageFragment message = null;
        int chatId = -1;
        try {
            message = ChatMessageFragment.createFromJsonString(intent.getStringExtra("message"));
            chatId = intent.getIntExtra("chatid", -1);
        } catch (JSONException e) {
            //Web service sent us something unexpected...I can't deal with this.
            throw new IllegalStateException("Error from Web Service. Contact Dev Support");
        }

        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);

        if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
            //app is in the foreground so send the message to the active Activities
            Log.d("PUSHY", "Message received in foreground: " + message);

            //create an Intent to broadcast a message to other parts of the app.
            Intent i = new Intent(RECEIVED_NEW_MESSAGE);
            i.putExtra("chatMessage", message);
            i.putExtra("chatid", chatId);
            i.putExtras(intent.getExtras());

            context.sendBroadcast(i);

        } else {
            //app is in the background so create and post a notification
            Log.d("PUSHY", "Message received in background: " + message.getMessage());

            Intent i = new Intent(context, AuthActivity.class);
            i.putExtras(intent.getExtras());

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    i, PendingIntent.FLAG_UPDATE_CURRENT);

            //research more on notifications the how to display them
            //https://developer.android.com/guide/topics/ui/notifiers/notifications
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_chat_notifications)
                    .setContentTitle("Message from: " + message.getSender())
                    .setContentText(message.getMessage())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);

            // Automatically configure a ChatMessageNotification Channel for devices running Android O+
            Pushy.setNotificationChannel(builder, context);

            // Get an instance of the NotificationManager service
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

            // Build the notification and display it
            notificationManager.notify(chatId, builder.build());
        }
    }

    private void handleNewContact(final Context context, final Intent intent) {
        Log.d("PUSHY", "Contact Request in PushReceiver from: " + intent.getStringExtra("username"));
        Invitation newContact = null;
        String senderUsername = "null";

        newContact = new Invitation(intent.getStringExtra("username"));
        senderUsername = intent.getStringExtra("username");

        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);

        if (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) {
            //app is in the foreground so send the message to the active Activities
            Log.d("PUSHY", "New contact received in foreground: " + newContact);

            //create an Intent to broadcast a message to other parts of the app.
            Intent i = new Intent(RECEIVED_NEW_CONTACT);
            i.putExtra("Invitation", newContact);
            i.putExtra("senderUsername", senderUsername);
            i.putExtras(intent.getExtras());

            context.sendBroadcast(i);
        } else {
            //app is in the background so create and post a notification
            Log.d("PUSHY", "New contact received in background: " + newContact.getSenderUsername());

            Intent i = new Intent(context, AuthActivity.class);
            i.putExtras(intent.getExtras());

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    i, PendingIntent.FLAG_UPDATE_CURRENT);

            //research more on notifications the how to display them
            //https://developer.android.com/guide/topics/ui/notifiers/notifications
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_chat_notifications)
                    .setContentTitle("Contact invitation from: " + newContact.getSenderUsername())
                    //.setContentText(newContact.getSenderUsername() + R.string.notification_newContactMessage)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);

            // Automatically configure a ChatMessageNotification Channel for devices running Android O+
            Pushy.setNotificationChannel(builder, context);

            // Get an instance of the NotificationManager service
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

            // Build the notification and display it
            //TODO: ID?
            notificationManager.notify(-1, builder.build());
        }
    }

    private void handleNewChatroom(final Context context, final Intent intent) {

    }
}
