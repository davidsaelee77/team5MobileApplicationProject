package edu.uw.tcss450.griffin.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

/**
 * @author Charles Bryan
 * @version May 2020
 */
/**
 * View model that used to observe
 * and manipulate data from chat message fragment.
 */
public class NewContactCountViewModel extends ViewModel {

    /**
     * Stores mutable live data integer value.
     */
    private MutableLiveData<Integer> mNewNotificationCount;

    /**
     * View model constructor
     */
    public NewContactCountViewModel() {
        mNewNotificationCount = new MutableLiveData<>();
        mNewNotificationCount.setValue(0);
    }

    /**
     * View model message count observer.
     *
     * @param owner  lifespan of given owner.
     * @param observer  callback from live data.
     */
    public void addContactCountObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super Integer> observer) {
        mNewNotificationCount.observe(owner, observer);
    }

    /**
     * Increments message value holder.
     */
    public void increment() {
        mNewNotificationCount.setValue(mNewNotificationCount.getValue() + 1);
    }

    /**
     * Resets message value holder.
     */
    public void reset() {
        mNewNotificationCount.setValue(0);
    }
}

