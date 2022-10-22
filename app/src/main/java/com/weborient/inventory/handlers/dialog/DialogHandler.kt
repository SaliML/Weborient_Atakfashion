package com.weborient.inventory.handlers.dialog

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.requestPermissions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.weborient.inventory.R
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
            DialogTypeEnums.Question->{
                binding.ivDialog.setImageResource(R.drawable.icon_question)
            }
            DialogTypeEnums.SettingsLocationProvider->{
                binding.ivDialog.setImageResource(R.drawable.icon_warning)
                binding.btDialogOk.text = activity.resources.getString(R.string.dialog_button_next)
            }
            DialogTypeEnums.SettingsNetwork->{
                binding.ivDialog.setImageResource(R.drawable.icon_warning)
                binding.btDialogOk.text = activity.resources.getString(R.string.dialog_button_next)
            }
        }

        binding.tvDialogInformation.text = information
        binding.btDialogCancel.visibility = View.VISIBLE
        binding.btDialogOk.visibility = View.VISIBLE

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
                else->{
                    resultHandler.onDialogResult(DialogResultEnums.OK)
                }
            }
            createdDialog.dismiss()
        }
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