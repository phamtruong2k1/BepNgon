package com.phamtruong.bepngon.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.gson.Gson
import com.phamtruong.bepngon.util.Constant
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileModel(
    var profileId: String,
    var accountId: String,
    var name: String,
    var birthDay: String,
    var gender: Boolean,
    var avt: String,
    var address: String,
) : Parcelable {

    constructor() : this("", "", "", "", true, Constant.URL_AVATAR_DEFAUT, "") {}

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "profileId" to profileId,
            "accountId" to accountId,
            "name" to name,
            "birthDay" to birthDay,
            "gender" to gender,
            "avt" to avt,
            "address" to address,
        )
    }

    companion object {
        fun toPostModel(jsonData: String): ProfileModel? {
            return Gson().fromJson(jsonData, ProfileModel::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}
