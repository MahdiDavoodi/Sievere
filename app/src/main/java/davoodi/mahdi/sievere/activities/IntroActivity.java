package davoodi.mahdi.sievere.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import davoodi.mahdi.sievere.R;

public class IntroActivity extends AppCompatActivity {

    protected static final int DELAY = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        new Thread(this::nextActivity).start();
    }

    private void nextActivity() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish();
        }
    }
}