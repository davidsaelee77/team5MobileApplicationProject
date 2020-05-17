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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.team5tcss450client.R;
import edu.uw.tcss450.team5tcss450client.databinding.FragmentWeatherListBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherListFragment extends Fragment {

    private WeatherViewModel mModel;

    private FragmentWeatherListBinding binding;

    private List<WeatherData> list;

    public WeatherListFragment() {
        // Required empty public constructor
        list = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
        generate();
    }

    public void generate() {
        for (int i = 0; i < 10; i++) {
            list.add(new WeatherData("Sunny", "Wednesday", 56 + i + "", 70 + i + ""));
        }
    }

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
                binding.listPage.setAdapter(new WeatherRecyclerViewAdapter(list));
            }
        });
    }
}
