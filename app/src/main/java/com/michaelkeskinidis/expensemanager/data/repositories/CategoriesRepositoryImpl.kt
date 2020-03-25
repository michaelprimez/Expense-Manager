package com.michaelkeskinidis.expensemanager.data.repositories

import androidx.lifecycle.LiveData
import com.michaelkeskinidis.expensemanager.data.db.daos.ExpenseCategoryDao
import com.michaelkeskinidis.expensemanager.data.db.daos.IncomeCategoryDao
import com.michaelkeskinidis.expensemanager.data.db.entities.ExpenseCategory
import com.michaelkeskinidis.expensemanager.data.db.entities.IncomeCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoriesRepositoryImpl(
    private val expenseCategoryDao: ExpenseCategoryDao,
    private val incomeCategoryDao: IncomeCategoryDao
) : CategoriesRepository {

    override suspend fun saveIncomeCategory(incomeCategory: IncomeCategory) {
        withContext(Dispatchers.IO) {
            incomeCategoryDao.upsert(incomeCategory)
        }
    }

    override suspend fun saveExpenceCategory(expenseCategory: ExpenseCategory) {
        withContext(Dispatchers.IO) {
            expenseCategoryDao.upsert(expenseCategory)
        }
    }

    override suspend fun getIncomeCategories(): LiveData<List<IncomeCategory>> {
        return withContext(Dispatchers.IO) {
            return@withContext incomeCategoryDao.getIncomeCategories()
        }
    }

    override suspend fun getExpenseCategories(): LiveData<List<ExpenseCategory>> {
        return withContext(Dispatchers.IO) {
            return@withContext expenseCategoryDao.getExpenseCategories()
        }
    }
}