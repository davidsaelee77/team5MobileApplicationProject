package edu.uw.tcss450.griffin.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class UserInfoViewModel extends ViewModel {

    private final String mEmail;
    private final String mJwt;
    private final int mMemberId;
    private final String mUsername;
    private final MutableLiveData<List<Notification>> mNotificationList;

    /**
     * Constructor that instantiates fields with given email and jwt.
     * @author David Salee
     * @version May 2020
     * @param email
     * @param jwt
     */
    private UserInfoViewModel(String email, String jwt, int memberId, String username) {
        mEmail = email;
        mJwt = jwt;
        mMemberId = memberId;
        mUsername = username;
        mNotificationList = new MutableLiveData<>();
        mNotificationList.setValue(new ArrayList<>());
    }

    /**
     * Returns mEmail.
     * @author David Salee
     * @version May 2020
     * @return mEmail
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Returns mJwt.
     * @author David Salee
     * @version May 2020
     * @return mJwt
     */
    public String getJwt() {
        return mJwt;
    }

    public int getMemberId(){
        return mMemberId;
    }

    public String getUsername() {
        return mUsername;
    }

    public MutableLiveData<List<Notification>> getNotifications() {
        return this.mNotificationList;
    }

    public void addNotifications(Notification notification) {
        List<Notification> newList = this.mNotificationList.getValue();
        newList.add(notification);
        this.mNotificationList.setValue(newList);
    }

    public void clearNotifications() {
        List<Notification> newList = this.mNotificationList.getValue();
        newList.clear();
        this.mNotificationList.setValue(newList);
    }

    public void addNotificationsObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Notification>> observer) {
        mNotificationList.observe(owner, observer);
    }


    /**
     * Subclass that instantiates ViewModels
     * @author David Salee
     * @version May 2020
     */
    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        private final String email;
        private final String jwt;
        private final int memberId;
        private final String username;

        /**
         * UserInfoViewModelFactory constructor.
         * @param email
         * @param jwt
         */
        public UserInfoViewModelFactory(String email, String jwt, int memberId, String username) {
            this.email = email;
            this.jwt = jwt;
            this.memberId = memberId;
            this.username = username;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt, memberId, username);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }


}