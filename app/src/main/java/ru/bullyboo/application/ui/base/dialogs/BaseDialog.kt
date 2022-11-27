package ru.bullyboo.application.ui.base.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpAppCompatDialogFragment
import ru.bullyboo.application.factories.WatcherFactory
import ru.bullyboo.application.ui.base.BaseView
import ru.bullyboo.application.ui.general.LoadingDialog
import ru.bullyboo.core_ui.binding.InflateType
import ru.bullyboo.core_ui.extensions.platform.show

/**
 * Базовый класс Dialog, реализующий основные функции для работы экрана
 *
 * Данный класс наследуется от MvpAppCompatDialogFragment, чем реализует автоматическое
 * соединение Model View Presenter
 */
abstract class BaseDialog<B : ViewBinding>(
    private val inflate: InflateType<B>
) : MvpAppCompatDialogFragment(), BaseView {

    private var _binding: B? = null
    val binding get() = _binding!!

    /**
     * Диалог, отображающий загрузку
     */
    private var loadingDialog = LoadingDialog.newInstance()

    /**
     * Флаг, по которому можно ориентироваться, показывается на текущий момент диалог
     * загрузки, или нет
     */
    private var isShowingLoading = false

    private val compositeDisposable by lazy { CompositeDisposable() }

    /**
     * Фабрика для создания и регистрации TextWatcher у EditText
     */
    private val watcherFactory by lazy {
        WatcherFactory { it.disposeOnDialogDestroy() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        compositeDisposable.clear()
        _binding = null
    }

    override fun showToast(stringId: Int) {
        showToast(getString(stringId))
    }

    override fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    override fun showLoadingDialog() {
        if (!isShowingLoading) {
            isShowingLoading = true
            loadingDialog.show(childFragmentManager)
        }
    }

    override fun hideLoadingDialog() {
        if (isShowingLoading && loadingDialog.isAdded) {
            isShowingLoading = false
            loadingDialog.dismiss()
        }
    }

    inline fun <reified M> attachListener(): M {
        return parentFragment as? M ?: requireActivity() as M
    }

    /**
     * Метод, позволяющий привязать Disposable к жизненному циклу Dialog
     * Таким образом происходит автоматическая отписка всех подписок Dialog,
     * при разрушении Dialog
     */
    fun Disposable.disposeOnDialogDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }

    /**
     * Метод, позволяющий сделать соединение между EditText и Fragment, привязывает
     * TextWatcher к жизненному циклу фрагмента
     */
    fun EditText.connect(): WatcherFactory.Connector {
        return watcherFactory.connect(this)
    }
}