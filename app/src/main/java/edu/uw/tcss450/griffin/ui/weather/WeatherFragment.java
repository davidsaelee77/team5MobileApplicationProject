package edu.uw.tcss450.griffin.ui.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.griffin.R;

/**
 *
 * @author David Saelee
 * @version May 2020
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {


    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Instantiates weather fragment UI view.
     *
     * @param inflater           object to inflate any view in layout.
     * @param container          parent view fragment UI is attached to.
     * @param savedInstanceState reconstructed fragment from previous saved state.
     * @return home fragment view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }
}
