package com.weborient.inventory.config

/**
 * Alkalmazás konfigurációját tartalmazó osztály
 */
object AppConfig {
    //API címe
    var apiAddress: String? = null

    //Nyomtató MAC címe
    var macAddress: String? = null

    //Jogosultságkérés kódja
    const val REQUEST_CODE_PERMISSION = 1

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
    const val SHAREDPREF_KEY_API_ADDRESS = "api_address"
}