package davoodi.mahdi.sievere.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.masoudss.lib.WaveformSeekBar;
import com.masoudss.lib.utils.WaveGravity;

import davoodi.mahdi.sievere.R;

public class NowPlayingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        WaveformSeekBar waveformSeekBar = findViewById(R.id.npa_seekbar);
        int[] array = {0, 1, 2, 3, 4, 5, 6, 2, 3, 8, 2, 3, 4, 6, 1, 2, 3, 4, 5, 6, 2, 3, 8, 2, 3, 4, 6, 1, 2, 3, 4, 5, 6, 2, 3, 8, 2, 3, 4, 6, 1, 2, 3, 4, 5, 6, 2, 3, 8, 2, 3, 4, 6, 1, 2, 3, 4, 5, 6, 2, 3, 8, 2, 3, 4, 6, 1, 2, 3, 4, 5, 6, 2, 3, 8, 2, 3, 4, 6, 1, 2, 3, 4, 5, 6, 2, 3, 8, 2, 3, 4, 6, 1, 2, 3, 4, 5, 6, 2, 3, 8, 2, 3, 4, 6, 1, 2, 3, 4, 5, 6, 2, 3, 8, 2, 3, 4, 6, 0, 8, 4, 6, 3, 2, 3, 5, 8, 0, 0, 0, 2, 5, 3, 6, 6, 4, 7, 1, 0, 0, 0};
        waveformSeekBar.setSampleFrom(array);
    }
}