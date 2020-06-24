/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.dnadetec.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.dnadetec.viewmodels.*

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    /* add new view model here if need to use dagger*/
    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityViewModel::class)
    abstract fun bindLoginActivityViewModel(viewModel: LoginActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpActivityViewModel::class)
    abstract fun bindSignUpActivityViewModel(viewModel: SignUpActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingActivityViewModel::class)
    abstract fun bindSettingActivityViewModel(viewModel: SettingActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AnalyticActivityViewModel::class)
    abstract fun bindAnalyticActivityViewModel(viewModel: AnalyticActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TimerActivityViewModel::class)
    abstract fun bindTimerActivityViewModel(viewModel: TimerActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResultListActivityViewModel::class)
    abstract fun bindResultListActivityViewModel(viewModel: ResultListActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CameraViewModel::class)
    abstract fun bindCameraViewModel(viewModel: CameraViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
