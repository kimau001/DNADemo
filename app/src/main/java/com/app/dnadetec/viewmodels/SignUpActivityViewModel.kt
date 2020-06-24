package com.app.dnadetec.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.dnadetec.Global
import com.app.dnadetec.R
import com.app.dnadetec.models.UserProfile
import com.app.dnadetec.repos.SignUpRepo
import com.app.dnadetec.responses.Result
import javax.inject.Inject

class SignUpActivityViewModel @Inject constructor(private val context: Context) : ViewModel() {


    private var signInResult: MutableLiveData<Result<Boolean>>? = null

    private val apiRepo = SignUpRepo()

    fun signIn(
        fullName: String,
        userName: String,
        password: String,
        confirmPassword: String,
        avatar: String
    ): LiveData<Result<Boolean>>{

        signInResult = MutableLiveData()

        val resultValidate = dataValidate(fullName,userName, password,confirmPassword)

        if (resultValidate.isSuccess) {

            val user = UserProfile("dummy",fullName,userName,password,avatar)

            apiRepo.signUpUser(user).observeForever {

                if (it.isSuccess) {

                        Global.userProfile = user

                        signInResult?.value = Result.success(it.data)

                } else {

                    signInResult?.value = Result.error(it.message)

                }

            }
        } else {
            signInResult?.value = Result.error(resultValidate.message)
        }

        return signInResult as MutableLiveData<Result<Boolean>>
    }


    private fun dataValidate(
        fullName: String,
        userName: String,
        password: String,
        confirmPassword: String
    ): Result<String> {

        return when {
            fullName.isEmpty() -> {
                Result.error(context.getString(R.string.text_please_enter_fullname))
            }
            userName.isEmpty() -> {
                Result.error(context.getString(R.string.text_please_enter_username))
            }
            password.isEmpty() -> {
                Result.error(context.getString(R.string.text_please_enter_password))
            }
            confirmPassword.isEmpty() -> {
                Result.error(context.getString(R.string.text_please_confirm_password))
            }
            (password != confirmPassword) -> {
                Result.error(context.getString(R.string.text_confirm_pass_not_match))
            }
            else -> {
                Result.success("Passed")
            }
        }

    }

}