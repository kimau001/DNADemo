package com.app.dnadetec.activities

import android.content.Intent
import android.widget.Toast
import com.app.dnadetec.Global
import com.app.dnadetec.R
import com.app.dnadetec.bases.BaseActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var backPressed: Long = 0

    override fun getLayoutID(): Int = R.layout.activity_main

    override fun onCreate() {

        initView()

    }

    private fun initView() {

        textFullname.text = Global.userProfile?.Name

        Glide.with(this).load(Global.userProfile?.Avatar).apply(
            RequestOptions.circleCropTransform()
                .placeholder(R.drawable.person_white)
                .error(R.drawable.person_white)
        ).into(imageProfile)

        buttonAnalytic.setOnClickListener {

            val intent = Intent(this,AnalyticActivity::class.java)
            startActivity(intent)

        }
        buttonResult.setOnClickListener {
            val intent = Intent(this,ResultListActivity::class.java)
            startActivity(intent)
        }
        buttonProfile.setOnClickListener {

            val intent = Intent(this,UserProfileActivity::class.java)
            startActivity(intent)
        }
        buttonSetting.setOnClickListener {

//            val intent = Intent(this,SettingActivity::class.java)
//            startActivity(intent)

            //todo test camera page
            val intent = Intent(this,CameraActivity::class.java)
            startActivity(intent)

        }

    }

    override fun onBackPressed() {

        if (backPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(
                this
                , getString(R.string.text_press_to_exit)
                , Toast.LENGTH_SHORT
            ).show()
            backPressed = System.currentTimeMillis()
        }

    }

}
