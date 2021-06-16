package davoodi.mahdi.sievere.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import davoodi.mahdi.sievere.R;

public class InfoActivity extends AppCompatActivity {

    public static final String TELEGRAM = "https://t.me/MusicSievere";
    public static final String REPOSITORY = "https://github.com/MahdiDavoodi/Sievere";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    public void infoTelegram(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM)));
    }

    public void infoGithub(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(REPOSITORY)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}