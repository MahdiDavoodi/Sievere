package davoodi.mahdi.sievere.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
        ****** In case we decide to show action bar in later versions. ******
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.tracksFragment, R.id.playlistsFragment, R.id.forYouFragment)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ma_toolbar_search) {
            /*Info*/
            return true;
        } else if (item.getItemId() == R.id.ma_toolbar_info) {
            openInfoActivity();
            return true;
        } else if (item.getItemId() == R.id.ma_toolbar_settings) {
            /*Settings*/
            return true;
        } else if (item.getItemId() == R.id.ma_toolbar_rate) {
            /*Rate*/
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    private void openInfoActivity() {
        startActivity(new Intent(MainActivity.this, InfoActivity.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}