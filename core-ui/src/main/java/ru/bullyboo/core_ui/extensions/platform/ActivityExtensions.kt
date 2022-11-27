package ru.bullyboo.core_ui.extensions.platform

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import ru.bullyboo.core.utils.log
import kotlin.reflect.KClass

/**
 * Обычный запуск [Activity]
 * Данный метод добавляет [Activity] в конец стека [Activity]
 *
 * @param kClass - класс [Activity], которую нужно запустить
 * @param onBundle - лямбда выражение, через которое можно передать вызываемой [Activity] данные
 */
fun Activity.start(
    kClass: KClass<out Activity>,
    onBundle: ((Intent) -> Unit)? = null
) {
    val intent = Intent(this, kClass.java)
    onBundle?.invoke(intent)

    startActivity(intent)
}

/**
 * Запуск [Activity] как главного экране
 * Данный метод запускает [Activity] как одну единственную в процессе, то есть полностью
 * чистит весь стек [Activity]
 *
 * @param kClass - класс [Activity], которую нужно запустить
 * @param onBundle - лямбда выражение, через которое можно передать вызываемой [Activity] данные
 */
fun Activity.startAsRoot(
    kClass: KClass<out Activity>,
    onBundle: ((Intent) -> Unit)? = null
) {
    val intent = Intent(this, kClass.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    onBundle?.invoke(intent)

    startActivity(intent)
    finish()
}

/**
 * Метод получения цвета из ресурсов
 *
 * @param colorId - идентификатор цвета в ресурасах
 */
fun Activity.getCompatColor(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}