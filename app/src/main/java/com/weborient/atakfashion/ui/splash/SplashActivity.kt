package com.weborient.atakfashion.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.weborient.atakfashion.R
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.databinding.ActivitySplashBinding
import com.weborient.atakfashion.handlers.dialog.*
import com.weborient.atakfashion.handlers.file.FileHandler
import com.weborient.atakfashion.handlers.permission.PermissionHandler
import com.weborient.atakfashion.handlers.preferences.SharedPreferencesHandler
import com.weborient.atakfashion.repositories.RemovaledItemRepository
import com.weborient.atakfashion.viewmodels.splash.SplashViewModel
import com.weborient.atakfashion.views.login.LoginActivity
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), ISplashContract.ISplashView, IDialogResultHandler, IConfigDialogHandler {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    private val presenter = SplashPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

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

    override fun getSettings() {
        presenter.onFetchedSettings(/*SharedPreferencesHandler.getValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_MAC_ADDRESS)*/
        SharedPreferencesHandler.getValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_IP_ADDRESS),
        SharedPreferencesHandler.getValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_API_ADDRESS),
        SharedPreferencesHandler.getValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_AUTO_CUT) ?: false,
        SharedPreferencesHandler.getValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_CUT_AT_END) ?: false,
         SharedPreferencesHandler.getValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_LABEL_ID) ?: 0)
    }

    /**
     * Navigálás a bejelentkező felületre
     */
    override fun navigateToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
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

    override fun setConfigDatas(apiAddress: String, ipAddress: String) {
        SharedPreferencesHandler.saveValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_API_ADDRESS, apiAddress)
        SharedPreferencesHandler.saveValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_IP_ADDRESS, ipAddress)
        presenter.onFetchedSettings(ipAddress, apiAddress, false, false, 0)
    }

    /**
     * Mappák ellenőrzése
     */
    override fun checkFolders(){
        FileHandler.createFolder(this, AppConfig.ATAKFASHION_EXTERNAL_REMOVALED_ITEMS_DIRECTORY)
        FileHandler.createFolder(this, AppConfig.ATAKFASHION_EXTERNAL_SETTINGS)
    }

    /**
     * Mentett kiadott termék kiolvasása
     */
    override fun readRemovaledProducts(){
        RemovaledItemRepository.ReadRemovaledProducts(this, Calendar.getInstance().time, true)
    }

    /**
     * Felhasználók felolvasása
     */
    override fun readUsers() {
        viewModel.readUsersFromStorage(this)
    }
}