package com.acdevs.reflex.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.BatteryManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

class DeviceInfoProvider(private val context: Context) {

    fun getBattery(): String {
        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        val pct = if (scale > 0) (level * 100 / scale) else -1

        Log.d("DeviceInfoProvider","getBatteryInfo")
        return "Battery: $pct%"
    }



    fun getNetwork(): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val active = cm.activeNetworkInfo
        val connected = active?.isConnectedOrConnecting == true
        val type = active?.typeName ?: "Unknown"
        Log.d("DeviceInfoProvider","getNetworkInfo")
        return "Network: $type, Connected: $connected"
    }

    fun getLocation(callback: (String) -> Unit) {
        val fused = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            callback("Location: Permission denied")
            return
        }
        fused.lastLocation.addOnSuccessListener { loc ->
            if (loc != null) {
                callback("Location: ${loc.latitude},${loc.longitude}")
            } else {
                callback("Location: Unknown")
            }
        }
    }
}
