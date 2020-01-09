package minipaige.example.provenance.com.Model

import android.os.Parcel
import android.os.Parcelable

class ArchivalItem(
    var repository: String?,
    var collection: String?,
    var box: String?,
    var folder: String?,
    var otherCntr: String?,
    var description: String?,
    var tags: ArrayList<String>,
    var image: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readArrayList(ClassLoader.getSystemClassLoader()) as ArrayList<String>,
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(repository)
        parcel.writeString(collection)
        parcel.writeString(box)
        parcel.writeString(folder)
        parcel.writeString(otherCntr)
        parcel.writeString(description)
        parcel.writeStringList(tags)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArchivalItem> {
        override fun createFromParcel(parcel: Parcel): ArchivalItem {
            return ArchivalItem(parcel)
        }

        override fun newArray(size: Int): Array<ArchivalItem?> {
            return arrayOfNulls(size)
        }
    }

}