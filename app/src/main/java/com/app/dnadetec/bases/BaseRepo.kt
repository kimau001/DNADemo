package com.app.dnadetec.bases

import android.content.Context
import com.app.dnadetec.App
import com.app.dnadetec.https.HttpManager
import javax.inject.Inject

abstract class BaseRepo{

    constructor(){
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var context: Context

    val service = HttpManager.service

}