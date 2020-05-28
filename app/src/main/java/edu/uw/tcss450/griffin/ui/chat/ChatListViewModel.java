package edu.uw.tcss450.griffin.ui.chat;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.constants.JSONKeys;
import edu.uw.tcss450.griffin.model.UserInfoViewModel;

/**
 * @author David Salee & Tyler Lorella
 * @version May 2020
 */
public class ChatListViewModel extends AndroidViewModel {

    /**
     * Mutable Live Data of List<ChatRoom>.
     */
    private MutableLiveData<List<ChatRoom>> mChatRoomList;

    /**
     * UserInfoViewModel object.
     */
    private UserInfoViewModel userInfoViewModel;

    /**
     * ChatListViewModel constuctor that accepts an application object.
     *
     * @param application Application object.
     */
    public ChatListViewModel(@NonNull Application application) {
        super(application);

        mChatRoomList = new MutableLiveData<>();
        mChatRoomList.setValue(new ArrayList<>());

    }

    /**
     * An observer on the HTTP Response from the web server.
     *
     * @param owner    LifecycleOwner object.
     * @param observer Observer object of type List<ChatRoom>.
     */
    public void addChatListObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<ChatRoom>> observer) {
        mChatRoomList.observe(owner, observer);
    }

    /**
     * Method to handle volley errors.
     *
     * @param error VolleyError object.
     */
    private void handleError(final VolleyError error) {
        if (error != null && error.getMessage() != null) {
            Log.e("CONNECTION ERROR", error.getMessage());
            throw new IllegalStateException(error.getMessage());
        }
    }


    /**
     * Method to interpret given JSONObject.
     *
     * @param result Given JSONObject object.
     */
    private void handleResult(final JSONObject result) {
        if (!result.has("rows")) {
            throw new IllegalStateException("Unexpected response in ChatListViewModel: " + result);
        }
        try {
            ArrayList<ChatRoom> listOfChatRooms = new ArrayList<>();
            JSONArray rows = result.getJSONArray("rows");

            for (int i = 0; i < rows.length(); i++) {
                JSONObject row = rows.getJSONObject(i);
                int chatId = row.getInt("chatid");

                //Log.d("ChatListViewModel", ("found chatId: " + chatId));
                ChatRoom cr = new ChatRoom(getApplication(), userInfoViewModel, chatId);
                listOfChatRooms.add(cr);

            }
            mChatRoomList.setValue(listOfChatRooms);
        } catch (JSONException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method to connect to webservice and get chat data. Chats retrieves the .
     */
    public void connectGet() {
        if (userInfoViewModel == null) {
            throw new IllegalArgumentException("No UserInfoViewModel is assigned");
        }
        // Log.d("ChatListViewModel", "calling connectGet to chatData");

        String url = getApplication().getResources().getString(R.string.base_url) +
                "chatData?memberId=" + userInfoViewModel.getMemberId();

        Request request = new JsonObjectRequest(Request.Method.GET, url, null,
                //no body for this get request
                this::handleResult, this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", "Bearer " + userInfoViewModel.getJwt());
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10_000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
    }


    public void setUserInfoViewModel(UserInfoViewModel vm) {
        userInfoViewModel = vm;
    }

//    public void connectGetDummy() {
//        ArrayList<ChatRoom> listOfRooms = new ArrayList<>();
//        ChatRoom room = new ChatRoom(userInfoViewModel.getMemberId(), "userName", "firstnam", "lastname", 1);
//        listOfRooms.add(room);
//        mChatRoomList.setValue(listOfRooms);
//    }
}
