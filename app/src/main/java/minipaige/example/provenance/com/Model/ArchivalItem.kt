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
    var tags: String?,
    var image: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(repository)
        parcel.writeString(collection)
        parcel.writeString(box)
        parcel.writeString(folder)
        parcel.writeString(otherCntr)
        parcel.writeString(description)
        parcel.writeString(tags)
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