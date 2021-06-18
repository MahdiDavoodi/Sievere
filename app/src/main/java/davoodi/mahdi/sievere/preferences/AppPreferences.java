package davoodi.mahdi.sievere.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private final SharedPreferences preferences;

    private static final String APP_PREF = "appPref";
    private static final String FIRST_TIME = "firstTime";

    public AppPreferences(Context context) {
        this.preferences = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
    }

    public boolean getFirstTime() {
        return preferences.getBoolean(FIRST_TIME, false);
    }

    public void setFirstTime(boolean firstTime) {
        preferences.edit().putBoolean(FIRST_TIME, firstTime).apply();
    }
}