package davoodi.mahdi.sievere.components

import android.content.Context
import davoodi.mahdi.sievere.data.DataLoader
import davoodi.mahdi.sievere.tools.Utilities

class Album(context: Context, name: String, tracks: IntArray) :
    Holder(name, tracks) {
    init {
        cover = Utilities.getAlbumArt(context, DataLoader.tracks[tracks.first()].uri)
    }
}