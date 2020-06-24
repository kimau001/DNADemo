package com.app.dnadetec.activities

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.app.dnadetec.Global
import com.app.dnadetec.R
import com.app.dnadetec.bases.BaseActivity
import com.app.dnadetec.databinding.ActivityUserProfileBinding
import com.app.dnadetec.viewmodels.UserProfileViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : BaseActivity() {

    private lateinit var viewModel: UserProfileViewModel

    private lateinit var viewDataBinding:ActivityUserProfileBinding

    override fun getLayoutID(): Int  = R.layout.activity_user_profile

    override fun onCreate() {

        viewModel =
            ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutID())
        viewDataBinding.viewmodel = viewModel


        initView()

    }

    private fun initView() {

        buttonBack.setOnClickListener {

            finish()

        }

        llChangePassword.setOnClickListener {

            //TODO go to change password page

        }

        textEditProfile.setOnClickListener {

            //TODO go to edit profile page

        }

        
//        Glide.with(this).load(Global.userProfile?.Avatar).apply(
//            RequestOptions.circleCropTransform()
//                .placeholder(R.drawable.person_white)
//                .error(R.drawable.person_white)
//        ).into(imageProfile)
//
//        textFullname.text = Global.userProfile?.Name
//        textUsername.text = Global.userProfile?.User

    }

}
