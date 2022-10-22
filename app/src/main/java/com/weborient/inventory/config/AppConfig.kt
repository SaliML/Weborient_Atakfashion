package com.weborient.inventory.config

/**
 * Alkalmazás konfigurációját tartalmazó osztály
 */
object AppConfig {
    //Jogosultságkérés kódja
    val REQUEST_CODE_PERMISSION = 1


    //Kezdőképernyő időzítő beállításai
    val SPLASH_TIMER_DURATION_HOURS: Long = 0
    val SPLASH_TIMER_DURATION_MINUTES: Long = 0
    val SPLASH_TIMER_DURATION_SECONDS: Long = 3
    val SPLASH_TIMER_DOWN_INTERVAL: Long = 1000

    //QR kód generálás beállításai
    val DEFAULT_QR_CODE_SIZE: Int = 256

    //Nyomtató beállításai
}