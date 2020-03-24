package com.michaelkeskinidis.expensemanager.data.db.utilities

import com.michaelkeskinidis.expensemanager.data.db.entities.*
import org.threeten.bp.OffsetDateTime
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

val testAccounts = arrayListOf(
    Account("Cash"),
    Account("Credit Card"),
    Account("Bank account")
)

val testExpenseCategories = arrayListOf(
    ExpenseCategory("Tax"),
    ExpenseCategory("Grocery"),
    ExpenseCategory("Entertainment"),
    ExpenseCategory("Gym"),
    ExpenseCategory("Health")
)

val testIncomeCategories = arrayListOf(
    IncomeCategory("Salary"),
    IncomeCategory("Dividends")
)

val testTransactions = arrayListOf(
    Transaction(BigDecimal("-100"), Locale.US, OffsetDateTime.now(), testExpenseCategories[0].name, testAccounts[2].name),
    Transaction(BigDecimal("-200"), Locale.US, OffsetDateTime.now().minusDays(3).minusHours(1), testExpenseCategories[1].name, testAccounts[0].name),
    Transaction(BigDecimal("-300"), Locale.US, OffsetDateTime.now().minusDays(1).minusMinutes(10), testExpenseCategories[2].name, testAccounts[1].name),
    Transaction(BigDecimal("-50"), Locale.US, OffsetDateTime.now().minusDays(12).minusHours(12), testExpenseCategories[3].name, testAccounts[1].name),
    Transaction(BigDecimal("-800"), Locale.US, OffsetDateTime.now().minusDays(6).minusHours(3), testExpenseCategories[4].name, testAccounts[2].name),
    Transaction(BigDecimal("-900"), Locale.US, OffsetDateTime.now().minusDays(8).minusHours(4), testExpenseCategories[0].name, testAccounts[2].name),
    Transaction(BigDecimal("50"), Locale.US, OffsetDateTime.now().minusDays(10).minusHours(5), testIncomeCategories[0].name, testAccounts[2].name),
    Transaction(BigDecimal("100"), Locale.US, OffsetDateTime.now().minusDays(15).minusHours(9), testIncomeCategories[1].name, testAccounts[0].name),
    Transaction(BigDecimal("150"), Locale.US, OffsetDateTime.now().minusDays(4).minusHours(2), testIncomeCategories[1].name, testAccounts[1].name)
)