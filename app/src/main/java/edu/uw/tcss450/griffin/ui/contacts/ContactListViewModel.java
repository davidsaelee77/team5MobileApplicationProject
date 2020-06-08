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
import edu.uw.tcss450.griffin.io.RequestQueueSingleton;
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

    private MutableLiveData<List<Contacts>> mRequestList;

    private final MutableLiveData<JSONObject> mResponse;

    private MutableLiveData<String[]> searchResult;

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
     *
     * @param application Application object.
     */
    public ContactListViewModel(@NonNull Application application) {
        super(application);

//        list = new ArrayList<>();
//
//        alphabet = new ArrayList<>();

        mContactList = new MutableLiveData<>();
        mRequestList = new MutableLiveData<>();
        mContactList.setValue(new ArrayList<>());
        mRequestList.setValue(new ArrayList<>());

        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());

        searchResult = new MutableLiveData<>();
        searchResult.setValue(new String[]{"null"});

    }

    /**
     * Method to add ContactListObserver.
     */
    public void addContactListObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Contacts>> observer) {
        mContactList.observe(owner, observer);
    }

    public void addRequestListObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Contacts>> observer) {
        mRequestList.observe(owner, observer);
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
     *
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

                JSONArray invitations = root.getJSONArray(JSONKeys.invitations);
                ArrayList<Contacts> listOfInvites = new ArrayList<>();
                for (int i = 0; i < invitations.length(); i++) {
                    JSONObject jsonContact = invitations.getJSONObject(i);
                    try {
                        Contacts contact = new Contacts(jsonContact);
                        listOfInvites.add(contact);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                JSONArray contacts = root.getJSONArray(JSONKeys.contacts);
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
                mRequestList.setValue(listOfInvites); //TODO: Replace with proper parse/set value
            } else {
                Log.e("ERROR!", "No response");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mContactList.setValue(mContactList.getValue());
        mRequestList.setValue(mRequestList.getValue());
    }

    /**
     * Method to connect to webservice and get contacts from server.
     */
    public void connectGet() {
        if (userInfoViewModel == null) {
            throw new IllegalArgumentException("No UserInfoViewModel is assigned");
        }
        String url = getApplication().getResources().getString(R.string.base_url) +
                "contact";

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

    public void connectDeleteContact(final int memberid) {
        String url = getApplication().getResources().getString(R.string.base_url) + "contact" +
                "?memberId=" + memberid;
        Request request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                this::handleDeleteResult, this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", userInfoViewModel.getJwt());
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10_000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
    }

    private void handleDeleteResult(JSONObject result) {
        try {
            Log.d("ContactListViewModel DELETE", "Result for delete attempt: " +
                    result.getString("success"));
        } catch (JSONException e) {
            throw new IllegalStateException("Unexpected response in ContactListViewModel: " + result);
        }
    }

    public void connectAcceptContact(final int memberid) {
        if (userInfoViewModel == null) {
            throw new IllegalArgumentException("No UserInfoViewModel is assigned");
        }
        String url = getApplication().getResources().getString(R.string.base_url) +
                "contact?memberId=" + memberid;
        Request request = new JsonObjectRequest(Request.Method.PUT, url, null,
                null, this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", userInfoViewModel.getJwt());

                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10_000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
    }


    public void connectAddContactPost(String username) {
        String url = getApplication().getResources().getString(R.string.base_url) +
                "contact";

        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body, //push token found in the JSONObject body
                mResponse::setValue, // we get a response but do nothing with it
                this::handleError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                // headers.put("Authorization", "Bearer " + jwt);
                headers.put("Authorization", "Bearer " + userInfoViewModel.getJwt());
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    public void setUserInfoViewModel(UserInfoViewModel vm) {
        userInfoViewModel = vm;
    }

    public void connectGetSearch(String stringToSearch) {
        String url = getApplication().getResources().getString(R.string.base_url)
                + "searchContacts?searchString=" + stringToSearch;
        Log.d("CONTACTS", "Results: " + stringToSearch);
        Request request = new JsonObjectRequest(Request.Method.GET, url, null,
                this::handleSearchResult, this::handleError) {
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

    private void handleSearchResult(JSONObject result) {
        if (!result.has("rows")) {
            throw new IllegalStateException("Unexpected response in ContactSearch: " + result);
        }
        try {
            Log.d("CONTACTS", "Results: " + result.toString());
            JSONArray rows = result.getJSONArray("rows");
            String[] stringResults = new String[rows.length()];

            for (int counter = 0; counter < rows.length(); counter++) {
                JSONObject row = rows.getJSONObject(counter);
                String username = row.getString("username");
                stringResults[counter] = username;
            }
            this.searchResult.setValue(stringResults);
            String log = "none";
            if (stringResults.length > 0) {
                log = stringResults[0];
            }
            Log.d("CONTACTS", "results for search in AddContactFragment: " + log);
        } catch (JSONException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String[] getSearchResult() {
        return searchResult.getValue();
    }

    public void addSearchResultObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super String[]> observer) {
        this.searchResult.observe(owner, observer);
    }
}
