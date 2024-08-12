package com.weborient.atakfashion.handlers.dialog

import android.app.Activity
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.requestPermissions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.weborient.atakfashion.R
import com.weborient.atakfashion.databinding.DialogConfigLayoutBinding
import com.weborient.atakfashion.databinding.DialogInformationLayoutBinding
import com.weborient.atakfashion.databinding.DialogProgressLayoutBinding

object DialogHandler {
    private  var progressDialog: AlertDialog? = null
    /**
     * Jogosultságablak megjelenítése
     */
    fun showPermissionsDialog(activity: Activity, information: String, buttonTitle: String, permissions: Array<String>, requestCode: Int){
        val dialog = MaterialAlertDialogBuilder(activity, R.style.DialogRoundedCorners)
        val binding = DialogInformationLayoutBinding.inflate(activity.layoutInflater)

        dialog.setCancelable(false)
        dialog.setView(binding.root)

        binding.ivDialog.setImageResource(R.drawable.icon_warning)
        binding.tvDialogInformation.text = information
        binding.btDialogOk.visibility = View.VISIBLE
        binding.btDialogOk.text = buttonTitle

        val createdDialog = dialog.create()

        binding.btDialogOk.setOnClickListener {
            requestPermissions(activity, permissions, requestCode)
            createdDialog.dismiss()
        }

        createdDialog.show()
    }

    fun showDialogWithResult(activity: Activity, resultHandler: IDialogResultHandler, information: String, type: DialogTypeEnums){
        val dialog = MaterialAlertDialogBuilder(activity, R.style.DialogRoundedCorners)
        val binding = DialogInformationLayoutBinding.inflate(activity.layoutInflater)

        dialog.setCancelable(false)
        dialog.setView(binding.root)

        binding.tvDialogInformation.text = information
        binding.btDialogCancel.visibility = View.VISIBLE
        binding.btDialogOk.visibility = View.VISIBLE

        when(type){
            DialogTypeEnums.Information->{
                binding.ivDialog.setImageResource(R.drawable.icon_information)
            }
            DialogTypeEnums.Warning->{
                binding.ivDialog.setImageResource(R.drawable.icon_warning)
            }
            DialogTypeEnums.WarningClose->{
                binding.ivDialog.setImageResource(R.drawable.icon_warning)
                binding.btDialogCancel.visibility = View.GONE
            }
            DialogTypeEnums.Successful->{
                binding.ivDialog.setImageResource(R.drawable.icon_successful)
            }
            DialogTypeEnums.Error->{
                binding.ivDialog.setImageResource(R.drawable.icon_error)
            }
            DialogTypeEnums.ErrorClose->{
                binding.ivDialog.setImageResource(R.drawable.icon_error)
                binding.btDialogCancel.visibility = View.GONE
            }
            DialogTypeEnums.Question, DialogTypeEnums.QuestionDeleteUser, DialogTypeEnums.QuestionModifyUser, DialogTypeEnums.QuestionExit->{
                binding.ivDialog.setImageResource(R.drawable.icon_question)
            }
            DialogTypeEnums.SettingsLocationProvider->{
                binding.ivDialog.setImageResource(R.drawable.icon_warning)
                binding.btDialogOk.text = activity.resources.getString(R.string.dialog_button_turnon)
            }
            DialogTypeEnums.SettingsNetwork->{
                binding.ivDialog.setImageResource(R.drawable.icon_warning)
                binding.btDialogOk.text = activity.resources.getString(R.string.dialog_button_turnon)
            }
            DialogTypeEnums.SettingsBluetooth->{
                binding.ivDialog.setImageResource(R.drawable.icon_warning)
                binding.btDialogOk.text = activity.resources.getString(R.string.dialog_button_turnon)
            }
            DialogTypeEnums.SettingsWifi->{
                binding.ivDialog.setImageResource(R.drawable.icon_warning)
                binding.btDialogOk.text = activity.resources.getString(R.string.dialog_button_turnon)
            }
        }

        val createdDialog = dialog.create()
        createdDialog.show()

        binding.btDialogCancel.setOnClickListener {
            resultHandler.onDialogResult(DialogResultEnums.Cancel)
            createdDialog.dismiss()
        }

        binding.btDialogOk.setOnClickListener {
            when(type){
                DialogTypeEnums.SettingsNetwork->{
                    resultHandler.onDialogResult(DialogResultEnums.SettingsNetwork)
                }
                DialogTypeEnums.SettingsLocationProvider->{
                    resultHandler.onDialogResult(DialogResultEnums.SettingsLocationProvider)
                }
                DialogTypeEnums.SettingsBluetooth->{
                    resultHandler.onDialogResult(DialogResultEnums.SettingsBluetooth)
                }
                DialogTypeEnums.SettingsWifi->{
                    resultHandler.onDialogResult(DialogResultEnums.SettingsWifi)
                }
                DialogTypeEnums.QuestionDeleteUser -> {
                    resultHandler.onDialogResult(DialogResultEnums.DeleteUserOk)
                }
                DialogTypeEnums.QuestionModifyUser -> {
                    resultHandler.onDialogResult(DialogResultEnums.ModifyUserOk)
                }
                DialogTypeEnums.QuestionExit->{
                    resultHandler.onDialogResult(DialogResultEnums.ExitOk)
                }
                else->{
                    resultHandler.onDialogResult(DialogResultEnums.OK)
                }
            }
            createdDialog.dismiss()
        }
    }

    fun showInformationDialog(activity: Activity, information: String, type: DialogTypeEnums){
        val dialog = MaterialAlertDialogBuilder(activity, R.style.DialogRoundedCorners)
        val binding = DialogInformationLayoutBinding.inflate(activity.layoutInflater)

        dialog.setCancelable(false)
        dialog.setView(binding.root)

        binding.tvDialogInformation.text = information
        binding.btDialogCancel.visibility = View.GONE
        binding.btDialogOk.visibility = View.VISIBLE

        when(type){
            DialogTypeEnums.Information->{
                binding.ivDialog.setImageResource(R.drawable.icon_information)
            }
            DialogTypeEnums.Warning, DialogTypeEnums.WarningClose->{
                binding.ivDialog.setImageResource(R.drawable.icon_warning)
            }
            DialogTypeEnums.Successful->{
                binding.ivDialog.setImageResource(R.drawable.icon_successful)
            }
            DialogTypeEnums.Error,DialogTypeEnums.ErrorClose->{
                binding.ivDialog.setImageResource(R.drawable.icon_error)
            }
            else->{
                binding.ivDialog.setImageResource(R.drawable.icon_information)
            }
        }

        val createdDialog = dialog.create()
        createdDialog.show()


        binding.btDialogOk.setOnClickListener {
            createdDialog.dismiss()
        }
    }

    fun showTimedDialog(activity: Activity, information: String, type: DialogTypeEnums, onFinishHandler: IDialogTimeOnFinishHandler? = null, timeInMillisec: Long = 2000){
        val dialog = MaterialAlertDialogBuilder(activity, R.style.DialogRoundedCorners)
        val binding = DialogInformationLayoutBinding.inflate(activity.layoutInflater)

        dialog.setCancelable(false)
        dialog.setView(binding.root)

        when(type){
            DialogTypeEnums.Information->{
                binding.ivDialog.setImageResource(R.drawable.icon_information)
            }
            DialogTypeEnums.Warning->{
                binding.ivDialog.setImageResource(R.drawable.icon_warning)
            }
            DialogTypeEnums.Successful->{
                binding.ivDialog.setImageResource(R.drawable.icon_successful)
            }
            DialogTypeEnums.Error->{
                binding.ivDialog.setImageResource(R.drawable.icon_error)
            }
            else->{}
        }

        binding.tvDialogInformation.text = information

        val createdDialog = dialog.create()
        createdDialog.show()

        object: CountDownTimer(timeInMillisec, 1000){
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                createdDialog.dismiss()
                onFinishHandler?.closePage()
            }

        }.start()
    }

    fun showConfigDialog(activity: Activity, dialogHandler: IConfigDialogHandler, macAddress: String?, apiAddress: String?): AlertDialog{
        val dialog = MaterialAlertDialogBuilder(activity, R.style.DialogRoundedCorners)
        val binding = DialogConfigLayoutBinding.inflate(activity.layoutInflater)

        dialog.setCancelable(false)
        dialog.setView(binding.root)

        binding.etConfigApiAddress.setText(apiAddress, TextView.BufferType.EDITABLE)
        binding.etConfigPrinterIpAddress.setText(macAddress, TextView.BufferType.EDITABLE)

        val createdDialog = dialog.create()
        createdDialog.show()

        binding.btConfigSave.setOnClickListener {
            binding.tilApiAddress.error = null
            binding.tilIpAddress.error = null

            val tempApiAddress = binding.etConfigApiAddress.text.toString()
            val tempIPAddress = binding.etConfigPrinterIpAddress.text.toString()

            if(tempApiAddress.isNotEmpty() && tempIPAddress.isNotEmpty()){
                dialogHandler.setConfigDatas(tempApiAddress, tempIPAddress)
                createdDialog.dismiss()
            }
            else{
                if(tempApiAddress.isEmpty()){
                    binding.tilApiAddress.error = "Hiányzó API cím"
                }

                if(tempIPAddress.isEmpty()){
                    binding.tilIpAddress.error = "Hiányzó MAC cím"
                }
            }
        }

        return createdDialog
    }

    fun showProgressDialog(activity: Activity, message: String) {
        if(progressDialog == null){
            progressDialog = createProgressDialog(activity, message)
            progressDialog?.show()
        }
    }

    fun closeProgressDialog() {
        if(progressDialog != null){
            progressDialog?.dismiss()
            progressDialog = null
        }
    }

    private fun createProgressDialog(activity: Activity, information: String): AlertDialog {
        val dialog = MaterialAlertDialogBuilder(activity, R.style.DialogRoundedCorners)
        val binding = DialogProgressLayoutBinding.inflate(activity.layoutInflater)

        dialog.setCancelable(false)
        dialog.setView(binding.root)

        binding.tvDialogInformation.text = information

        val createdDialog = dialog.create()

        return createdDialog
    }
}