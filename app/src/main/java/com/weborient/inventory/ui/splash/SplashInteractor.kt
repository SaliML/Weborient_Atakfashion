package com.weborient.inventory.ui.splash

import android.os.Build
import android.os.CountDownTimer
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.models.PrinterModel
import com.weborient.inventory.repositories.permission.PermissionRepository

class SplashInteractor(private val presenter: ISplashContract.ISplashPresenter): ISplashContract.ISplashInteractor {
    private var countDownTimer: CountDownTimer? = null

    /**
     * Jogosultságok lekérdezése
     */
    override fun getPermissions() {
        presenter.onFetchedPermissions(PermissionRepository.getPermissions(Build.VERSION.SDK_INT))
    }

    /**
     * Időzítő indítása
     * @param intervalHours Óra
     * @param intervalMinutes Perc
     * @param intervalSeconds Másodperc
     * @param countDownIntervalInMillis Léptetés milliszekundumban
     */
    override fun startTimer(intervalHours: Long, intervalMinutes: Long, intervalSeconds: Long, countDownIntervalInMillis: Long) {
        val interval = ((intervalHours * 3600) + (intervalMinutes * 60) + intervalSeconds) * 1000
        val countDownInterval = countDownIntervalInMillis

        countDownTimer = object : CountDownTimer(interval, countDownInterval){
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                presenter.onFinishedTimer()
            }

        }.start()
    }

    /**
     * Nyomtató MAC címének beállítása
     */
    override fun setMacAddress(macAddress: String) {
        AppConfig.macAddress = macAddress
    }

    /**
     * API cím beállítása
     */
    override fun setApiAddress(apiAddress: String) {
        AppConfig.apiAddress = apiAddress
    }
}