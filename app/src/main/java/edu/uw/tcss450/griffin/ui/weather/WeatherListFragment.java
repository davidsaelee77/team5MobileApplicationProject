package edu.uw.tcss450.griffin.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import edu.uw.tcss450.griffin.MainActivity;
import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentWeatherListBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherListFragment extends Fragment {
    /**
     * WeatherViewModel object. 
     */
    private WeatherViewModel mModel;
    /**
     * FragmentWeatherListBinding object. 
     */
    private FragmentWeatherListBinding binding;
    /**
     * List of Weather data for hourly weather.
     */
    private List<WeatherData> hourList;
    /**
     * List of Weather data for daily weather.
     */
    private List<WeatherData> dayList;
   // private List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thurdsay", "Friday", "Saturday");
    /**
     * Empty public constructor. 
     */
    public WeatherListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);

        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            mModel.setUserInfoViewModel(activity.getUserInfoViewModel());
            mModel.connectGet();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWeatherListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentWeatherListBinding binding = FragmentWeatherListBinding.bind(getView());
        binding.weatherSearchButton.setOnClickListener(this::searchZip);

        mModel.addWeatherObserver(getViewLifecycleOwner(), weatherList -> {
            if (!weatherList.isEmpty()) {

                hourList = weatherList.subList(1, 25);
                dayList = weatherList.subList(25, 30);

                binding.recyclerViewToday.setAdapter(new WeatherRecyclerViewAdapter(hourList));
                binding.recyclerViewWeekly.setAdapter(new WeatherWeekRecyclerViewAdapter(dayList));
                binding.textviewCurrentData.setText(weatherList.get(0).getWeather() +
                        ", " + String.format("%.2f", weatherList.get(0).getTemp()) + " F");
                binding.weatherPlaceText.setText("Tacoma, 98402");
                if (weatherList.get(0).getWeather().equals("Thunderstorm")){
                    binding.weatherIconImage.setImageResource(R.drawable.weather_thunder_art);
                } else if (weatherList.get(0).getWeather().equals("Drizzle")){
                    binding.weatherIconImage.setImageResource(R.drawable.weather_drizzle_art);
                } else if (weatherList.get(0).getWeather().equals("Rain")){
                    binding.weatherIconImage.setImageResource(R.drawable.weather_rain_art);
                }else if (weatherList.get(0).getWeather().equals("Snow")){
                    binding.weatherIconImage.setImageResource(R.drawable.weather_snow_art);
                } else if (weatherList.get(0).getWeather().equals("Mist")){
                    binding.weatherIconImage.setImageResource(R.drawable.weather_mist_art);
                } else if (weatherList.get(0).getWeather().equals("Clear")){
                    binding.weatherIconImage.setImageResource(R.drawable.weather_clear_art);
                } else if (weatherList.get(0).getWeather().equals("Clouds")){
                    binding.weatherIconImage.setImageResource(R.drawable.weather_clouds_art);
                }
            }
        });
    }

    private void searchZip(View view) {
        mModel.connectGet(binding.weatherPlaceText.getText().toString());
    }
}
