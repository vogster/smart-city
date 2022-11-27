package ru.bullyboo.domain.storages

interface PreferenceStorage {

    /**
     * Сохранение флага первого запуска приложения
     */
    fun setFirstLaunchApp(isFirstLaunchApp: Boolean)

    /**
     * Флаг, является ли запуск приложения первым
     */
    fun isFirstLaunchApp(): Boolean

    /**
     * Получение deviceId устройства
     * Используется для фейковых deviceId в debug сборках
     */
    fun getDeviceId(): String

    /**
     * Сохранение deviceId устройства
     * Используется для фейковых deviceId в debug сборках
     */
    fun saveDeviceId(deviceId: String)
}