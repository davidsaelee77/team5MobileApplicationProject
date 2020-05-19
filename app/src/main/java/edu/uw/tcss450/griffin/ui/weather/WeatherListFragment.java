package edu.uw.tcss450.griffin.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.uw.tcss450.griffin.MainActivity;
import edu.uw.tcss450.griffin.databinding.FragmentWeatherListBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherListFragment extends Fragment {

    private WeatherViewModel mModel;

    private FragmentWeatherListBinding binding;

    private List<WeatherData> list;
    private List<WeatherData> dayList;
    private List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thurdsay", "Friday", "Saturday");

    public WeatherListFragment() {
        // Required empty public constructor
//        list = new ArrayList<>();
//        dayList = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);

        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            mModel.setUserInfoViewModel(activity.getUserInfoViewModel());
//        generate();
            mModel.connectGet();
        }
    }

    /**
     * Method to generate dummy data.
     */
//    public void generate() {
//        for (int i = 0; i < 7; i++) {
//            list.add(new WeatherData("Sunny", days.get(i), 56 - i + "", 70 - i + ""));
//        }
//        for (int i = 0; i < 24; i++) {
//
//            dayList.add(new WeatherData("Sunny", i + ":00", 56 + i + "", 70 + i + ""));
//        }
//
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_weather_list, container, false);
        binding = FragmentWeatherListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentWeatherListBinding binding = FragmentWeatherListBinding.bind(getView());

        mModel.addWeatherObserver(getViewLifecycleOwner(), weatherList -> {
            if (!weatherList.isEmpty()) {
                //Using dummy data
                binding.weatherTodayList.setAdapter(new WeatherRecyclerViewAdapter(weatherList));
                binding.weatherWeekList.setAdapter(new WeatherRecyclerViewAdapter(weatherList));
                binding.weatherCurrentText.setText("BLAH");
                binding.weatherPlaceText.setText("Tacoma");


            }
        });
    }
}
