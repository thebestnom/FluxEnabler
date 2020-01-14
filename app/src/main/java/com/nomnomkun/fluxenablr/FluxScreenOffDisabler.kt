package com.nomnomkun.fluxenablr

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder


class FluxScreenOffDisabler : Service() {

    private var mScreenIntentReceiver: BroadcastReceiver? = ScreenIntentReceiver()
    private val binder = LocalBinder()

    inner class ScreenIntentReceiver
        : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val i = if(intent?.action == Intent.ACTION_SCREEN_OFF) 1 else 0

            FluxSocketClient.sendLocalSocket("disable=$i")
        }
    }

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): FluxScreenOffDisabler = this@FluxScreenOffDisabler
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForegroundService(intent)
        val screenIntentsFilter = IntentFilter()
        screenIntentsFilter.addAction(Intent.ACTION_USER_PRESENT)
        screenIntentsFilter.addAction(Intent.ACTION_SCREEN_OFF)
        registerReceiver(mScreenIntentReceiver, screenIntentsFilter)
        return START_STICKY
    }

    override fun onDestroy() {
        unregisterReceiver(mScreenIntentReceiver)
    }
}
