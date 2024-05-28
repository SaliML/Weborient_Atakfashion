package com.weborient.atakfashion.viewmodels.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import com.weborient.atakfashion.repositories.settings.SettingsRepository

class SplashViewModel: ViewModel(){
    /**
     * Felhasználók felolvasása a háttértárról
     */
    fun readUsersFromStorage(context: Context){
        SettingsRepository.readUsers(context)
    }
}
