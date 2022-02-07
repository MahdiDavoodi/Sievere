package davoodi.mahdi.sievere.data

import android.provider.MediaStore
import android.content.ContentUris
import android.content.Context
import davoodi.mahdi.sievere.components.Album
import davoodi.mahdi.sievere.components.Track
import linc.com.amplituda.Amplituda
import java.util.ArrayList

object DataLoader {

    @JvmField
    var tracks: ArrayList<Track> = ArrayList()

    @JvmField
    var albums: ArrayList<Album> = ArrayList()

    private fun updateTracks(
        context: Context, projection: Array<String?>?,
        selection: String?,
        selectionArgs: Array<String?>?,
        sortOrder: String?
    ): ArrayList<Track> {
        val all = ArrayList<Track>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor =
            context.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID))
                val track = Track(
                    context,
                    id,
                    ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME))
                        .split("\\.").first(),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.BITRATE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.YEAR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.GENRE)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATE_ADDED))
                )
                all.add(track)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        tracks = all
        return all
    }

    private fun updateAlbums(context: Context): ArrayList<Album> {
        if (tracks.isNotEmpty()) {
            val map = mutableMapOf<String, ArrayList<Int>>()
            for (i in tracks.indices) {
                val track = tracks[i]
                if (track.album in map)
                    map[track.album]?.add(i)
                else map += track.album to arrayListOf(i)
            }
            val updatedAlbums = arrayListOf<Album>()
            for ((name, tracks) in map) updatedAlbums.add(Album(context, name, tracks.toIntArray()))
            albums = updatedAlbums
        }
        return albums
    }

    @JvmStatic
    fun loadData(
        context: Context, projection: Array<String?>?,
        selection: String?,
        selectionArgs: Array<String?>?,
        sortOrder: String?
    ) {
        updateTracks(context, projection, selection, selectionArgs, sortOrder)
        updateAlbums(context)

        if (!tracks.isNullOrEmpty())
            SiQueue.defaultSamples =
                Amplituda(context).processAudio(tracks.first().path).get().amplitudesAsList()
                    .toIntArray()
    }
}