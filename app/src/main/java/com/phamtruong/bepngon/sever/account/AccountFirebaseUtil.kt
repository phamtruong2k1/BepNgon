package com.phamtruong.bepngon.sever.account

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast
import java.math.BigInteger
import java.security.MessageDigest

object AccountFirebaseUtil {

    const val ROOT = "root"
    const val PROFILE = "profile"
    const val ACCOUNT = "account"

    val mDatabase = FirebaseDatabase.getInstance().getReference(ROOT)

    private fun convertToMD5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    fun logIn(context: Context, useName : String, password : String, actionSuccess : () -> Unit) {
        val id = convertToMD5(useName)
        mDatabase.child(ACCOUNT).child(id).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                val account = result.getValue<AccountModel>()
                if (account != null) {
                    if (account.password == password && account.userName == useName) {
                        SharePreferenceUtils.setAccountID(account.account_id)
                        SharePreferenceUtils.setUserName(account.userName)
                        SharePreferenceUtils.setPassword(account.password)
                        actionSuccess()
                    } else {
                        context.showToast("Thông tin đăng nhập không đúng!")
                    }
                } else {
                    context.showToast("Thông tin đăng nhập không đúng!")
                }
            }
        }.addOnFailureListener {
            context.showToast("Có lỗi!")
        }
    }

    fun logUp(context: Context, useName : String, password : String, actionSuccess : () -> Unit) {
        val id = convertToMD5(useName)
        mDatabase.child(ACCOUNT).child(id).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                val account = result.getValue<AccountModel>()
                if (account != null) {
                    context.showToast("Tài khoản đã tồn tại!")
                } else {
                    addNewAccount(
                        AccountModel(
                            id,
                            useName,
                            password,
                            "",
                            "",
                            false
                        )
                    )
                    SharePreferenceUtils.setAccountID(id)
                    SharePreferenceUtils.setUserName(useName)
                    SharePreferenceUtils.setPassword(password)
                    actionSuccess()
                }
            }
        }.addOnFailureListener {
            context.showToast("Có lỗi!")
        }
    }

    private fun addNewAccount(account: AccountModel) {
        mDatabase.child(ACCOUNT).child(account.account_id).setValue(account)
    }
}