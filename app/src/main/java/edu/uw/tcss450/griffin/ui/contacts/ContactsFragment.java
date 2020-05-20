package edu.uw.tcss450.griffin.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentContactsBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {
    /**
     * Empty public constructor. 
     */
    public ContactsFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ContactsFragmentArgs args = ContactsFragmentArgs.fromBundle(getArguments());
        FragmentContactsBinding binding = FragmentContactsBinding.bind(getView());
        binding.memberidTextView.setText(args.getContact().getMemberID());
        binding.usernameTextView.setText(args.getContact().getUserName());
        binding.firstnameTextView.setText(args.getContact().getFirstName());
        binding.lastnameTextView.setText(args.getContact().getLastName());

    }
}
