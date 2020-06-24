package com.app.dnadetec

import android.app.Application
import android.content.Context
import com.app.dnadetec.di.AppComponent
import com.app.dnadetec.di.AppModule
import com.app.dnadetec.di.DaggerAppComponent
import com.app.dnadetec.utills.LocalizationUtil


class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}