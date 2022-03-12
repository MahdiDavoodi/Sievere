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

    private fun updateTracks(context: Context): ArrayList<Track> {
        val all = ArrayList<Track>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor =
            context.contentResolver.query(
                uri,
                arrayOf(
                    MediaStore.Audio.AudioColumns._ID,
                    MediaStore.Audio.AudioColumns.DISPLAY_NAME,
                    MediaStore.Audio.AudioColumns.DATA,
                    MediaStore.Audio.AudioColumns.TITLE,
                    MediaStore.Audio.AudioColumns.ARTIST,
                    MediaStore.Audio.AudioColumns.ALBUM,
                ),
                null,
                null,
                MediaStore.Audio.Media.DATE_ADDED + " DESC"
            )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID))
                val fileName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME))
                        .split("\\.").first()
                val title =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE))
                        ?: fileName
                val track = Track(
                    id,
                    uri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    ),
                    path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)),
                    fileName = fileName,
                    title = title,
                    artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST))
                        ?: context.getString(
                            R.string.unknown
                        ),
                    album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM))
                        .shortTitle()
                        ?: context.getString(
                            R.string.single
                        ),
                    shortTitle = title.shortTitle() ?: title
                )
                all.add(track)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        tracks = all
        return all
    }

    private fun updateAlbumsAndArtists() {
        if (tracks.isNotEmpty()) {
            val alb = mutableMapOf<String, ArrayList<Int>>()
            val art = mutableMapOf<String, ArrayList<Int>>()
            for (i in tracks.indices) with(tracks[i]) {
                when (album) {
                    in alb -> alb[album]?.add(i)
                    else -> alb += album to arrayListOf(i)
                }
                when (artist) {
                    in art -> art[artist]?.add(i)
                    else -> art += artist to arrayListOf(i)
                }
            }
            albums.clear()
            artists.clear()
            for ((name, tracks) in alb) albums.add(Album(name, tracks.toIntArray()))
            for ((name, tracks) in art) artists.add(Artist(name, tracks.toIntArray()))
        }
    }

    @JvmStatic
    fun loadData(
        context: Context
    ) {
        updateTracks(context)
        updateAlbumsAndArtists()

        if (!tracks.isNullOrEmpty())
            SiQueue.defaultSamples = IntArray(300) { (1..20).random() }
    }
}

fun String?.shortTitle(): String? {
    return if (this == null) null
    else {
        val index = this.indexOf('(')
        if (index != -1) this.substring(0, index)
        else this
    }
}