package com.app.dnadetec.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.dnadetec.R
import com.app.dnadetec.bases.BaseRepo
import com.app.dnadetec.models.UserProfile
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.app.dnadetec.responses.Result

class SignUpRepo : BaseRepo() {

    private var apiResponse: MutableLiveData<Result<Boolean>>? = null

    fun signUpUser(userProfile: UserProfile): LiveData<Result<Boolean>> {

        apiResponse = MutableLiveData()

        service.signUpUser(
            userProfile.Name,
            userProfile.User,
            userProfile.Password,
            userProfile.Avatar
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isSuccessful) {
                    if (it.body() != null) {

                        apiResponse?.value = Result.success(it.body())

                    } else {
                        apiResponse?.value =
                            Result.error(context.getString(R.string.txt_invalid_data))
                    }

                } else {
                    apiResponse?.value =
                        Result.error(context.getString(R.string.txt_invalid_data))
                }
            }, {
                apiResponse?.value = Result.error(context.getString(R.string.text_no_internet))
            })

        return apiResponse as MutableLiveData<Result<Boolean>>
    }

}