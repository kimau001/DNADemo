package com.app.dnadetec.https

import com.app.dnadetec.models.AnalyticData
import com.app.dnadetec.models.UserProfile
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("getUser.php")
    fun getUserProfile(
        @Query("User") username: String
        , @Query("isAdd") isAdd: Boolean = true
    ): Observable<Response<List<UserProfile>>>

    @GET("addUser.php")
    fun signUpUser(
        @Query("Name") fullname: String,
        @Query("User") username: String,
        @Query("Password") password: String,
        @Query("Avatar") avatar: String,
        @Query("isAdd") isAdd: Boolean = true
    ): Observable<Response<Boolean>>

    @GET("addAnalytic.php")
    fun addAnalytic(
        @Query("Name") projectName: String,
        @Query("Place") place: String,
        @Query("Disease") disease: String,
        @Query("Detail") detail: String,
        @Query("Temp") temp: String,
        @Query("Timer") timer: Long, // in minutes
        @Query("isAdd") isAdd: Boolean = true
    ): Observable<Response<Boolean>>

    @GET("getAllProduct.php")
    fun getAllProduct(): Observable<Response<List<AnalyticData>>>


//    @GET("users/{id}/notifications")
//    fun getUserNotification(@Path("id") id: String): Observable<Response<List<NotificationDao>>>

}