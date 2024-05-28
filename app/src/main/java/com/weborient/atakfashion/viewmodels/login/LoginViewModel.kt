package com.weborient.atakfashion.viewmodels.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weborient.atakfashion.models.login.LoginResult
import com.weborient.atakfashion.repositories.settings.SettingsRepository

class LoginViewModel: ViewModel() {
    /**
     * Felhasználónév
     */
    var userName = MutableLiveData<String>()

    /**
     * Jelszó
     */
    var password = MutableLiveData<String>()

    /**
     * Felhasználó hitelesítése
     */
    fun verifyUser(): LoginResult {
        if (!userName.value.isNullOrEmpty()){
            if(!password.value.isNullOrEmpty()){
                val selectedUser = SettingsRepository.userList.firstOrNull { it.userName.equals(userName.value) }

                if (selectedUser != null){
                    if(SettingsRepository.verifyPassword(password.value!!, selectedUser.password)){
                        SettingsRepository.loggedUser = selectedUser

                        return LoginResult.Successful
                    }
                    else{
                        return LoginResult.PasswordNotEquals
                    }
                }
                else{
                    return LoginResult.UserNotExists
                }
            }
            else{
                return LoginResult.PasswordEmpty
            }
        }
        else{
            return LoginResult.UserEmpty
        }
    }
}