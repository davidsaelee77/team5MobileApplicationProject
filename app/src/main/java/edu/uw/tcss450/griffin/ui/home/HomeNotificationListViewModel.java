package edu.uw.tcss450.griffin.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.griffin.model.Notification;
import edu.uw.tcss450.griffin.model.UserInfoViewModel;

public class HomeNotificationListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Notification>> mNotificationList;

    private UserInfoViewModel mUserModel;


    public HomeNotificationListViewModel(@NonNull Application application) {
        super(application);
        mNotificationList = new MutableLiveData<>();
        mNotificationList.setValue(new ArrayList<>());
    }

    public void addHomeNotificationListObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Notification>> observer) {
        mNotificationList.observe(owner, observer);
    }

    public MutableLiveData<List<Notification>> getNotifications() {
        return this.mNotificationList;
    }

    public void setUserInfoViewModel(UserInfoViewModel vm) {
        mUserModel = vm;
    }

}
