package davoodi.mahdi.sievere.components

import android.net.Uri

data class Track(
    val id: Long,
    val uri: Uri,
    val path: String,
    val fileName: String,
    val title: String,
    val artist: String,
    val album: String,
)