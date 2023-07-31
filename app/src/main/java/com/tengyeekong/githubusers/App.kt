package com.tengyeekong.githubusers

import android.app.Application
import com.tengyeekong.githubusers.ui.common.utils.NetworkUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkUtils.initNetworkListener(this)
    }
}
