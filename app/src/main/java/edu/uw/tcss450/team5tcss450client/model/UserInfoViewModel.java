package edu.uw.tcss450.team5tcss450client.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserInfoViewModel extends ViewModel {

    private final String mEmail;
    private final String mJwt;

    /**
     * Constructor that instantiates fields with given email and jwt.
     * @author David Salee
     * @version May 2020
     * @param email
     * @param jwt
     */
    private UserInfoViewModel(String email, String jwt) {
        mEmail = email;
        mJwt = jwt;
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

    /**
     * Subclass that instantiates ViewModels
     * @author David Salee
     * @version May 2020
     */
    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        private final String email;
        private final String jwt;

        /**
         * UserInfoViewModelFactory constructor.
         * @param email
         * @param jwt
         */
        public UserInfoViewModelFactory(String email, String jwt) {
            this.email = email;
            this.jwt = jwt;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }


}