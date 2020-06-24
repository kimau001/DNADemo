package com.app.dnadetec.viewmodels

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.dnadetec.BuildConfig
import com.app.dnadetec.Global
import com.app.dnadetec.models.UserProfile
import com.app.dnadetec.repos.LoginRepo
import com.app.dnadetec.responses.Result
import com.app.dnadetec.utills.LocaleHelper
import com.app.dnadetec.utills.SharedPreferencesUtill
import java.util.*

class SplashScreenActivityViewModel : ViewModel() {

    private var isLogin: MutableLiveData<Result<Boolean>>? = null

    private val apiRepo = LoginRepo()

    fun checkLogin(): LiveData<Result<Boolean>> {

        isLogin = MutableLiveData()

        val userProfile = getUserFromToken()

        if (userProfile.isSuccess) {

            apiRepo.getUserProfileFromAPI(userProfile.data!!.User).observeForever {

                if (it.isSuccess) {

                    Global.userProfile = it.data?.first()
                    isLogin?.value = Result.success(true)

                    saveToken(it.data?.first())

                } else {

                    isLogin?.value = Result.error(it.message)

                }

            }

        } else {
            isLogin?.value = Result.success(false)
        }

        return isLogin as MutableLiveData<Result<Boolean>>
    }

    private fun getUserFromToken(): Result<UserProfile> {

        val token =
            SharedPreferencesUtill().getToken()

        return if (!token.isNullOrEmpty()) {

            val tokenArray = token?.split("/")
            val id = tokenArray?.first().toString()
            val username = tokenArray?.get(1).toString()
            Result.success(UserProfile(id, "", username, "", ""))

        } else {
            Result.error("Not login")
        }

    }

    private fun saveToken(user: UserProfile?) {

        val token = "${user?.id}/${user?.User}"

        SharedPreferencesUtill().saveToken(token)

    }


    fun getDefaultLanguage(context:Context) {

        Global.lang = LocaleHelper.getLanguage(context)

        if (Global.lang == "th") {
            LocaleHelper.setLocale(context, "th")
        } else if (Global.lang == "en") {
            LocaleHelper.setLocale(context, "en")
        }

    }


    fun createNotificationChannel(context:Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification"
            val descriptionText = "DNA Detec application notification channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(BuildConfig.NOTI_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}