package davoodi.mahdi.sievere.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.adapters.TAFLinearListAdapter;

public class MainActivity extends AppCompatActivity implements TAFLinearListAdapter.OnTrackListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.ma_nav_buttons);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.ma_nav_host);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        /*
        ****** In case we decide to show action bar in later versions. ******
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.tracksFragment, R.id.playlistsFragment, R.id.forYouFragment)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }


    /*Toolbar Menu*/
    public void toolbarMenu(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.inflate(R.menu.main_toolbar);
        menu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.ma_toolbar_info) {
                openInfoActivity();
                return true;
            } else if (item.getItemId() == R.id.ma_toolbar_settings) {
                /*Setting*/
                return true;
            } else if (item.getItemId() == R.id.ma_toolbar_rate) {
                /*Rate*/
                return true;
            } else if (item.getItemId() == R.id.ma_toolbar_scan) {
                /*Scan*/
                return true;
            } else if (item.getItemId() == R.id.ma_toolbar_now_playing) {
                openNPActivity();
                return true;
            } else return false;
        });
        menu.show();
    }

    /*Actions*/
    private void openInfoActivity() {
        startActivity(new Intent(MainActivity.this, InfoActivity.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private void openNPActivity() {
        startActivity(new Intent(MainActivity.this, NowPlayingActivity.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    // Track list onclick.
    @Override
    public void onTrackClick(int position) {

    }
}