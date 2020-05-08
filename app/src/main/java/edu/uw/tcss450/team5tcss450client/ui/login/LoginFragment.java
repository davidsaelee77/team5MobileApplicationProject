package edu.uw.tcss450.team5tcss450client.ui.login;

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

import edu.uw.tcss450.team5tcss450client.databinding.FragmentLoginBinding;
import edu.uw.tcss450.team5tcss450client.utility.PasswordValidator;

import static edu.uw.tcss450.team5tcss450client.utility.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.team5tcss450client.utility.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.team5tcss450client.utility.PasswordValidator.checkPwdSpecialChar;

/**
 *
 * @author David Saelee
 * @version May 2020
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    /**
     * Stores FragmentLoginBinding variable.
     */
    private FragmentLoginBinding binding;
    /**
     * Stores LoginViewModel variable.
     */
    private LoginViewModel mSignInModel;

    /**
     * Method to validate email.
     */
    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    /**
     * Method to validate password.
     */
    private PasswordValidator mPassWordValidator = checkPwdLength(1)
            .and(checkExcludeWhiteSpace());

    /**
     * Empty public constructor.
     */
    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Instantiates a LoginViewModel when login fragment is created.
     *
     * @param savedInstanceState reconstructed fragment from previous saved state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignInModel = new ViewModelProvider(getActivity())
                .get(LoginViewModel.class);
    }

    /**
     * Inflates LoginFragment layout and instantiates LoginFragment binding when created.
     *
     * @param inflater           object to inflate any view in layout.
     * @param container          parent view fragment UI is attached to.
     * @param savedInstanceState reconstructed fragment from previous saved state.
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    /**
     * When view is created the login fragment allows navigation to home fragment or registration fragment.
     * An observer checks for proper credentials before allowing user into home screen.
     * Passes parameters to fill email and password fields.
     *
     * @param view returned by onCreateView.
     * @param savedInstanceState reconstructed fragment from previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registerButton.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment()));

        binding.signinButton.setOnClickListener(this::attemptSignIn);

        binding.helpButton.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginFragmentToHelpFragment()));


        mSignInModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observeResponse);


        LoginFragmentArgs args = LoginFragmentArgs.fromBundle(getArguments());

        binding.emailText.setText(args.getEmail().equals("default") ? "" : args.getEmail());
        binding.passwordText.setText(args.getPassword().equals("default") ? "" : args.getPassword());
    }

    /**
     * Helper method to being sequential validation process.
     *
     * @param button login button.
     */
    private void attemptSignIn(final View button) {
        validateEmail();
    }

    /**
     *Helper method to validate email.  Sets error message
     * if incorrect input is given.  Calls validate password method.
     */
    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.emailText.getText().toString().trim()),
                this::validatePassword,
                result -> binding.emailText.setError("Please enter a valid Email address."));
    }

    /**
     * Helper method to  validate password.  Sets error message if
     * incorrect input is given.  Calls method to verify and authenticate credentials.
     *
     */
    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.passwordText.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.passwordText.setError("Please enter a valid Password."));
    }

    /**
     * Method passes credentials to server for authentication.
     */
    private void verifyAuthWithServer() {

        mSignInModel.connect(binding.emailText.getText().toString(),
                binding.passwordText.getText().toString());

    }

    /**
     * Helper to abstract the navigation to the Activity past Authentication.
     *
     * @param email users email
     * @param jwt   the JSON Web Token supplied by the server
     */
    private void navigateToSuccess(final String email, final String jwt) {
        Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity(email, jwt));

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
                    binding.emailText.requestFocus();
                    binding.emailText.setError("Invalid credentials");
                }
            } else {
                try {
                    navigateToSuccess(binding.emailText.getText().toString(), response.getString("token"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                    binding.emailText.requestFocus();
                    binding.emailText.setError("Invalid credentials");
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }

    }
}
