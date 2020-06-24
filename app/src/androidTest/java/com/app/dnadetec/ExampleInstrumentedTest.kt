package com.app.dnadetec

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.dnadetec.viewmodels.AnalyticActivityViewModel

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.app.dnadetec", appContext.packageName)
    }

    @Test
    fun getFormatedDurationCorrect() {

        val vm = AnalyticActivityViewModel(InstrumentationRegistry.getInstrumentation().targetContext)

        vm.duration = 1800000

        val string = vm.getFormatedDuration()

        Log.e("getFormatedDuration","Result : $string")

        assertNotNull(string)

    }

}
