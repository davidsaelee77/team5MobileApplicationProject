package edu.uw.tcss450.griffin.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentChatlistCardBinding;

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatListViewHolder> {


    List<ChatRoom> mChatRooms;

    public ChatListRecyclerViewAdapter(List<ChatRoom> chats) {
        this.mChatRooms = chats;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ChatListViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_chatlist_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {

        holder.setChatRoom(mChatRooms.get(position));
    }

    @Override
    public int getItemCount() {

        return mChatRooms.size();
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public FragmentChatlistCardBinding binding;


        public ChatListViewHolder(View view) {
            super(view);
            mView = view;

            binding = FragmentChatlistCardBinding.bind(view);

        }

        void setChatRoom(final ChatRoom chatRoom) {

            binding.navigateToChatroom.setOnClickListener(view-> Navigation.findNavController(mView).navigate(ChatListFragmentDirections.actionChatListFragmentToChatFragment(chatRoom)));
            binding.chatRoomTextView.setText("Chat Room No " + chatRoom.getChatRoomID());

        }
    }
}
