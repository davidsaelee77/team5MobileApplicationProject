package edu.uw.tcss450.griffin.ui.contacts;

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

//import static edu.uw.tcss450.team5tcss450client.ui.contacts.ContactsGenerator.randomNameGenerator;
/**
 * @author David Salee
 * @version May 2020
 */
public class ContactListViewModel extends AndroidViewModel {
    /**
     * MutableLiveData object of type List<Contacts>.
     */
    private MutableLiveData<List<Contacts>> mContactList;
    /**
     * UserInfoViewModel. 
     */
    private UserInfoViewModel userInfoViewModel;
    // private MutableLiveData<List<String>> mAlaphabet;

//    private List<Contacts> list;
//
//    private List<String> alphabet;

//    private MutableLiveData<JSONObject> mResponse;

    // private MutableLiveData<List<Contacts>> mResponse;
    /**
     * Constructor that instantiates fields.   
     * @param application Application object. 
     */
    public ContactListViewModel(@NonNull Application application) {
        super(application);

//        list = new ArrayList<>();
//
//        alphabet = new ArrayList<>();

        mContactList = new MutableLiveData<>();
        mContactList.setValue(new ArrayList<>());
        // mResponse.setValue(new JSONObject());

//        for (int i = 0; i < 26; i++) {
//
//            alphabet.add(Character.toString((char) (65 + i)));
//        }
//
//        for (int i = 0; i < 26; i++) {
//            list.add(new Contacts(randomNameGenerator(), randomNameGenerator(), randomNameGenerator(), alphabet.get(i)));
//        }

        //mContactList.setValue(list);

    }

    /**
     * Method to add ContactListObserver. 
     */
    public void addContactListObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Contacts>> observer) {
        mContactList.observe(owner, observer);
    }

    /**
     * Method that handles errors. 
     */
    private void handleError(final VolleyError error) {
        if (error != null && error.getMessage() != null) {
            Log.e("CONNECTION ERROR", error.getMessage());
            throw new IllegalStateException(error.getMessage());
        }
    }
    
    /**
     * Method that populates list of contacts based on JSON object. 
     * @param result
     */
    private void handleResult(final JSONObject result) {
        // IntFunction<String> getString = getApplication().getResources()::getString;
        try {
            JSONObject root = result;
            if (root.has(JSONKeys.success)) {
                boolean isSuccess = root.getBoolean(JSONKeys.success);
                if (!isSuccess) {
                    return;
                }
                JSONArray contacts = root.getJSONArray(JSONKeys.message);
                ArrayList<Contacts> listOfContacts = new ArrayList<>();
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject jsonContacts = contacts.getJSONObject(i);
                    try {
                        Contacts contact = new Contacts(jsonContacts);
                        listOfContacts.add(contact);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ;
                    }
                }
                mContactList.setValue(listOfContacts);
            } else {
                Log.e("ERROR!", "No response");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mContactList.setValue(mContactList.getValue());
    }

    /**
     * Method to connect to webservice and get contacts from server. 
     */
    public void connectGet() {
        if (userInfoViewModel == null) {
            throw new IllegalArgumentException("No UserInfoViewModel is assigned");
        }
        String url = getApplication().getResources().getString(R.string.base_url) +
                "contact/?memberid=" + userInfoViewModel.getMemberId();

//        String url = getApplication().getResources().getString(R.string.base_url);

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

}
