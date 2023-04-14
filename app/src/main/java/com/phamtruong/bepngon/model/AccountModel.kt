package com.phamtruong.bepngon.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccountModel(
    val account_id: String,
    val userName: String,
    val password: String,
    val gmail: String,
    val phoneNumber:  String,
    val status: Boolean
) : Parcelable {

    constructor() : this("", "", "", "", "", false) {}

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "account_id" to account_id,
            "userName" to userName,
            "password" to password,
            "gmail" to gmail,
            "phoneNumber" to phoneNumber,
            "status" to status
        )
    }
}