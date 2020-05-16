package edu.uw.tcss450.team5tcss450client.ui.contacts;

import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.team5tcss450client.R;
import edu.uw.tcss450.team5tcss450client.databinding.FragmentContactlistCardBinding;

public class ContactListRecyclerViewAdapter extends RecyclerView.Adapter<ContactListRecyclerViewAdapter.ContactListViewHolder> {

    List<Contacts> mContacts;
   // List<String> alphabet;

    public ContactListRecyclerViewAdapter(List<Contacts> contacts) {
        this.mContacts = contacts;
    }

    @NonNull
    @Override
    public ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ContactListViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_contactlist_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListViewHolder holder, int position) {

        holder.setContact(mContacts.get(position));
    }

//    public void generateAlphabet() {
//
//        for (int i = 0; i < 26; i++) {
//
//            alphabet.add(Character.toString((char) (65 + i)));
//
//        }
//    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ContactListViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public FragmentContactlistCardBinding binding;


        public ContactListViewHolder(View view) {
            super(view);
            mView = view;

            binding = FragmentContactlistCardBinding.bind(view);
            binding.seeMoreButton.setOnClickListener(this::handleMoreOrLess);

        }

        private void handleMoreOrLess(final View button) {
            if (binding.contactPreviewText.getVisibility() == View.GONE) {
                binding.contactPreviewText.setVisibility(View.VISIBLE);
                binding.seeMoreButton.setImageIcon(Icon.createWithResource(mView.getContext(), R.drawable.ic_arrow_drop_up_black_24dp));
            } else {
                binding.contactPreviewText.setVisibility(View.GONE);
                binding.seeMoreButton.setImageIcon(Icon.createWithResource(mView.getContext(), R.drawable.ic_arrow_drop_down_black_24dp));
            }
        }

        void setContact(final Contacts contact) {

            binding.contactNameText.setText(contact.getFirstName());
            binding.contactPreviewText.setText(contact.getLastName());

        }
//
//        void setAlphabet(final String alphabet) {
//
//            binding.alphabetLetterText.setText(alphabet);
//        }
    }
}

