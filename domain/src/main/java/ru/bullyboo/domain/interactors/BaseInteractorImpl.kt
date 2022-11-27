package ru.bullyboo.domain.interactors

import io.reactivex.Completable

open class BaseInteractorImpl(

) : BaseInteractor {

//    TODO добавить проверку наличия токенов
    override fun hasTokens(): Boolean {
        return false
    }

//    TODO добавить обновление токена
    override fun refreshAccessToken(): Completable {
        return Completable.complete()
    }
}