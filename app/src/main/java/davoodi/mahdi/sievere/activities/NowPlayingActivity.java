package davoodi.mahdi.sievere.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.masoudss.lib.WaveformSeekBar;
import com.masoudss.lib.utils.Utils;
import com.masoudss.lib.utils.WaveGravity;

import davoodi.mahdi.sievere.R;

public class NowPlayingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        WaveformSeekBar waveformSeekBar = findViewById(R.id.waveformSeekBar);
        waveformSeekBar.setProgress(33);
        waveformSeekBar.setMaxProgress(100);
        waveformSeekBar.setWaveGravity(WaveGravity.CENTER);
        waveformSeekBar.setWaveBackgroundColor(ContextCompat.getColor(this, R.color.dark_accent));
        waveformSeekBar.setWaveProgressColor(ContextCompat.getColor(this, R.color.dark_primary));
        int[] array = {0, 0, 0, 0, 2, 5, 3, 6, 6, 4, 7, 1, 0, 0, 0};
        waveformSeekBar.setSampleFrom(array);
    }
}