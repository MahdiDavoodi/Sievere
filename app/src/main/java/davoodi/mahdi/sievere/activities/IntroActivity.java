package davoodi.mahdi.sievere.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import davoodi.mahdi.sievere.R;

public class IntroActivity extends AppCompatActivity {

    protected static final int DELAY = 500;
    public static final int PERMISSION_STORAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        if (checkReadStoragePermission())
            new Thread(this::startTheApp).start();
    }

    private void startTheApp() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish();
        }
    }

    public boolean checkReadStoragePermission() {
        int READ_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions,
                    PERMISSION_STORAGE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_STORAGE) {
            if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String toast;
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    toast = getResources().getString(R.string.denied_read_data_permission_toast);
                } else {
                    new Thread(this::startTheApp).start();
                    toast = getResources().getString(R.string.accepted_read_data_permission_toast);
                }
                Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
            }
        }
    }
}