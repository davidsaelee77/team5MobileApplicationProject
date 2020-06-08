package edu.uw.tcss450.griffin.ui.contacts;

import android.app.AlertDialog;
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

/**
 * @author David Salee
 * @version May 2020
 */
public class ContactListRecyclerViewAdapter extends RecyclerView.Adapter<ContactListRecyclerViewAdapter.ContactListViewHolder> {


    private final ContactsListFragment mParent;

    /**
     * List of contacts.
     */
    List<Contacts> mContacts;

    /**
     * Constructor that instantiates fields.
     *
     * @param contacts
     */
    public ContactListRecyclerViewAdapter(List<Contacts> contacts, ContactsListFragment parent) {
        this.mContacts = contacts;
        this.mParent = parent;
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

    /**
     * Helper class that creates a view holder.
     */
    public class ContactListViewHolder extends RecyclerView.ViewHolder {
        /**
         * View object.
         */
        public final View mView;

        /**
         * FragmentConcactListCardBinding object.
         */
        public FragmentContactlistCardBinding binding;

        /**
         * Constructor that creates the view holder.
         *
         * @param view
         */
        public ContactListViewHolder(View view) {
            super(view);
            mView = view;

            binding = FragmentContactlistCardBinding.bind(view);
            binding.buttonMore.setOnClickListener(this::handleMoreOrLess);

        }

        /**
         * Method that makes a button show more of a card or show less.
         *
         * @param button
         */
        private void handleMoreOrLess(final View button) {
            if (binding.textPreview.getVisibility() == View.GONE) {
                binding.textPreview.setVisibility(View.VISIBLE);
                binding.buttonMore.setImageIcon(Icon.createWithResource(mView.getContext(), R.drawable.ic_arrow_drop_up_black_24dp));
            } else {
                binding.textPreview.setVisibility(View.GONE);
                binding.buttonMore.setImageIcon(Icon.createWithResource(mView.getContext(), R.drawable.ic_arrow_drop_down_black_24dp));
            }
        }

        /**
         * Method that sets contacts.
         */
        void setContact(final Contacts contact) {

            binding.buttonDelete.setOnClickListener(view -> deleteContact(this, contact));
            binding.textviewMemberID.setText(contact.getMemberID());
            binding.textviewUsername.setText(contact.getUserName());
            binding.textviewFirstName.setText(contact.getUserName());
            binding.textviewLastName.setText(contact.getLastName());

        }
    }

    private void deleteContact(final ContactListViewHolder view, final Contacts contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mParent.getActivity());
        builder.setTitle(R.string.dialog_contactListRecycler_title);
        builder.setMessage(R.string.dialog_contactListRecycler_message);
        builder.setPositiveButton(R.string.dialog_contactListRecycler_positive, (dialog, which) -> {
            mContacts.remove(contact);
            notifyItemRemoved(view.getLayoutPosition());
            final String memberId = contact.getMemberID();
            mParent.deleteContact(Integer.parseInt(memberId));
        });
        builder.setNegativeButton(R.string.dialog_chatListRecycler_negative, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}

