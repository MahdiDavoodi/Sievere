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
import davoodi.mahdi.sievere.preferences.Finals;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Version 0.8 -> SET UP THE MENU.
        /*BottomNavigationView bottomNavigationView = findViewById(R.id.ma_bnv);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.ma_fcv);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNavigationView, navController);*/
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
            } else if (item.getItemId() == R.id.ma_toolbar_scan) {
                /*Scan*/
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
        startActivity(new Intent(MainActivity.this, NowPlayingActivity.class)
                .putExtra(Finals.PLAY, false));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}