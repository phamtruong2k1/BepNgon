package com.phamtruong.bepngon.util

import android.content.Context
import android.os.Environment
import java.text.SimpleDateFormat
import java.util.*

object DataUtil {
    fun getTime(): String {
        val sdf = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        return sdf.format(Date())
    }
}