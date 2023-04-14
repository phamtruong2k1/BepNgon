package com.phamtruong.bepngon.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlin.math.pow
import kotlin.math.roundToInt


fun Context.showToast(msg: String, isShowDurationLong: Boolean = false) {
    val duration = if (isShowDurationLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    Toast.makeText(this, msg, duration).show()
}

fun Context.checkPermission(permission: String): Boolean =
    ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Activity.requestPermission(permission: Array<String>, requestCode: Int) {
    ActivityCompat.requestPermissions(this, permission, requestCode)
}

fun String.upperFirstCase(): String {
    val firstCase = this.first().toString().uppercase()
    return firstCase + this.substring(1)
}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}