package com.michaelkeskinidis.expensemanager.viewmodel

import androidx.lifecycle.ViewModel
import com.michaelkeskinidis.expensemanager.data.db.entities.ExpenseCategory
import com.michaelkeskinidis.expensemanager.data.db.entities.IncomeCategory
import com.michaelkeskinidis.expensemanager.data.repositories.CategoriesRepository
import com.michaelkeskinidis.expensemanager.internal.lazyDeferred
import kotlinx.coroutines.runBlocking


class CategoriesViewModel(
    private val categoriesRepository: CategoriesRepository
): ViewModel() {

    suspend fun insertExpenseCategory(expenseCategory: ExpenseCategory) = runBlocking { categoriesRepository.saveExpenceCategory(expenseCategory) }

    suspend fun insertIncomeCategory(incomeCategory: IncomeCategory) = runBlocking { categoriesRepository.saveIncomeCategory(incomeCategory) }

    val expenseCategories by lazyDeferred { categoriesRepository.getExpenseCategories() }

    val incomeCategories by lazyDeferred { categoriesRepository.getIncomeCategories() }

    val expenseCategoriesList: suspend () -> List<String>? = suspend { expenseCategories.await().value?.map { it.name }?.toList() }
}