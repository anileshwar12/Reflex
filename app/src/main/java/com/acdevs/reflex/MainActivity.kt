package com.acdevs.reflex

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.acdevs.reflex.presentation.screens.MainScreen
import com.acdevs.reflex.presentation.ui.theme.ReflexTheme

class MainActivity : ComponentActivity() {

    private val requestSmsPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                Log.d("Permissions", "SEND_SMS granted")
            } else {
                Log.d("Permissions", "SEND_SMS denied")
            }
        }

    private val notificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val sender = intent?.getStringExtra("sender") ?: ""
            val message = intent?.getStringExtra("message") ?: ""

            Log.d("MainActivity", "Got SMS notification: $sender -> $message")

            if (message.contains("anil", ignoreCase = true)) {
                Log.d("Notification", "Received in MainActivity")
                // SendSms("+919392259628", "Hello there")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")

        requestContactsPermission()
        registerNotificationReceiver()
        requestSmsPermissions()

        if (!hasNotificationAccess(this)) {
            requestNotificationAccess()
        }

        setContent {
            ReflexTheme {
                Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (hasNotificationAccess(this)) {
            Log.d("Permissions", "Notification access granted")
        } else {
            Log.d("Permissions", "Notification access not granted")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(notificationReceiver)
    }

    // --- Permissions helpers ---

    private fun requestSmsPermissions() {
        val smsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        if (smsPermission != PackageManager.PERMISSION_GRANTED) {
            requestSmsPermission.launch(Manifest.permission.SEND_SMS)
        }
    }

    private fun requestContactsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                1001
            )
        }
    }

    private fun requestNotificationAccess() {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }

    private fun registerNotificationReceiver() {
        val filter = IntentFilter("ReflexNotification")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            registerReceiver(notificationReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(notificationReceiver, filter)
        }
    }

    private fun hasNotificationAccess(context: Context): Boolean {
        val enabledListeners =
            Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
        return enabledListeners?.contains(context.packageName) == true
    }
}
