package edu.uw.tcss450.griffin.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import edu.uw.tcss450.griffin.MainActivity;
import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentAddContactBinding;
import edu.uw.tcss450.griffin.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends Fragment implements
        View.OnClickListener {

    private ContactListViewModel mModel;

    private String[] searchResult = new String[]{"DevPat", "cfb3", "dsael1"};

    private UserInfoViewModel mUsermodel;

    private ArrayAdapter<String> adapter;

    FragmentAddContactBinding binding;

    public AddContactFragment() {
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
        mUsermodel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddContactBinding.inflate(inflater);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AutoCompleteTextView editText = getView().findViewById(R.id.editText_searchUsername_addcontactfragment);
        //adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, searchResult);
        //editText.setAdapter(adapter);

        mModel.addSearchResultObserver(getViewLifecycleOwner(), result -> {
            editText.setAdapter(new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, result));
        });

        binding.editTextSearchUsernameAddcontactfragment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                //send new information to the server to update testSearch
                Log.d("CONTACTS", "searching for new string: " + s.toString());
                mModel.connectGetSearch(s.toString());
                //searchResult = mModel.getSearchResult();
            }
        });
        binding.editTextSearchUsernameAddcontactfragment.setOnClickListener(this);
        binding.imageButtonSearchUserContactfragment.setOnClickListener(this);

    }

    public Boolean usernameValidation(String username) {

        if (username.equals("")) {
            binding.editTextSearchUsernameAddcontactfragment.setError("One or more blank username(s)");
            binding.editTextSearchUsernameAddcontactfragment.requestFocus();
            return false;
        } else if (username.length() > 32 || username.length() < 4) {
            binding.editTextSearchUsernameAddcontactfragment.setError("Valid usernames are 4-32 characters long");
            binding.editTextSearchUsernameAddcontactfragment.requestFocus();
            return false;
        } else if (!username.matches("^\\w+$")) {
            binding.editTextSearchUsernameAddcontactfragment.setError("Usernames must be alphanumeric");
            binding.editTextSearchUsernameAddcontactfragment.requestFocus();
            return false;
        } else {
            binding.editTextSearchUsernameAddcontactfragment.setError(null);
            return true;
        }
    }

    @Override
    public void onClick(View v) {

        if (usernameValidation(binding.editTextSearchUsernameAddcontactfragment.getText().toString())) {

            if (v == binding.imageButtonSearchUserContactfragment) {
                mModel.connectAddContactPost(binding.editTextSearchUsernameAddcontactfragment.getText().toString());
                // Navigation.findNavController(getView()).navigate(RequestContactFragmentDirections.actionRequestContactFragmentToContactListFragment());
            }

        }
    }

}
