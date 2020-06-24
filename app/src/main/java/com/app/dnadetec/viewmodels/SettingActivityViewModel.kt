package com.app.dnadetec.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.dnadetec.Global
import com.app.dnadetec.utills.LocaleHelper
import com.app.dnadetec.utills.LocalizationUtil
import com.app.dnadetec.utills.SharedPreferencesUtill
import javax.inject.Inject


class SettingActivityViewModel @Inject constructor(private val context: Context) : ViewModel() {

    fun changeLanguage(context: Context,language: String) {

        if (Global.lang == language) {
            return
        }

        Global.lang = language

//        LocalizationUtil.applyLanguage(context,language)

        LocaleHelper.setLocale(context, language)

        //todo refresh app to another language

    }

    fun signOut(): Boolean {

        Global.userProfile = null
        SharedPreferencesUtill().saveToken("")

        return true

    }


}