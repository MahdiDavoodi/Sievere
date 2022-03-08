package davoodi.mahdi.sievere.data

import android.provider.MediaStore
import android.content.ContentUris
import android.content.Context
import davoodi.mahdi.sievere.R
import davoodi.mahdi.sievere.components.Album
import davoodi.mahdi.sievere.components.Artist
import davoodi.mahdi.sievere.components.Track
import java.util.ArrayList

object DataLoader {

    @JvmField
    var tracks: ArrayList<Track> = ArrayList()

    @JvmField
    var albums: ArrayList<Album> = ArrayList()

    @JvmField
    var artists: ArrayList<Artist> = ArrayList()

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
                val fileName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME))
                        .split("\\.").first()
                val track = Track(
                    id,
                    uri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    ),
                    path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)),
                    fileName = fileName,
                    title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE))
                        ?: fileName,
                    artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST))
                        ?: context.getString(
                            R.string.unknown
                        ),
                    album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM))
                        ?: context.getString(
                            R.string.single
                        ),
                )
                all.add(track)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        tracks = all
        return all
    }

    private fun updateAlbums(): ArrayList<Album> {
        if (tracks.isNotEmpty()) {
            val map = mutableMapOf<String, ArrayList<Int>>()
            for (i in tracks.indices) {
                val track = tracks[i]
                if (track.album in map) map[track.album]?.add(i)
                else map += track.album to arrayListOf(i)
            }
            val updatedAlbums = arrayListOf<Album>()
            for ((name, tracks) in map) updatedAlbums.add(Album(name, tracks.toIntArray()))
            albums = updatedAlbums
        }
        return albums
    }

    private fun updateArtists(): ArrayList<Artist> {
        if (tracks.isNotEmpty()) {
            val map = mutableMapOf<String, ArrayList<Int>>()
            for (i in tracks.indices) {
                val track = tracks[i]
                if (track.artist in map) map[track.artist]?.add(i)
                else map += track.artist to arrayListOf(i)
            }
            val updatedArtists = arrayListOf<Artist>()
            for ((name, tracks) in map) updatedArtists.add(Artist(name, tracks.toIntArray()))
            artists = updatedArtists
        }
        return artists
    }

    @JvmStatic
    fun loadData(
        context: Context, projection: Array<String?>?,
        selection: String?,
        selectionArgs: Array<String?>?,
        sortOrder: String?
    ) {
        updateTracks(context, projection, selection, selectionArgs, sortOrder)
        updateAlbums()
        updateArtists()

        if (!tracks.isNullOrEmpty())
            SiQueue.defaultSamples = IntArray(200) { (0..20).random() }
    }
}