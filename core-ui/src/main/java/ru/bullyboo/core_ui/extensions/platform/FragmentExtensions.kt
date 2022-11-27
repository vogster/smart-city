package ru.bullyboo.core_ui.extensions.platform

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.provider.MediaStore
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

/**
 * Обычный запуск [Activity]
 * Данный метод добавляет [Activity] в конец стека [Activity]
 *
 * @param kClass - класс [Activity], которую нужно запустить
 * @param onBundle - лямбда выражение, через которое можно передать вызываемой [Activity] данные
 */
fun Fragment.start(
    kClass: KClass<out Activity>,
    onBundle: ((Intent) -> Unit)? = null
) {
    val intent = Intent(requireContext(), kClass.java)
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
fun Fragment.startAsRoot(
    kClass: KClass<out Activity>,
    onBundle: ((Intent) -> Unit)? = null
) {
    val intent = Intent(requireActivity(), kClass.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    onBundle?.invoke(intent)

    startActivity(intent)
    requireActivity().finish()
}

/**
 * Запуск [Activity] для получения результата
 *
 * @param requestCode - идентификатор запуска [Activity], по которому
 * будем получатть результат в [Activity.onActivityResult]
 * @param kClass - класс [Activity], которую нужно запустить
 * @param onBundle - лямбда выражение, через которое можно передать вызываемой [Activity] данные
 */
fun Fragment.startForResult(
    requestCode: Int,
    kClass: KClass<out Activity>,
    onBundle: ((Intent) -> Unit)? = null
) {
    val intent = Intent(requireContext(), kClass.java)
    onBundle?.invoke(intent)

    startActivityForResult(intent, requestCode)
}

/**
 * Переход на [Activity] из стека, и чистит все [Activity], которые находились в стеке выше
 *
 * @param kClass - класс [Activity], которую нужно запустить
 */
fun Fragment.startBackTo(kClass: KClass<out Activity>) {
    val intent = Intent(requireContext(), kClass.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    }
    startActivity(intent)
}

/**
 * Метод получения цвета из ресурсов
 *
 * @param colorId - идентификатор цвета в ресурасах
 */
fun Fragment.getCompatColor(@ColorRes colorId: Int) =
    ContextCompat.getColor(requireContext(), colorId)