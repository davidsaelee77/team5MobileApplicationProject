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
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel mSignInModel;

    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    private PasswordValidator mPassWordValidator = checkPwdLength(1)
            .and(checkExcludeWhiteSpace());


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignInModel = new ViewModelProvider(getActivity())
                .get(LoginViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registerButton.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment()));

        binding.signinButton.setOnClickListener(this::attemptSignIn);


        mSignInModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observeResponse);


        LoginFragmentArgs args = LoginFragmentArgs.fromBundle(getArguments());

        binding.emailText.setText(args.getEmail().equals("default") ? "" : args.getEmail());
        binding.passwordText.setText(args.getPassword().equals("default") ? "" : args.getPassword());
    }

    private void attemptSignIn(final View button) {
        validateEmail();
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

        mSignInModel.connect(binding.emailText.getText().toString(),
                binding.passwordText.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        // result of connect().


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
