package edu.uw.tcss450.team5tcss450client.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.team5tcss450client.databinding.FragmentContactListBinding;

import static edu.uw.tcss450.team5tcss450client.ui.contacts.ContactsGenerator.randomIdentifier;


/**
 * @author David Saelee
 * @version May 2020
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsListFragment extends Fragment {

    private ContactListViewModel mModel;

    private FragmentContactListBinding binding;

    private RecyclerView rv;

    private List<Contacts> list = new ArrayList<Contacts>();

    public ContactsListFragment() {
        // Required empty public constructor
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        // mModel.connectGet();
        // mModel.connect("BOB", "JONES");
        generateRandomData();

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

    public void generateRandomData() {

        for (int i = 0; i < 10; i++) {

            list.add(new Contacts(randomIdentifier(), randomIdentifier(), randomIdentifier()));
        }
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

        mModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {

            if (!contactList.isEmpty()) {
                binding.listRoot.setAdapter(new ContactListRecyclerViewAdapter(list));

            }
        });
    }
}
