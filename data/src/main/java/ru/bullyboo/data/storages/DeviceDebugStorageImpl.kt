package ru.bullyboo.data.storages

import ru.bullyboo.domain.storages.DeviceStorage
import ru.bullyboo.domain.storages.PreferenceStorage
import java.util.*
import javax.inject.Inject

class DeviceDebugStorageImpl @Inject constructor(
    private val preferenceStorage: PreferenceStorage
): DeviceStorage {

    override fun getDeviceId(): String {
        val savedDeviceId = preferenceStorage.getDeviceId()

        return if(savedDeviceId.isEmpty()){
            val newDeviceId = UUID.randomUUID().toString()
            preferenceStorage.saveDeviceId(newDeviceId)
            newDeviceId
        } else {
            savedDeviceId
        }
    }
}