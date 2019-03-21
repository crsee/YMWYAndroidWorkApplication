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

data class YMWYMessage2(val message: String? = "", val show: Boolean? = true) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
        parcel.writeValue(show)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<YMWYMessage2> {
        override fun createFromParcel(parcel: Parcel): YMWYMessage2 {
            return YMWYMessage2(parcel)
        }

        override fun newArray(size: Int): Array<YMWYMessage2?> {
            return arrayOfNulls(size)
        }
    }
}