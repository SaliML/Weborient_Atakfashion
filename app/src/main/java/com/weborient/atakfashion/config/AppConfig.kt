package com.weborient.atakfashion.config

import android.graphics.Bitmap
import android.os.Environment
import com.brother.sdk.lmprinter.PrinterModel
import com.brother.sdk.lmprinter.setting.QLPrintSettings
import com.weborient.atakfashion.handlers.api.IApiRequests
import com.weborient.atakfashion.models.QLPrinterLabelType
import java.io.File

/**
 * Alkalmazás konfigurációját tartalmazó osztály
 */
object AppConfig {
    //API címe
    var apiAddress: String? = null

    //AccessToken
    var accessToken: String? = null

    //ApiServices
    var apiServiceWithBearer: IApiRequests? = null
    var apiServiceWithoutBearer: IApiRequests? = null

    //Mappa útvonalak
    const val TEMP_FOLDER = "Inventory/temp"

    //Formátumok
    const val DATE_FORMAT = "yyyyMMdd"
    const val DATE_FORMAT2 = "yyyy. MM. dd."

    //Fájlnév
    const val TEMP_PHOTO_UPLOAD_FILE = "tempphotoupload.dat"
    const val ATAKFASHION_EXTERNAL_REMOVALED_ITEMS_DIRECTORY = "Atakfashion/Removaled"
    const val ATAKFASHION_EXTERNAL_SETTINGS = "Atakfashion/Settings"

    //Nyomtató beállításai
    var macAddress: String? = null
    var ipAddress: String? = null
    val bitmapCompressFormat = Bitmap.CompressFormat.PNG
    val bitmapCompressQuality = 100

    val imageFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), "tempCode.png")
    val printerWorkPath = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString())
    val printerModel = PrinterModel.QL_820NWB
    var printerLabelSize: QLPrintSettings.LabelSize? = null
    var isAutoCut = true
    var isCutAtEnd = true


    //Jogosultságkérés kódja
    const val REQUEST_CODE_PERMISSION = 1
    const val REQUEST_PHOTO = 2

    //QR kód szkennelés eredmény kulcs
    const val SCANNING_RESULT = "scanning_result"


    //Kezdőképernyő időzítő beállításai
    const val SPLASH_TIMER_DURATION_HOURS: Long = 0
    const val SPLASH_TIMER_DURATION_MINUTES: Long = 0
    const val SPLASH_TIMER_DURATION_SECONDS: Long = 3
    const val SPLASH_TIMER_DOWN_INTERVAL: Long = 1000

    //QR kód generálás beállításai
    const val DEFAULT_QR_CODE_SIZE: Int = 256

    //SharedPrefences azonosítók
    const val SHAREDPREF_ID = "inventory_app"
    const val SHAREDPREF_KEY_PRINTER_MAC_ADDRESS = "printer_mac_address"
    const val SHAREDPREF_KEY_PRINTER_IP_ADDRESS = "printer_ip_address"
    const val SHAREDPREF_KEY_PRINTER_AUTO_CUT = "printer_auto_cut"
    const val SHAREDPREF_KEY_PRINTER_CUT_AT_END = "printer_cut_at_end"
    const val SHAREDPREF_KEY_PRINTER_LABEL_ID = "printer_label_id"
    const val SHAREDPREF_KEY_API_ADDRESS = "api_address"

    //Formátumok
    const val DATETIME_FORMAT_yyyyMMdd_HHmmss = "yyyyMMdd_HHmmss"

    //Címke típusok
    val labelSizeList = arrayListOf(QLPrinterLabelType(QLPrintSettings.LabelSize.RollW62RB, "sz: 62 mm"), QLPrinterLabelType(QLPrintSettings.LabelSize.RollW38, "sz: 38 mm"), QLPrinterLabelType(QLPrintSettings.LabelSize.DieCutW29H90, "sz: 29 mm x h: 90 mm"))
}