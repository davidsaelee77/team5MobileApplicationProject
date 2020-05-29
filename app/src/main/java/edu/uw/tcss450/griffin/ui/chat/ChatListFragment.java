package edu.uw.tcss450.griffin.ui.chat;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.badge.BadgeDrawable;

import edu.uw.tcss450.griffin.MainActivity;
import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentAddChatBinding;
import edu.uw.tcss450.griffin.databinding.FragmentChatListBinding;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author David Salee & Tyler Lorella
 * @version May 2020
 */
public class ChatListFragment extends Fragment {

    /**
     * Chat List View Model.
     */
    private ChatListViewModel mModel;
    /**
     * Binding for Chat List.
     */
    private FragmentChatListBinding binding;


    /**
     * Empty public constructor.
     */
    public ChatListFragment() {
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
        mModel.connectGet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentChatListBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentChatListBinding binding = FragmentChatListBinding.bind(getView());

        //TODO: Figure out how to implement adding chat name to chat room.
        binding.imageButtonAddChat.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(ChatListFragmentDirections.actionChatListFragmentToAddChatFragment()));

        mModel.addChatListObserver(getViewLifecycleOwner(), chatRoomList -> {
            if (!chatRoomList.isEmpty()) {
                binding.chatlistRoot.setAdapter(new ChatListRecyclerViewAdapter(chatRoomList, this));
            }
            //TODO: sorry no chats message if it's empty?
        });
    }

    public void deleteChat(final int chatId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_chatListRecycler_title);
        builder.setMessage(R.string.dialog_chatListRecycler_message);
        mModel.connectDeleteChat(chatId);
    }
}
