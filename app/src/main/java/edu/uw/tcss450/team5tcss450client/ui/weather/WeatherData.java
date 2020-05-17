package edu.uw.tcss450.team5tcss450client.ui.weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author Gordon Tran
 * @version May 2020
 */
public class WeatherData implements Serializable {

    /**
     * Current weather status. (Sunny, Rainy, etc.)
     */
    private final String mWeather;

    /**
     * Hour or day of the week. (Tuesday, 12:00, etc.)
     */
    private final String mTime;

    /**
     * Low temperature.
     */
    private final int mLowTemp;

    /**
     * High temperature
     */
    private final int mHighTemp;

//    private final int mZipCode;


    /**
     * Constructor for weather data class.
     * @param weather String of weather status.
     * @param time String of hour or day of the week.
     * @param low Int of low temperature.
     * @param high Int of high temperature.
     */
    public WeatherData(String weather, String time, int low, int high){
        mWeather = weather;
        mTime = time;
        mLowTemp = low;
        mHighTemp = high;
//        mZipCode = zip;
    }

    /**
     * Pulls data from JSON object.
     * @param cmAsJson JSON string
     * @return WeatherData object based on the JSON.
     * @throws JSONException
     */
    public static WeatherData createFromJsonString(final String cmAsJson) throws JSONException {
        final JSONObject msg = new JSONObject(cmAsJson);
        return new WeatherData(msg.getString("weather"),msg.getString("time"),msg.getInt("low"),msg.getInt("high"));//,msg.getInt("zip"));
    }


    /**
     * Gets the weather status.
     * @return String of weather status.
     */
    public String getmWeather() {
        return mWeather;
    }

    /**
     * Gets the time specified.
     * @return String of time.
     */
    public String getmTime() {
        return mTime;
    }

    /**
     * Gets the low temperature.
     * @return Int of the low temperature.
     */
    public int getmLowTemp() {
        return mLowTemp;
    }

    /**
     * Gets the high temperature.
     * @return Int of the high temperature.
     */
    public int getmHighTemp() {
        return mHighTemp;
    }

//    public int getmZipCode() {
//        return mZipCode;
//    }

}
