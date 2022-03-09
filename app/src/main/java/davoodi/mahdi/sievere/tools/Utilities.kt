package davoodi.mahdi.sievere.tools

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri

class Utilities {
    companion object {
        fun getAlbumArt(context: Context, uri: Uri): Bitmap? {
            val data = getAlbumArtByte(context, uri)
            return if (data != null) BitmapFactory.decodeByteArray(data, 0, data.size) else null
        }

        fun getAlbumArt(data: ByteArray) = BitmapFactory.decodeByteArray(data, 0, data.size)

        fun getAlbumArtByte(context: Context, uri: Uri): ByteArray? {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(context, uri)
            return retriever.embeddedPicture
        }
    }
}