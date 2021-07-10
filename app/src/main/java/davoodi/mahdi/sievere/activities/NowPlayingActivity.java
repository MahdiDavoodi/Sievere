package davoodi.mahdi.sievere.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.masoudss.lib.WaveformSeekBar;

import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.components.Track;
import davoodi.mahdi.sievere.data.SiQueue;
import davoodi.mahdi.sievere.players.SiPlayer;

public class NowPlayingActivity extends AppCompatActivity {

    // UI
    TextView artist, title, seekbar_duration, seekbar_position;
    ImageView album_art;

    // Components
    SiPlayer siPlayer;
    Track track;
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

        artist = findViewById(R.id.npa_artist);
        title = findViewById(R.id.npa_title);
        seekbar_duration = findViewById(R.id.npa_total_duration);
        seekbar_position = findViewById(R.id.npa_current_position);
        album_art = findViewById(R.id.npa_album_art);

        setUp();
        configMusic();
    }

    private void setUp() {
        siPlayer = new SiPlayer(this);
        seekBar = findViewById(R.id.npa_seekbar);
        new Thread(() -> seekBar.setSampleFrom(WAVE_PATTERN)).start();

        seekBar.setOnProgressChanged((waveformSeekBar, v, fromUser) -> {
            current_position = waveformSeekBar.getProgress();
            if (fromUser)
                siPlayer.seekTo((int) current_position);
        });

        siPlayer.setOnCompletionListener(mediaPlayer -> {
            if (SiQueue.position == SiQueue.queue.size() - 1 && !SiQueue.isOnRepeat) {
                // Queue finished.
                pause();
            } else if (!SiQueue.isOnRepeatOne) {
                SiQueue.updatePosition(1);
                configMusic();
            } else
                configMusic();
        });
    }

    private void configMusic() {
        if (SiQueue.isQueueReady()) {
            track = SiQueue.getTrackToPlay();

            siPlayer.playTrack(track);
            buildUI();
        }
    }

    private void buildUI() {
        if (track != null) {
            title.setText(getResources().getString(R.string.italicText, track.getTitle()));
            artist.setText(getResources().getString(R.string.italicText, track.getArtistName()));

            // Set album art.
            if (getAlbumArt(track.getUri()) != null)
                album_art.setImageBitmap(getAlbumArt(track.getUri()));
            else
                album_art.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pic_sample_music_art, getTheme()));

            current_position = siPlayer.getCurrentPosition();
            total_duration = siPlayer.getDuration();

            seekbar_duration.setText(getTimes((long) total_duration));
            seekbar_position.setText(getTimes((long) current_position));
            seekBar.setMaxProgress((int) total_duration);

            Handler handler = new Handler();
            NowPlayingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        current_position = siPlayer.getCurrentPosition();
                        seekbar_position.setText(getTimes((long) current_position));
                        seekBar.setProgress((int) current_position);
                        handler.postDelayed(this, 500);
                    } catch (IllegalStateException ed) {
                        ed.printStackTrace();
                    }
                }
            });
        }
    }

    private String getTimes(long value) {
        int[] times = siPlayer.convertTime(value);
        String result;
        if (times[0] > 0) {
            result = getResources().getString(R.string.track_time_hour, times[0], times[1], times[2]);
        } else {
            result = getResources().getString(R.string.track_time_minutes, times[1], times[2]);
        }
        return result;
    }

    private Bitmap getAlbumArt(Uri uri) {
        android.media.MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this, uri);
        byte[] data = retriever.getEmbeddedPicture();
        if (data != null) return BitmapFactory.decodeByteArray(data, 0, data.length);
        return null;
    }

    private void pause() {
        if (siPlayer != null)
            siPlayer.pause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public void exit(View view) {
        onBackPressed();
    }

    public void changeVolume(View view) {
    }

    public void playPause(View view) {

    }

    public void next(View view) {
        SiQueue.updatePosition(1);
        configMusic();
    }

    public void previous(View view) {
        SiQueue.updatePosition(-1);
        configMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (siPlayer != null)
            siPlayer.release();
    }
}