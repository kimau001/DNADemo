package com.app.dnadetec.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.dnadetec.Global
import com.app.dnadetec.models.UserProfile
import com.app.dnadetec.utills.LocaleHelper
import com.app.dnadetec.utills.LocalizationUtil
import com.app.dnadetec.utills.SharedPreferencesUtill
import javax.inject.Inject


class UserProfileViewModel : ViewModel() {

    val userProfile = MutableLiveData<UserProfile>()

    init{

        userProfile.value = Global.userProfile

    }

}