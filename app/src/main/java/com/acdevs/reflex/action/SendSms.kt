import android.telephony.SmsManager
import android.util.Log

fun SendSms(phoneNumber: String, message: String) {
    try {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        Log.d("SMS", "SMS sent to $phoneNumber: $message")
    } catch (e: Exception) {
        Log.e("SMS", "Failed to send SMS", e)
    }
}
