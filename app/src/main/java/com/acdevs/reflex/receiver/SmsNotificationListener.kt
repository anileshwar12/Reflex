package com.acdevs.reflex.receiver

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.acdevs.reflex.service.DeviceInfoService

class SmsNotificationListener : NotificationListenerService() {

    private fun notifyApp(title: String, text: String, pkg: String) {
        val intent = Intent("ReflexNotification")
        intent.putExtra("sender", title)
        intent.putExtra("message", text)
        intent.putExtra("package", pkg)
        sendBroadcast(intent)
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d("SmsNotification", "Notification listener connected")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn?.let {
            val packageName = it.packageName
            val extras = it.notification.extras
            val title = extras.getCharSequence("android.title")?.toString() ?: ""
            val text = extras.getCharSequence("android.text")?.toString() ?: ""

            if (packageName == "com.google.android.apps.messaging") {
                Log.d("SmsNotification", "From: $title, Message: $text, Package: $packageName")

                if (text.contains("@66", ignoreCase = true)) {
                    val serviceIntent = Intent(this, DeviceInfoService::class.java).apply {
                        putExtra("contactName", title)
                        putExtra("originalMessage", text)
                    }
                    startForegroundService(serviceIntent)

                    Log.d("Notification In Listener", text)
                    notifyApp(title, text, packageName)
                }
            }



            Log.d("SmsNotification", "From: $title, Message: $text, Package: $packageName")
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        sbn?.let {
            Log.d("SmsNotification", "Notification removed from ${it.packageName}")
        }
    }
}
