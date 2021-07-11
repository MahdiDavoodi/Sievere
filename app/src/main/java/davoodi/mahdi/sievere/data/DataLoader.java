package davoodi.mahdi.sievere.data;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

import java.util.ArrayList;

import davoodi.mahdi.sievere.components.Track;
import davoodi.mahdi.sievere.fragments.tracks.TracksAllFragment;

public class DataLoader {

    public static boolean isAllReady = false;
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
                long length = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION));
                int bitrate = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.BITRATE));
                int year = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.YEAR));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.GENRE));
                long added = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATE_ADDED));

                Track track = new Track(id,
                        songUri,
                        fileName,
                        title,
                        artist,
                        album,
                        length,
                        bitrate,
                        year,
                        genre,
                        added);
                all.add(track);
            } while (cursor.moveToNext());
        }
        assert cursor != null;
        cursor.close();
        return all;
    }

    public static void allTracksList(Context context, String[] projection,
                                     String selection,
                                     String[] selectionArgs,
                                     String sortOrder) {
        tracks = getTracks(context, projection, selection, selectionArgs, sortOrder);

        /*Must be in UI(Main) Thread*/
        Handler handler = new Handler(context.getMainLooper());
        Runnable runnable = () -> {
            if (tracks != null) {
                isAllReady = true;
                TracksAllFragment.getInstance().showTheList();
            }
        };
        handler.post(runnable);
    }
}