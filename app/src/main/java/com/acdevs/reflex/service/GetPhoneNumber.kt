import android.content.Context
import android.provider.ContactsContract

fun getPhoneNumberFromName(context: Context, contactName: String): String? {
    val resolver = context.contentResolver
    val uri = ContactsContract.Contacts.CONTENT_URI
    val cursor = resolver.query(
        uri,
        null,
        "${ContactsContract.Contacts.DISPLAY_NAME} = ?",
        arrayOf(contactName),
        null
    )

    cursor?.use {
        if (it.moveToFirst()) {
            val id = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
            val hasPhone = it.getInt(it.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))
            if (hasPhone > 0) {
                val phoneCursor = resolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                    arrayOf(id),
                    null
                )
                phoneCursor?.use { pc ->
                    if (pc.moveToFirst()) {
                        return pc.getString(pc.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    }
                }
            }
        }
    }
    return null
}
