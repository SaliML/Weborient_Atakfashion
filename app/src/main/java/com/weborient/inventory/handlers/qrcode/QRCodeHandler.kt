package com.weborient.inventory.handlers.qrcode

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

/**
 * QR kód kezelését segítő osztály
 */
object QRCodeHandler {
    /**
     * QR kód generálása a paraméterként átadott szövegből
     * @param content Szöveg, amelyből a QR kód generálásra kerül
     * @param sizeInPixels A QR kód mérete pixelben (X*X)
     * @return Generált QR kód Bitmap típusként
     */
    fun generateQRCode(content: String, sizeInPixels: Int? = null): Bitmap {
        val size = sizeInPixels ?: 256

        val bits = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size)

        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}