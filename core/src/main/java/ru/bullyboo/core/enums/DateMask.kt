package ru.bullyboo.core.enums

enum class DateMask (val source: String){

    YYYY_MM_DD_T_HH_MM_SS_TIMEZONE ("yyyy-MM-dd'T'HH:mm:ssX"),
    DD_MM_YYYY ("dd.MM.yyyy"),
    HH_MM ("HH:mm"),
    DD_MMMM_COMMA_YYYY("d MMMM, yyyy"),
    YYYY_MM_DD_T_HH_MM_SS ("yyyy-MM-dd'T'HH:mm:ss")
}