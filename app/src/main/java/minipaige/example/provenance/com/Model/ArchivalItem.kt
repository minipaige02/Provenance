package minipaige.example.provenance.com.Model

import android.os.Parcel
import android.os.Parcelable

data class ArchivalItem(
    var id: String?,
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
        "",
        ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
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

object ArchivalItems {
    val testList = listOf<ArchivalItem>(
        ArchivalItem("1","Hoover", "Gahagan papers", "7", "", "", "", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2F96be606a-1695-4522-86c3-9ff945bc1196?alt=media&token=55b52c9a-d152-438f-9a58-8f7eddcd1e73"),
        ArchivalItem("2","UC Berkeley", "Wolfe papers", "9", "10", "", "a sample description", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2Fe3c39ad2-3d87-4c52-95b5-8b7e896cc127?alt=media&token=fa4c9218-c392-4111-8ad3-b28de9d68256"),
        ArchivalItem("3","Hoover", "Gahagan papers", "", "", "Map case 10", "", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2F96be606a-1695-4522-86c3-9ff945bc1196?alt=media&token=55b52c9a-d152-438f-9a58-8f7eddcd1e73"),
        ArchivalItem("4","UC Berkeley", "Wolfe papers", "9", "10", "", "a sample description", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2Fe3c39ad2-3d87-4c52-95b5-8b7e896cc127?alt=media&token=fa4c9218-c392-4111-8ad3-b28de9d68256"),
        ArchivalItem("5","Hoover", "Gahagan papers", "7", "", "", "", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2F96be606a-1695-4522-86c3-9ff945bc1196?alt=media&token=55b52c9a-d152-438f-9a58-8f7eddcd1e73"),
        ArchivalItem("6","UC Berkeley", "Wolfe papers", "9", "10", "", "a sample description", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2Fe3c39ad2-3d87-4c52-95b5-8b7e896cc127?alt=media&token=fa4c9218-c392-4111-8ad3-b28de9d68256"),
        ArchivalItem("7","Hoover", "Gahagan papers", "7", "", "", "", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2F96be606a-1695-4522-86c3-9ff945bc1196?alt=media&token=55b52c9a-d152-438f-9a58-8f7eddcd1e73"),
        ArchivalItem("8","UC Berkeley", "Wolfe papers", "9", "10", "", "a sample description", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2Fe3c39ad2-3d87-4c52-95b5-8b7e896cc127?alt=media&token=fa4c9218-c392-4111-8ad3-b28de9d68256"),
        ArchivalItem("9","Hoover", "Gahagan papers", "7", "", "", "", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2F96be606a-1695-4522-86c3-9ff945bc1196?alt=media&token=55b52c9a-d152-438f-9a58-8f7eddcd1e73"),
        ArchivalItem("10","UC Berkeley", "Wolfe papers", "9", "10", "", "a sample description", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2Fe3c39ad2-3d87-4c52-95b5-8b7e896cc127?alt=media&token=fa4c9218-c392-4111-8ad3-b28de9d68256"),
        ArchivalItem("11","Hoover", "Gahagan papers", "7", "", "", "", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2F96be606a-1695-4522-86c3-9ff945bc1196?alt=media&token=55b52c9a-d152-438f-9a58-8f7eddcd1e73"),
        ArchivalItem("12","UC Berkeley", "Wolfe papers", "9", "10", "", "a sample description", "", "https://firebasestorage.googleapis.com/v0/b/provenance-6fca1.appspot.com/o/uploads%2Fe3c39ad2-3d87-4c52-95b5-8b7e896cc127?alt=media&token=fa4c9218-c392-4111-8ad3-b28de9d68256")
    )

}