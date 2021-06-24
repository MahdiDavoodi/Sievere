package davoodi.mahdi.sievere.data;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

import davoodi.mahdi.sievere.components.Track;
import davoodi.mahdi.sievere.fragments.tracks.TracksAllFragment;

public class DataLoader {

    public static ArrayList<Track> tracks;
    public static boolean initialDataReady = false;

    public static ArrayList<Track> getTracks(Context context, String[] projection,
                                             String selection,
                                             String[] selectionArgs,
                                             String sortOrder) {
        ArrayList<Track> all = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
                Uri songUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                String length = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                int bitrate = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.BITRATE)));
                int year = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR)));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.GENRE));

                Track track = new Track(id, songUri, title, artist, album, length, bitrate, year, genre);
                all.add(track);

            } while (cursor.moveToNext());
        }
        assert cursor != null;
        cursor.close();
        tracks = all;
        return all;
    }

    public static void tfAllListStart(Context context, String[] projection,
                                      String selection,
                                      String[] selectionArgs,
                                      String sortOrder) {
        getTracks(context, projection, selection, selectionArgs, sortOrder);
        if (tracks != null) {
            initialDataReady = true;
            TracksAllFragment fragment = new TracksAllFragment();
            fragment.showTheList();
        }
    }
}
