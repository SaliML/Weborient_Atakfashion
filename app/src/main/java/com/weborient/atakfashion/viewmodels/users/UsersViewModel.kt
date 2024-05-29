package com.weborient.atakfashion.viewmodels.users

import android.content.Context
import android.icu.util.Calendar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weborient.atakfashion.models.user.User
import com.weborient.atakfashion.models.user.UserOperationResult
import com.weborient.atakfashion.models.user.UserPermission
import com.weborient.atakfashion.repositories.settings.SettingsRepository

class UsersViewModel: ViewModel() {
    /**
     * Felhasználók listája
     */
    var users = MutableLiveData(SettingsRepository.userList)

    /**
     * Kijelölt felhasználó
     */
    var selectedUser = MutableLiveData<User>()

    /**
     * Felhasználónév
     */
    var userName = MutableLiveData<String>()

    /**
     * Jelszó
     */
    var password = MutableLiveData<String>()

    /**
     * Jelszó megerősítése
     */
    var passwordConfirm = MutableLiveData<String>()

    /**
     * Felhasználó jogosultságok
     */
    var userPermissions = MutableLiveData<ArrayList<UserPermission>>()

    /**
     * Kiválasztott felhasználó módosítása
     */
    fun modifySelectedUser(context: Context): UserOperationResult{
        if (selectedUser.value != null){
            if (userName.value.isNullOrEmpty()){
                return UserOperationResult.UsernameEmpty
            }

            val tempUser = users.value?.firstOrNull { it.userName.equals(userName.value) && !it.equals(selectedUser.value) }

            if (tempUser != null){
                return UserOperationResult.UserExists
            }

            if (!password.value.isNullOrEmpty() || !passwordConfirm.value.isNullOrEmpty()){
                if (password.value.isNullOrEmpty()){
                    return UserOperationResult.PasswordEmpty
                }

                if (passwordConfirm.value.isNullOrEmpty()){
                    return UserOperationResult.PasswordConfirmEmpty
                }

                if(!password.value.equals(passwordConfirm.value, true)){
                    return UserOperationResult.PasswordNotEquals
                }
            }

            selectedUser.value!!.userName = userName.value!!

            if (!password.value.isNullOrEmpty()){
                selectedUser.value?.password = SettingsRepository.encryptPassword(password.value!!)
            }

            selectedUser.value!!.permissions = userPermissions.value!!
            SettingsRepository.saveUsers(context)

            return UserOperationResult.Successful

        }
        else{
            return UserOperationResult.UserNotSelected
        }
    }

    /**
     * Új felhasználó felvétele
     */
    fun addNewUser(context: Context): UserOperationResult{
        if (userName.value.isNullOrEmpty()){
            return UserOperationResult.UsernameEmpty
        }

        if (password.value.isNullOrEmpty()){
            return UserOperationResult.PasswordEmpty
        }

        if (passwordConfirm.value.isNullOrEmpty()){
            return UserOperationResult.PasswordConfirmEmpty
        }

        if (!password.value.equals(passwordConfirm.value, true)){
            return UserOperationResult.PasswordNotEquals
        }

        val selectedUser = users.value?.firstOrNull { it.userName.equals(userName.value, true) }

        if (selectedUser == null){
            SettingsRepository.userList.add(User(userName.value.toString(), SettingsRepository.encryptPassword(password.value!!), userPermissions.value ?: arrayListOf()))
            SettingsRepository.saveUsers(context)

            return UserOperationResult.Successful
        }
        else{
            return UserOperationResult.UserExists
        }
    }
}