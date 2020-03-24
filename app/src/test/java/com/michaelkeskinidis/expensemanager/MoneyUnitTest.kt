package com.michaelkeskinidis.expensemanager

import com.michaelkeskinidis.expensemanager.data.db.entities.Money
import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal
import java.util.*


class MoneyUnitTest {
    @Test
    fun isMinus_isCorrect() {
        assert(Money(BigDecimal("-10.12")).isMinus)
    }

    @Test
    fun isPlus_isCorrect() {
        assert(Money(BigDecimal("10.12")).isPlus)
    }

    @Test
    fun isZero_isCorrect() {
        assert(!Money(BigDecimal("10.12")).isZero)
        assert(Money(BigDecimal("0")).isZero)
        assert(Money(BigDecimal("0.0")).isZero)
        assert(Money(BigDecimal("-0")).isZero)
    }

    @Test
    fun addition_isCorrect() {
        assert(Money(BigDecimal("10.12")).plus(Money(BigDecimal("10.12"))).eq(Money(BigDecimal("20.24"))))
    }

    @Test
    fun times_isCorrect() {
        assert(Money(BigDecimal("10.12")).times(2).eq(Money(BigDecimal("20.24"))))
    }

    @Test
    fun div_isCorrect() {
        assert(Money(BigDecimal("10.12")).div(2).eq(Money(BigDecimal("5.06"))))
    }

    @Test(expected = Money.MismatchedCurrencyException::class)
    fun additionDifferentCurrenciesException() {
        Money(BigDecimal("10.12")).plus(Money(BigDecimal("10.12"), Currency.getInstance(Locale("en", "GB"))))
    }
}
