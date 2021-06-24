package davoodi.mahdi.sievere.data;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

import davoodi.mahdi.sievere.components.Track;
import davoodi.mahdi.sievere.fragments.tracks.TracksAllFragment;

public class DataLoader {

    public static ArrayList<Track> tracks = new ArrayList<>();

    public static ArrayList<Track> getTracks(Context context, String[] projection,
                                             String selection,
                                             String[] selectionArgs,
                                             String sortOrder) {
        ArrayList<Track> all = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                /*Track metadata*/
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID));
                Uri songUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                String fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)).split("\\.")[0];
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM));
                String length = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION));
                int bitrate = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.BITRATE));
                int year = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.YEAR));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.GENRE));
                long added = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATE_ADDED));

                int artistId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST_ID));
                Log.i("DataLoader", title + artistId);
                Track track = new Track(id, songUri, fileName, title, artist, album, length, bitrate, year, genre, added);
                all.add(track);

            } while (cursor.moveToNext());
        }
        assert cursor != null;
        cursor.close();
        tracks = all;
        return all;
    }

    public static void startAllTracksList(Context context, String[] projection,
                                          String selection,
                                          String[] selectionArgs,
                                          String sortOrder) {
        getTracks(context, projection, selection, selectionArgs, sortOrder);
        if (tracks != null)
            TracksAllFragment.getInstance().showTheList();
    }
}
