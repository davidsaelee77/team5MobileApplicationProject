package edu.uw.tcss450.griffin.ui.chat;

        import android.content.Intent;
        import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentTransaction;
        import androidx.lifecycle.ViewModelProvider;
        import androidx.navigation.Navigation;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import java.util.function.Function;
        import java.util.stream.Stream;

        import edu.uw.tcss450.griffin.MainActivity;
        import edu.uw.tcss450.griffin.R;
        import edu.uw.tcss450.griffin.databinding.FragmentAddChatBinding;
        import edu.uw.tcss450.griffin.databinding.FragmentChatListBinding;

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

        binding.imageButtonAddChatAddchatfragment.setOnClickListener(this);
        // binding.editTextEnterChatNameAddchatfragment.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //TODO Add observer and response, error handling for edit Text if empty.
        if (v == binding.imageButtonAddChatAddchatfragment) {
            mModel.connectAddChat(
                    binding.editTextEnterChatNameAddchatfragment.getText().toString());
            Navigation.findNavController(getView()).navigate(AddChatFragmentDirections.actionAddChatFragmentToChatListFragment());

            //binding.imageButtonAddChatAddchatfragment.setOnClickListener(button -> binding.editTextEnterChatNameAddchatfragment.getText().clear());
        }

    }
}
