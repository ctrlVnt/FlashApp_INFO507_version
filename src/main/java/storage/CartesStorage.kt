package storage

import android.content.Context
import android.content.SharedPreferences
import model.Cartes
import storage.utility.Storage

object CartesStorage {
    private const val STORAGE = "storage"
    const val NONE = 0
    const val FILE_JSON = 1
    private const val DEFAULT = 1

    private fun getPreferences(context: Context): SharedPreferences{
        return context.getSharedPreferences("com.btm.info507.cartes", Context.MODE_PRIVATE)
    }

    fun getStorage(context: Context): Int {
        return getPreferences(context).getInt(STORAGE, DEFAULT)
    }

    fun setStorage(context: Context, prefStorage: Int) {
        getPreferences(context).edit().putInt(STORAGE, prefStorage).apply()
    }

    fun get(context: Context): Storage<Cartes> {
        lateinit var storage: Storage<Cartes>
        when (getStorage(context)) {
            NONE -> storage = CartesNoneStorage()
            FILE_JSON -> storage = CartesJSONFileStorage(context)
        }
        return storage
    }


}