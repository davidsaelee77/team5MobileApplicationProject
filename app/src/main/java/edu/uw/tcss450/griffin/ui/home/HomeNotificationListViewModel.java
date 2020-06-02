package edu.uw.tcss450.griffin.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class HomeNotificationListViewModel extends AndroidViewModel {

    private MutableLiveData<List<HomeNotifications>> mNotificationList;

    private List<HomeNotifications> list;

    public HomeNotificationListViewModel(@NonNull Application application) {
        super(application);

        list = new ArrayList<HomeNotifications>();
        mNotificationList = new MutableLiveData<List<HomeNotifications>>();
        // buildDummyData(list);

        for (int i = 0; i < 5; i++) {
            list.add(list.get(i));
        }

        mNotificationList.setValue(list);
    }

    public void addHomeNotificationListObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<HomeNotifications>> observer) {
        mNotificationList.observe(owner, observer);
    }

//    public static ArrayList<Integer> buildDummyData(ArrayList<Integer> list) {
//
//        for (int i = 0; i < 5; i++) {
//
//            list.add(i);
//        }
//    }
}
