package edu.uw.tcss450.team5tcss450client.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.uw.tcss450.team5tcss450client.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListOfContactFragment extends Fragment {

    List<ContactList> list;

    public ListOfContactFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list_of_contact, container, false);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_contact, container, false);
        if (view instanceof RecyclerView) {
            //Try out a grid layout to achieve rows AND columns. Adjust the widths of the
            // cards on display
           // ((RecyclerView) view).setLayoutManager(new GridLayoutManager(getContext(), 2));

            //Try out horizontal scrolling. Adjust the widths of the card so that it is
            // obvious that there are more cards in either direction. i.e. don't have the cards
            // span the entire witch of the screen. Also, when considering horizontal scroll
            // on recycler view, ensure that there is other content to fill the screen.
            ((LinearLayoutManager) ((RecyclerView) view).getLayoutManager()).setOrientation(LinearLayoutManager.HORIZONTAL);
          //  ((RecyclerView) view).setAdapter(new ContactListRecyclerViewAdapter(list));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
