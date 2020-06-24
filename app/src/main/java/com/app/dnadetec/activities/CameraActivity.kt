package com.app.dnadetec.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.dnadetec.App
import com.app.dnadetec.R
import com.app.dnadetec.bases.BaseActivity
import com.app.dnadetec.databinding.ActivityCameraBinding
import com.app.dnadetec.databinding.ActivityUserProfileBinding
import com.app.dnadetec.viewmodels.CameraViewModel
import com.app.dnadetec.viewmodels.SettingActivityViewModel
import com.app.dnadetec.viewmodels.UserProfileViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_camera.*
import javax.inject.Inject

class CameraActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CameraViewModel

    private lateinit var viewDataBinding: ActivityCameraBinding

    override fun getLayoutID(): Int = R.layout.activity_camera

    override fun onCreate() {

        App.appComponent.inject(this)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CameraViewModel::class.java)
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutID())
        viewDataBinding.viewmodel = viewModel

        initView()
        observeDate()

    }

    private fun observeDate() {

        viewModel.selectedImage.observe(this, Observer {

            if (it.isSuccess){
                Glide.with(this).load(it.data).into(imageView)
            }else{
                showDialog(it.message.toString(),resources.getString(R.string.text_close))
            }

        })

    }

    private fun initView() {

        buttonBack.setOnClickListener {

            finish()

        }

        llGallery.setOnClickListener {

            viewModel.openGallery(this)

        }


        llCamera.setOnClickListener {

            viewModel.openCamera(this)

        }


    }


}