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

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.constants.JSONKeys;
import edu.uw.tcss450.griffin.model.UserInfoViewModel;
import edu.uw.tcss450.griffin.ui.contacts.Contacts;


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

//    private void handleError(final VolleyError error) {
//        if (error != null && error.getMessage() != null) {
//            Log.e("CONNECTION ERROR", error.getMessage());
//            throw new IllegalStateException(error.getMessage());
//        }
//    }
//
//    private void handleResult(final JSONObject result) {
//
//        try {
//            JSONObject root = result;
//            if (root.has(JSONKeys.success)) {
//                boolean isSuccess = root.getBoolean(JSONKeys.success);
//                if (!isSuccess) {
//                    return;
//                }
//                JSONArray weatherData = root.getJSONArray(JSONKeys.message);
//                ArrayList<WeatherData> weatherDataValues = new ArrayList<>();
//                for (int i = 0; i < weatherData.length(); i++) {
//                    JSONObject jsonWeatherData = weatherData.getJSONObject(i);
//                    try {
//                        WeatherData data = new WeatherData(jsonWeatherData);
//                        weatherDataValues.add(data);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                        ;
//                    }
//                }
//                mWeatherData.setValue(listOfContacts);
//            } else {
//                Log.e("ERROR!", "No response");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e("ERROR!", e.getMessage());
//        }
//        mContactList.setValue(mContactList.getValue());
//    }
//
//    public void connectGet() {
//        if (userInfoViewModel == null) {
//            throw new IllegalArgumentException("No UserInfoViewModel is assigned");
//        }
//        String url = getApplication().getResources().getString(R.string.base_url) +
//                "contact/?memberid=" + userInfoViewModel.getMemberId();
//
////        String url = getApplication().getResources().getString(R.string.base_url);
//
//        Request request = new JsonObjectRequest(Request.Method.GET, url, null,
//                //no body for this get request
//                this::handleResult, this::handleError) {
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                // add headers <key,value>
//                headers.put("Authorization", "Bearer " + userInfoViewModel.getJwt());
//                return headers;
//            }
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(10_000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        //Instantiate the RequestQueue and add the request to the queue
//        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
//    }
//
//    public void setUserInfoViewModel(UserInfoViewModel vm) {
//        userInfoViewModel = vm;
//    }
//

}
