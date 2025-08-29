package com.acdevs.reflex.service

import SendSms
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.acdevs.reflex.util.DeviceInfoProvider
import getPhoneNumberFromName

class DeviceInfoService : Service() {

    private var contactName: String? = null
    private var originalMessage: String? = null

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        contactName = intent?.getStringExtra("contactName")
        originalMessage = intent?.getStringExtra("originalMessage")

        collectAndSendInfo()

        return START_NOT_STICKY
    }

    private fun collectAndSendInfo() {
        val provider = DeviceInfoProvider(this)

        val battery = provider.getBattery()
        val network = provider.getNetwork()

        provider.getLocation { location ->
            val msg = """
                $battery
                $network
                $location
            """.trimIndent()

            Log.d("DeviceInfoService", "SendSmsCalled")

            val phoneNumber = contactName?.let { getPhoneNumberFromName(this, it) }

            if (phoneNumber != null) {
                SendSms(phoneNumber, msg)
                Log.d("DeviceInfoService", "SMS sent to $phoneNumber for $contactName")
            } else {
                Log.d("DeviceInfoService", "Could not resolve number for $contactName")
            }
        }
    }

    private fun startForegroundService() {
        val channelId = "reflex_service"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Reflex Background Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
        }
        val notification: Notification = Notification.Builder(this, channelId)
            .setContentTitle("Reflex Running")
            .setContentText("Collecting device info")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .build()
        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
