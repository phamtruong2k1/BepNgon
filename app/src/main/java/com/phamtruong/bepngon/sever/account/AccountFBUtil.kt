package com.phamtruong.bepngon.sever.account

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast
import java.math.BigInteger
import java.security.MessageDigest

object AccountFBUtil {


    val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)

    private fun convertToMD5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    fun logIn(context: Context, useName : String, password : String, actionSuccess : () -> Unit) {
        val id = convertToMD5(useName)
        mDatabase.child(FBConstant.ACCOUNT).child(id).get().addOnCompleteListener { task ->
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
        mDatabase.child(FBConstant.ACCOUNT).child(id).get().addOnCompleteListener { task ->
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
        mDatabase.child(FBConstant.ACCOUNT).child(account.account_id).setValue(account)
    }

    fun getAccount(idUser : String, actionSuccess : () -> Unit, actionFail : () -> Unit) {
        mDatabase.child(FBConstant.ACCOUNT).child(idUser).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                val account = result.getValue<AccountModel>()
                if (account != null) {
                    actionSuccess()
                } else {
                    actionFail()
                }
            }
        }.addOnFailureListener {
            actionFail()
        }
    }
}