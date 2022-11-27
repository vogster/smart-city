package ru.bullyboo.domain.enums

enum class ValidationStatus {
    EMPTY,
    INVALID,
    VALID
}

fun ValidationStatus.isEmpty(): Boolean =
    this == ValidationStatus.EMPTY

fun ValidationStatus.isInvalid(): Boolean =
    this == ValidationStatus.INVALID

fun ValidationStatus.isValid(): Boolean =
    this == ValidationStatus.VALID