package com.envyglit.chat

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.envyglit.chat.util.FireBaseUtils
import dagger.hilt.android.HiltAndroidApp
import java.util.*


@HiltAndroidApp
class App: Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        Log.d("Time-App", Date().time.toString())
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onAppForegrounded() {
        FireBaseUtils.updateCurrentUser(Date(), true)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onAppBackgrounded() {
        FireBaseUtils.updateCurrentUser(Date(), false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onAppDestroyed(){
        FireBaseUtils.updateCurrentUser(Date(), false)
    }

}