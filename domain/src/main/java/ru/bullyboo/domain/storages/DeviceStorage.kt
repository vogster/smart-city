package ru.bullyboo.domain.storages

interface DeviceStorage {

    /**
     * Метод получения уникального device Id
     */
    fun getDeviceId(): String
}