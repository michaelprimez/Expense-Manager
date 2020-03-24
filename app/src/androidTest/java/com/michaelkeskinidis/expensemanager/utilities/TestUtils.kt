package com.michaelkeskinidis.expensemanager.utilities

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

val testAccount = testAccounts[2]

val testExpenseCategories = arrayListOf(
    ExpenseCategory("Tax"),
    ExpenseCategory("Grocery"),
    ExpenseCategory("Entertainment"),
    ExpenseCategory("Gym"),
    ExpenseCategory("Health")
)

val testExpenseCategory = testExpenseCategories[0]

val testIncomeCategories = arrayListOf(
    IncomeCategory("Salary"),
    IncomeCategory("Dividends")
)

val testIncomeCategory = testIncomeCategories[0]

val testTransactions = arrayListOf(
    Transaction(BigDecimal("-100"), Locale.US, OffsetDateTime.now(), testExpenseCategories[0].name, testAccounts[2].name),
    Transaction(BigDecimal("-200"), Locale.US, OffsetDateTime.now(), testExpenseCategories[1].name, testAccounts[2].name),
    Transaction(BigDecimal("-300"), Locale.US, OffsetDateTime.now(), testExpenseCategories[2].name, testAccounts[1].name),
    Transaction(BigDecimal("-50"), Locale.US, OffsetDateTime.now(), testExpenseCategories[3].name, testAccounts[1].name),
    Transaction(BigDecimal("-800"), Locale.US, OffsetDateTime.now(), testExpenseCategories[4].name, testAccounts[2].name),
    Transaction(BigDecimal("900"), Locale.US, OffsetDateTime.now(), testIncomeCategories[1].name, testAccounts[2].name),
    Transaction(BigDecimal("50"), Locale.US, OffsetDateTime.now(), testIncomeCategories[0].name, testAccounts[2].name),
    Transaction(BigDecimal("100"), Locale.US, OffsetDateTime.now(), testIncomeCategories[1].name, testAccounts[0].name),
    Transaction(BigDecimal("150"), Locale.US, OffsetDateTime.now(), testIncomeCategories[1].name, testAccounts[1].name)
)

val testTransaction = testTransactions[0]