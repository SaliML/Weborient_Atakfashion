package com.weborient.atakfashion.handlers.file

import android.content.Context
import java.io.*
import java.lang.Exception

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

    fun getFolder(context: Context, folder: String, createIfNotExists: Boolean = false): File{
        val directory = File(context.getExternalFilesDir(null), folder)

        if(createIfNotExists && !directory.exists()){
            directory.mkdirs()
        }

        return directory
    }

    fun saveInStorage(context: Context, folder: String, fileName: String?, data: Any?): Boolean{
        var fos: FileOutputStream? = null
        var oos: ObjectOutputStream? = null

        try{
            fileName?.let{
                fos = FileOutputStream(File(getFolder(context, folder), it))

                fos?.let{ fileOutputStream ->
                    oos = ObjectOutputStream(fileOutputStream)

                    oos?.writeObject(data)
                }
            }

        }
        catch (exception: Exception){
            return false
        }
        finally {
            fos?.close()
            oos?.close()
        }

        return true
    }

    fun <T>readFromStorage(context: Context, folder: String, fileName: String?): T?{
        var fis: FileInputStream? = null
        var ois: ObjectInputStream? = null
        var data: T? = null

        try{
            var file: File? = null

            fileName?.let{
                file = File(getFolder(context, folder), it)
            }

            file?.let{
                if(it.exists()){
                    fis = FileInputStream(it)
                    ois = ObjectInputStream(fis)

                    data = ois?.readObject() as T
                }
            }
        }
        catch (exception: Exception){
            return null
        }
        finally {
            ois?.close()
            fis?.close()
        }

        return data
    }
}