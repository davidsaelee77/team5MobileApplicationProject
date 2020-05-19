package edu.uw.tcss450.griffin.ui.weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * @author Gordon Tran
 * @version May 2020
 */
public class WeatherData implements Serializable {

    /**
     * Current weather status. (Sunny, Rainy, etc.)
     */
//    private final String mWeather;

    /**
     * Hour or day of the week. (Tuesday, 12:00, etc.)
     */
//    private final String mTime;
//
//    /**
//     * Low temperature.
//     */
//    private final String mLowTemp;
//
//    /**
//     * High temperature
//     */
//    private final String mHighTemp;

//    private final int mZipCode;


//    private List<Double> currentTime;



    /**
//     * Constructor for weather data class.
//     *
//     * @param weather String of weather status.
//     * @param time    String of hour or day of the week.
//     * @param low     Int of low temperature.
//     * @param high    Int of high temperature.
     */
//    public WeatherData(String weather, String time, String low, String high) {
//        this.mWeather = weather;
//        this.mTime = time;
//        this.mLowTemp = low;
//        this.mHighTemp = high;
////        mZipCode = zip;
//    }

    private double currentTemp;
    private String currentWeather;
    private double hourlyTempMin;
    private String hourlyWeather;
    private double dailyTempMin;
    private double dailyTempMax;
    private String dailyWeather;

    public WeatherData(JSONObject jsonCurrent, JSONObject jsonHourly, JSONObject jsonDaily) throws Exception {

        currentTemp = jsonCurrent.getDouble("temp");
        currentWeather = jsonCurrent.getString("weather");
       hourlyTempMin = jsonHourly.getDouble("tempMin");
       hourlyWeather = jsonHourly.getString("weather");
       dailyTempMin = jsonDaily.getDouble("tempMin");
       dailyTempMax = jsonDaily.getDouble("tempMax");
       dailyWeather = jsonDaily.getString("weather");

    }
//
//    /**
//     * Pulls data from JSON object.
//     * @param cmAsJson JSON string
//     * @return WeatherData object based on the JSON.
//     * @throws JSONException
//     */
//    public static WeatherData createFromJsonString(final String cmAsJson) throws JSONException {
//        final JSONObject msg = new JSONObject(cmAsJson);
//        return new WeatherData(msg.getString("weather"),msg.getString("time"),msg.getInt("low"),msg.getInt("high"));//,msg.getInt("zip"));
//    }


    public double getCurrentTemp() {
        return currentTemp;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public double getHourlyTempMin() {
        return hourlyTempMin;
    }

    public String getHourlyWeather() {
        return hourlyWeather;
    }

    public double getDailyTempMin() {
        return dailyTempMin;
    }

    public double getDailyTempMax() {
        return dailyTempMax;
    }

    public String getDailyWeather() {
        return dailyWeather;
    }

    /**
     * Gets the weather status.
     *
     * @return String of weather status.
     */
//    public String getmWeather() {
//        return mWeather;
//    }
//
//    /**
//     * Gets the time specified.
//     *
//     * @return String of time.
//     */
//    public String getmTime() {
//        return mTime;
//    }
//
//    /**
//     * Gets the low temperature.
//     *
//     * @return Int of the low temperature.
//     */
//    public String getmLowTemp() {
//        return mLowTemp;
//    }
//
//    /**
//     * Gets the high temperature.
//     *
//     * @return Int of the high temperature.
//     */
//    public String getmHighTemp() {
//        return mHighTemp;
//    }

//    public int getmZipCode() {
//        return mZipCode;
//    }





}
