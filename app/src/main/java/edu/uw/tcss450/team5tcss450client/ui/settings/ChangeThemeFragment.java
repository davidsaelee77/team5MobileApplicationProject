package edu.uw.tcss450.team5tcss450client.ui.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team5tcss450client.R;
import edu.uw.tcss450.team5tcss450client.application.GriffinApplication;
import edu.uw.tcss450.team5tcss450client.databinding.FragmentChangeThemeBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeThemeFragment extends Fragment {

    FragmentChangeThemeBinding binding;

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


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GriffinApplication.id = R.style.AppTheme;

        binding.UWThemeButton.setOnClickListener(button -> changeTheme(R.style.AppTheme));
        binding.sonicsThemeButton.setOnClickListener(button -> changeTheme(R.style.SonicsTheme));
        binding.bluegreyThemeButton.setOnClickListener(button -> changeTheme(R.style.BluegreyTheme));
    }

    public void changeTheme(int id) {

        GriffinApplication.id = id;

        getActivity().recreate();

    }
}
