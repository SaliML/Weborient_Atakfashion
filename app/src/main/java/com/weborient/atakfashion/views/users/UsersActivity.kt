package com.weborient.atakfashion.views.users

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.weborient.atakfashion.R
import com.weborient.atakfashion.databinding.ActivityUsersBinding
import com.weborient.atakfashion.handlers.dialog.DialogHandler
import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.handlers.dialog.IDialogResultHandler
import com.weborient.atakfashion.models.QLPrinterLabelType
import com.weborient.atakfashion.models.user.User
import com.weborient.atakfashion.models.user.UserOperationResult
import com.weborient.atakfashion.models.user.UserPermission
import com.weborient.atakfashion.viewmodels.login.LoginViewModel
import com.weborient.atakfashion.viewmodels.users.UsersViewModel

class UsersActivity : AppCompatActivity(), IDialogResultHandler {
    private lateinit var binding: ActivityUsersBinding
    private val viewModel: UsersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.autoUsersUser.setOnItemClickListener { adapterView, view, i, l ->
            viewModel.setSelectedUser(adapterView.getItemAtPosition(i) as User)
        }

        binding.ivUsersDelete.setOnClickListener {
            DialogHandler.showDialogWithResult(this, this, "Biztosan szeretné törölni?", DialogTypeEnums.QuestionDeleteUser)
        }

        binding.ivUsersBack.setOnClickListener {
            finish()
        }

        binding.ivUsersEdit.setOnClickListener {
            DialogHandler.showDialogWithResult(this, this, "Biztosan szeretné módosítani?", DialogTypeEnums.QuestionModifyUser)
        }

        binding.ivUsersNew.setOnClickListener {
            binding.tilUsersUsername.error = null
            binding.tilUsersPassword.error = null
            binding.tilUsersPasswordConfirm.error = null

            when (viewModel.addNewUser(this)){
                UserOperationResult.UnknownError -> {
                    DialogHandler.showInformationDialog(this, "Ismeretlen hiba történt a felhasználó módosítása során!", DialogTypeEnums.Error)
                }
                UserOperationResult.Successful -> {
                    DialogHandler.showInformationDialog(this, "Sikeres felvétel!", DialogTypeEnums.Successful)

                    viewModel.userName.value = ""
                    viewModel.password.value = ""
                    viewModel.passwordConfirm.value = ""
                }
                UserOperationResult.UsernameEmpty -> {
                    binding.tilUsersUsername.error = "Kötelező kitölteni!"
                }
                UserOperationResult.PasswordEmpty -> {
                    binding.tilUsersPassword.error = "Kötelező kitölteni!"
                }
                UserOperationResult.PasswordConfirmEmpty -> {
                    binding.tilUsersPasswordConfirm.error = "Kötelező kitölteni!"
                }
                UserOperationResult.PasswordNotEquals -> {
                    binding.tilUsersPassword.error = "A két jelszó nem egyezik!"
                    binding.tilUsersPasswordConfirm.error = "A két jelszó nem egyezik!"
                }
                UserOperationResult.UserExists -> {
                    DialogHandler.showInformationDialog(this, "Már létezik ilyen felhasználónév!", DialogTypeEnums.Warning)
                }
                UserOperationResult.UserNotSelected -> {
                    DialogHandler.showInformationDialog(this, "Nincs kiválasztva felhasználó!", DialogTypeEnums.Warning)
                }
            }
        }

        getUsers()
    }

    /**
     * Felhasználók lekérdezése a listába
     */
    private fun getUsers(){
        viewModel.users.value?.let{
            binding.autoUsersUser.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, it.filter { user -> !user.userName.equals("admin") }))

            viewModel.setSelectedUser(null)
            binding.autoUsersUser.text.clear()
            //binding.autoUsersUser.setText(binding.autoUsersUser.adapter.getItem(0).toString(), false)

        }
    }

    /**
     * Felhasználó törlése
     */
    private fun deleteUser(){
        binding.tilUsersUsername.error = null
        binding.tilUsersPassword.error = null
        binding.tilUsersPasswordConfirm.error = null

        viewModel.selectedUser.value?.let{
            when (viewModel.deleteUser(this)){
                UserOperationResult.UnknownError -> {
                    DialogHandler.showInformationDialog(this, "Ismeretlen hiba történt a felhasználó módosítása során!", DialogTypeEnums.Error)
                }
                UserOperationResult.Successful -> {
                    DialogHandler.showInformationDialog(this, "Sikeres törlés!", DialogTypeEnums.Successful)

                    viewModel.userName.value = ""
                    viewModel.password.value = ""
                    viewModel.passwordConfirm.value = ""
                }
                UserOperationResult.UserNotSelected -> {
                    DialogHandler.showInformationDialog(this, "Nincs kiválasztva felhasználó!", DialogTypeEnums.Warning)
                }
                else ->{

                }
            }

            getUsers()
        }
    }

    /**
     * Felhasználó módosítása
     */
    private fun modifyUser(){
        binding.tilUsersUsername.error = null
        binding.tilUsersPassword.error = null
        binding.tilUsersPasswordConfirm.error = null

        when (viewModel.modifySelectedUser(this)){
            UserOperationResult.UnknownError -> {
                DialogHandler.showInformationDialog(this, "Ismeretlen hiba történt a felhasználó módosítása során!", DialogTypeEnums.Error)
            }
            UserOperationResult.Successful -> {
                DialogHandler.showInformationDialog(this, "Sikeres módosítás!", DialogTypeEnums.Successful)

                viewModel.userName.value = ""
                viewModel.password.value = ""
                viewModel.passwordConfirm.value = ""
            }
            UserOperationResult.UsernameEmpty -> {
                binding.tilUsersUsername.error = "Kötelező kitölteni!"
            }
            UserOperationResult.PasswordEmpty -> {
                binding.tilUsersPassword.error = "Kötelező kitölteni!"
            }
            UserOperationResult.PasswordConfirmEmpty -> {
                binding.tilUsersPasswordConfirm.error = "Kötelező kitölteni!"
            }
            UserOperationResult.PasswordNotEquals -> {
                binding.tilUsersPassword.error = "A két jelszó nem egyezik!"
                binding.tilUsersPasswordConfirm.error = "A két jelszó nem egyezik!"
            }
            UserOperationResult.UserExists -> {
                DialogHandler.showInformationDialog(this, "Már létezik ilyen felhasználó!", DialogTypeEnums.Warning)
            }
            UserOperationResult.UserNotSelected -> {
                DialogHandler.showInformationDialog(this, "Nincs kiválasztva felhasználó!", DialogTypeEnums.Warning)
            }
        }
    }

    /**
     * Párbeszédablak eredménye
     */
    override fun onDialogResult(result: DialogResultEnums) {
        when(result){
            DialogResultEnums.DeleteUserOk -> {
                deleteUser()
            }
            DialogResultEnums.ModifyUserOk -> {
                modifyUser()
            }
            else -> {}
        }
    }
}