package davoodi.mahdi.sievere.data;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

import davoodi.mahdi.sievere.components.Track;

public class DataLoader {

    public ArrayList<Track> getTracks(Context context, String[] projection,
                                      String selection,
                                      String[] selectionArgs,
                                      String sortOrder) {
        ArrayList<Track> tracks = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                Uri songUri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String length = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                int bitrate = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.BITRATE));
                int year = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
                String genre = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.GENRE));

                Track track = new Track(id, songUri, title, artist, album, length, bitrate, year, genre);
                tracks.add(track);

            } while (cursor.moveToNext());
        }
        assert cursor != null;
        cursor.close();
        return tracks;
    }
}
