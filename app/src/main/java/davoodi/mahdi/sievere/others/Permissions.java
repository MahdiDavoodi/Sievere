package davoodi.mahdi.sievere.others;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {
    public static final int PERMISSION_READ_STORAGE = 0;

    public static boolean checkStoragePermission(Activity activity) {
        int READ_PERMISSION = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_READ_STORAGE);
            return false;
        }
        return true;
    }
}
