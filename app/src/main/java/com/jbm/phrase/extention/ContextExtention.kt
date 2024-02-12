package com.jbm.phrase.extention

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Handler
import android.text.TextPaint
import androidx.compose.ui.unit.sp
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import com.jbm.phrase.R

fun Context.getDownloadableFont(
    fontName: String,
    callback: FontsContractCompat.FontRequestCallback
) {
    val request = FontRequest(
        "com.google.android.gms.fonts",
        "com.google.android.gms",
        fontName,
        R.array.com_google_android_gms_fonts_certs
    )
    FontsContractCompat.requestFont(this, request, callback, Handler())
}

fun Context.textAsBitmap(
    text: String,
    fontSize: Int,
    color: Int,
    letterSpacing: Float = 0.1f,
    typeFace: Typeface?
): Bitmap {
    val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    paint.textSize = fontSize.sp.value
    paint.color = color
    paint.letterSpacing = letterSpacing
    paint.typeface = typeFace ?: resources.getFont(R.font.roboto_regular)

    val baseline = -paint.ascent()
    val width = (paint.measureText(text)).toInt()
    val height = (baseline + paint.descent()).toInt()
    val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(image)
    canvas.drawText(text, 0f, baseline, paint)
    return image
}