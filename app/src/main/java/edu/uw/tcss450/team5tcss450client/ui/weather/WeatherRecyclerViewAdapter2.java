package edu.uw.tcss450.team5tcss450client.ui.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.team5tcss450client.R;
import edu.uw.tcss450.team5tcss450client.databinding.FragmentWeatherCardBinding;

public class WeatherRecyclerViewAdapter2 extends RecyclerView.Adapter<WeatherRecyclerViewAdapter2.WeatherViewHolder> {

    List<WeatherData> mData;

    public WeatherRecyclerViewAdapter2(List<WeatherData> data){
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

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private FragmentWeatherCardBinding binding;

        public WeatherViewHolder(@NonNull View view){
            super(view);
            mView = view;
            binding = FragmentWeatherCardBinding.bind(view);
        }

        void setWeather(final WeatherData data){
            binding.weatherTypeText.setText(data.getmWeather());
            binding.weatherTimeText.setText(data.getmTime());
            binding.weatherLowText.setText(data.getmLowTemp());
            binding.weatherHighText.setText(data.getmHighTemp());
        }
    }
}
