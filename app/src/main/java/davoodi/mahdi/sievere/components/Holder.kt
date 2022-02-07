package davoodi.mahdi.sievere.components

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

open class Holder(val name: String?, val tracks: IntArray?) : Parcelable {
    var cover: Bitmap? = null

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createIntArray()
    ) {
        cover = parcel.readParcelable(Bitmap::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeIntArray(tracks)
        parcel.writeParcelable(cover, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Holder> {
        override fun createFromParcel(parcel: Parcel): Holder {
            return Holder(parcel)
        }

        override fun newArray(size: Int): Array<Holder?> {
            return arrayOfNulls(size)
        }
    }
}