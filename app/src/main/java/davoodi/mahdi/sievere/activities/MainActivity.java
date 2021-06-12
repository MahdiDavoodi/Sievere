package davoodi.mahdi.sievere.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import davoodi.mahdi.sievere.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.ma_nav_buttons);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.ma_nav_host);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        /*
        ****** In case we decide to shoe action bar in later versions. ******
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.tracksFragment, R.id.playlistsFragment, R.id.forYouFragment)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}