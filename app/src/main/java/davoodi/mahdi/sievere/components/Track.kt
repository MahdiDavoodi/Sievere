package davoodi.mahdi.sievere.components

import android.content.Context
import android.net.Uri
import davoodi.mahdi.sievere.R
import java.util.*

class Track(
    var id: Long,
    var uri: Uri,
    var path: String,
    var fileName: String,
    private var title: String?,
    private var artistName: String?,
    private var albumName: String?,
    var length: Long,
    var bitrate: Int,
    var year: Int,
    private var genre: String?, var dateAdded: Long,
    val samples:IntArray
) {
    var albumId = 0
    var artistId = 0
    var trackNumber = 0
    var playedCount = 0
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val track = o as Track
        return id == track.id &&
                uri == track.uri
    }

    override fun hashCode(): Int {
        return Objects.hash(id, uri)
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun setAlbumName(albumName: String?) {
        this.albumName = albumName
    }

    fun setArtistName(artistName: String?) {
        this.artistName = artistName
    }

    fun setGenre(genre: String?) {
        this.genre = genre
    }

    fun getTitle(): String {
        return title ?: fileName
    }

    fun getAlbumName(context: Context): String {
        return albumName ?: context.resources.getString(R.string.single)
    }

    fun getArtistName(context: Context): String {
        return artistName ?: context.resources.getString(R.string.unknown)
    }

    fun getGenre(context: Context): String {
        return genre ?: context.resources.getString(R.string.blank)
    }

    companion object {
        const val KEY_ID = "id"
        const val KEY_URI = "uri"
        const val KEY_PATH = "path"
        const val KEY_TITLE = "title"
        const val KEY_ALBUM_ID = "albumId"
        const val KEY_ALBUM = "albumName"
        const val KEY_ARTIST_ID = "artistId"
        const val KEY_ARTIST = "artistName"
        const val KEY_LENGTH = "length"
        const val KEY_NUMBER = "trackNumber"
        const val KEY_BITRATE = "bitrate"
        const val KEY_YEAR = "year"
        const val KEY_GENRE = "genre"
        const val KEY_PLAYED = "playedCount"
    }
}