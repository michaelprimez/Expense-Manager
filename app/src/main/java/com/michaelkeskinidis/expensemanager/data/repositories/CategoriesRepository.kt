package com.michaelkeskinidis.expensemanager.data.repositories

import androidx.lifecycle.LiveData
import com.michaelkeskinidis.expensemanager.data.db.entities.*

interface CategoriesRepository {

    suspend fun saveIncomeCategory(incomeCategory: IncomeCategory)

    suspend fun saveExpenceCategory(expenseCategory: ExpenseCategory)

    suspend fun getIncomeCategories(): LiveData<List<IncomeCategory>>

    suspend fun getExpenseCategories(): LiveData<List<ExpenseCategory>>
}