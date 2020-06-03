package edu.uw.tcss450.griffin.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.griffin.MainActivity;
import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentRequestContactBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestContactFragment extends Fragment implements View.OnClickListener {


    private ContactListViewModel mModel;

    FragmentRequestContactBinding binding;

    public RequestContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            mModel.setUserInfoViewModel(activity.getUserInfoViewModel());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRequestContactBinding.inflate(inflater);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentRequestContactBinding binding = FragmentRequestContactBinding.bind(getView());


        mModel.addRequestListObserver(getViewLifecycleOwner(), requestList -> {
            if (!requestList.isEmpty()) {
                binding.recyclerViewRequests.setAdapter(new ContactRequestRecyclerViewAdapter(requestList));
            }
        });

    }


    @Override
    public void onClick(View v) {

    }
}
