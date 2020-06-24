package com.app.dnadetec.viewmodels

import android.R
import android.animation.ObjectAnimator
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.CountDownTimer
import android.widget.ProgressBar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.dnadetec.BuildConfig
import com.app.dnadetec.activities.TimerActivity
import com.app.dnadetec.models.AnalyticData
import com.app.dnadetec.models.TimerModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class TimerActivityViewModel @Inject constructor(private val context: Context) : ViewModel() {

    var analysticData: AnalyticData? = null
    var timer: CountDownTimer? = null
    var progressBar: ProgressBar? = null
    var progressAnimator: ObjectAnimator? = null

    var timimgResult: MutableLiveData<TimerModel>? = null
    var timerModel = TimerModel()
    var ringtone: Ringtone? = null
    val notiTimimgId = 1
    val notiAlertId = 2
    val notiReqCode = 1
    var isShowNoti = false

    fun createSound() {

        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(
            context,
            notification
        )
    }

    fun getCurrentHours(timeInMills: Long): Int {

        return TimeUnit.MILLISECONDS.toHours(timeInMills).toInt()

    }


    fun getCurrentMinutes(timeInMills: Long): Int {

        return (TimeUnit.MILLISECONDS.toMinutes(timeInMills) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeInMills))).toInt()


    }

    fun getCurrentSeconds(timeInMills: Long): Int {

        return (TimeUnit.MILLISECONDS.toSeconds(timeInMills) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMills))
                ).toInt()


    }

    fun getTimeInMill(): Long {

        return if (analysticData != null) {

            analysticData!!.timerInMillis

        } else {

            0

        }

    }

    fun setProgressBar(progressBar: ProgressBar, maxProgress: Int, startProgress: Int) {

        this.progressBar = progressBar
        progressBar.max = maxProgress
        progressBar.progress = startProgress

    }

    fun animateProgressBar(maxValue: Int, duration: Long) {

        progressAnimator =
            ObjectAnimator.ofInt(progressBar, "progress", maxValue, 0)
        progressAnimator?.duration = duration
        progressAnimator?.start()

    }

    fun pauseProgressBar() {

        progressAnimator?.pause()

    }


    fun getResultTimer(): LiveData<TimerModel> {

        timimgResult = MutableLiveData()

        timerModel.totalTimerInMillis = getTimeInMill()
        timerModel.currentTimerInMillis = getTimeInMill()

        timimgResult?.value = timerModel

        return timimgResult as LiveData<TimerModel>

    }

    fun setTimer(millis: Long, interval: Long) {

        timer = object : CountDownTimer(millis, interval) {
            override fun onTick(time: Long) {

                timerModel.currentTimerInMillis = time
                timimgResult?.value = timerModel

                notifyTimer( String.format(
                        context.resources.getString(com.app.dnadetec.R.string.text_timer_value)
                        , getCurrentHours(timerModel.currentTimerInMillis)
                        , getCurrentMinutes(timerModel.currentTimerInMillis)
                        , getCurrentSeconds(timerModel.currentTimerInMillis)
                    )
                )

            }

            override fun onFinish() {

                timerModel.isFinish = true
                timerModel.currentTimerInMillis = 0L
                timimgResult?.value = timerModel
                timer?.cancel()
                removeNoti(notiTimimgId)
                notifyTimer(
                    context.getString(com.app.dnadetec.R.string.text_countdown_finish)
                )
                playSound()
            }
        }

    }

    fun runTimer() {

        if (timerModel.isFinish) {

            removeAllNoti()
            stopSound()

            return
        }

        if (timerModel.isPause) {

            startTimer()

        } else {

            pauseTimer(false)

        }

    }

    private fun startTimer() {

        if (timerModel.isStart) {

            setTimer(timerModel.currentTimerInMillis, 1000)
            animateProgressBar(progressBar!!.progress, timerModel.currentTimerInMillis)

        } else {

            timerModel.isStart = true
            animateProgressBar(progressBar!!.max, timerModel.totalTimerInMillis)

        }

        timer?.start()

        timerModel.isPause = false

    }


    fun pauseTimer(isStop: Boolean) {

        if (isStop) {
            timerModel.isStart = false
            timerModel.isFinish = false
            removeAllNoti()
            stopSound()
        }

        timer?.cancel()
        timerModel.isPause = true
        pauseProgressBar()

    }

    fun destroyTimer() {

        timerModel.isStart = false
        timerModel.isFinish = false
        timer?.cancel()
        timerModel.isPause = true
        pauseProgressBar()
        removeAllNoti()
        stopSound()

    }

    private fun notifyTimer(text: String) {

        if (!isShowNoti){
            return
        }

        val intent = Intent(context,TimerActivity::class.java)
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, notiReqCode, intent, 0)

        var builder = NotificationCompat.Builder(context!!, BuildConfig.NOTI_CHANNEL_ID)
            .setSmallIcon(R.mipmap.sym_def_app_icon)
            .setContentTitle(context.resources.getString(com.app.dnadetec.R.string.text_timer_value))
            .setContentText(text)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(notiTimimgId, builder.build())
        }


    }

    fun removeAllNoti() {

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancelAll()

    }

    private fun removeNoti(id: Int) {

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancel(id)

    }

    private fun playSound() {
        try {
            ringtone?.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopSound() {
        try {
            ringtone?.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}