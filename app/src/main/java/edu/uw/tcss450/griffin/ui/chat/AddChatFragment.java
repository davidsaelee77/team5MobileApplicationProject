package edu.uw.tcss450.griffin.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.uw.tcss450.griffin.MainActivity;
import edu.uw.tcss450.griffin.databinding.FragmentAddChatBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddChatFragment extends Fragment implements View.OnClickListener {

    private ChatListViewModel mModel;

    private FragmentAddChatBinding binding;

    public AddChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            mModel.setUserInfoViewModel(activity.getUserInfoViewModel());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddChatBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editTextEnterChatNameAddchatfragment.setOnClickListener(this);
        binding.imageButtonAddChatAddchatfragment.setOnClickListener(this);

    }

    public Boolean chatNameValidation(String str) {


        if (str.equals("")) {
            binding.editTextEnterChatNameAddchatfragment.setError("Not a valid chat name.  No input was provided");
            return false;
        }
        return true;
    }

    public Boolean usernameValidation(List<String> username) {

        for (int i = 0; i < username.size(); i++) {

            if (username.get(i).equals("")) {
                binding.editTextEnterUser.setError("Not a valid e-mail.  No input was provided");
                return false;
            } else if (username.get(i).length() > 0) {
                int count = 0;
                for (int j = 0; j < username.get(i).length(); j++) {

                    if ((int) username.get(i).charAt(j) == 64) {
                        count++;
                    }
                }
                if (count <= 0) {

                    binding.editTextEnterUser.setError("You have entered an invalid e-mail.  Missing at least 1 @ from address");
                    return false;
                }
            }
        }

        return true;
    }

    public List<String> parseUsername(String str) {

        List<String> usernames = new ArrayList<String>(Arrays.asList(str.split(" , ")));

        return usernames;

    }

    @Override
    public void onClick(View v) {

        if (chatNameValidation(binding.editTextEnterChatNameAddchatfragment.getText().toString()) &&
                usernameValidation(parseUsername(binding.editTextEnterUser.getText().toString()))) {

            if (v == binding.imageButtonAddChatAddchatfragment) {
                mModel.connectAddChat(
                        binding.editTextEnterChatNameAddchatfragment.getText().toString());
                Navigation.findNavController(getView()).navigate(AddChatFragmentDirections.actionAddChatFragmentToChatListFragment());
            }
//             else {
//                binding.imageButtonAddChatAddchatfragment.setVisibility(View.GONE);
//
//            }

        }
    }
}
