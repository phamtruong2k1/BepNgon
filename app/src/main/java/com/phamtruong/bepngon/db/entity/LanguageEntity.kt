package com.phamtruong.bepngon.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "language")
data class LanguageEntity @JvmOverloads constructor(
    @ColumnInfo(name = "icon")
    var icon: String,
    @ColumnInfo(name = "title")
    var title: String,
    @PrimaryKey
    @ColumnInfo(name = "langKey")
    var langKey: String
) : Parcelable {

    companion object {
        fun toUser(jsonData: String): LanguageEntity? {
            return Gson().fromJson(jsonData, LanguageEntity::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}