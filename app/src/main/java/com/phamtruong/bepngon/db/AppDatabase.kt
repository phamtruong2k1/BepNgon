package com.phamtruong.bepngon.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phamtruong.bepngon.db.dao.UserDao
import com.phamtruong.bepngon.db.entity.FavoriteEntity
import com.phamtruong.bepngon.db.entity.LanguageEntity

@Database(
    version = 1,
    entities = [
        FavoriteEntity::class,
        LanguageEntity::class
    ]
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
