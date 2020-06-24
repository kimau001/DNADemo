package com.app.dnadetec.models

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.app.dnadetec.R
import com.google.gson.annotations.SerializedName
import java.lang.Exception
import java.util.concurrent.TimeUnit

data class AnalyticData(
    @SerializedName("no") var no: String?
    ,@SerializedName("name") var name: String?
    ,@SerializedName("place") var place: String?
    ,@SerializedName("disease") var disease: String?
    ,@SerializedName("detail") var detail: String?
    ,@SerializedName("temp") var temp: String?
    ,@SerializedName("timer") var timer: String?
    ,@SerializedName("timerInMillis") var timerInMillis: Long
    ,@SerializedName("date") var date: String?
    ,@SerializedName("result") var result: String?
    ,@SerializedName("check") var check: String?
    ,@SerializedName("imgDNA") var imgDNA: String?
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {

        parcel.writeString(no)
        parcel.writeString(name)
        parcel.writeString(place)
        parcel.writeString(disease)
        parcel.writeString(detail)
        parcel.writeString(temp)
        parcel.writeString(timer)
        parcel.writeLong(timerInMillis)
        parcel.writeString(date)
        parcel.writeString(result)
        parcel.writeString(check)
        parcel.writeString(imgDNA)

    }

    fun getTimeText(context:Context):String{

        try {

            val timerinMill = TimeUnit.MINUTES.toMillis(timer?.toLong()!!)

            if(getCurrentHours(timerinMill) > 0){

                return String.format(context.resources.getString(R.string.text_time_unit_hour)
                    , getCurrentHours(timerinMill)
                    , getCurrentMinutes(timerinMill)
                    , getCurrentSeconds(timerinMill)
                )

            }else{

                return String.format(context.resources.getString(R.string.text_time_unit_minute)
                    , getCurrentMinutes(timerinMill)
                    , getCurrentSeconds(timerinMill)
                )

            }


        }catch (e:Exception){
            return "-"
        }

    }

    fun getCurrentHours(timeInMills: Long): Int {

        return TimeUnit.MILLISECONDS.toHours(timeInMills).toInt()


    }


    fun getCurrentMinutes(timeInMills: Long): Int {

        return (TimeUnit.MILLISECONDS.toMinutes(timeInMills) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeInMills))).toInt()


    }

    fun getCurrentSeconds(timeInMills: Long): Int {

        return (TimeUnit.MILLISECONDS.toSeconds(timeInMills) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMills))
                ).toInt()


    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnalyticData> {
        override fun createFromParcel(parcel: Parcel): AnalyticData {
            return AnalyticData(parcel)
        }

        override fun newArray(size: Int): Array<AnalyticData?> {
            return arrayOfNulls(size)
        }
    }

}
