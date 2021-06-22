package davoodi.mahdi.sievere.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import davoodi.mahdi.sievere.components.Album;
import davoodi.mahdi.sievere.components.Artist;
import davoodi.mahdi.sievere.components.Playlist;
import davoodi.mahdi.sievere.components.Track;

public class ComponentsDatabase extends SQLiteOpenHelper {
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
    private static final String CMD_CREATE_ARTISTS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_ARTISTS + "' " + "( '"
            + Artist.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Artist.KEY_NAME + "' TEXT, '"
            + Artist.KEY_TRACKS + "' INTEGER, '"
            + Artist.KEY_ALBUMS + "' INTEGER"
            + ")";

    private static final String TABLE_TRACKS = "tracks";
    private static final String CMD_CREATE_TRACKS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_TRACKS + "' " + "( '"
            + Track.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Track.KEY_PATH + "' TEXT, '"
            + Track.KEY_TITLE + "' TEXT, '"
            + Track.KEY_ALBUM_ID + "' INTEGER, '"
            + Track.KEY_ALBUM + "' TEXT, '"
            + Track.KEY_ARTIST_ID + "' INTEGER, '"
            + Track.KEY_ARTIST + "' TEXT, '"
            + Track.KEY_LENGTH + "' INTEGER, '"
            + Track.KEY_NUMBER + "' INTEGER, '"
            + Track.KEY_BITRATE + "' INTEGER, '"
            + Track.KEY_YEAR + "' INTEGER, '"
            + Track.KEY_GENRE + "' TEXT, '"
            + Track.KEY_PLAYED + "' INTEGER"
            + ")";

    private static final String TABLE_PLAYLISTS = "playlists";
    private static final String CMD_CREATE_PLAYLISTS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PLAYLISTS + "' " + "( '"
            + Playlist.KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '"
            + Playlist.KEY_NAME + "' TEXT, '"
            + Playlist.KEY_TRACKS + "' INTEGER"
            + ")";

    public ComponentsDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CMD_CREATE_TRACKS);
        db.execSQL(CMD_CREATE_ARTISTS);
        db.execSQL(CMD_CREATE_ALBUMS);
        db.execSQL(CMD_CREATE_PLAYLISTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*TODO: Handle update.*/
    }
}