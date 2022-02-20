package davoodi.mahdi.sievere.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.data.DataLoader;
import davoodi.mahdi.sievere.data.SiQueue;
import davoodi.mahdi.sievere.players.SiPlayer;
import davoodi.mahdi.sievere.preferences.Finals;
import davoodi.mahdi.sievere.tools.Utilities;

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

    @Override
    protected void onResume() {
        super.onResume();
        if (DataLoader.tracks.isEmpty())
            refresh();
        else updateTheCard();
    }

    public void toolbarMenu(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.inflate(R.menu.main_toolbar);
        menu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.ma_toolbar_info) {
                openInfoActivity();
                return true;
            } else if (item.getItemId() == R.id.ma_toolbar_settings) {
                Toast.makeText(this, "Demo, v0.6.0", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.ma_toolbar_scan) {
                refresh();
                return true;
            } else return false;
        });
        menu.show();
    }

    private void refresh() {
        try {
            Thread loader = new Thread(() -> DataLoader.loadData(this, null, null, null, null));
            loader.start();
            Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_LONG).show();
            loader.join();
            recreate();
            if (DataLoader.tracks.isEmpty()) {
                Toast.makeText(this, "There is no audio file in this phone", Toast.LENGTH_LONG).show();
                finish();
                // TODO: Improve this behavior.
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void openInfoActivity() {
        startActivity(new Intent(MainActivity.this, InfoActivity.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void npCard(View view) {
        startActivity(new Intent(MainActivity.this, NowPlayingActivity.class)
                .putExtra(Finals.PLAY, false));
    }

    private void updateTheCard() {
        CardView cardView = findViewById(R.id.ma_np_card);
        ImageButton pause = findViewById(R.id.ma_np_card_pause);
        ImageView albumArt = findViewById(R.id.ma_np_card_art);
        TextView title = findViewById(R.id.ma_np_card_title);
        SiPlayer siPlayer = SiPlayer.getInstance();
        if (siPlayer != null && SiPlayer.playing != null) {
            cardView.setVisibility(View.VISIBLE);
            title.setText(getString(R.string.italicText, SiPlayer.playing.getTitle()));
            Bitmap newArt = Utilities.Companion.getAlbumArt(this, SiPlayer.playing.getUri());
            if (newArt != null)
                albumArt.setImageBitmap(newArt);
            else albumArt.setImageResource(R.drawable.pic_sample_music_art);

            if (siPlayer.isPlaying())
                pause.setImageResource(R.drawable.ic_pause_solid);
            else pause.setImageResource(R.drawable.ic_play_solid);

            siPlayer.setOnCompletionListener(listener -> {
                try {
                    if (SiQueue.position == SiQueue.queue.size() - 1 && !SiQueue.isOnRepeat) {
                        npCardPause(findViewById(R.id.ma_np_card_pause).getRootView());
                    } else if (!SiQueue.isOnRepeatOne) {
                        npCardNext(findViewById(R.id.ma_np_card_next).getRootView());
                    } else {
                        siPlayer.playAgain(this);
                        updateTheCard();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void npCardNext(View view) throws Exception {
        SiPlayer siPlayer = SiPlayer.getInstance();
        if (siPlayer != null) {
            SiQueue.updatePosition(1);
            siPlayer.playTrack(this, SiQueue.getTrackToPlay());
            updateTheCard();
        }
    }

    public void npCardPause(View view) {
        SiPlayer siPlayer = SiPlayer.getInstance();
        if (siPlayer != null)
            if (siPlayer.isPlaying())
                siPlayer.pause();
            else siPlayer.start();
        updateTheCard();
    }

    public void npCardPrev(View view) throws Exception {
        SiPlayer siPlayer = SiPlayer.getInstance();
        if (siPlayer != null) {
            SiQueue.updatePosition(-1);
            siPlayer.playTrack(this, SiQueue.getTrackToPlay());
            updateTheCard();
        }
    }
}