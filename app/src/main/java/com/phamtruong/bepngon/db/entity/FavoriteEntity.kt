package com.phamtruong.bepngon.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorite")
data class FavoriteEntity @JvmOverloads constructor(
    @PrimaryKey
    @ColumnInfo(name = "image")
    var image : String = "",
    @ColumnInfo(name = "thumbs")
    var thumbs : String = "",
    @ColumnInfo(name = "categoryId")
    var categoryId : Int = 0,
    @ColumnInfo(name = "categoryFullSlug")
    var categoryFullSlug : String = "",
    @ColumnInfo(name = "title")
    var title : String = "",
    @ColumnInfo(name = "altTag")
    var altTag : String = "",
    @ColumnInfo(name = "lang")
    var lang : String = ""
) : Parcelable {

    companion object {
        fun toUser(jsonData: String): FavoriteEntity? {
            return Gson().fromJson(jsonData, FavoriteEntity::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}