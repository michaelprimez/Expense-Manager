package com.michaelkeskinidis.expensemanager.data.db.entities

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import com.michaelkeskinidis.expensemanager.BR
import java.io.Serializable
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class Money @JvmOverloads constructor(
    private val amount: BigDecimal,
    private val currency: Currency = DEFAULT_CURRENCY,
    private val roundingStyle: RoundingMode = DEFAULT_ROUNDING
): Comparable<Money>, Serializable {

    class MismatchedCurrencyException internal constructor(message: String?) :
        RuntimeException(message)

    fun isSameCurrencyAs(that: Money): Boolean {
        return currency == that.currency
    }

    val isPlus: Boolean
        get() = amount > BigDecimal.ZERO

    val isMinus: Boolean
        get() = amount < BigDecimal.ZERO

    val isZero: Boolean
        get() = amount.compareTo(BigDecimal.ZERO) == 0

    operator fun plus(that: Money): Money {
        checkCurrenciesMatch(that)
        return Money(amount.add(that.amount), currency, roundingStyle)
    }

    operator fun minus(that: Money): Money {
        checkCurrenciesMatch(that)
        return Money(amount.subtract(that.amount), currency, roundingStyle)
    }

    fun eq(that: Money): Boolean {
        checkCurrenciesMatch(that)
        return compareAmount(that) == 0
    }

    fun gt(that: Money): Boolean {
        checkCurrenciesMatch(that)
        return compareAmount(that) > 0
    }

    fun gteq(that: Money): Boolean {
        checkCurrenciesMatch(that)
        return compareAmount(that) >= 0
    }

    fun lt(that: Money): Boolean {
        checkCurrenciesMatch(that)
        return compareAmount(that) < 0
    }

    fun lteq(that: Money): Boolean {
        checkCurrenciesMatch(that)
        return compareAmount(that) <= 0
    }

    operator fun times(aFactor: Int): Money {
        val factor = BigDecimal(aFactor)
        val newAmount = amount.multiply(factor)
        return Money(newAmount, currency, roundingStyle)
    }

    operator fun times(factor: Double): Money {
        var newAmount = amount.multiply(asBigDecimal(factor))
        newAmount = newAmount.setScale(numDecimalsForCurrency, roundingStyle)
        return Money(newAmount, currency, roundingStyle)
    }

    operator fun div(aDivisor: Int): Money {
        val divisor = BigDecimal(aDivisor)
        val newAmount = amount.divide(divisor, roundingStyle)
        return Money(newAmount, currency, roundingStyle)
    }

    operator fun div(divisor: Double): Money {
        val newAmount = amount.divide(asBigDecimal(divisor), roundingStyle)
        return Money(newAmount, currency, roundingStyle)
    }

    fun abs(): Money {
        return if (isPlus) this else times(-1)
    }

    fun negate(): Money {
        return times(-1)
    }

    override fun toString(): String {
        return amount.toPlainString() + " " + currency.symbol
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Money) return false
        for (i in sigFields.indices) {
            if (sigFields[i] != other.sigFields[i]) {
                return false
            }
        }
        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(*sigFields)
    }

    override operator fun compareTo(other: Money): Int {
        val EQUAL = 0
        if (this === other) return EQUAL

        //the object fields are never null
        var comparison = amount.compareTo(other.amount)
        if (comparison != EQUAL) return comparison
        comparison = currency.currencyCode.compareTo(
            other.currency.currencyCode
        )
        if (comparison != EQUAL) return comparison
        comparison = roundingStyle.compareTo(other.roundingStyle)
        return if (comparison != EQUAL) comparison else EQUAL
    }

    private val sigFields: Array<Any?>
        get() = arrayOf(amount, currency, roundingStyle)

    private fun validateState() {
        require(amount.scale() <= numDecimalsForCurrency) {
            "Number of decimals is " + amount.scale() + ", but currency only takes " +
                    numDecimalsForCurrency + " decimals."
        }
    }

    private val numDecimalsForCurrency: Int
        get() = currency.defaultFractionDigits

    private fun checkCurrenciesMatch(aThat: Money) {
        if (currency != aThat.currency) {
            throw MismatchedCurrencyException(
                aThat.currency
                    .toString() + " doesn't match the expected currency : " + currency
            )
        }
    }

    private fun compareAmount(aThat: Money): Int {
        return amount.compareTo(aThat.amount)
    }

    private fun asBigDecimal(aDouble: Double): BigDecimal {
        val asString = java.lang.Double.toString(aDouble)
        return BigDecimal(asString)
    }

    var bigDesicmal = BigDecimal.ZERO
        get() = amount

    companion object {
        fun init(
            defaultCurrency: Currency,
            defaultRounding: RoundingMode
        ) {
            DEFAULT_CURRENCY = defaultCurrency
            DEFAULT_ROUNDING = defaultRounding
        }

        fun sum(
            moneys: Collection<Money>,
            currencyIfEmpty: Currency
        ): Money {
            var sum = Money(BigDecimal.ZERO, currencyIfEmpty)
            for (money in moneys) {
                sum = sum.plus(money)
            }
            return sum
        }

        private var DEFAULT_CURRENCY: Currency = Currency.getInstance(Locale.US)

        private var DEFAULT_ROUNDING: RoundingMode = RoundingMode.HALF_EVEN

        private const val serialVersionUID = 7526471155622776147L
    }

    init {
        validateState()
    }
}