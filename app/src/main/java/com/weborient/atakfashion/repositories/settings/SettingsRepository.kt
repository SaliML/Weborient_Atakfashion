package com.weborient.atakfashion.repositories.settings

import android.content.Context
import com.google.gson.Gson
import com.toxicbakery.bcrypt.Bcrypt
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.file.FileHandler
import com.weborient.atakfashion.models.user.User
import com.weborient.atakfashion.models.user.UserPermission

object SettingsRepository {

    /**
     * Felhasználók listája
     */
    var userList = arrayListOf<User>()

    /**
     * Bejelentkezett felhasználó
     */
    var loggedUser: User? = null

    /**
     * Felhasználók mentése
     */
    fun saveUsers(context: Context){
        FileHandler.saveInStorage(context, AppConfig.ATAKFASHION_EXTERNAL_SETTINGS, "users.json", Gson().toJson(userList))
    }

    /**
     * Mentett kiadott termékek kiolvasása a háttértárról dátum alapján
     */
    fun readUsers(context: Context){

        val json: String? = FileHandler.readFromStorage<String>(context, AppConfig.ATAKFASHION_EXTERNAL_SETTINGS, "users.json")

        json?.let{
            userList = Gson().fromJson<ArrayList<User>>(it, ArrayList::class.java)
        }?:run{
            userList = arrayListOf(User("admin", encryptPassword("Admin@01"),
                arrayListOf(
                    UserPermission.In,
                    UserPermission.Out,
                    UserPermission.Removal,
                    UserPermission.Edit,
                    UserPermission.ManualPrinting,
                    UserPermission.Photos,
                    UserPermission.Users,
                    UserPermission.Settings,
                    )))
        }
    }

    /**
     * Jelszó titkosítása
     */
    fun encryptPassword(password: String): ByteArray{
        return Bcrypt.hash(password, 5)
    }

    /**
     * Jelszó ellenőrzése
     */
    fun verifyPassword(password: String, hash: ByteArray): Boolean{
        return Bcrypt.verify(password, hash)
    }
}