package com.phamtruong.bepngon.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.phamtruong.bepngon.db.entity.FavoriteEntity
import com.phamtruong.bepngon.db.entity.LanguageEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM favorite")
    fun getAllFavorite( ): LiveData<MutableList<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(data : FavoriteEntity) : Long

    @Delete
    fun deleteFavorite(favoriteEntity: FavoriteEntity): Int

    @Query("SELECT COUNT(*) FROM favorite WHERE image = :image ")
    fun checkExitFavorite(image: String) : LiveData<Int>



    //Language
    @Query("SELECT * FROM language")
    fun getAllLanguage(): LiveData<MutableList<LanguageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllLanguage(listData : ArrayList<LanguageEntity>) : List<Long>

}
