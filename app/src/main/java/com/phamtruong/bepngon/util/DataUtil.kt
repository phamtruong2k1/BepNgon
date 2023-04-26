package com.phamtruong.bepngon.util

import android.content.Context
import android.os.Environment
import com.phamtruong.bepngon.sever.FBConstant
import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

object DataUtil {
    fun getTime(): String {
        val sdf = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getIdByTime(): String {
        return ConvertToMD5(getTime());
    }

    fun ConvertToMD5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}