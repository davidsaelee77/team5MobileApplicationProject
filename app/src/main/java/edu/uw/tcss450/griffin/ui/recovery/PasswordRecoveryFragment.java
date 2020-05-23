package edu.uw.tcss450.griffin.ui.recovery;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import org.json.JSONObject;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentPasswordrecoveryBinding;
import edu.uw.tcss450.griffin.utility.PasswordValidator;

import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.griffin.utility.PasswordValidator.checkPwdSpecialChar;

/**
 * @author Tyler Lorella
 * @version May 2020
 */
public class PasswordRecoveryFragment extends Fragment {

    /**
     * Stores FragmentPasswordrecoveryBinding variable.
     */
    private FragmentPasswordrecoveryBinding binding;

    /**
     * Stores PasswordRecoveryViewModel variable.
     */
    private edu.uw.tcss450.griffin.ui.recovery.PasswordRecoveryViewModel mPasswordRecoveryModel;

    /**
     * Method to validate email.
     */
    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    /**
     * Empty public constructor.
     */
    public PasswordRecoveryFragment() {
    }

    /**
     * Instantiates a PasswordRecoveryViewModel when PasswordRecoveryFragment is created.
     *
     * @param savedInstanceState reconstructed fragment from previous saved state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPasswordRecoveryModel = new ViewModelProvider(getActivity())
                .get(edu.uw.tcss450.griffin.ui.recovery.PasswordRecoveryViewModel.class);
    }

    /**
     * Inflates PasswordRecoveryFragment layout and instantiates PasswordRecoveryFragment
     * binding when created.
     *
     * @param inflater           object to inflate any view in layout.
     * @param container          parent view fragment UI is attached to.
     * @param savedInstanceState reconstructed fragment from previous saved state.
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPasswordrecoveryBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * TODO: Tell em' what it is
     *
     * @param view               returned by onCreateView.
     * @param savedInstanceState reconstructed fragment from previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCancel.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        PasswordRecoveryFragmentDirections.actionPasswordRecoveryFragmentToLoginFragment()));

        binding.buttonEmail.setOnClickListener(this::attemptRecovery);

        mPasswordRecoveryModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observeResponse);

        PasswordRecoveryFragmentArgs args = PasswordRecoveryFragmentArgs.fromBundle(getArguments());

        binding.emailText.setText(args.getEmail().equals("default") ? "" : args.getEmail());
    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to  PasswordRecoveryViewModel
     *
     * @param response the Response from the server
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            //don't want the client "user" to know if the email was correct or not?
            //email was or wasn't send, we want to create a dialog possible success dialog
            createDialogAcknowledge();
            //Snackbar mySnackbar = Snackbar.make(getView(),
            //       R.string.forgotPassword_snackbar_acknowledge, Snackbar.LENGTH_SHORT);
        } else {
            Log.d("JSON Response", "No Response");
            //binding.emailText.setError("Unable to communicate with Server");
            //binding.emailText.requestFocus();
            //couldn't connect with the server error
        }
    }
    /**
     * Creates a dialog box that acknowledges the users input to recover password. 
     */
    private void createDialogAcknowledge() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.textview_forgotPassword_dialogMessage);
        builder.setTitle(R.string.title_forgotPassword_dialog);
        builder.setPositiveButton(R.string.button_forgotPassword_dialogAcknowledge, (dialog, which) -> {
            Log.d("Recovery", "Acknowledge");
        });
        builder.setOnDismissListener(dialog -> {
            Log.d("Recovery", "Dismissing, returning to login");
            navigateToLogin();
        });
        builder.create();
        builder.show();
    }
    /**
     * Navigates the user to login fragment. 
     */
    private void navigateToLogin() {
        PasswordRecoveryFragmentDirections.ActionPasswordRecoveryFragmentToLoginFragment directions =
                PasswordRecoveryFragmentDirections.actionPasswordRecoveryFragmentToLoginFragment();

        directions.setEmail(binding.emailText.getText().toString());
        Navigation.findNavController(getView()).navigate(directions);
    }

    /**
     * Helper method to being sequential validation process.
     *
     * @param button login button.
     */
    private void attemptRecovery(final View button) {
        Log.d("Recovery", "User selected to recover password");
        validateEmail();
    }

    /**
     * Helper method to validate email.  Sets error message
     * if incorrect input is given.  Calls validate password method.
     */
    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.emailText.getText().toString().trim()),
                this::sendRecoveryRequest,
                result -> binding.emailText.setError("Please enter a valid Email address."));
    }
    /**
     * Method to send recovery request. 
     */
    private void sendRecoveryRequest() {
        //recover the users password
        mPasswordRecoveryModel.connect(binding.emailText.getText().toString());
    }

}
