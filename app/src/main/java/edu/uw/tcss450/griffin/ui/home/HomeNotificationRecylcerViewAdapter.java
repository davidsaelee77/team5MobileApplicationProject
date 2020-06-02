package edu.uw.tcss450.griffin.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentHomeCardBinding;

public class HomeNotificationRecylcerViewAdapter extends RecyclerView.Adapter<HomeNotificationRecylcerViewAdapter.HomeNotificationViewHolder> {


    List<HomeNotifications> mNotifications;

    public HomeNotificationRecylcerViewAdapter(List<HomeNotifications> notifications) {

        this.mNotifications = notifications;
    }

    @NonNull
    @Override
    public HomeNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new HomeNotificationViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_home_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeNotificationViewHolder holder, int position) {

        holder.setNotifications(mNotifications.get(position));
    }

    @Override
    public int getItemCount() {

        return mNotifications.size();
    }

    /**
     * Helper class that creates a view holder.
     */
    public class HomeNotificationViewHolder extends RecyclerView.ViewHolder {

        public final View mView;


        public FragmentHomeCardBinding binding;

        public HomeNotificationViewHolder(View view) {

            super(view);
            mView = view;
            binding = FragmentHomeCardBinding.bind(view);
        }


        void setNotifications(final HomeNotifications notifications) {


            binding.numberNotificationTextView.setText(notifications.getNotifications() + "");

        }
    }


}

