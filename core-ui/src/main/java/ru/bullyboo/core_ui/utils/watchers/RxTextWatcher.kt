package ru.bullyboo.core_ui.utils.watchers

open class RxTextWatcher : RxWatcher<String>() {

    override fun convert(text: String): String {
        return text
    }
}