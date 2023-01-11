package com.weborient.inventory.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.weborient.inventory.R
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.databinding.ActivitySplashBinding
import com.weborient.inventory.handlers.dialog.*
import com.weborient.inventory.handlers.file.FileHandler
import com.weborient.inventory.handlers.permission.PermissionHandler
import com.weborient.inventory.handlers.preferences.SharedPreferencesHandler
import com.weborient.inventory.ui.main.MainActivity
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), ISplashContract.ISplashView, IDialogResultHandler, IConfigDialogHandler {
    private val presenter = SplashPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Jogosultságok lekérdezése és majd ellenőrzése
        presenter.getPermissions()
    }

    /**
     * Jogosultságok ellenőrzése
     * @param permissions Jogosultságtömb
     */
    override fun checkPermissions(permissions: Array<String>) {
        presenter.onCheckedPermissions(PermissionHandler.getNotGrantedPermissions(this, permissions))
    }

    override fun getAddressConfigs() {
        presenter.onFetchedConfigAddresses(SharedPreferencesHandler.getValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_MAC_ADDRESS),
        SharedPreferencesHandler.getValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_API_ADDRESS))
    }

    /**
     * Navigálás a főoldalra
     */
    override fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    /**
     * Alkalmazás bezárása
     */
    override fun closeApplication() {
        finishAffinity()
        System.exit(0)
    }

    /**
     * Jogosultság ablak megjelenítése
     * @param permissions Jogosultságtömb
     * @param requestCode Kérés azonosító
     */
    override fun showPermissionDialog(permissions: Array<String>, requestCode: Int) {
        DialogHandler.showPermissionsDialog(this, getString(R.string.dialog_permissions_information), getString(R.string.dialog_button_alright), permissions, requestCode)
    }

    /**
     * Információs ablak megjelenítésének definíciója.
     * @param information párbeszédablakban megjelenítendő információ, String típus.
     * @param type párbeszédablak jellegét meghatározó beállítás, DialogTypeEnums típus.
     */
    override fun showInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showDialogWithResult(this, this, information, type)
    }

    /**
     * Konfig adatok párbeszédablak
     */
    override fun showConfigDialog(macAddress: String?, apiAddress: String?) {
        DialogHandler.showConfigDialog(this, this, macAddress, apiAddress)
    }

    /**
     * Jogosultságok megadását követően visszatérő értékek kezelése
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //Jogosultságok megadásának ellenőrzése
        presenter.onGrantedPermissions(requestCode, grantResults)
    }

    /**
     * Párbeszédablak visszatérő értékének kezelése
     */
    override fun onDialogResult(result: DialogResultEnums) {
        presenter.onDialogResult(result)
    }

    override fun setConfigDatas(apiAddress: String, macAddress: String) {
        SharedPreferencesHandler.saveValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_API_ADDRESS, apiAddress)
        SharedPreferencesHandler.saveValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_MAC_ADDRESS, macAddress.uppercase(Locale.getDefault()))

        presenter.onFetchedConfigAddresses(macAddress, apiAddress)
    }
}