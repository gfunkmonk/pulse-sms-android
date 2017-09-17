package xyz.klinker.messenger.shared.service

import android.app.IntentService
import android.content.Intent
import android.os.Build
import com.google.firebase.iid.FirebaseInstanceId
import xyz.klinker.messenger.api.implementation.Account
import xyz.klinker.messenger.api.implementation.ApiUtils
import xyz.klinker.messenger.shared.data.Settings

class FirebaseTokenUpdateCheckService : IntentService("FirebaseTokenRefresh") {

    companion object {
        private val TOKEN_PREF_KEY = "stored-firebase-token"
    }

    override fun onHandleIntent(intent: Intent?) {
        val sharedPrefs = Settings.get(this).getSharedPrefs(this)

        val currentToken = FirebaseInstanceId.getInstance().token
        val storedToken = sharedPrefs.getString(TOKEN_PREF_KEY, null)

        if (currentToken != null && currentToken != storedToken) {
            sharedPrefs.edit().putString(TOKEN_PREF_KEY, currentToken).apply()

            if (storedToken != null) {
                val api = ApiUtils
                val account = Account.get(this)

                Thread {
                    api.updateDevice(account.accountId, Integer.parseInt(account.deviceId).toLong(), Build.MODEL,
                            currentToken)
                }.start()
            }
        }

    }
}