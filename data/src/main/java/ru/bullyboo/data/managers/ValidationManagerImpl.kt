package ru.bullyboo.data.managers

import android.util.Patterns
import ru.bullyboo.domain.enums.ValidationStatus
import ru.bullyboo.domain.managers.ValidationManager
import javax.inject.Inject

class ValidationManagerImpl @Inject constructor(

) : ValidationManager {

    override fun validateEmail(email: String): ValidationStatus {
        return if (email.isEmpty()) {
            ValidationStatus.EMPTY
        } else {
            val matcher = Patterns.EMAIL_ADDRESS.matcher(email)

            if (matcher.find()) {
                ValidationStatus.VALID
            } else {
                ValidationStatus.INVALID
            }
        }
    }
}