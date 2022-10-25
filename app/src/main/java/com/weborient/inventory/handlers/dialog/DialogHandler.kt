package com.weborient.inventory.handlers.dialog

import android.app.Activity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.requestPermissions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.weborient.inventory.R
import com.weborient.inventory.databinding.DialogConfigLayoutBinding
import com.weborient.inventory.databinding.DialogInformationLayoutBinding
import com.weborient.inventory.databinding.DialogProgressLayoutBinding

object DialogHandler {
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
            DialogTypeEnums.Question->{
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

    fun showConfigDialog(activity: Activity, dialogHandler: IConfigDialogHandler, macAddress: String?, apiAddress: String?): AlertDialog{
        val dialog = MaterialAlertDialogBuilder(activity, R.style.DialogRoundedCorners)
        val binding = DialogConfigLayoutBinding.inflate(activity.layoutInflater)

        dialog.setCancelable(false)
        dialog.setView(binding.root)

        binding.etConfigApiAddress.setText(apiAddress, TextView.BufferType.EDITABLE)
        binding.etConfigPrinterMacAddress.setText(macAddress, TextView.BufferType.EDITABLE)

        val createdDialog = dialog.create()
        createdDialog.show()

        binding.btConfigSave.setOnClickListener {
            binding.tilApiAddress.error = null
            binding.tilMacAddress.error = null

            val tempApiAddress = binding.etConfigApiAddress.text.toString()
            val tempMacAddress = binding.etConfigPrinterMacAddress.text.toString()

            if(tempApiAddress.isNotEmpty() && tempMacAddress.isNotEmpty()){
                dialogHandler.setConfigDatas(tempApiAddress, tempMacAddress)
                createdDialog.dismiss()
            }
            else{
                if(tempApiAddress.isEmpty()){
                    binding.tilApiAddress.error = "Hiányzó API cím"
                }

                if(tempMacAddress.isEmpty()){
                    binding.tilMacAddress.error = "Hiányzó MAC cím"
                }
            }
        }

        return createdDialog
    }

    /**
     * Folyamatjelző ablak
     */
    fun showProgressDialog(activity: Activity, information: String): AlertDialog {
        val dialog = MaterialAlertDialogBuilder(activity, R.style.DialogRoundedCorners)
        val binding = DialogProgressLayoutBinding.inflate(activity.layoutInflater)

        dialog.setCancelable(false)
        dialog.setView(binding.root)

        binding.tvDialogInformation.text = information

        val createdDialog = dialog.create()
        createdDialog.show()

        return createdDialog
    }
}