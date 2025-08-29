package com.acdevs.reflex.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("SmsReceiver", "onReceive called with action: ${intent?.action}")
        Log.d("SmsReceiver", "=== SMS RECEIVER CALLED ===")
        Log.d("SmsReceiver", "Action: ${intent?.action}")
        Log.d("SmsReceiver", "Intent: $intent")


        if (intent?.action == "android.provider.Telephony.SMS_RECEIVED") {
            try {
                val bundle: Bundle? = intent.extras
                Log.d("SmsReceiver", "Bundle received: ${bundle != null}")

                if (bundle != null) {
                    val pdus = bundle.get("pdus") as? Array<*>
                    val format = bundle.getString("format")

                    Log.d("SmsReceiver", "PDUs count: ${pdus?.size}, Format: $format")

                    pdus?.forEach { pdu ->
                        val sms = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            SmsMessage.createFromPdu(pdu as ByteArray, format)
                        } else {
                            @Suppress("DEPRECATION")
                            SmsMessage.createFromPdu(pdu as ByteArray)
                        }

                        val sender = sms.displayOriginatingAddress
                        val message = sms.displayMessageBody

                        Log.d("SmsMessage", "Sender: $sender, Message: $message")
                    }
                }
            } catch (e: Exception) {
                Log.e("SmsReceiver", "Error processing SMS", e)
            }
        }
    }
}