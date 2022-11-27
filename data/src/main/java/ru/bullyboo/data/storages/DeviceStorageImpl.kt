package ru.bullyboo.data.storages

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import ru.bullyboo.domain.storages.DeviceStorage
import javax.inject.Inject

class DeviceStorageImpl @Inject constructor(
    private val context: Context
): DeviceStorage {

    @SuppressLint("HardwareIds")
    override fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}