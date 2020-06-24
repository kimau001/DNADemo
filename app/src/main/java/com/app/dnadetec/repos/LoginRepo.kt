package com.app.dnadetec.repos

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.dnadetec.App
import com.app.dnadetec.R
import com.app.dnadetec.bases.BaseRepo
import com.app.dnadetec.https.HttpManager
import com.app.dnadetec.models.UserProfile
import com.app.dnadetec.responses.ApiListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.NullPointerException
import com.app.dnadetec.responses.Result
import javax.inject.Inject

class LoginRepo : BaseRepo() {

    private var apiResponse : MutableLiveData<Result<List<UserProfile>>>? = null

    fun getUserProfileFromAPI(userName: String): LiveData<Result<List<UserProfile>>> {

        apiResponse = MutableLiveData()

        service.getUserProfile(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isSuccessful) {

                    if (it.body() != null){

                        apiResponse?.value = Result.success(it.body())

                    }else{
                        apiResponse?.value = Result.error(context.getString(R.string.text_login_error))
                    }

                } else {
                    apiResponse?.value = Result.error(context.getString(R.string.text_login_error))
                }
            }, {
                apiResponse?.value = Result.error(context.getString(R.string.text_no_internet))
            })

        return apiResponse as MutableLiveData<Result<List<UserProfile>>>
    }

}