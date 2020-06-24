package com.app.dnadetec.activities

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.dnadetec.App
import com.app.dnadetec.R
import com.app.dnadetec.bases.BaseActivity
import com.app.dnadetec.viewmodels.SignUpActivityViewModel
import com.app.dnadetec.views.MessageDialog
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.editPassword
import kotlinx.android.synthetic.main.activity_sign_up.editUsername
import javax.inject.Inject

class SignUpActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SignUpActivityViewModel

    override fun getLayoutID(): Int = R.layout.activity_sign_up

    override fun onCreate() {

        App.appComponent.inject(this)

        viewModel =
            ViewModelProviders.of(this,viewModelFactory).get(SignUpActivityViewModel::class.java)

        initView()

    }

    private fun initView() {

        buttonBack.setOnClickListener {

            finish()

        }

        buttonConfirm.setOnClickListener {

            showProgressDialog()

            viewModel.signIn(
                editStaffName.text.toString().trim(),
                editUsername.text.toString().trim(),
                editPassword.text.toString().trim(),
                editConfirmPassword.text.toString().trim(),
                ""
            ).observe(this, Observer {

                dismissProgressDialog()

                if (it.isSuccess){
                    showDialog(getString(R.string.text_sign_up_successful),resources.getString(R.string.text_close),MessageDialog.CallBack {
                        nextPage()
                    })
                }else{
                    showDialog(it.message.toString(),resources.getString(R.string.text_close))
                }

            })

        }

    }

    private fun nextPage() {

        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)

    }

}
