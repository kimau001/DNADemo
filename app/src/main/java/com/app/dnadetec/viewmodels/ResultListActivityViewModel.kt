package com.app.dnadetec.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.dnadetec.models.AnalyticData
import com.app.dnadetec.repos.ResultListRepo
import com.app.dnadetec.responses.Result
import javax.inject.Inject

class ResultListActivityViewModel @Inject constructor(private val context: Context) : ViewModel() {

    private var getAnalyticResult: MutableLiveData<Result<List<AnalyticData>>>? = null

    private val apiRepo = ResultListRepo()

    fun getAllAnalytic():LiveData<Result<List<AnalyticData>>>{

        getAnalyticResult = MutableLiveData()

        apiRepo.getAllProduct().observeForever {

            if (it.isSuccess) {


                getAnalyticResult?.value = Result.success(it.data)

            } else {

                getAnalyticResult?.value = Result.error(it.message)

            }

        }

        return getAnalyticResult as MutableLiveData<Result<List<AnalyticData>>>

    }

}