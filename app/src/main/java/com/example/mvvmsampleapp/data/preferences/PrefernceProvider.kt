package com.example.mvvmsampleapp.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
private const val KEY_SAVED_AT="key_saved_at"
class PrefernceProvider(
    context:Context
) {
    //saving context is a bad practise,cz it leads to memory leak
    //so store application context from context instance
    //even if we pass context from fragment or activity appContext will get whole application context.so it prevent the memory leaks
    private val appContext=context.applicationContext

    private val preference:SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    //fn for saving last saved time

    fun savelastSavedAt(savedAt:String){
        preference.edit().putString(
            KEY_SAVED_AT,
            savedAt

        ).apply()
    }


    //string should be nullable cz at first stage there will not be any value,so null should be returned
    fun getLastSavedAt(): String?
    {
        return preference.getString(KEY_SAVED_AT,null)
    }
}