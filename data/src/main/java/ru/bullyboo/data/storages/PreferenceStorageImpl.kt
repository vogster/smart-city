package ru.bullyboo.data.storages

import android.content.Context
import android.content.SharedPreferences
import ru.bullyboo.domain.storages.PreferenceStorage
import javax.inject.Inject

class PreferenceStorageImpl @Inject constructor(
    context: Context
): PreferenceStorage {

    companion object {
        private const val PREFERENCE_NAME = "Preferences"

//        region start
//        Preferences Fields
        private const val IS_FIRST_LAUNCH_APP = "is_first_launch_app"

        private const val DEVICE_ID = "device_id"
//        region end
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    override fun setFirstLaunchApp(isFirstLaunchApp: Boolean) {
        prefs.edit()
            .putBoolean(IS_FIRST_LAUNCH_APP, isFirstLaunchApp)
            .apply()
    }

    override fun isFirstLaunchApp(): Boolean {
        return prefs.getBoolean(IS_FIRST_LAUNCH_APP, true)
    }

    override fun getDeviceId(): String {
        return prefs.getString(DEVICE_ID, "").orEmpty()
    }

    override fun saveDeviceId(deviceId: String) {
        prefs.edit().putString(DEVICE_ID, deviceId).apply()
    }
}