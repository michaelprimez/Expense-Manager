package com.michaelkeskinidis.expensemanager.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.michaelkeskinidis.expensemanager.data.db.ExpenseManagerDatabase
import com.michaelkeskinidis.expensemanager.data.db.daos.AccountDao
import com.michaelkeskinidis.expensemanager.data.db.daos.ExpenseCategoryDao
import com.michaelkeskinidis.expensemanager.data.db.daos.IncomeCategoryDao
import com.michaelkeskinidis.expensemanager.data.db.daos.TransactionDao
import com.michaelkeskinidis.expensemanager.utilities.*
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class DatabaseReadWriteTest {
    private lateinit var db: ExpenseManagerDatabase

    private lateinit var accountDao: AccountDao
    private lateinit var expenseCategoryDao: ExpenseCategoryDao
    private lateinit var transactionDao: TransactionDao
    private lateinit var incomeCategoryDao: IncomeCategoryDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ExpenseManagerDatabase::class.java).build()
        accountDao = db.accountDao()
        expenseCategoryDao = db.expenseCategoryDao()
        transactionDao = db.transactionDao()
        incomeCategoryDao = db.incomeCategoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun shouldWriteAndReadDataFromDatabase() {
        testAccounts.forEach { account ->
            accountDao.upsert(account)
        }

        testExpenseCategories.forEach { expenseCategory ->
            expenseCategoryDao.upsert(expenseCategory)
        }

        testTransactions.forEach { transaction ->
            transactionDao.upsert(transaction)
        }

        testIncomeCategories.forEach { incomeCategory ->
            incomeCategoryDao.upsert(incomeCategory)
        }

        runBlocking {
            val accounts = suspend { accountDao.getAccounts() }.invoke()
            assertNotNull(accounts)
            accounts.value?.isNotEmpty()?.let { assert(it) }

            val readAccountTransactions = suspend { accountDao.getAccountTransactions() }.invoke()
            assertNotNull(readAccountTransactions)
            readAccountTransactions.value?.isNotEmpty()?.let { assert(it) }

            val readTransactions = suspend { transactionDao.getTransactions() }.invoke()
            assertNotNull(readTransactions)
            readTransactions.value?.isNotEmpty()?.let { assert(it) }
        }
    }

    @Test
    @Throws(Exception::class)
    fun shouldCalculateTheCorrectAmountForAnAccount() {

        testAccounts.forEach { account ->
            accountDao.upsert(account)
        }

        testExpenseCategories.forEach { expenseCategory ->
            expenseCategoryDao.upsert(expenseCategory)
        }

        testTransactions.forEach { transaction ->
            transactionDao.upsert(transaction)
        }

        testIncomeCategories.forEach { incomeCategory ->
            incomeCategoryDao.upsert(incomeCategory)
        }

        runBlocking {
            val readAccountTransactions = suspend { accountDao.getAccountTransactions() }

            val accountTransactions = readAccountTransactions.invoke()

            assertNotNull(accountTransactions)

            accountTransactions.value?.isNotEmpty()?.let { assert(it) }

            val totalAmount = accountTransactions.value?.get(0)?.transactions?.map {
                    expense -> expense.amount
            }?.reduce { init, expense ->
                init + expense
            } ?: BigDecimal.ZERO

            assert(totalAmount.toInt() == 250)
        }
    }
}