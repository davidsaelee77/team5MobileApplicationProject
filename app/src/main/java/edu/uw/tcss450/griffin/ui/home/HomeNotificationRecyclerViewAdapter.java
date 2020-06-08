package edu.uw.tcss450.griffin.ui.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentHomeCardBinding;
import edu.uw.tcss450.griffin.model.Notification;
import edu.uw.tcss450.griffin.model.UserInfoViewModel;

public class HomeNotificationRecyclerViewAdapter extends RecyclerView.Adapter<HomeNotificationRecyclerViewAdapter.HomeNotificationViewHolder> {


    private final List<Notification> mNotifications;

    private final UserInfoViewModel mModel;

    public HomeNotificationRecyclerViewAdapter(List<Notification> notifications, Activity activity) {

        this.mNotifications = notifications;

        mModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(UserInfoViewModel.class);
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


        void setNotifications(final Notification notifications) {

            binding.textViewNotificationHomefragment.setText(notifications.getNotificationMessage());
            binding.textViewNotificationLabelHomefragment.setText(notifications.getNotificationTitle());

            binding.buttonHomeDelete.setOnClickListener(button -> mModel.deleteNotification(notifications));

        }
    }


}

