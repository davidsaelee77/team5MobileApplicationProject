package edu.uw.tcss450.griffin.ui.weather;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentWeatherCardBinding;

/**
 * @author David Salee
 * @version May 2020
 */
public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherViewHolder> {
    /**
     * List of type WeatherData.
     */
    List<WeatherData> mData;
    /**
     * Constructor that instantiates fields. 
     * @param data
     */
    public WeatherRecyclerViewAdapter(List<WeatherData> data) {
        this.mData = data;

    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherViewHolder((LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_weather_card, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        holder.setWeather(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * Helper class that creates view holder. 
     */
    class WeatherViewHolder extends RecyclerView.ViewHolder {
        /**
         * View object. 
         */
        private final View mView;
        /**
         * FragmentWeatherCardBinding object. 
         */
        private FragmentWeatherCardBinding binding;
         /**
          * Constructor that instantiantes fields. 
          * @param view 
          */
        public WeatherViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            binding = FragmentWeatherCardBinding.bind(view);
        }
         /**
          * Method that sets weather data.
          */
        void setWeather(final WeatherData data) {
            binding.textviewTime.setText(String.valueOf(data.getIncrement()) + ":00");
            binding.textviewType.setText(data.getWeather());
            if (data.getTemp() == -1 || data.getTemp() < -459) {
//                binding.weatherLowText.setText(String.valueOf(data.getTempMin()));
//                binding.weatherHighText.setText(String.valueOf(data.getTempMax()));
                binding.textviewHighTemp.setText(String.format("%.2f", data.getTempMax()));
            } else {
                binding.textviewHighTemp.setText(String.format("%.2f", data.getTemp()));
            }
        }
    }
}
