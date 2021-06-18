package davoodi.mahdi.sievere.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import davoodi.mahdi.sievere.components.Album;

public class ComponentsData extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "si-components-db";

    private static final String TABLE_ALBUMS = "albums";
    private static final String CMD_CREATE_ALBUMS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_ALBUMS + "' " + "( '"
            + Album.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Album.KEY_TITLE + "' TEXT, '"
            + Album.KEY_ARTIST + "' INTEGER, '"
            + Album.KEY_ARTIST_NAME + "' TEXT, '"
            + Album.KEY_TRACKS + "' INTEGER, '"
            + Album.KEY_YEAR + "' INTEGER"
            + ")";

    private static final String TABLE_ARTISTS = "artists";
    private static final String CMD_CREATE_ARTISTS = "";

    private static final String TABLE_TRACKS = "tracks";
    private static final String CMD_CREATE_TRACKS = "";

    private static final String TABLE_PLAYLISTS = "playlists";
    private static final String CMD_CREATE_PLAYLISTS = "";

    public ComponentsData(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
