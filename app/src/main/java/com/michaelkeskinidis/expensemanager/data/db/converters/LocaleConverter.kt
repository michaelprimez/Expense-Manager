package com.michaelkeskinidis.expensemanager.data.db.converters

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

object LocaleConverter {
    @TypeConverter
    @JvmStatic
    fun toLocale(value: String?): Locale? {
        return value?.let { localeText ->
            val languageCountryStrings = localeText.split("-")
            return Locale(languageCountryStrings[0], languageCountryStrings[1])
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromLocale(locale: Locale?): String? {
        return "${locale?.language}-${locale?.country}"
    }
}