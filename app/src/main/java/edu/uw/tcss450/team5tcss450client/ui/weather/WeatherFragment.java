package edu.uw.tcss450.team5tcss450client.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.team5tcss450client.R;
import edu.uw.tcss450.team5tcss450client.databinding.FragmentWeatherBinding;

/**
 *
 * @author David Saelee
 * @version May 2020
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {
    private WeatherViewModel mWeatherModel;

    List<WeatherData> dummyData = new ArrayList<WeatherData>();


    public WeatherFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ViewModelProvider provider = new ViewModelProvider(getActivity());
//        mWeatherModel = provider.get(WeatherViewModel.class);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentWeatherBinding binding = FragmentWeatherBinding.bind(getView());
//
//        //Testing dummy data
//        dummyData.add(new WeatherData("Sunny", "Tuesday", 56, 70));
//        dummyData.add(new WeatherData("Sunny", "Wednesday", 56, 70));
//        dummyData.add(new WeatherData("Sunny", "Thursday", 56, 70));
//
//        binding.weatherTodayList.setAdapter(new WeatherRecyclerViewAdapter(dummyData));

//        binding.weatherHourList.setAdapter(new WeatherRecyclerViewAdapter(dummyData));
//        binding.weatherWeekList.setAdapter(new WeatherRecyclerViewAdapter(dummyData));
    }
}
