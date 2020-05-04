package edu.uw.tcss450.team5tcss450client.ui.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.team5tcss450client.databinding.FragmentRegisterBinding;
import edu.uw.tcss450.team5tcss450client.utility.PasswordValidator;

import static edu.uw.tcss450.team5tcss450client.utility.PasswordValidator.checkClientPredicate;
import static edu.uw.tcss450.team5tcss450client.utility.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.team5tcss450client.utility.PasswordValidator.checkPwdDigit;
import static edu.uw.tcss450.team5tcss450client.utility.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.team5tcss450client.utility.PasswordValidator.checkPwdLowerCase;
import static edu.uw.tcss450.team5tcss450client.utility.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.tcss450.team5tcss450client.utility.PasswordValidator.checkPwdUpperCase;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    private RegisterViewModel mRegisterModel;

    private PasswordValidator mNameValidator = checkPwdLength(1);

    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.retypepasswordText.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterModel = new ViewModelProvider(getActivity())
                .get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registerAcctButton.setOnClickListener(this::attemptRegister);
        mRegisterModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);

    }

    private void attemptRegister(final View button) {
        validateFirst();
    }

    private void validateFirst() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.firstnameText.getText().toString().trim()),
                this::validateLast,
                result -> binding.emailText.setError("Please enter a valid Email address."));
    }

    private void validateLast() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.lastnameText.getText().toString().trim()),
                this::validateEmail,
                result -> binding.emailText.setError("Please enter a valid Email address."));
    }

    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.emailText.getText().toString().trim()),
                this::validatePassword,
                result -> binding.emailText.setError("Please enter a valid Email address."));
    }

    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.passwordText.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.passwordText.setError("Please enter a valid Password."));
    }

    private void verifyAuthWithServer() {

        mRegisterModel.connect(
                binding.firstnameText.getText().toString(),
                binding.lastnameText.getText().toString(),
                binding.emailText.getText().toString(),
                binding.passwordText.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        // result of connect().

    }

    private void navigateToLogin() {
        RegisterFragmentDirections.ActionRegisterFragmentToLoginFragment directions =
                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment();

        directions.setEmail(binding.emailText.getText().toString());
        directions.setPassword(binding.passwordText.getText().toString());

        Navigation.findNavController(getView()).navigate(directions);

    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param response the Response from the server
     */

    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.emailText.setError("Error Authenticating: " + response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                navigateToLogin();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}

