package com.weborient.inventory.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.weborient.inventory.R
import com.weborient.inventory.databinding.ActivitySplashBinding
import com.weborient.inventory.handlers.dialog.DialogHandler
import com.weborient.inventory.handlers.permission.PermissionHandler

class SplashActivity : AppCompatActivity(), ISplashContract.ISplashView {
    private val presenter = SplashPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.checkPermissions()
    }

    /**
     * Jogosultságok ellenőrzése
     */
    override fun checkPermissions(permissions: Array<String>) {

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

    override fun showPermissionDialog(permissions: Array<String>, requestCode: Int) {
        DialogHandler.showPermissionsDialog(this, getString(R.string.dialog_permissions_information), getString(R.string.dialog_button_alright), permissions, requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        presenter.onGrantedPermissions(requestCode, grantResults)
    }
}