package com.app.dnadetec.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.dnadetec.Global
import com.app.dnadetec.R
import com.app.dnadetec.models.UserProfile
import com.app.dnadetec.repos.LoginRepo
import com.app.dnadetec.responses.Result
import com.app.dnadetec.utills.SharedPreferencesUtill
import javax.inject.Inject

class LoginActivityViewModel @Inject constructor(private val context: Context) : ViewModel() {

    private var signInResult: MutableLiveData<Result<UserProfile>>? = null

    private val apiRepo = LoginRepo()

    fun signIn(userName: String, password: String): LiveData<Result<UserProfile>> {

        signInResult = MutableLiveData()

        val checkIsNotEmpty = checkInput(userName, password)

        if (checkIsNotEmpty.isSuccess) {

            apiRepo.getUserProfileFromAPI(userName).observeForever {

                if (it.isSuccess) {

                    val checkPasswordIsPass = checkPassword(password, it.data?.first())

                    if (checkPasswordIsPass.isSuccess) {
                        Global.userProfile = it.data?.first()
                        signInResult?.value = Result.success(it.data?.first())

                        saveToken(it.data?.first())

                    } else {
                        signInResult?.value = Result.error(checkPasswordIsPass.message)
                    }


                } else {

                    signInResult?.value = Result.error(it.message)

                }

            }
        } else {
            signInResult?.value = Result.error(checkIsNotEmpty.message)
        }

        return signInResult as MutableLiveData<Result<UserProfile>>
    }

    private fun saveToken(user: UserProfile?) {

        val token = "${user?.id}/${user?.User}"

        SharedPreferencesUtill().saveToken(token)

    }

    private fun checkPassword(
        password: String,
        data: UserProfile?
    ): Result<String> {

        if (password == data?.Password) {
            return Result.success("Password equal")
        } else {
            return Result.error(context.getString(R.string.text_invalid_password))
        }

    }

    private fun checkInput(userName: String, password: String): Result<String> {

        return when {
            userName.isEmpty() -> {
                Result.error(context.getString(R.string.text_please_enter_username))
            }
            password.isEmpty() -> {
                Result.error(context.getString(R.string.text_please_enter_password))
            }
            else -> {
                Result.success("Passed")
            }
        }

    }

}