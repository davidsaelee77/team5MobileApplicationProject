package edu.uw.tcss450.griffin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.Objects;

import edu.uw.tcss450.griffin.application.GriffinApplication;
import edu.uw.tcss450.griffin.databinding.ActivityMainBinding;
import edu.uw.tcss450.griffin.model.NewMessageCountViewModel;
import edu.uw.tcss450.griffin.model.UserInfoViewModel;
import edu.uw.tcss450.griffin.services.PushReceiver;
import edu.uw.tcss450.griffin.ui.chat.ChatMessageFragment;
import edu.uw.tcss450.griffin.ui.chat.ChatViewModel;


/**
 * @author David Saelee
 * @version May 2020
 */

/**
 * Activity used to navigate between fragments after
 * successful login via button clicks or menu bar.
 */

public class MainActivity extends AppCompatActivity {

    private MainPushMessageReceiver mPushMessageReceiver;
    private NewMessageCountViewModel mNewMessageModel;

    private ActivityMainBinding binding;

    /**
     * Stores AppBarConfiguration value.
     */
    private AppBarConfiguration mAppBarConfiguration;

    NavController navController;

    private UserInfoViewModel userInfoViewModel;

    private MainActivityArgs mArgs;

    private MutableLiveData<JSONObject> mResponse;


    /**
     * Instantiates a main activity fragment, and builds a menu bar
     * for fragment navigation.
     *
     * @param savedInstanceState reconstructed activity from previous saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (GriffinApplication.id != -1) {
            getTheme().applyStyle(GriffinApplication.id, true);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.bottom_menu_bar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.chatListFragment, R.id.contactListFragment, R.id.weatherListFragment).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());

        userInfoViewModel = new ViewModelProvider(this,
                new UserInfoViewModel.UserInfoViewModelFactory(args.getEmail(), args.getJwt(), args.getMemberid())
        ).get(UserInfoViewModel.class);


        //MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());
        mArgs = MainActivityArgs.fromBundle(getIntent().getExtras());

        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());

        new ViewModelProvider(this,
                //new UserInfoViewModel.UserInfoViewModelFactory(args.getEmail(), args.getJwt())
                new UserInfoViewModel.UserInfoViewModelFactory(mArgs.getEmail(), mArgs.getJwt(), mArgs.getMemberid())
        ).get(UserInfoViewModel.class);

        mNewMessageModel = new ViewModelProvider(this).get(NewMessageCountViewModel.class);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.chatFragment) {
                //When the user navigates to the chats page, reset the new message count.
                // This will need some extra logic for your project as it should have
                // multiple chat rooms.
                mNewMessageModel.reset();
            }
        });
        mNewMessageModel.addMessageCountObserver(this, count -> {

            BadgeDrawable badge = binding.bottomMenuBar.getOrCreateBadge(R.id.chatFragment); //THIS WAS NAV_CHAT BEFORE I CHANGED IT
            badge.setMaxCharacterCount(2);
            if (count > 0) {
                //new messages! update and show the notification badge.
                badge.setNumber(count);
                badge.setVisible(true);
            } else {
                //user did some action to clear the new messages, remove the badge
                badge.clearNumber();
                badge.setVisible(false);
            }
        });

    }

    /**
     * Provides clickable back navigation on the top of the screen within fragment.
     *
     * @return previous fragment.
     */
    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    /**
     * Toolbar menu with defined functions.
     *
     * @param menu the options menu where we place the items.
     * @return boolean to determine if menu is to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("Theme", "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.navigate_button_theme:

                navController.navigate(R.id.changeThemeFragment);
                break;

            case R.id.navigate_button_password:
                //navController.navigate(R.id.changePasswordFragment);
                createChangePasswordDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates a popou dialog box that prompts users to change their password. 
     */
    private void createChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.changePassword_text_title);
        builder.setMessage(R.string.changePassword_text_message);
        builder.setPositiveButton(R.string.changePassword_button_accept, (dialog, which) -> {
            Log.d("ChangeP", "User wants to change password");
            connectChangePassword();
        });
        builder.setNegativeButton(R.string.changePassword_button_cancel, (dialog, which) -> {
            Log.d("changeP", "cancel change");
            //do nothing
        });
        builder.create();
        builder.show();
    }
    /**
     * Method that connects to a webservice that sends a email to change password.
     */
    private void connectChangePassword() {
        String url = "https://team5-tcss450-server.herokuapp.com/changePassword";
        String email = mArgs.getEmail();
        JSONObject body = new JSONObject();
        try {
            body.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(Request.Method.POST, url, body, mResponse::setValue, this::handleError);
        request.setRetryPolicy(new DefaultRetryPolicy(10_000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
    }

    /**
     * Server credential authentication error handling.
     *
     * @param error message
     */
    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            try {
                mResponse.setValue(new JSONObject("{" +
                        "error:\"" + error.getMessage() +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        } else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset())
                    .replace('\"', '\'');
            try {
                mResponse.setValue(new JSONObject("{" +
                        "code:" + error.networkResponse.statusCode +
                        ", data:\"" + data +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
    }

    /**
     * A BroadcastReceiver that listens for messages sent from PushReceiver
     */
    private class MainPushMessageReceiver extends BroadcastReceiver {
        private ChatViewModel mModel = new ViewModelProvider(MainActivity.this).get(ChatViewModel.class);

        @Override
        public void onReceive(Context context, Intent intent) {
            NavController nc = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
            NavDestination nd = nc.getCurrentDestination();
            if (intent.hasExtra("chatMessage")) {
                ChatMessageFragment cm = (ChatMessageFragment) intent.getSerializableExtra("chatMessage");
                //If the user is not on the chat screen, update the
                // NewMessageCountView Model
                if (nd.getId() != R.id.chatFragment) {
                    mNewMessageModel.increment();
                }
                //Inform the view model holding chatroom messages of the new
                // message.
                mModel.addMessage(intent.getIntExtra("chatid", -1), cm);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPushMessageReceiver == null) {
            mPushMessageReceiver = new MainPushMessageReceiver();
        }
        IntentFilter iFilter = new IntentFilter(PushReceiver.RECEIVED_NEW_MESSAGE);
        registerReceiver(mPushMessageReceiver, iFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPushMessageReceiver != null) {
            unregisterReceiver(mPushMessageReceiver);
        }
    }

    /**
     * Returns UserInfoViewModel. 
     */
    public UserInfoViewModel getUserInfoViewModel() {
        return userInfoViewModel;
    }


}
