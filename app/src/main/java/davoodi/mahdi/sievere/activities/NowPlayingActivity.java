package davoodi.mahdi.sievere.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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
        player = new SiPlayer();
        seekBar = findViewById(R.id.npa_seekbar);
        new Thread(() -> seekBar.setSampleFrom(WAVE_PATTERN)).start();

        // Find UI elements
        artist = findViewById(R.id.npa_artist);
        title = findViewById(R.id.npa_title);
        duration = findViewById(R.id.npa_total_duration);
        duration_passed = findViewById(R.id.npa_current_position);
        picture = findViewById(R.id.npa_album_art);

        setListeners();
        start();

    }

    private void setListeners() {
        seekBar.setOnProgressChanged(new SeekBarOnProgressChanged() {
            @Override
            public void onProgressChanged(@NotNull WaveformSeekBar waveformSeekBar, float v, boolean b) {
                current_position = waveformSeekBar.getProgress();
                player.seekTo((int) current_position);
            }
        });

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // when completed.
            }
        });

    }

    private void start() {
        setNowPlaying();
        playTrack();
        buildUI();
    }

    private void setNowPlaying() {
        if (SiQueue.isQueueReady())
            nowPlaying = SiQueue.getTrackToPlay();

    }

    private void playTrack() {
        try {
            player.reset();
            player.setDataSource(this, nowPlaying.getUri());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildUI() {
        setTrackProgress();
    }

    private void setTrackProgress() {
        //get the audio duration
        current_position = player.getCurrentPosition();
        total_duration = player.getDuration();

        //display the audio duration
        duration.setText(player.convertTime((long) total_duration));
        duration_passed.setText(player.convertTime((long) current_position));
        seekBar.setMaxProgress((int) total_duration);
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current_position = player.getCurrentPosition();
                    duration_passed.setText(player.convertTime((long) current_position));
                    seekBar.setProgress((int) current_position);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
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