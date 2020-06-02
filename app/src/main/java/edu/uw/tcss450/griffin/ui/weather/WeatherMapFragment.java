package edu.uw.tcss450.griffin.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentWeatherMapBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    /**
     * WeatherMapViewModel object.
     */
    private WeatherMapViewModel mModel;
    /**
     * WeatherViewModel object.
     */
    private WeatherViewModel mModelData;
    /**
     * GoogleMap object.
     */
    private GoogleMap mMap;
    /**
     * LatLng object.
     */
    private LatLng mLatLng;
    /**
     * Empty public constructor.
     */
    public WeatherMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentWeatherMapBinding binding = FragmentWeatherMapBinding.bind(getView());
        mModel = new ViewModelProvider(getActivity())
                .get(WeatherMapViewModel.class);
        mModel.addLocationObserver(getViewLifecycleOwner(), location ->
                binding.textLatLong.setText(location.toString()));
        binding.buttonSearch.setOnClickListener(this::searchLatLong);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //add this fragment as the OnMapReadyCallback -> See onMapReady()
        mapFragment.getMapAsync(this);
    }

    /**
     * Function for passing latitude and longitude on button press.
     * @param view
     */
    private void searchLatLong(View view) {
        WeatherMapFragmentDirections.ActionWeatherMapFragmentToWeatherListFragment directions = WeatherMapFragmentDirections.actionWeatherMapFragmentToWeatherListFragment();
        directions.setLat(Double.toString(mLatLng.latitude));
        directions.setLng(Double.toString(mLatLng.longitude));
        Navigation.findNavController(getView()).navigate(directions);
//        Navigation.findNavController(getView()).navigate(WeatherMapFragmentDirections.actionWeatherMapFragmentToWeatherListFragment());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        FragmentWeatherMapBinding binding = FragmentWeatherMapBinding.bind(getView());
        WeatherMapViewModel model = new ViewModelProvider(getActivity())
                .get(WeatherMapViewModel.class);
        model.addLocationObserver(getViewLifecycleOwner(), location -> {
            if(location != null) {
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setMyLocationEnabled(true);
                final LatLng c = new LatLng(location.getLatitude(), location.getLongitude());
                mLatLng = c;
                //Zoom levels are from 2.0f (zoomed out) to 21.f (zoomed in)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(c, 15.0f));
                binding.textLatLong.setText("Latitude:" + Double.toString(c.latitude) + "\nLongitude:" + Double.toString(c.longitude));
            }
        });
        mMap.setOnMapClickListener(this);

    }

    @Override
    public void onMapClick(LatLng latLng) {
        FragmentWeatherMapBinding binding = FragmentWeatherMapBinding.bind(getView());
        Log.d("LAT/LONG", latLng.toString());
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("New Marker"));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        latLng, mMap.getCameraPosition().zoom));
        binding.textLatLong.setText("Latitude:" + Double.toString(latLng.latitude) + "\nLongitude:" + Double.toString(latLng.longitude));
        mLatLng = latLng;
    }

}
