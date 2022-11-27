package ru.bullyboo.application.factories

import android.widget.EditText
import io.reactivex.disposables.Disposable
import ru.bullyboo.core.utils.log
import ru.bullyboo.core_ui.utils.watchers.*

class WatcherFactory(
    private val actionAddDisposable: (Disposable) -> Unit
) {

    fun connect(editText: EditText): Connector {
        return Connector(editText)
    }

    private fun <M : Any> addDisposable(
        watcher: RxWatcher<M>,
        editText: EditText,
        onNext: ((M) -> Unit)? = null
    ) {
        val disposable = watcher.observe(editText)
            .subscribe({
                onNext?.invoke(it)
            }, {
                log(it)
            })

        actionAddDisposable(disposable)
    }

    inner class Connector(
        private val editText: EditText
    ) {

        fun asText(
            onNext: ((String) -> Unit)? = null
        ): RxTextWatcher {
            val watcher = RxTextWatcher()
            addDisposable(watcher, editText, onNext)
            return watcher
        }

        fun asDate(
            onNext: ((String) -> Unit)? = null
        ): RxDateWatcher {
            val watcher = RxDateWatcher()
            addDisposable(watcher, editText, onNext)
            return watcher
        }

        fun asMask(
            onNext: ((String) -> Unit)? = null
        ): RxMaskWatcher {
            val watcher = RxMaskWatcher()
            addDisposable(watcher, editText, onNext)
            return watcher
        }
    }
}