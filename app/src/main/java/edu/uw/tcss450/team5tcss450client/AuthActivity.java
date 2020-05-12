package edu.uw.tcss450.team5tcss450client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

/**
 * @author David Saelee
 * @version May 2020
 */

/**
 * Activity used for authentication of login credentials
 * and validation of registration.
 */
public class AuthActivity extends AppCompatActivity {

   // NavController authNavController;

    /**
     * Instantiates a authActivity when authentication activity is created.
     *
     * @param savedInstanceState reconstructed activity from previous saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

       // authNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }
}
