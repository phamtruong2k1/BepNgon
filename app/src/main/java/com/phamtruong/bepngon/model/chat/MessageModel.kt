package com.phamtruong.bepngon.model.chat

import android.os.Message
import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessageModel(
    val message_id : String,
    val room_id: String,
    val accountId: String,
    val content: String,
    val crete_time : String
) : Parcelable {

    constructor() : this("","", "", "","") {}

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "message_id" to message_id,
            "room_id" to room_id,
            "accountId" to accountId,
            "content" to content,
            "crete_time" to crete_time
        )
    }
}