package edu.uw.tcss450.griffin.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserInfoViewModel extends ViewModel {

    private final String mEmail;
    private final String mJwt;
    private final int mMemberId;

    /**
     * Constructor that instantiates fields with given email and jwt.
     * @author David Salee
     * @version May 2020
     * @param email
     * @param jwt
     */
    private UserInfoViewModel(String email, String jwt, int memberId) {
        mEmail = email;
        mJwt = jwt;
        mMemberId = memberId;
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

    /**
     * Subclass that instantiates ViewModels
     * @author David Salee
     * @version May 2020
     */
    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        private final String email;
        private final String jwt;
        private final int memberId;

        /**
         * UserInfoViewModelFactory constructor.
         * @param email
         * @param jwt
         */
        public UserInfoViewModelFactory(String email, String jwt, int memberId) {
            this.email = email;
            this.jwt = jwt;
            this.memberId = memberId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt, memberId);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }


}