package davoodi.mahdi.sievere.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.masoudss.lib.SeekBarOnProgressChanged;
import com.masoudss.lib.WaveformSeekBar;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.components.Track;
import davoodi.mahdi.sievere.data.SiQueue;
import davoodi.mahdi.sievere.players.SiPlayer;

public class NowPlayingActivity extends AppCompatActivity {

    // UI
    TextView artist, title, duration, duration_passed;
    ImageView picture;

    // Components
    SiPlayer player;
    Track nowPlaying;
    WaveformSeekBar seekBar;
    double current_position, total_duration;

    // Sample final array for seekbar waves pattern. I have to change it in the future.
    // TODO: Create wave pattern with audio files.(Already have the dependency)
    private final int[] WAVE_PATTERN = {0, 1, 1, 0, 1, 1, 2, 3, 4, 2,
            1, 0, 1, 5, 4, 6, 1, 2, 8, 6, 4, 3, 1, 1, 1, 1, 2, 3, 1, 5
            , 4, 5, 2, 8, 4, 1, 1, 2, 1, 5, 6, 4, 5, 6, 8, 9, 1, 2,
            5, 4, 5, 6, 1, 2, 1, 4, 5, 5, 6, 5, 4, 6, 8, 9, 8, 7, 5,
            9, 8, 7, 6, 4, 0, 5, 1, 9, 6, 4, 5, 9, 8, 4, 2, 3, 1, 1, 1, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        setUp();

        start();
    }

    private void setUp() {
        player = new SiPlayer(this);
        seekBar = findViewById(R.id.npa_seekbar);
        new Thread(() -> seekBar.setSampleFrom(WAVE_PATTERN)).start();

        artist = findViewById(R.id.npa_artist);
        title = findViewById(R.id.npa_title);
        duration = findViewById(R.id.npa_total_duration);
        duration_passed = findViewById(R.id.npa_current_position);
        picture = findViewById(R.id.npa_album_art);

        seekBar.setOnProgressChanged((waveformSeekBar, v, fromUser) -> {
            current_position = waveformSeekBar.getProgress();
            if (fromUser)
                player.seekTo((int) current_position);
        });

        player.setOnCompletionListener(mediaPlayer -> {
            if (SiQueue.position == SiQueue.queue.size() - 1 && !SiQueue.isOnRepeat) {
                // Queue finished.
                pause();
            } else if (!SiQueue.isOnRepeatOne) {
                SiQueue.updatePosition(1);
                start();
            } else
                start();

        });
    }


    private void pause() {
        if (player != null)
            player.pause();
    }

    private void start() {
        setNowPlaying();
        player.playTrack(nowPlaying);
        setTrackProgress();
        refreshUI();
    }

    private void setNowPlaying() {
        if (SiQueue.isQueueReady())
            nowPlaying = SiQueue.getTrackToPlay();
    }

    private void refreshUI() {
        if (nowPlaying != null) {
            title.setText(nowPlaying.getTitle());
            artist.setText(nowPlaying.getArtistName());

            // Set album art.
            if (getAlbumArt(nowPlaying.getUri()) != null)
                picture.setImageBitmap(getAlbumArt(nowPlaying.getUri()));
            else
                picture.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pic_sample_music_art, getTheme()));

        }
    }

    private Bitmap getAlbumArt(Uri uri) {
        android.media.MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this, uri);
        byte[] data = retriever.getEmbeddedPicture();
        if (data != null) return BitmapFactory.decodeByteArray(data, 0, data.length);
        return null;
    }


    private void setTrackProgress() {
        current_position = player.getCurrentPosition();
        total_duration = player.getDuration();

        duration.setText(player.convertTime((long) total_duration));
        duration_passed.setText(player.convertTime((long) current_position));
        seekBar.setMaxProgress((int) total_duration);

        Handler handler = new Handler();
        NowPlayingActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    current_position = player.getCurrentPosition();
                    duration_passed.setText(player.convertTime((long) current_position));
                    seekBar.setProgress((int) current_position);
                    handler.postDelayed(this, 500);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public void exit(View view) {
        onBackPressed();
    }
}