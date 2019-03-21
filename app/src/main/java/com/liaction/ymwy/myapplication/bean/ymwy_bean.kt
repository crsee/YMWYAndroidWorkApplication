package com.liaction.ymwy.myapplication.bean

import android.os.Parcel
import android.os.Parcelable

data class YMWYMessage(val message: String? = "") : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<YMWYMessage> {
        override fun createFromParcel(parcel: Parcel): YMWYMessage {
            return YMWYMessage(parcel)
        }

        override fun newArray(size: Int): Array<YMWYMessage?> {
            return arrayOfNulls(size)
        }
    }
}