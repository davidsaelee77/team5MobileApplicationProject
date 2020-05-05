package edu.uw.tcss450.team5tcss450client.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team5tcss450client.R;
import edu.uw.tcss450.team5tcss450client.databinding.FragmentChatBinding;

/**
 *
 * @author David Salee & Tyler Lorella
 * @version May 2020
 */
public class ChatFragment extends Fragment {

    FragmentChatBinding binding;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentChatBinding.bind(getView());

        binding.chatButtonNewmessage.setOnClickListener(button ->
                Navigation.findNavController(getView()).
                        navigate(ChatFragmentDirections.actionChatFragmentToNewMessageFragment()));

        binding.tempPlaceHolderCHATM.setOnClickListener(button ->
                Navigation.findNavController(getView()).
                        navigate(ChatFragmentDirections.actionChatFragmentToConversationFragment()));

    }


}
