package davoodi.mahdi.sievere.components

import android.content.Context
import android.graphics.Bitmap
import davoodi.mahdi.sievere.tools.Utilities

class Album(context: Context, val name: String, val tracks: List<Track>) {
    var cover: Bitmap? = null

    init {
        cover = Utilities.getAlbumArt(context, tracks.first().uri)
    }
}