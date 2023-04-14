package com.phamtruong.bepngon.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile(
    val name: String,
    val age: Int,
    val gmail: String
) : Parcelable {
    constructor() : this("", 0, "") {}

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "age" to age,
            "gmail" to gmail
        )
    }
}
