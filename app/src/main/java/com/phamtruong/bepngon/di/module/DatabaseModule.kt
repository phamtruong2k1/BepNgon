package com.phamtruong.bepngon.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.phamtruong.bepngon.db.AppDatabase
import com.phamtruong.bepngon.db.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {
    val DATABASE_NAME = "bep_ngon_1"


    @Singleton
    @Provides
    fun getDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, DATABASE_NAME
        ).addMigrations(/*MIGRATION_1_2,MIGRATION_2_3*/)
            .build()
    }


    @Singleton
    @Provides
    fun provideCategoryDao(db: AppDatabase): UserDao {
        return db.userDao()
    }



}
