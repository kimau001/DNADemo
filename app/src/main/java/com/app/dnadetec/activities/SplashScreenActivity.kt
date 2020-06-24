package com.app.dnadetec.activities

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.dnadetec.App
import com.app.dnadetec.R
import com.app.dnadetec.bases.BaseActivity
import com.app.dnadetec.utills.CheckPermission
import com.app.dnadetec.viewmodels.SplashScreenActivityViewModel

class SplashScreenActivity : BaseActivity() {

    private lateinit var viewModel: SplashScreenActivityViewModel

    override fun getLayoutID(): Int = R.layout.activity_splash_screen

    override fun onCreate() {

        App.appComponent.inject(this)

        viewModel =
            ViewModelProviders.of(this).get(SplashScreenActivityViewModel::class.java)

        viewModel.getDefaultLanguage(this)
        viewModel.createNotificationChannel(this)

        if (CheckPermission.isComplete(mActivity)) {
            nextPage()
        } else {
            CheckPermission.requestPermissions(mActivity)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {

            CheckPermission.REQUEST_CODE -> if (grantResults.isNotEmpty()) {

                var isComplete = true
                for (i in grantResults.indices) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        isComplete = false
                        break
                    }
                }
                if (isComplete) {
                    nextPage()
                } else {
                    showDialog("Permission not granted.","ปิด", true)
                }

            } else {
                showDialog("Permission not granted.","ปิด", true)
            }
        }
    }

    private fun nextPage() {

        viewModel.checkLogin().observe(this, Observer {

            if (it.isSuccess){
                if (it.data!!){
                    val intent = Intent(mActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(mActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }else{
                showDialog(it.message.toString(),getString(R.string.text_close),true)
            }

        })

    }



}
