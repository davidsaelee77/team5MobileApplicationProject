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

import edu.uw.tcss450.griffin.MainActivity;
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

    private ChatListRecyclerViewAdapter chatListRecyclerViewAdapter;

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

        //TODO: Figure out how to implement adding chat name to chat room.
        binding.imageButtonAddChatChatlistfragment.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(ChatListFragmentDirections.actionChatListFragmentToAddChatFragment()));
        chatListRecyclerViewAdapter = new ChatListRecyclerViewAdapter(new ArrayList<>(), this);
        binding.chatlistRoot.setAdapter(chatListRecyclerViewAdapter);
        mModel.addChatListObserver(getViewLifecycleOwner(), chatRoomList -> {
            chatListRecyclerViewAdapter.setChatRooms(chatRoomList);
        });

    }

    public void deleteChat(final int chatId) {
        mModel.connectDeleteChat(chatId);
    }
}
