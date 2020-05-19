package edu.uw.tcss450.team5tcss450client.ui.settings;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.Objects;

/**
 *
 * @author Tyler Lorella
 * @version May 2020
 */
/**
 * Store and manage register UI-related data in lifecycle.
 */
public class ChangePasswordFragmentViewModel extends AndroidViewModel {

    /**
     * Store JSON object variable.
     */
    private MutableLiveData<JSONObject> mResponse;

    /**
     * Constructor that initializes JSON object and sets its value.
     *
     * @param application maintains application state.
     */
    public ChangePasswordFragmentViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Notifies observer of any modifications of
     * wrapped data.
     *
     * @param owner    lifespan of given owner.
     * @param observer callback from live data.
     */
    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    /**
     * Server credential authentication error handling.
     *
     * @param error message
     */
    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            try {
                mResponse.setValue(new JSONObject("{" +
                        "error:\"" + error.getMessage() +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        } else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset())
                    .replace('\"', '\'');
            try {
                mResponse.setValue(new JSONObject("{" +
                        "code:" + error.networkResponse.statusCode +
                        ", data:\"" + data +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
    }

    /**
     * Server credential verification.
     * *
     * @param email    string user input value, email of user
     * @param oldPassword string user input value, password to change from
     * @param newPassword string user input value, password to change to
     */
    public void connect(final String email, final String oldPassword, final String newPassword) {
        //TODO fix url
        String url = "https://team5-tcss450-server.herokuapp.com/auth";
        JSONObject body = new JSONObject();
        try {
            body.put("email", email);
            body.put("oldPassword", oldPassword);
            body.put("newPassword", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(Request.Method.POST, url, body, mResponse::setValue, this::handleError);
        request.setRetryPolicy(new DefaultRetryPolicy(10_000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
    }
}
