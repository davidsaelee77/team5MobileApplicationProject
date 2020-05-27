package edu.uw.tcss450.griffin.ui.settings;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.griffin.MainActivity;
import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.application.GriffinApplication;
import edu.uw.tcss450.griffin.databinding.FragmentChangeThemeBinding;
import edu.uw.tcss450.griffin.util.SharedPreferencesManager;
import edu.uw.tcss450.griffin.util.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeThemeFragment extends Fragment {

    FragmentChangeThemeBinding binding;

    /**
     * Empty public constructor.
     */
    public ChangeThemeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeThemeBinding.inflate(inflater);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        GriffinApplication.id = R.style.AppTheme;

//        binding.UWThemeButton.setOnClickListener(button -> changeTheme(R.style.AppTheme));
//        binding.sonicsThemeButton.setOnClickListener(button -> changeTheme(R.style.SonicsTheme));
//        binding.bluegreyThemeButton.setOnClickListener(button -> changeTheme(R.style.BluegreyTheme));

        binding.UWThemeButton.setOnClickListener(button -> Utils.changeToTheme(getActivity(), new SharedPreferencesManager(getContext()).retrieveInt("theme", Utils.UW_THEME)));
        binding.sonicsThemeButton.setOnClickListener(button -> Utils.changeToTheme(getActivity(), new SharedPreferencesManager(getContext()).retrieveInt("theme", Utils.SONICS_THEME)));
        binding.bluegreyThemeButton.setOnClickListener(button -> Utils.changeToTheme(getActivity(), new SharedPreferencesManager(getContext()).retrieveInt("theme", Utils.BLUEGREY_THEME)));


    }

    /**
     * Method to change themes.
     *
     * @param id Integer theme id.
     */
//    public void changeTheme(int id) {
//
//        GriffinApplication.id = id;
//
//        getActivity().recreate();
//
//    }
}
