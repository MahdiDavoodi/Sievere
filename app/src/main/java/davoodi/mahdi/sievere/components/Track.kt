package davoodi.mahdi.sievere.components

data class Track(
    val id: Long,
    val uri: android.net.Uri,
    val path: String,
    val fileName: String,
    val title: String,
    val artist: String,
    val album: String,
)