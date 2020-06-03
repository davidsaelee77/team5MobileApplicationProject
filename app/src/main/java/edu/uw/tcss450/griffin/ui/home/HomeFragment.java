package edu.uw.tcss450.griffin.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.griffin.MainActivity;
import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentHomeBinding;
import edu.uw.tcss450.griffin.databinding.FragmentHomeNotificationListBinding;
import edu.uw.tcss450.griffin.model.Notification;
import edu.uw.tcss450.griffin.model.UserInfoViewModel;
import edu.uw.tcss450.griffin.ui.weather.WeatherRecyclerViewAdapter;
import edu.uw.tcss450.griffin.ui.weather.WeatherViewModel;
import edu.uw.tcss450.griffin.ui.weather.WeatherWeekRecyclerViewAdapter;

/**
 * @author David Saelee
 * @version May 2020
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    /**
     * WeatherViewModel object for retrieving the default location current data
     */
    private WeatherViewModel mWeatherModel;

    private HomeNotificationListViewModel mModel;

//    private List<Notification> list = new ArrayList<>();


    /**
     * FragmentHomeBinding object.
     */
    private FragmentHomeBinding binding;

    /**
     * Empty public constructor. 
     */
    public HomeFragment() {
        // Required empty public constructor
    }


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeatherModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            mWeatherModel.setUserInfoViewModel(activity.getUserInfoViewModel());
            mWeatherModel.connectGet();
        }
        mModel = new ViewModelProvider(getActivity()).get(HomeNotificationListViewModel.class);

        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            mModel.setUserInfoViewModel(activity.getUserInfoViewModel());
        }

       // generateRandomData();
    }

//    public void generateRandomData() {
//
//        for (int i = 0; i < 5; i++) {
//
//            list.add(new HomeNotifications(i));
//        }
//    }

    /**
     * Instantiates home fragment UI view.
     *
     * @param inflater object to inflate any view in layout.
     * @param container parent view fragment UI is attached to.
     * @param savedInstanceState reconstructed fragment from previous saved state.
     * @return home fragment view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    /**
     * Home fragment view constructor
     *
     * @param view returned by onCreateView.
     * @param savedInstanceState reconstructed fragment from previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWeatherModel.addLocationObserver(getViewLifecycleOwner(), location -> {
            binding.textViewLocation.setText(location.get("city"));
        });

        mWeatherModel.addWeatherObserver(getViewLifecycleOwner(), weatherList -> {
            binding.textviewCurrentData.setText(weatherList.get(0).getWeather() +
                    ", " + String.format("%.2f", weatherList.get(0).getTemp()) + " F");
            if (weatherList.get(0).getWeather().equals("Thunderstorm")) {
                binding.imageiconWeatherIcon.setImageResource(R.drawable.weather_thunder_art);
            } else if (weatherList.get(0).getWeather().equals("Drizzle")) {
                binding.imageiconWeatherIcon.setImageResource(R.drawable.weather_drizzle_art);
            } else if (weatherList.get(0).getWeather().equals("Rain")) {
                binding.imageiconWeatherIcon.setImageResource(R.drawable.weather_rain_art);
            } else if (weatherList.get(0).getWeather().equals("Snow")) {
                binding.imageiconWeatherIcon.setImageResource(R.drawable.weather_snow_art);
            } else if (weatherList.get(0).getWeather().equals("Mist")) {
                binding.imageiconWeatherIcon.setImageResource(R.drawable.weather_mist_art);
            } else if (weatherList.get(0).getWeather().equals("Clear")) {
                binding.imageiconWeatherIcon.setImageResource(R.drawable.weather_clear_art);
            } else if (weatherList.get(0).getWeather().equals("Clouds")) {
                binding.imageiconWeatherIcon.setImageResource(R.drawable.weather_clouds_art);
            }
        });

        mModel.addHomeNotificationListObserver(getViewLifecycleOwner(), requestNotifications -> {
            if (!requestNotifications.isEmpty()) {
                binding.homenotificationslistRoot.setAdapter(new HomeNotificationRecylcerViewAdapter(requestNotifications));
            }
        });
    }

}
