package com.michaelkeskinidis.expensemanager.data.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.michaelkeskinidis.expensemanager.data.db.converters.BigDecimalConverter
import com.michaelkeskinidis.expensemanager.data.db.converters.LocaleConverter
import com.michaelkeskinidis.expensemanager.data.db.converters.OffsetDateTimeConverter
import com.michaelkeskinidis.expensemanager.data.db.daos.*
import com.michaelkeskinidis.expensemanager.data.db.entities.*
import com.michaelkeskinidis.expensemanager.data.db.entities.Transaction
import com.michaelkeskinidis.expensemanager.data.db.utilities.testAccounts
import com.michaelkeskinidis.expensemanager.data.db.utilities.testExpenseCategories
import com.michaelkeskinidis.expensemanager.data.db.utilities.testIncomeCategories
import com.michaelkeskinidis.expensemanager.data.db.utilities.testTransactions
import java.util.concurrent.Executors

@Database(
    entities = [
        Account::class,
        Transaction::class,
        ExpenseCategory::class,
        IncomeCategory::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(OffsetDateTimeConverter::class, BigDecimalConverter::class, LocaleConverter::class)
abstract class ExpenseManagerDatabase: RoomDatabase() {

    abstract fun accountDao(): AccountDao

    abstract fun transactionDao(): TransactionDao

    abstract fun expenseCategoryDao(): ExpenseCategoryDao

    abstract fun incomeCategoryDao(): IncomeCategoryDao

    companion object {
        @Volatile private var instance: ExpenseManagerDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context.applicationContext).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context)
                = Room.databaseBuilder(
                        context.applicationContext,
                        ExpenseManagerDatabase::class.java,
                        "expense_manager.db")
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                Executors.newSingleThreadExecutor().execute {
                                    instance?.also {
                                        testAccounts.forEach { account ->
                                            it.accountDao().upsert(account)
                                        }

                                        testExpenseCategories.forEach { expenseCategory ->
                                            it.expenseCategoryDao().upsert(expenseCategory)
                                        }

                                        testIncomeCategories.forEach { incomeCategory ->
                                            it.incomeCategoryDao().upsert(incomeCategory)
                                        }

                                        testTransactions.forEach { transaction ->
                                            it.transactionDao().upsert(transaction)
                                        }
                                    }
                                }
                            }
                        })
                        .build()
    }
}