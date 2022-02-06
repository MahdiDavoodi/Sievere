package davoodi.mahdi.sievere.data

import android.provider.MediaStore
import android.content.ContentUris
import android.content.Context
import android.os.Handler
import davoodi.mahdi.sievere.components.Album
import davoodi.mahdi.sievere.components.Track
import davoodi.mahdi.sievere.fragments.tracks.TracksAllFragment
import linc.com.amplituda.Amplituda
import java.util.ArrayList

object DataLoader {
    @JvmField
    var isAllReady = false

    @JvmField
    var tracks: ArrayList<Track> = ArrayList()

    @JvmField
    var albums: ArrayList<Album> = ArrayList()

    private fun getTracks(
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
                val songUri =
                    ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                val path =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA))
                val fileName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME))
                        .split("\\.").toTypedArray()[0]
                val title =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE))
                val artist =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST))
                val album =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM))
                val length =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION))
                val bitrate =
                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.BITRATE))
                val year =
                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.YEAR))
                val genre =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.GENRE))
                val added =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATE_ADDED))

                val track = Track(
                    context,
                    id,
                    songUri,
                    path,
                    fileName,
                    title,
                    artist,
                    album,
                    length,
                    bitrate,
                    year,
                    genre,
                    added
                )
                all.add(track)
            } while (cursor.moveToNext())
        }
        assert(cursor != null)
        cursor!!.close()
        return all
    }

    fun updateAlbums(context: Context): ArrayList<Album> {
        if (tracks.isNotEmpty()) {
            val map = mutableMapOf<String, ArrayList<Track>>()
            for (track in tracks) {
                if (track.album in map)
                    map[track.album]?.add(track)
                else map += track.album to arrayListOf(track)
            }
            val updatedAlbums = arrayListOf<Album>()
            for ((name, tracks) in map) updatedAlbums.add(Album(context, name, tracks))
            albums = updatedAlbums
        }
        return albums
    }

    @JvmStatic
    fun allTracksList(
        context: Context, projection: Array<String?>?,
        selection: String?,
        selectionArgs: Array<String?>?,
        sortOrder: String?
    ) {
        tracks = getTracks(context, projection, selection, selectionArgs, sortOrder)
        if (!tracks.isNullOrEmpty())
            SiQueue.defaultSamples =
                Amplituda(context).processAudio(tracks!!.first().path).get().amplitudesAsList()
                    .toIntArray()
        val handler = Handler(context.mainLooper)
        val runnable = Runnable {
            isAllReady = true
            TracksAllFragment.getInstance().showTheList()
        }
        handler.post(runnable)
    }
}