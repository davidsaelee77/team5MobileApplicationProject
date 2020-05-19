package edu.uw.tcss450.griffin.ui.weather;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

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
import java.util.function.IntFunction;


public class WeatherViewModel extends AndroidViewModel {

    private MutableLiveData<List<WeatherData>> mWeatherData;
    private List<WeatherData> list;


    public WeatherViewModel(@NonNull Application application) {
        super(application);
        list = new ArrayList<>();

        mWeatherData = new MutableLiveData<>();

        for (int i = 0; i < 10; i++) {
            list.add(new WeatherData("Sunny", "Wednesday", 56 + i + "", 70 + i + ""));
        }

        mWeatherData.setValue((list));
    }

    public void addWeatherObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<WeatherData>> observer) {
        mWeatherData.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

//Adding this stuff just for testing
    ///////////////////////////////////////////////////////////////////

//    private void handleResult(final JSONObject result) {
//        IntFunction<String> getString = getApplication().getResources()::getString;
//        try {
//            JSONObject root = result;
//            if (root.has(getString.apply(R.string.keys_json_contactlist_response))) {
//                JSONObject response = root.getJSONObject(getString.apply(R.string.keys_json_contactlist_response));
//                if (response.has(getString.apply(R.string.keys_json_contactlist_full_name))) {
//                    JSONArray data = response.getJSONArray(
//                            getString.apply(R.string.keys_json_contactlist_full_name));
//                    for (int i = 0; i < data.length(); i++) {
//                        JSONObject jsonContacts = data.getJSONObject(i);
//
////                        mContactList.getValue().add(new Contacts.Builder(jsonContacts.getString(getString.apply(R.string.keys_json_contactlist_first_name)),
////                                jsonContacts.getString(getString.apply(R.string.keys_json_contactlist_last_name))).build());
//                    }
//                } else {
//                    Log.e("ERROR!", "No data array");
//                }
//            } else {
//                Log.e("ERROR!", "No response");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e("ERROR!", e.getMessage());
//        }
////        mContactList.setValue(mContactList.getValue());
//        mWeatherData.setValue(mWeatherData.getValue());
//    }
//
//    public void connectGet() {
//        String url = "https://dsael1-lab4-backend.herokuapp.com/demosql";
//        Request request = new JsonObjectRequest(Request.Method.GET, url, null,
//                //no body for this get request
//                this::handleResult, this::handleError) {
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                // add headers <key,value>
//                headers.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXV" +
//                        "CJ9.eyJ1c2VybmFtZSI6InV3bmV0aWQ1QGZha2UuZW1haWwuY29tIi" +
//                        "wiaWF0IjoxNTg3NTMwNTM5LCJleHAiOjE1ODg3NDAxMzl9.g02500z" +
//                        "joCH0pHxx9-9Ye_ILZHCAdbMvdjpwdfvktcU");
//                return headers;
//            }
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(10_000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        //Instantiate the RequestQueue and add the request to the queue
//        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
//    }
}
