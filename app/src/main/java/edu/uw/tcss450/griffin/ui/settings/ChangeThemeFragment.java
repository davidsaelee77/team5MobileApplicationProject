package edu.uw.tcss450.griffin.ui.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        binding.UWThemeButton.setOnClickListener(button -> Utils.changeToTheme(getActivity(), new SharedPreferencesManager(getContext()).retrieveInt("theme", Utils.UW_THEME)));
        binding.sonicsThemeButton.setOnClickListener(button -> Utils.changeToTheme(getActivity(), new SharedPreferencesManager(getContext()).retrieveInt("theme", Utils.SONICS_THEME)));
        binding.bluegreyThemeButton.setOnClickListener(button -> Utils.changeToTheme(getActivity(), new SharedPreferencesManager(getContext()).retrieveInt("theme", Utils.BLUEGREY_THEME)));


    }
}
