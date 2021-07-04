package davoodi.mahdi.sievere.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.masoudss.lib.WaveformSeekBar;

import davoodi.mahdi.sievere.R;

public class NowPlayingActivity extends AppCompatActivity {

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

        WaveformSeekBar waveformSeekBar = findViewById(R.id.npa_seekbar);
        new Thread(() -> waveformSeekBar.setSampleFrom(WAVE_PATTERN)).start();
    }
}