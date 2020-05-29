package edu.uw.tcss450.griffin.ui.chat;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.List;

import edu.uw.tcss450.griffin.R;
import edu.uw.tcss450.griffin.databinding.FragmentChatlistCardBinding;
/**
 * @author David Salee & Tyler Lorella
 * @version May 2020
 */
public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatListViewHolder> {

    /**
     * The fragment that built this recycler view and will contain the delete method.
     */
    private final ChatListFragment mParent;

    /**
     * List of Chat rooms. 
     */
    List<ChatRoom> mChatRooms;

    /**
     * Constructor that builds the recycler view adapter from
     *  a list of Chat rooms.
     * @param chats List of chat rooms.
     */
    public ChatListRecyclerViewAdapter(List<ChatRoom> chats, ChatListFragment parent) {
        this.mChatRooms = chats;
        this.mParent = parent;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ChatListViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_chatlist_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {

        try {
            holder.setChatRoom(mChatRooms.get(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

        void setChatRoom(final ChatRoom chatRoom) throws JSONException {
            binding.buttonDelete.setOnClickListener(view -> deleteChat(this, chatRoom));
            binding.buttonGoTo.setOnClickListener(view -> Navigation.findNavController(mView).navigate(ChatListFragmentDirections.actionChatListFragmentToChatFragment(chatRoom)));
            // binding.chatRoomTextView.setText("Chat Room No " + chatRoom.getChatRoomID());

            //for (int i = 0; i < chatRoom.getRowCount(); i++) {
            binding.chatRoomTextView.setText(("Chat Room: #: " + chatRoom.getChatId()));
            //}
        }
    }

    private void deleteChat(final ChatListViewHolder view, final ChatRoom chatRoom) {
        Log.d("ChatListRecycle", "Pop up dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(mParent.getActivity());
        builder.setTitle(R.string.dialog_chatListRecycler_title);
        builder.setMessage(R.string.dialog_chatListRecycler_message);
        builder.setPositiveButton(R.string.dialog_chatListRecycler_positive, (dialog, which) -> {
            mChatRooms.remove(chatRoom);
            notifyItemRemoved(view.getLayoutPosition());
            final int chatId = chatRoom.getChatId();
            mParent.deleteChat(chatId);
            Log.d("ChatListRecycle", "Removed chatroom with ID: " + chatId);
        });
        builder.setNegativeButton(R.string.dialog_chatListRecycler_negative, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
