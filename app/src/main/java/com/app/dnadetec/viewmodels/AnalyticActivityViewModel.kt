package com.app.dnadetec.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.dnadetec.R
import com.app.dnadetec.models.AnalyticData
import com.app.dnadetec.repos.AnalyticRepo
import com.app.dnadetec.responses.Result
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class AnalyticActivityViewModel @Inject constructor(private val context: Context) : ViewModel() {

    private var addAnalyticResult: MutableLiveData<Result<Boolean>>? = null

    private val apiRepo = AnalyticRepo()

    var duration = 0L

    fun getFormatedDuration():String{

        val hours = TimeUnit.MILLISECONDS.toHours(duration)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration))

        val seconds = TimeUnit.MILLISECONDS.toSeconds(duration) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))

        return "$hours : $minutes : $seconds"

    }

    fun addAnalytic(
        projectName: String,
        place: String,
        disease: String,
        detail: String,
        temp: String
    ): LiveData<Result<Boolean>> {

        addAnalyticResult = MutableLiveData()

        apiRepo.addAnalytic(projectName, place, disease, detail, temp, duration)
            .observeForever {

                if (it.isSuccess) {

                    addAnalyticResult?.value = Result.success(it.data)

                } else {

                    addAnalyticResult?.value = Result.error(it.message)

                }

            }

        return addAnalyticResult as MutableLiveData<Result<Boolean>>

    }


    fun dataValidate(
        projectName: String,
        place: String,
        disease: String,
        detail: String,
        temp: String,
        timer: String
    ): Result<AnalyticData> {

        return when {
            projectName.isEmpty() -> {
                Result.error(context.getString(R.string.text_please_enter_project_name))
            }
            place.isEmpty() -> {
                Result.error(context.getString(R.string.text_please_enter_place))
            }
            disease.isEmpty() -> {
                Result.error(context.getString(R.string.text_please_enter_disease))
            }
            detail.isEmpty() -> {
                Result.error(context.getString(R.string.text_please_enter_some_detail))
            }
            temp.isEmpty() -> {
                Result.error(context.getString(R.string.text_please_enter_temp))
            }
            timer.isEmpty() -> {
                Result.error(context.getString(R.string.text_please_enter_time))
            }
            (duration <= 0L) -> {
                Result.error(context.getString(R.string.text_please_enter_time_more_than_zero))
            }
            else -> {
                Result.success(AnalyticData("dummy",projectName,place,disease,detail,temp,duration.toString(),duration,"","","",""))
            }
        }

    }

}