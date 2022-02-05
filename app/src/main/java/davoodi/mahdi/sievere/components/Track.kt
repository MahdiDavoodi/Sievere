package davoodi.mahdi.sievere.components

import android.content.Context
import android.net.Uri
import davoodi.mahdi.sievere.R
import java.util.*

class Track(
    context: Context,
    val id: Long,
    val uri: Uri,
    val path: String,
    fileName: String,
    setTitle: String?,
    setArtist: String?,
    setAlbum: String?,
    val length: Long,
    val bitrate: Int,
    val year: Int,
    val genre: String?,
    val dateAdded: Long
) {
    val title = setTitle ?: fileName
    val artist = setArtist ?: context.resources.getString(R.string.unknown)
    val album = setAlbum ?: context.resources.getString(R.string.single)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val track = other as Track
        return id == track.id &&
                uri == track.uri
    }

    override fun hashCode(): Int {
        return Objects.hash(id, uri)
    }
}