package com.michaelkeskinidis.expensemanager.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michaelkeskinidis.expensemanager.data.repositories.AccountRepository
import com.michaelkeskinidis.expensemanager.data.repositories.CategoriesRepository
import com.michaelkeskinidis.expensemanager.data.repositories.TransactionRepository
import com.michaelkeskinidis.expensemanager.viewmodel.AccountViewModel
import com.michaelkeskinidis.expensemanager.viewmodel.CategoriesViewModel

class CategoriesViewModelFactory(
    private val categoriesRepository: CategoriesRepository
):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoriesViewModel(categoriesRepository) as T
    }
}