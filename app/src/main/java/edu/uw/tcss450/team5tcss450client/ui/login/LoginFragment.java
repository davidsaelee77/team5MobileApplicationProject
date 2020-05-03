package edu.uw.tcss450.team5tcss450client.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team5tcss450client.R;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        FragmentLoginBinding binding = FragmentLoginBinding.bind(getView());

        binding.signinButton.setOnClickListener(button -> Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity()));

        binding.registerButton.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                ));
    }
}
