package edu.uw.tcss450.griffin.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.badge.BadgeDrawable;

import edu.uw.tcss450.griffin.MainActivity;
import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentChatListBinding;

/**
 * A simple {@link Fragment} subclass.
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

        mModel.addChatListObserver(getViewLifecycleOwner(), chatRoomList -> {
            if (!chatRoomList.isEmpty()) {
                binding.chatlistRoot.setAdapter(new ChatListRecyclerViewAdapter(chatRoomList));
            }
        });
    }
}
