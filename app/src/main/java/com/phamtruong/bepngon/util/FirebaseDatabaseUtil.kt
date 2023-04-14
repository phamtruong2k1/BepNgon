package com.phamtruong.bepngon.util

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.model.UserProfile
import java.math.BigInteger
import java.security.MessageDigest

object FirebaseDatabaseUtil {
    const val ROOT = "root"
    const val PROFILE = "profile"
    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    fun getProfile(): UserProfile? {
        val id = Firebase.auth.currentUser?.email ?: ""
        val mDatabase = FirebaseDatabase.getInstance().getReference(ROOT)
        mDatabase.child(PROFILE).child(md5(id)).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                val userProfile = result.getValue<UserProfile>()
                Log.d("dddd", "getProfile: $userProfile")
            } else {
                Log.d("dddd", "getProfile: failed")
            }
        }
        return null
    }

    fun addNewProfile(newProfile: UserProfile) {
        val id = Firebase.auth.currentUser?.email ?: ""
        val mDatabase = FirebaseDatabase.getInstance().getReference(ROOT)
        mDatabase.child(PROFILE).child(md5(id)).setValue(newProfile)
    }
}