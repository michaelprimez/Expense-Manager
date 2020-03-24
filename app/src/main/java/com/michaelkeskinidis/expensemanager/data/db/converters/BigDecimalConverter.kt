package com.michaelkeskinidis.expensemanager.data.db.converters

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.math.BigInteger

object BigDecimalConverter {
    @TypeConverter
    @JvmStatic
    fun toBigDecimal(value: String?): BigDecimal? {
        return value?.let { moneyAsString ->
            return moneyAsString.toBigDecimal()
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromBigDecimal(date: BigDecimal?): String? {
        return date?.toString()
    }

}