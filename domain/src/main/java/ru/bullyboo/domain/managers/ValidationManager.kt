package ru.bullyboo.domain.managers

import ru.bullyboo.domain.enums.ValidationStatus

interface ValidationManager {

    /**
     * Валидации email адреса
     */
    fun validateEmail(email: String): ValidationStatus
}
