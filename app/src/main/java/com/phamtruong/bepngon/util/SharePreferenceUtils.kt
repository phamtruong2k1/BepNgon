package com.phamtruong.bepngon.util

import android.content.Context
import android.content.SharedPreferences

object SharePreferenceUtils {

    val PER_NAME = "data_app_bep_ngon"

    lateinit var pre: SharedPreferences

    fun init(context: Context) {
        pre = context.getSharedPreferences(PER_NAME, Context.MODE_PRIVATE)
    }

    fun getAccountID() : String {
        return pre.getString("account_id", "").toString()
    }

    fun setAccountID(data: String) {
        pre.edit().putString("account_id", data).apply()
    }

}