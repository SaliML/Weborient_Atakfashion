package com.weborient.atakfashion.ui.splash

import android.os.Build
import android.os.CountDownTimer
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.repositories.permission.PermissionRepository

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

    override fun setLabelType(labelTypeID: Int) {
        AppConfig.printerLabelSize = AppConfig.labelSizeList.firstOrNull { it.id.ordinal == labelTypeID }?.id
    }

    /**
     * Nyomtató IP címének beállítása
     */
    override fun setIPAddress(ipAddress: String) {
        AppConfig.ipAddress = ipAddress
    }

    /**
     * API cím beállítása
     */
    override fun setApiAddress(apiAddress: String) {
        AppConfig.apiAddress = apiAddress
    }

    /**
     * Vágási beállítások rögzítése
     */
    override fun setCutSettings(isAutoCut: Boolean, isCutAtEnd: Boolean) {
        AppConfig.isAutoCut = isAutoCut
        AppConfig.isCutAtEnd = isCutAtEnd
    }
}