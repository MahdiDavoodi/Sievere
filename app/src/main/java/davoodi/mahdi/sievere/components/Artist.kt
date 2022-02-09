package davoodi.mahdi.sievere.components

import android.content.Context
import android.graphics.Bitmap
import davoodi.mahdi.sievere.data.DataLoader
import davoodi.mahdi.sievere.tools.Utilities

class Artist(
    context: Context,
    name: String,
    tracks: IntArray,
    cover: Bitmap? = Utilities.getAlbumArt(context, DataLoader.tracks[tracks.last()].uri),
) : Holder(name, tracks, cover)