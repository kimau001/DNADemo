package com.app.dnadetec.activities

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.dnadetec.App
import com.app.dnadetec.R
import com.app.dnadetec.bases.BaseActivity
import com.app.dnadetec.models.AnalyticData
import com.app.dnadetec.models.TimerModel
import com.app.dnadetec.viewmodels.TimerActivityViewModel
import kotlinx.android.synthetic.main.activity_timer.*
import javax.inject.Inject


class TimerActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: TimerActivityViewModel

    override fun getLayoutID(): Int = R.layout.activity_timer

    override fun onCreate() {

        App.appComponent.inject(this)

        viewModel =
            ViewModelProviders.of(this,viewModelFactory).get(TimerActivityViewModel::class.java)

        viewModel.createSound()

        getIntentData()
        initView()
        setProgressBar()
        setTimer()

    }

    private fun getIntentData() {

        if (intent.extras != null) {
            val analysticData = intent.extras?.getParcelable<AnalyticData>("data")
            viewModel.analysticData = analysticData
        }

    }

    private fun setProgressBar(){

        viewModel.setProgressBar(progressBar,viewModel.getTimeInMill().toInt(),viewModel.getTimeInMill().toInt())

    }

    private fun setTimer() {

        viewModel.setTimer(viewModel.getTimeInMill(),1000)

    }

    private fun initView() {

        viewModel.getResultTimer().observe(this, Observer {

            textTimer.text =  String.format(resources.getString(R.string.text_timer_value)
                ,viewModel.getCurrentHours(it.currentTimerInMillis)
                ,viewModel.getCurrentMinutes(it.currentTimerInMillis)
                ,viewModel.getCurrentSeconds(it.currentTimerInMillis))

            if (it.isFinish){
                updateButton(TimerModel(),true)
                blinkingView(textTimer)
            }

//            Log.e("timer",it.isFinish.toString())

        })

        flPlayPause.setOnClickListener {

            viewModel.runTimer()
            updateButton(viewModel.timerModel)

        }

        textReset.setOnClickListener {

            viewModel.pauseTimer(true)
            setProgressBar()
            setTimer()
            resetTimer()
            updateButton(TimerModel())

        }

        buttonBack.setOnClickListener {

            finish()

        }

    }

    private fun updateButton(timerModel: TimerModel , isForceFinish : Boolean = false) {

        if (isForceFinish){
            imageButton.setImageResource(R.drawable.baseline_stop_black_48)
            return
        }

        if (timerModel.isFinish){
            imageButton.setImageResource(R.drawable.baseline_play_arrow_black_48)
            textTimer.clearAnimation()
            return
        }

        if (timerModel.isStart && !timerModel.isPause){
            imageButton.setImageResource(R.drawable.baseline_pause_black_48)
        }else if (timerModel.isPause){
            imageButton.setImageResource(R.drawable.baseline_play_arrow_black_48)
        }else{
            imageButton.setImageResource(R.drawable.baseline_play_arrow_black_48)
        }

    }

    private fun resetTimer() {

        textTimer.text =  String.format(resources.getString(R.string.text_timer_value)
            ,viewModel.getCurrentHours(viewModel.getTimeInMill())
            ,viewModel.getCurrentMinutes(viewModel.getTimeInMill())
            ,viewModel.getCurrentSeconds(viewModel.getTimeInMill()))

    }

    private fun blinkingView(view: View){

        val anim: Animation = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 50
        anim.startOffset = 20
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        view.startAnimation(anim)

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroyTimer()
    }

    override fun onPause() {
        super.onPause()

        viewModel.isShowNoti = true
    }

    override fun onResume() {
        super.onResume()

        viewModel.isShowNoti = false
        viewModel.removeAllNoti()

    }


}
