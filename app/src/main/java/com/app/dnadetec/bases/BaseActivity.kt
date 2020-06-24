package com.app.dnadetec.bases

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.dnadetec.R
import com.app.dnadetec.views.MessageDialog
import com.kaopiz.kprogresshud.KProgressHUD

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mActivity: AppCompatActivity

    abstract fun getLayoutID(): Int
    abstract fun onCreate()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(getLayoutID())
        mActivity = this
        onCreate()


    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_from_right)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit)
    }

    var progressDialog: KProgressHUD? = null
    fun showProgressDialog() {
        try {
            dismissProgressDialog()

            progressDialog = KProgressHUD.create(mActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
            progressDialog!!.show()
        } catch (e: Exception) {
        }

    }

    fun dismissProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog!!.dismiss()
                progressDialog = null
            }
        } catch (e: Exception) {
        }
    }

    fun showYesNoDialog(msg: String, YesButtonText: String, callback: MessageDialog.CallBack, NoButtonText: String, cancelCallBack: MessageDialog.CallBack) {
        try {
            var dialog = MessageDialog(this, R.style.AppTheme)
            dialog.setCallBack(callback)
            dialog.setCancelCallback(cancelCallBack)
            dialog.show()

            dialog.setTextBtnClose(YesButtonText)
            dialog.setCancelButtonText(NoButtonText)
            dialog.showCancelButton()
            dialog.setTextMsg(msg)

        } catch (e: Exception) {

        }
    }


    fun showDialog(msg: String, closeText: String, callback: MessageDialog.CallBack) {
        try {
            var dialog = MessageDialog(mActivity, R.style.AppTheme)
            dialog.setCallBack(callback)
            dialog.show()

            dialog.setTextMsg(msg)
            dialog.setTextBtnClose(closeText)
        } catch (e: Exception) {
        }
    }

    fun showDialog(msg: String, closeText: String, isClose: Boolean = false) {
        try {
            var dialog = MessageDialog(mActivity, R.style.AppTheme)
            dialog.setCallBack {
                if (isClose) {
                    finish()
                }
            }
            dialog.show()

            dialog.setTextMsg(msg)
            dialog.setTextBtnClose(closeText)
        } catch (e: Exception) {
        }
    }


}
