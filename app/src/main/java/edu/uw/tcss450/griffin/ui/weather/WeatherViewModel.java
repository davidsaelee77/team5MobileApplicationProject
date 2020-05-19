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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.model.UserInfoViewModel;


public class WeatherViewModel extends AndroidViewModel {

    private MutableLiveData<List<WeatherData>> mWeatherData;

    private UserInfoViewModel userInfoViewModel;
    // private List<WeatherData> list;


    public WeatherViewModel(@NonNull Application application) {
        super(application);
//        list = new ArrayList<>();

        mWeatherData = new MutableLiveData<>();

//        for (int i = 0; i < 10; i++) {
//            list.add(new WeatherData("Sunny", "Wednesday", 56 + i + "", 70 + i + ""));
//        }
//
//        mWeatherData.setValue((list));
    }

    public void addWeatherObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<WeatherData>> observer) {
        mWeatherData.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        if (error != null && error.getMessage() != null) {
            Log.e("CONNECTION ERROR", error.getMessage());
            throw new IllegalStateException(error.getMessage());
        }
    }

    private void handleResult(final JSONObject result) {

//        if (!result.has("current")) {
//            throw new IllegalStateException("Unexpected response in WeatherViewModel: " + result);
//        }
        try {


            Log.d("TEST", result.toString());
            Log.d("TEST2", result + "");


            ArrayList<JSONObject> list = new ArrayList<>();

//            JSONObject currentTemp = result.getJSONObject("current");
//
//            list.add(currentTemp);

            JSONObject hour = result.getJSONObject("hourly");

            list.add(hour);

            Log.d("TESTER", list.toString());

            ArrayList<WeatherData> weatherData = new ArrayList<>();
           // JSONObject currentTemp = result.getJSONObject("current");
            //  String currentWeather = result.getString("weather");
            //JSONArray hourly = result.getJSONArray("hourly");
//            for (int i = 0; i < hourly.length(); i++) {
//                JSONObject hwd = hourly.getJSONObject(i);
//
//                WeatherData wd = new WeatherData(hwd);
//            JSONArray daily = result.getJSONArray("daily");

            //  for (int i = 0; i < hourly.length(); i++) {
            //  for (int j = 0; j < daily.length(); j++) {
            //   JSONObject hwd = hourly.getJSONObject(i);
            //  JSONObject dwd = daily.getJSONObject(j);

            //WeatherData wd = new WeatherData(currentTemp, hwd, dwd);
            //weatherData.add(wd);
            mWeatherData.setValue(weatherData);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void connectGet() {
        if (userInfoViewModel == null) {
            throw new IllegalArgumentException("No UserInfoViewModel is assigned");
        }
        String url = getApplication().getResources().getString(R.string.base_url) +
                "weather";

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
