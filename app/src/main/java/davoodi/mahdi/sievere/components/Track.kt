package davoodi.mahdi.sievere.components

import android.content.Context
import android.net.Uri
import davoodi.mahdi.sievere.R

data class Track(
    private val context: Context,
    val id: Long,
    val uri: Uri,
    val path: String,
    private val fileName: String,
    private val setTitle: String?,
    private val setArtist: String?,
    private val setAlbum: String?,
) {
    val title = setTitle ?: fileName
    val artist = setArtist ?: context.resources.getString(R.string.unknown)
    val album = setAlbum ?: context.resources.getString(R.string.single)
}