package com.phamtruong.bepngon.sever.account

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.model.AccountModel
import java.math.BigInteger
import java.security.MessageDigest

object AccountFirebaseUtil {

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
}