package com.phamtruong.bepngon.sever

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.model.UserProfile
import java.math.BigInteger
import java.security.MessageDigest

object FirebaseDatabaseUtil {

    const val ROOT = "root"
    const val PROFILE = "profile"
    const val ACCOUNT = "account"

    val mDatabase = FirebaseDatabase.getInstance().getReference(ROOT)

    fun ConvertToMD5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    fun getAccount(): AccountModel? {
        val id = Firebase.auth.currentUser?.email ?: ""

        mDatabase.child(ACCOUNT).child(ConvertToMD5(id)).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                val userProfile = result.getValue<AccountModel>()
                Log.d("dddd", "getAccount: $userProfile")
            } else {
                Log.d("dddd", "getAccount: failed")
            }
        }
        return null
    }

    fun addNewAccount(account: AccountModel) {
        mDatabase.child(ACCOUNT).child(ConvertToMD5(account.account_id)).setValue(account)
    }

    fun getProfile(): UserProfile? {
        val id = Firebase.auth.currentUser?.email ?: ""

        mDatabase.child(PROFILE).child(ConvertToMD5(id)).get().addOnCompleteListener { task ->
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
        mDatabase.child(PROFILE).child(ConvertToMD5(id)).setValue(newProfile)
    }
}