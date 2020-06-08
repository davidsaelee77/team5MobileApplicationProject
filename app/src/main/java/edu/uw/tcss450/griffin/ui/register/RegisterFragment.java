package edu.uw.tcss450.griffin.ui.register;

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

import edu.uw.tcss450.griffin.databinding.FragmentRegisterBinding;
import edu.uw.tcss450.griffin.utility.PasswordValidator;

import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkClientPredicate;
import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkPwdDigit;
import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkPwdLowerCase;
import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkPwdMatchesExp;
import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkPwdMaxLength;
import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkPwdUpperCase;

/**
 * @author David Saelee
 * @version May 2020
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    /**
     * Stores FragmentRegisterBinding variable.
     */
    private FragmentRegisterBinding binding;
    /**
     * Stores RegisterViewModel variable.
     */
    private RegisterViewModel mRegisterModel;
    /**
     * Method to validate name.
     */
    private PasswordValidator mNameValidator = checkPwdLength(1);

    /**
     *
     */
    private PasswordValidator mUsernameValidator = checkPwdLength(3)
            .and(checkPwdMaxLength(33))
            .and(checkPwdMatchesExp("^\\w+$"));

    /**
     * Method to validate email.
     */
    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));
    /**
     * Method to validate password.
     */
    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.passwordConfirmPassword.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    /**
     * Empty public constructor.
     */
    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Instantiates a RegisterViewModel when register fragment is created.
     *
     * @param savedInstanceState reconstructed fragment from previous saved state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterModel = new ViewModelProvider(getActivity())
                .get(RegisterViewModel.class);
    }

    /**
     * Inflates RegisterFragment layout and instantiates RegisterFragment binding when created.
     *
     * @param inflater           object to inflate any view in layout.
     * @param container          parent view fragment UI is attached to.
     * @param savedInstanceState reconstructed fragment from previous saved state.
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * When view is created the register fragment allows user to add input into text fields.
     * An observer checks for proper credentials before allowing user to register account.
     *
     * @param view               returned by onCreateView.
     * @param savedInstanceState reconstructed fragment from previous saved state.
     */
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonRegisterAcct.setOnClickListener(this::attemptRegister);
        mRegisterModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);

    }

    /**
     * Helper method to being sequential validation process.
     *
     * @param button login button.
     */
    private void attemptRegister(final View button) {
        validateFirst();
    }

    /**
     * Helper method to validate first name.  Sets error message
     * if incorrect input is given.  Calls validate last name method.
     */
    private void validateFirst() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.textplainFirstName.getText().toString().trim()),
                this::validateLast,
                result -> binding.textplainFirstName.setError("Please enter a valid first name."));
    }

    /**
     * Helper method to validate last name.  Sets error message
     * if incorrect input is given.  Calls validate username method.
     */
    private void validateLast() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.textplainLastName.getText().toString().trim()),
                this::validateUsername,
                result -> binding.textplainLastName.setError("Please enter a valid last name."));
    }

    /**
     * Helper method to validate username.  Sets error message
     * if incorrect input is given.  Calls validate email method.
     */
    private void validateUsername() {
        mNameValidator.processResult(
                mUsernameValidator.apply(binding.textviewUsername.getText().toString().trim()),
                this::validateEmail,
                result -> binding.textviewUsername.setError("Please enter a valid user name."));
    }

    /**
     * Helper method to validate email.  Sets error message
     * if incorrect input is given.  Calls validate password method.
     */
    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.emailInput.getText().toString().trim()),
                this::validatePassword,
                result -> binding.emailInput.setError("Please enter a valid Email address."));
    }

    /**
     * Helper method to  validate password.  Sets error message if
     * incorrect input is given.  Calls method to verify and authenticate credentials.
     */
    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.passwordPassword.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.passwordPassword.setError("Please enter a valid Password."));
    }

    /**
     * Method passes credentials to server for authentication.
     */
    private void verifyAuthWithServer() {

        mRegisterModel.connect(
                binding.textplainFirstName.getText().toString().trim(),
                binding.textplainLastName.getText().toString().trim(),
                binding.textviewUsername.getText().toString().trim(),
                binding.emailInput.getText().toString().trim(),
                binding.passwordPassword.getText().toString().trim());
    }

    /**
     * Helper method to navigate to the login fragment.  Fills
     * text fields with email and password.
     */
    private void navigateToLogin() {
        RegisterFragmentDirections.ActionRegisterFragmentToLoginFragment directions =
                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment();

         directions.setEmail(binding.emailInput.getText().toString().trim());
         directions.setPassword(binding.passwordPassword.getText().toString().trim());

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
                    JSONObject jObject = new JSONObject(response.getString("data"));
                    String message = jObject.getString("message");
                    binding.emailInput.setError("Error Authenticating: " + message);
                    binding.emailInput.requestFocus();
                } catch (JSONException e) {
                    Log.wtf("JSON Parse Error", e.getMessage());
                    binding.emailInput.requestFocus();
                    binding.emailInput.setError("Contact Developer");
                }
            } else {
                navigateToLogin();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}

