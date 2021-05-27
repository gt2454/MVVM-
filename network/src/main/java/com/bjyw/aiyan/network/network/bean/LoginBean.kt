package com.bjyw.aiyan.network.network.bean

import android.os.Parcel
import android.os.Parcelable

data class LoginBean (var admin : Boolean,
                    var chapterTops : List<*>?,
                    var coinCount : Int?,
                    var collectIds : List<*>?,
                    var email : String?,
                    var icon : String?,
                    var id : Int?,
                    var  nickname: String?,
                    var password :String?,
                    var publicName :String?,
                    var token :String?,
                    var type : Int?,
                    var username: String?) :Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        TODO("chapterTops"),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        TODO("collectIds"),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    )


    override fun toString(): String {
        return "LoginBean(admin=$admin, chapterTops=$chapterTops, coinCount=$coinCount, collectIds=$collectIds, email=$email, icon=$icon, id=$id, nickname=$nickname, password=$password, publicName=$publicName, token=$token, type=$type, username=$username)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (admin) 1 else 0)
        parcel.writeValue(coinCount)
        parcel.writeString(email)
        parcel.writeString(icon)
        parcel.writeValue(id)
        parcel.writeString(nickname)
        parcel.writeString(password)
        parcel.writeString(publicName)
        parcel.writeString(token)
        parcel.writeValue(type)
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginBean> {
        override fun createFromParcel(parcel: Parcel): LoginBean {
            return LoginBean(parcel)
        }

        override fun newArray(size: Int): Array<LoginBean?> {
            return arrayOfNulls(size)
        }
    }

}