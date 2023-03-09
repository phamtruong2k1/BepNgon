package com.phamtruong.bepngon.db

import com.phamtruong.bepngon.db.dao.UserDao
import com.phamtruong.bepngon.db.entity.FavoriteEntity
import com.phamtruong.bepngon.db.entity.LanguageEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val userDao: UserDao) {

    fun getAllFavorite() = userDao.getAllFavorite()

    fun addFavorite(data : FavoriteEntity) = userDao.addFavorite(data)

    fun deleteFavorite(favoriteEntity: FavoriteEntity) = userDao.deleteFavorite(favoriteEntity)

    fun checkExitFavorite(image: String) = userDao.checkExitFavorite(image)


    fun getAllLanguage() = userDao.getAllLanguage()

    fun addAllLanguage(listData : ArrayList<LanguageEntity>) = userDao.addAllLanguage(listData)

}