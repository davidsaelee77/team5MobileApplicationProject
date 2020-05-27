package edu.uw.tcss450.griffin.ui.login;

import android.app.AlertDialog;
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

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentLoginBinding;
import edu.uw.tcss450.griffin.model.PushyTokenViewModel;
import edu.uw.tcss450.griffin.model.UserInfoViewModel;
import edu.uw.tcss450.griffin.utility.PasswordValidator;

import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkPwdSpecialChar;

/**
 * @author David Saelee
 * @version May 2020
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private PushyTokenViewModel mPushyTokenViewModel;
    private UserInfoViewModel mUserViewModel;

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

        mPushyTokenViewModel = new ViewModelProvider(getActivity()).get(PushyTokenViewModel.class);

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * When view is created the login fragment allows navigation to home fragment or registration fragment.
     * An observer checks for proper credentials before allowing user into home screen.
     * Passes parameters to fill email and password fields.
     *
     * @param view               returned by onCreateView.
     * @param savedInstanceState reconstructed fragment from previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonRegister.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment()));

        binding.buttonSignin.setOnClickListener(this::attemptSignIn);

        binding.buttonForgotPassword.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginFragmentToPasswordRecoveryFragment()));


        mSignInModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observeResponse);


        LoginFragmentArgs args = LoginFragmentArgs.fromBundle(getArguments());

//        binding.emailInput.setText(args.getEmail().equals("default") ? "" : args.getEmail());
//        binding.passwordPassword.setText(args.getPassword().equals("default") ? "" : args.getPassword());

        binding.emailInput.setText("dsael1@uw.edu");
        binding.passwordPassword.setText("test12345");

        //don't allow sign in until pushy token retrieved
        mPushyTokenViewModel.addTokenObserver(getViewLifecycleOwner(),
                token -> binding.buttonSignin.setEnabled(!token.isEmpty()));

        mPushyTokenViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observePushyPutResponse);
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

        mSignInModel.connect(binding.emailInput.getText().toString(),
                binding.passwordPassword.getText().toString());

    }

    /**
     * Helper to abstract the navigation to the Activity past Authentication.
     *
     * @param email users email
     * @param jwt   the JSON Web Token supplied by the server
     */
    private void navigateToSuccess(final String email, final String jwt, final int memberid) {

        Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity(email, jwt, memberid));
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
                    if (message.contains("verified")) {
                        createDialogResendVerification();
                    }
                    binding.emailInput.setError("Error Authenticating: " + message);
                    binding.emailInput.requestFocus();
                } catch (JSONException e) {
                    Log.wtf("JSON Parse Error", e.getMessage());
                    binding.emailInput.requestFocus();
                    binding.emailInput.setError("Contact Developer");
                }
            } else {
                try {
                    mUserViewModel = new ViewModelProvider(getActivity(), new UserInfoViewModel.UserInfoViewModelFactory(
                            binding.emailInput.getText().toString(),
                            response.getString("token"),
                            response.getInt("memberid")
                    )).get(UserInfoViewModel.class);
                    sendPushyToken();

                    // navigateToSuccess(binding.emailInput.getText().toString(), response.getString("token"), response.getInt("memberid"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                    binding.emailInput.requestFocus();
                    binding.emailInput.setError("Invalid credentials");
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }

    }

    //pushy not currently used
    private void sendPushyToken() {
        mPushyTokenViewModel.sendTokenToWebservice(mUserViewModel.getJwt());
    }

    /**
     * Creates a dialog prompting the user if they would like to resent the verification email.
     * This method will only be called when an unverified user attempts to sign in.
     */
    private void createDialogResendVerification() {
        Log.d("Resend", "Attempting to create resend dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setMessage(R.string.textview_resendDialog_description);
        builder.setTitle(R.string.title_resendDialog);
        builder.setPositiveButton(R.string.button_resendDialog_resend, (dialog, which) -> {
            //resend verification email
            Log.d("Resend", "Resend button clicked!");
            mSignInModel.connectResendVerification(binding.emailInput.getText().toString());
            binding.emailInput.setError(null);
        });
        builder.setNegativeButton(R.string.button_resendDialog_cancel, (dialog, which) -> {
            //cancel, user doesn't want to resend apparently :c
        });
        builder.create();
        builder.show();
    }


    /**
     * An observer on the HTTP Response from the web server. This observer should be * attached to PushyTokenViewModel.
     *
     * @param response the Response from the server
     */
    private void observePushyPutResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                //this error cannot be fixed by the user changing credentials...
                binding.emailInput.setError("Error Authenticating on Push Token. Please contact support");
            } else {
                navigateToSuccess(binding.emailInput.getText().toString(), mUserViewModel.getJwt(), mUserViewModel.getMemberId());
            }
        }
    }
}
