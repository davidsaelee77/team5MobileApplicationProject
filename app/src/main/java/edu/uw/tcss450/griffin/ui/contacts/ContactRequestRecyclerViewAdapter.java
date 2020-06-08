package edu.uw.tcss450.griffin.ui.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentContactrequestCardBinding;

public class ContactRequestRecyclerViewAdapter extends RecyclerView.Adapter<ContactRequestRecyclerViewAdapter.ContactRequestViewHolder> {

    private final RequestContactFragment mParent;

    List<Contacts> mContactRequests;


    public ContactRequestRecyclerViewAdapter(List<Contacts> requests, RequestContactFragment parent) {
        this.mContactRequests = requests;
        this.mParent = parent;
    }

    @NonNull
    @Override
    public ContactRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactRequestViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_contactrequest_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRequestViewHolder holder, int position) {
        holder.setRequest(mContactRequests.get(position));
    }

    @Override
    public int getItemCount() {
        return mContactRequests.size();
    }

    public class ContactRequestViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public FragmentContactrequestCardBinding binding;

        public ContactRequestViewHolder(View view) {
            super(view);
            mView = view;

            binding = FragmentContactrequestCardBinding.bind(view);


        }

        void setRequest(final Contacts contact) {
            binding.textviewFirstName.setText(contact.getUserName());
            String fullName = contact.getFirstName() + " " + contact.getLastName();
            binding.textviewLastName.setText(fullName);
            binding.buttonAcceptContact.setOnClickListener(view -> acceptRequest(this, contact));
            binding.buttonDeclineContact.setOnClickListener(view -> declineRequest(this, contact));
        }

        private void acceptRequest(final ContactRequestViewHolder view, Contacts contact) {
            mContactRequests.remove(contact);
            notifyItemRemoved(view.getLayoutPosition());
            final int memberId = Integer.parseInt(contact.getMemberID());
            mParent.acceptContact(memberId);
            Navigation.findNavController(mView).navigate(RequestContactFragmentDirections.actionRequestContactFragmentToContactListFragment());
        }

        private void declineRequest(final ContactRequestViewHolder view, Contacts contact) {
            mContactRequests.remove(contact);
            notifyItemRemoved(view.getLayoutPosition());
            final int memberId = Integer.parseInt(contact.getMemberID());
            mParent.deleteContact(memberId);
        }
    }
}
