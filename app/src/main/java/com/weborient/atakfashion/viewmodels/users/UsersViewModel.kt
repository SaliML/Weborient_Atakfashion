package com.weborient.atakfashion.viewmodels.users

import android.content.Context
import android.icu.util.Calendar
import android.view.inputmethod.RemoveSpaceGesture
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
    var selectedUser = MutableLiveData<User?>()

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
     * Bevét jogosultság
     */
    var permissionIn = MutableLiveData<Boolean>()

    /**
     * Kiadás jogosultság
     */
    var permissionOut = MutableLiveData<Boolean>()

    /**
     * Kiadott termékek jogosultság
     */
    var permissionRemoval = MutableLiveData<Boolean>()

    /**
     * Termék szerkesztése jogosultság
     */
    var permissionEdit = MutableLiveData<Boolean>()

    /**
     * Manuális nyomtatás jogosultság
     */
    var permissionManualPrinting = MutableLiveData<Boolean>()

    /**
     * Fényképek jogosultság
     */
    var permissionPhotos = MutableLiveData<Boolean>()

    /**
     * Felhasználók jogosultság
     */
    var permissionUsers = MutableLiveData<Boolean>()

    /**
     * Beállítások jogosultság
     */
    var permissionSettings = MutableLiveData<Boolean>()

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

            selectedUser.value!!.permissions = getPermissions()
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
            SettingsRepository.userList.add(User(userName.value.toString(), SettingsRepository.encryptPassword(password.value!!), getPermissions()))
            SettingsRepository.saveUsers(context)

            return UserOperationResult.Successful
        }
        else{
            return UserOperationResult.UserExists
        }
    }

    /**
     * Felhasználó törlése
     */
    fun deleteUser(context: Context): UserOperationResult{
        if (selectedUser.value != null){
            if (users.value?.remove(selectedUser.value) == true){
                SettingsRepository.saveUsers(context)
                return UserOperationResult.Successful
            }
            else{
                return UserOperationResult.UnknownError
            }
        }
        else{
            return UserOperationResult.UserNotSelected
        }
    }

    /**
     * Felhasználó kiválasztása
     */
    fun setSelectedUser(user: User?){
        selectedUser.value = user

        userName.value = user?.userName

        if (user?.permissions?.contains(UserPermission.In) == true){
            permissionIn.value = true
        }
        else{
            permissionIn.value = false
        }

        if (user?.permissions?.contains(UserPermission.Out) == true){
            permissionOut.value = true
        }
        else{
            permissionOut.value = false
        }

        if (user?.permissions?.contains(UserPermission.Removal) == true){
            permissionRemoval.value = true
        }
        else{
            permissionRemoval.value = false
        }

        if (user?.permissions?.contains(UserPermission.Edit) == true){
            permissionEdit.value = true
        }
        else{
            permissionEdit.value = false
        }

        if (user?.permissions?.contains(UserPermission.ManualPrinting) == true){
            permissionManualPrinting.value = true
        }
        else{
            permissionManualPrinting.value = false
        }

        if (user?.permissions?.contains(UserPermission.Photos) == true){
            permissionPhotos.value = true
        }
        else{
            permissionPhotos.value = false
        }

        if (user?.permissions?.contains(UserPermission.Users) == true){
            permissionUsers.value = true
        }
        else{
            permissionUsers.value = false
        }

        if (user?.permissions?.contains(UserPermission.Settings) == true){
            permissionSettings.value = true
        }
        else{
            permissionSettings.value = false
        }
    }

    /**
     * Jogosultságok beállítása
     */
    private fun getPermissions(): ArrayList<UserPermission>{
        val permissions = arrayListOf<UserPermission>()

        if (permissionIn.value == true){
            permissions.add(UserPermission.In)
        }

        if (permissionOut.value == true){
            permissions.add(UserPermission.Out)
        }

        if (permissionRemoval.value == true){
            permissions.add(UserPermission.Removal)
        }

        if (permissionEdit.value == true){
            permissions.add(UserPermission.Edit)
        }

        if (permissionManualPrinting.value == true){
            permissions.add(UserPermission.ManualPrinting)
        }

        if (permissionPhotos.value == true){
            permissions.add(UserPermission.Photos)
        }

        if (permissionUsers.value == true){
            permissions.add(UserPermission.Users)
        }

        if (permissionSettings.value == true){
            permissions.add(UserPermission.Settings)
        }

        return permissions
    }
}