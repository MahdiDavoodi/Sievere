package davoodi.mahdi.sievere.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import davoodi.mahdi.sievere.components.TracksInPlaylists;

public class RelatedData extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "si-related-db";

    private static final String TABLE_TP = "tracksInPlaylists";
    private static final String CMD_CREATE_TP = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_TP + "' " + "( '"
            + TracksInPlaylists.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + TracksInPlaylists.KEY_TRACK + "' INTEGER, '"
            + TracksInPlaylists.KEY_PLAYLIST + "' INTEGER"
            + ")";

    public RelatedData(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CMD_CREATE_TP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*TODO: Finish this.*/
    }
}
