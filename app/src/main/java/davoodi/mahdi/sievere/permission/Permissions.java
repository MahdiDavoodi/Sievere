package davoodi.mahdi.sievere.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

import davoodi.mahdi.sievere.R;

public class Permissions extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    public static final int PERMISSION_READ_STORAGE = 0;

    public static boolean checkReadStoragePermission(Activity activity) {
        int READ_PERMISSION = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_READ_STORAGE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_READ_STORAGE) {
            if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(),
                            getApplication().getResources().getString(R.string.denied_read_data_permission_toast),
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
