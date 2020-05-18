package edu.uw.tcss450.griffin.ui.contacts;

import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentContactlistCardBinding;

public class ContactListRecyclerViewAdapter extends RecyclerView.Adapter<ContactListRecyclerViewAdapter.ContactListViewHolder> {

    List<Contacts> mContacts;

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


            // binding.alphabetLetterText.setText(contact.getAlphabet());

            // if (contact.getAlphabet().indexOf(0) == contact.getFirstName().charAt(0)) {


            binding.buttonFullPost.setOnClickListener(view -> Navigation.findNavController(mView).navigate(ContactsListFragmentDirections.actionContactListFragmentToContactsFragment(contact)));


            binding.memberidText.setText(contact.getMemberID());
            binding.contactUsernameText.setText(contact.getUserName());
            binding.contactFirstNameText.setText(contact.getFirstName());
            binding.contactLastNameText.setText(contact.getLastName());


            // }
        }
    }


}

