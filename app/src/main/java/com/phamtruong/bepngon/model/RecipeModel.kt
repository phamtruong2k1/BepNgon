package com.phamtruong.bepngon.model

import com.google.firebase.database.Exclude

class RecipeModel(
    var recipeId : String,
    var accountId : String,
    var tag : String,
    var content : String,
    var img : String,
    var like : Int,
    var disLike : Int,
    var status : String,
    var time : String
) {
    constructor() : this("", "", "", "", "", 0, 0, "", "") {}

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "recipeId" to recipeId,
            "accountId" to accountId,
            "tag" to tag,
            "content" to content,
            "img" to img,
            "like" to like,
            "disLike" to disLike,
            "status" to status,
            "time" to time
        )
    }
}