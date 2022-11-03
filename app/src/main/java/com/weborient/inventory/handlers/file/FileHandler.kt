package com.weborient.inventory.handlers.file

import android.content.Context
import java.io.File

object FileHandler {
    fun createFolder(context: Context, folder: String): Boolean{
        if(!File(context.getExternalFilesDir(null), folder).exists()){
            if(File(context.getExternalFilesDir(null), folder).mkdirs()){
                return true
            }
        }

        return false
    }

    fun deleteFolder(context: Context, folder: String){
        val directory = File(context.getExternalFilesDir(null), folder)

        if(directory.exists()){
            directory.deleteRecursively()
        }
    }

    fun deleteFile(context: Context, file: File): Boolean{
        if(file.exists()){
            file.delete()
        }
        return true
    }

    fun getFolderAbsolutePath(context: Context, folder: String, createIfNotExists: Boolean): File{
        val directory = File(context.getExternalFilesDir(null), folder)

        if(createIfNotExists && !directory.exists()){
            directory.mkdirs()
        }

        return directory
    }
}