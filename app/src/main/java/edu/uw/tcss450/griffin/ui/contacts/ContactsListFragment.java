package edu.uw.tcss450.griffin.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.griffin.MainActivity;
import edu.uw.tcss450.griffin.databinding.FragmentContactListBinding;


/**
 * @author David Saelee
 * @version May 2020
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsListFragment extends Fragment {
    /**
     * ContactListViewModel object.
     */
    private ContactListViewModel mModel;
    /**
     * FragmentContactListBinding object.
     */
    private FragmentContactListBinding binding;
    //private List<Contacts> list;
    // private List<String> alphabet;

    /**
     * Empty public constructor.
     */
    public ContactsListFragment() {
        // Required empty public constructor

//        list = new ArrayList<Contacts>();
//        alphabet = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            mModel.setUserInfoViewModel(activity.getUserInfoViewModel());
        }
        mModel.connectGet();
        //generateAlphabet();
        //generateRandomData();
    }

    /**
     * Instantiates contact fragment UI view.
     *
     * @param inflater           object to inflate any view in layout.
     * @param container          parent view fragment UI is attached to.
     * @param savedInstanceState reconstructed fragment from previous saved state.
     * @return home fragment view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentContactListBinding.inflate(inflater);

        return binding.getRoot();
    }

    /**
     * Contact fragment view constructor
     *
     * @param view               returned by onCreateView.
     * @param savedInstanceState reconstructed fragment from previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentContactListBinding binding = FragmentContactListBinding.bind(getView());

        binding.imageButtonAddContactContactfragment.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(ContactsListFragmentDirections.actionContactListFragmentToAddContactFragment()));

        binding.imageButtonRequestContactContactfragment.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(ContactsListFragmentDirections.actionContactListFragmentToRequestContactFragment()));

        mModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {

            if (!contactList.isEmpty()) {
                binding.listRoot.setAdapter(new ContactListRecyclerViewAdapter(contactList));
            }
        });

    }
}

