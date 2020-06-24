package com.app.dnadetec.di

import com.app.dnadetec.activities.*
import com.app.dnadetec.bases.BaseRepo
import com.app.dnadetec.repos.LoginRepo
import com.app.dnadetec.utills.SharedPreferencesUtill
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    ViewModelModule::class,
    AppModule::class
))
interface AppComponent {
    /* add new activity or repo here if need to use dagger*/
    fun inject(baseRepo: BaseRepo)
    fun inject(spu: SharedPreferencesUtill)
    fun inject(loginActivity:LoginActivity)
    fun inject(signUpActivity: SignUpActivity)
    fun inject(settingActivity: SettingActivity)
    fun inject(analyticActivity: AnalyticActivity)
    fun inject(timerActivity: TimerActivity)
    fun inject(ResultListActivity:ResultListActivity)
    fun inject(cameraActivity: CameraActivity)
    fun inject(splashScreenActivity: SplashScreenActivity)
}