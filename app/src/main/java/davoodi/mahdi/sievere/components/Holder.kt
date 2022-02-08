package davoodi.mahdi.sievere.components

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

open class Holder(
    val name: String?,
    val tracks: IntArray?,
    var cover: Bitmap? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createIntArray(),
        parcel.readParcelable(Bitmap::class.java.classLoader)
    )

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