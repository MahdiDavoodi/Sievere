package davoodi.mahdi.sievere.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.masoudss.lib.WaveformSeekBar;

import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.components.Track;
import davoodi.mahdi.sievere.data.SiQueue;
import davoodi.mahdi.sievere.players.SiPlayer;
import davoodi.mahdi.sievere.preferences.Finals;

public class NowPlayingActivity extends AppCompatActivity {

    TextView artist, title, seekbar_duration, seekbar_position;
    ImageView album_art;
    ImageButton play_pause, shuffle, repeat;

    SiPlayer siPlayer;
    Track track;
    WaveformSeekBar seekBar;
    double current_position, total_duration;
    boolean play_song;

    private final int[] WAVE_PATTERN = {0, 1, 1, 0, 1, 1, 2, 3, 4, 2,
            1, 0, 1, 5, 4, 6, 1, 2, 8, 6, 4, 3, 1, 1, 1, 1, 2, 3, 1, 5
            , 4, 5, 2, 8, 4, 1, 1, 2, 1, 5, 6, 4, 5, 6, 8, 9, 1, 2,
            5, 4, 5, 6, 1, 2, 1, 4, 5, 5, 6, 5, 4, 6, 8, 9, 8, 7, 5,
            9, 8, 7, 6, 4, 0, 5, 1, 9, 6, 4, 5, 9, 8, 4, 2, 3, 1, 1, 1, 0, 0};

    Drawable ic_repeat_one_primary_color,
            ic_play_solid,
            ic_pause_solid,
            ic_shuffle_solid,
            ic_shuffle_primary_color,
            ic_repeat_primary_color,
            ic_repeat_solid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                play_song = false;
            } else {
                play_song = extras.getBoolean(Finals.PLAY);
            }
        } else {
            play_song = (boolean) savedInstanceState.getSerializable(Finals.PLAY);
        }

        setUpUI();

        if (SiQueue.isQueueReady())
            setUpActivity(play_song);
    }

    private void setUpUI() {
        ic_repeat_one_primary_color = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_repeat_one_primary_color, getTheme());
        ic_play_solid = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_play_solid, getTheme());
        ic_pause_solid = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_pause_solid, getTheme());
        ic_shuffle_solid = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_shuffle_solid, getTheme());
        ic_shuffle_primary_color = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_shuffle_primary_color, getTheme());
        ic_repeat_primary_color = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_repeat_primary_color, getTheme());
        ic_repeat_solid = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_repeat_solid, getTheme());


        artist = findViewById(R.id.npa_tv2);
        title = findViewById(R.id.npa_tv1);
        seekbar_duration = findViewById(R.id.npa_total_duration);
        seekbar_position = findViewById(R.id.npa_current_position);
        album_art = findViewById(R.id.npa_iv);
        seekBar = findViewById(R.id.npa_sb);
        play_pause = findViewById(R.id.npa_play);
        shuffle = findViewById(R.id.npa_shuffle);
        repeat = findViewById(R.id.npa_repeat);
        new Thread(() -> seekBar.setSampleFrom(WAVE_PATTERN)).start();
    }

    private void setUpActivity(boolean play) {
        siPlayer = SiPlayer.getInstance();
        if (siPlayer == null)
            siPlayer = new SiPlayer();

        setTrack();

        if (track != null) {
            if (play) {
                configMusic();
            } else {
                buildUI();
                configIcons();
            }
        }

        seekBar.setOnProgressChanged((waveformSeekBar, v, fromUser) -> {
            current_position = waveformSeekBar.getProgress();
            if (fromUser)
                siPlayer.seekTo((int) current_position);
        });

        siPlayer.setOnCompletionListener(mediaPlayer -> {
            if (SiQueue.position == SiQueue.queue.size() - 1 && !SiQueue.isOnRepeat) {
                // Queue finished.
                mediaPlayer.pause();
            } else if (!SiQueue.isOnRepeatOne) {
                SiQueue.updatePosition(1);
                configMusic();
            } else
                configMusic();
        });
    }

    private void configMusic() {
        setTrack();
        siPlayer.playTrack(this, track);
        buildUI();
        configIcons();
    }

    private void setTrack() {
        if (SiQueue.isQueueReady())
            track = SiQueue.getTrackToPlay();
    }

    private void configIcons() {
        if (siPlayer != null) {
            if (!siPlayer.isPlaying())
                play_pause.setImageDrawable(ic_play_solid);
            else
                play_pause.setImageDrawable(ic_pause_solid);
            if (SiQueue.isOnShuffle)
                shuffle.setImageDrawable(ic_shuffle_primary_color);
            else shuffle.setImageDrawable(ic_shuffle_solid);
            if (SiQueue.isOnRepeatOne)
                repeat.setImageDrawable(ic_repeat_one_primary_color);
            else if (SiQueue.isOnRepeat)
                repeat.setImageDrawable(ic_repeat_primary_color);
            else repeat.setImageDrawable(ic_repeat_solid);
        }
    }

    private void buildUI() {
        if (track != null) {
            title.setText(getResources().getString(R.string.italicText, track.getTitle()));
            artist.setText(getResources().getString(R.string.italicText, track.getArtistName(this)));

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public void exit(View view) {
        onBackPressed();
    }

    public void showSystemVolume(View view) {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, AudioManager.FLAG_SHOW_UI);
    }

    public void playPause(View view) {
        if (siPlayer != null) {
            if (siPlayer.isPlaying())
                siPlayer.pause();
            else
                siPlayer.start();
            configIcons();
        }
    }

    public void next(View view) {
        SiQueue.updatePosition(1);
        configMusic();
    }

    public void previous(View view) {
        SiQueue.updatePosition(-1);
        configMusic();
    }

    public void shuffle(View view) {
        if (SiQueue.isOnShuffle) {
            SiQueue.unShuffle();
            SiQueue.isOnShuffle = false;
        } else {
            SiQueue.shuffle();
            SiQueue.isOnShuffle = true;
        }
        SiQueue.position = SiQueue.findTrackPosition(track);
        configIcons();
    }

    public void repeat(View view) {
        if (SiQueue.isOnRepeat && !SiQueue.isOnRepeatOne) {
            SiQueue.isOnRepeatOne = true;
        } else if (SiQueue.isOnRepeatOne) {
            SiQueue.isOnRepeat = false;
            SiQueue.isOnRepeatOne = false;
        } else {
            SiQueue.isOnRepeat = true;
        }
        configIcons();
    }

    public void favorite(View view) {
        // database.
    }
}