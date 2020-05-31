package edu.uw.tcss450.griffin.ui.contacts;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentContactrequestCardBinding;

public class ContactRequestRecyclerViewAdapter extends RecyclerView.Adapter<ContactRequestRecyclerViewAdapter.ContactRequestViewHolder> {

    List<Contacts> mContactRequests;

    public ContactRequestRecyclerViewAdapter(List<Contacts> requests) {
        this.mContactRequests = requests;
    }

    @NonNull
    @Override
    public ContactRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactRequestViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_contactrequest_card, parent, false));
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
            //binding.buttonSeeMore.setOnClickListener(this::acceptRequest);
            //binding.buttonDecline.setOnClickListener(this::declineRequest);

        }

        void setRequest(final Contacts contact) {
//            binding.buttonFullPost.setOnClickListener(view -> Log.d("WHAT IS THIS", "what"));
//            binding.textviewMemberID.setText(contact.getMemberID());
            binding.textviewUsername.setText(contact.getUserName());
//            binding.textviewFirstName.setText(contact.getFirstName());
//            binding.textviewLastName.setText(contact.getLastName());
        }
    }
}
