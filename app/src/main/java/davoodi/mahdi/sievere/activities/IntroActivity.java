package davoodi.mahdi.sievere.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.data.DataLoader;

public class IntroActivity extends AppCompatActivity {

    public static final int PERMISSION_STORAGE = 0;
    public static final String TAG = "IntroActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_LONG).show();
        if (checkReadStoragePermission())
            new Thread(this::loadData).start();
    }

    public void loadData() {
        Thread loadData = new Thread(() -> DataLoader.loadData(this));
        loadData.start();
        try {
            loadData.join();
            startApp();
        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void startApp() {
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
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
                    new Thread(this::loadData).start();
                    toast = getResources().getString(R.string.accepted_read_data_permission_toast);
                }
                Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
            }
        }
    }
}