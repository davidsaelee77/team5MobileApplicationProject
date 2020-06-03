package edu.uw.tcss450.griffin.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentChatBinding;
import edu.uw.tcss450.griffin.model.UserInfoViewModel;

/**
 * @author David Salee & Tyler Lorella
 * @version May 2020
 */
public class ChatFragment extends Fragment {

    /**
     * Chat Send View Model.
     */
    private ChatSendViewModel mSendModel;

    /**
     * The chat ID for "global" chat.
     */
    private int mChatId = -1;

    /**
     * Chat View Model.
     */
    private ChatViewModel mChatModel;

    /**
     * User View Model.
     */
    private UserInfoViewModel mUserModel;

    /**
     * Empty public constructor.
     */
    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatModel = provider.get(ChatViewModel.class);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //mChatModel.getFirstMessages(mChatId, mUserModel.getJwt());

        mSendModel = provider.get(ChatSendViewModel.class);

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
        ChatFragmentArgs args = ChatFragmentArgs.fromBundle(getArguments());
        mChatId = args.getChatroom().getChatId();
        FragmentChatBinding binding = FragmentChatBinding.bind(getView());
        mChatModel.getFirstMessages(mChatId, mUserModel.getJwt());
        //SetRefreshing shows the internal Swiper view progress bar. Show this until messages load
        binding.swipeContainer.setRefreshing(true);

        final RecyclerView rv = binding.recyclerMessages;
        //Set the Adapter to hold a reference to the list FOR THIS chat ID that the ViewModel
        //holds.
        rv.setAdapter(new ChatRecyclerViewAdapter(
                mChatModel.getMessageListByChatId(mChatId),
                mUserModel.getUsername()));


        //When the user scrolls to the top of the RV, the swiper list will "refresh"
        //The user is out of messages, go out to the service and get more
        binding.swipeContainer.setOnRefreshListener(() -> {
            mChatModel.getNextMessages(mChatId, mUserModel.getJwt());
        });

        mChatModel.addMessageObserver(mChatId, getViewLifecycleOwner(),
                list -> {
                    /*
                     * This solution needs work on the scroll position. As a group,
                     * you will need to come up with some solution to manage the
                     * recyclerview scroll position. You also should consider a
                     * solution for when the keyboard is on the screen.
                     */
                    //inform the RV that the underlying list has (possibly) changed
                    rv.getAdapter().notifyDataSetChanged();
                    rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
                    binding.swipeContainer.setRefreshing(false);
                });

        //Send button was clicked. Send the message via the SendViewModel
        binding.buttonSend.setOnClickListener(button -> {
            mSendModel.sendMessage(mChatId, mUserModel.getJwt(), binding.editMessage.getText().toString());
        });
        //when we get the response back from the server, clear the edittext
        mSendModel.addResponseObserver(getViewLifecycleOwner(), response -> binding.editMessage.setText(""));
    }
}
