package edu.uw.tcss450.team5tcss450client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.tcss450.team5tcss450client.application.GriffinApplication;
import edu.uw.tcss450.team5tcss450client.model.UserInfoViewModel;


/**
 * @author David Saelee
 * @version May 2020
 */

/**
 * Activity used to navigate between fragments after
 * successful login via button clicks or menu bar.
 */

public class MainActivity extends AppCompatActivity {

    /**
     * Stores AppBarConfiguration value.
     */
    private AppBarConfiguration mAppBarConfiguration;

    NavController navController;

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

        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.bottom_menu_bar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.chatFragment, R.id.contactListFragment, R.id.weatherListFragment).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());

        new ViewModelProvider(this,
                new UserInfoViewModel.UserInfoViewModelFactory(args.getEmail(), args.getJwt())
        ).get(UserInfoViewModel.class);


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

        switch (item.getItemId()) {

            case R.id.UW_theme_toolbar:

                GriffinApplication.id = R.style.AppTheme;
                recreate();

                break;
            case R.id.sonics_theme_toolbar:

                GriffinApplication.id = R.style.SonicsTheme;
                recreate();

                break;
            case R.id.bluegrey_theme_toolbar:

                GriffinApplication.id = R.style.BluegreyTheme;
                recreate();

                break;

            case R.id.navigate_changetheme_fragment:

                navController.navigate(R.id.changeThemeFragment);

                break;

//            case R.id.navigate_help_fragment:
//
//                Intent intent = new Intent(this, AuthActivity.class);
//
//
//                authNavController.navigate(R.id.helpFragment);
//                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
