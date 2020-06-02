package edu.uw.tcss450.griffin.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uw.tcss450.griffin.MainActivity;
import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentHomeBinding;
import edu.uw.tcss450.griffin.databinding.FragmentHomeNotificationListBinding;
import edu.uw.tcss450.griffin.ui.contacts.ContactListViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeNotificationListFragment extends Fragment {

    private FragmentHomeBinding binding;

    private HomeNotificationListViewModel mModel;

    public HomeNotificationListFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(HomeNotificationListViewModel.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentHomeNotificationListBinding binding = FragmentHomeNotificationListBinding.bind(getView());

        mModel.addHomeNotificationListObserver(getViewLifecycleOwner(), requestNotifications -> {
            if (!requestNotifications.isEmpty()) {
                binding.homenotificationslistRoot.setAdapter(new HomeNotificationRecylcerViewAdapter(requestNotifications));
            }
        });


    }
}
