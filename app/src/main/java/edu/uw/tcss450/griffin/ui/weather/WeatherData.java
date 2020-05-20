package edu.uw.tcss450.griffin.ui.weather;

import java.io.Serializable;

/**
 * @author Gordon Tran
 * @version May 2020
 */
public class WeatherData implements Serializable {

    // Daily, current, or hour
    private String type;

    // Increments past 0; defaults to 0
    private int increment;

    private double tempMin;
    private double tempMax;
    private double temp;
    private String weather;


    public WeatherData(String theType, int theIncrement, double tempMin, double tempMax, double temp, String theWeather) {
        this.type = theType;
        this.increment = theIncrement;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.temp = temp;
        this.weather = theWeather;
    }

    /**
     * Daily weather constructor
     * @param theIncrement
     * @param temp
     * @param theWeather
     */
    public WeatherData(String theType, int theIncrement, double temp, String theWeather) {
        this(theType, theIncrement, -1, -1, temp, theWeather);
    }

    /**
     *
     * @param theIncrement
     * @param tempMin
     * @param tempMax
     * @param theWeather
     */
    public WeatherData(int theIncrement, double tempMin, double tempMax, String theWeather) {
        this("daily", theIncrement, tempMin, tempMax, -1, theWeather);
    }

    public String getType() {
        return type;
    }

    public int getIncrement() {
        return increment;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getTemp() {
        return temp;
    }

    public String getWeather() {
        return weather;
    }

}
