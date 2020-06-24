package com.app.dnadetec.utills

import android.content.Context
import com.app.dnadetec.App
import javax.inject.Inject

class SharedPreferencesUtill{

    @Inject
    lateinit var context: Context

    private val PREF_NAME = "DNA"
    private val KEY_TOKEN = "token"

    constructor(){
        App.appComponent.inject(this)
    }

    fun saveToken(token: String) {
        val sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val prefsEditor = sp.edit()
        prefsEditor.putString(KEY_TOKEN, token)
        prefsEditor.commit()
    }

    fun getToken(): String? {
        val sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sp.getString(KEY_TOKEN, "")
    }


}