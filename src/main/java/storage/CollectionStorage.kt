package storage

import android.content.Context
import android.content.SharedPreferences
import model.Collection
import storage.utility.Storage

object CollectionStorage {

    private const val STORAGE = "storage"
    const val NONE = 0
    const val FILE_JSON = 1
    private const val DEFAULT = NONE

    private fun getPreferences(context: Context): SharedPreferences{
        return context.getSharedPreferences("com.btm.info507.collection", Context.MODE_PRIVATE)
    }

    fun getStorage(context: Context): Int {
        return getPreferences(context).getInt(STORAGE, DEFAULT)
    }

    fun setStorage(context: Context, prefStorage: Int) {
        getPreferences(context).edit().putInt(STORAGE, prefStorage).apply()
    }

    fun get(context: Context): Storage<Collection> {
        lateinit var storage: Storage<Collection>
        when (getStorage(context)) {
            NONE -> storage = CollectionNoneStorage()
            FILE_JSON -> storage = CollectionJSONFileStorage(context)
        }
        return storage
    }

}