package ru.bullyboo.application.ui.base.activities

import android.content.Intent
import android.widget.Toast
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpAppCompatActivity
import ru.bullyboo.application.ui.base.BaseView
import ru.bullyboo.application.ui.general.LoadingDialog
import ru.bullyboo.core_ui.extensions.platform.show
import ru.bullyboo.core_ui.managers.BackStackManager

/**
 * Базовый класс Activity, реализующий основные функции для работы экрана
 *
 * Данный класс наследуется от MvpAppCompatActivity, чем реализует автоматическое
 * соединение Model View Presenter
 */
abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    companion object {

        private const val DEFAULT_AUTHORIZATION_TIMEOUT = 60 * 1000L
    }

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
     * Флаг, свидетельствующий, что приложение вернулось на передний план, потому что другой
     * экран или приложение вернуло результат текущему приложению, а не просто было свернуто,
     * а затем развернуто
     */
    private var isResumeForGettingResult: Boolean = false

    override fun onResume() {
        super.onResume()

        isResumeForGettingResult = false
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onBackPressed() {
        BackStackManager.onBackPressed(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        isResumeForGettingResult = true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun showToast(stringId: Int) {
        showToast(getString(stringId))
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun showLoadingDialog() {
        if (!isShowingLoading) {
            isShowingLoading = true
            loadingDialog.show(supportFragmentManager)
        }
    }

    override fun hideLoadingDialog() {
        if (isShowingLoading) {
            isShowingLoading = false
            loadingDialog.dismiss()
        }
    }

    /**
     * Метод, позволяющий привязать Disposable к жизненному циклу Activity
     * Таким образом происходит автоматическая отписка всех подписок Activity,
     * при разрушении Activity
     */
    fun Disposable.disposeOnActivityDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }
}