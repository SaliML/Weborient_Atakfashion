package com.weborient.atakfashion.viewmodels.settings

import android.adservices.appsetid.AppSetId
import android.app.Activity
import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.dialog.DialogHandler
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.repositories.RemovaledItemRepository
import com.weborient.atakfashion.repositories.settings.SettingsRepository
import java.io.File
import java.nio.file.Path
import java.util.Calendar

class SettingsViewModel: ViewModel() {
    /**
     * A package/Atakfashion mappa teljes tartalmának exportálása a letöltések mappába
     */
    fun exportStoredData(activity: Activity){

        try {
            val source = File(activity.applicationContext.getExternalFilesDir(null), AppConfig.ATAKFASHION_ROOT_FOLDER)

            if(source.exists()){
                val destination = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), "Atakfashion")
                if(!destination.exists()){

                    if(destination.mkdirs()){
                        if(source.copyRecursively(destination, true)){
                            DialogHandler.showInformationDialog(activity, "Sikeres exportálás", DialogTypeEnums.Successful)
                        }
                        else{
                            DialogHandler.showInformationDialog(activity, "Sikertelen exportálás", DialogTypeEnums.Error)
                        }
                    }
                }
                else{
                    if(destination.deleteRecursively()){
                        if(destination.mkdirs()){
                            if(source.copyRecursively(destination, true)){
                                DialogHandler.showInformationDialog(activity, "Sikeres exportálás", DialogTypeEnums.Successful)
                            }
                            else{
                                DialogHandler.showInformationDialog(activity, "Sikertelen exportálás", DialogTypeEnums.Error)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            DialogHandler.showInformationDialog(activity, "Sikertelen exportálás\n\n${e.message}", DialogTypeEnums.Error)
        }
    }

    /**
     * Adatok importálása
     */
    fun importStoredData(activity: Activity){

        try {

            val source = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), "Atakfashion")
            if(source.exists()){
                val destination = File(activity.applicationContext.getExternalFilesDir(null), AppConfig.ATAKFASHION_ROOT_FOLDER)

                if(!destination.exists()){
                    if(destination.mkdirs()){
                        if(source.copyRecursively(destination, true)){
                            //Sikeres importálást követően a termékeket és a felhasználói beállításokat is
                            RemovaledItemRepository.ReadRemovaledProducts(activity.applicationContext, Calendar.getInstance().time, true)

                            //SettingsRepository.readUsers(activity.applicationContext)

                            DialogHandler.showInformationDialog(activity, "Sikeres importálás", DialogTypeEnums.Successful)
                        }
                        else{
                            DialogHandler.showInformationDialog(activity, "Sikertelen importálás", DialogTypeEnums.Error)
                        }
                    }
                }
                else{
                    if(destination.deleteRecursively()){
                        if(source.copyRecursively(destination, true)){
                            //Sikeres importálást követően a termékeket és a felhasználói beállításokat is
                            RemovaledItemRepository.ReadRemovaledProducts(activity.applicationContext, Calendar.getInstance().time, true)

                            //SettingsRepository.readUsers(activity.applicationContext)

                            DialogHandler.showInformationDialog(activity, "Sikeres importálás", DialogTypeEnums.Successful)
                        }
                        else{
                            DialogHandler.showInformationDialog(activity, "Sikertelen importálás", DialogTypeEnums.Error)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            DialogHandler.showInformationDialog(activity, "Sikertelen exportálás\n\n${e.message}", DialogTypeEnums.Error)
        }
    }
}