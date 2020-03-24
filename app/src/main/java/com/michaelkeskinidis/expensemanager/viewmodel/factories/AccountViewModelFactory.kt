package com.michaelkeskinidis.expensemanager.viewmodel.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michaelkeskinidis.expensemanager.data.repositories.AccountRepository
import com.michaelkeskinidis.expensemanager.data.repositories.TransactionRepository
import com.michaelkeskinidis.expensemanager.viewmodel.AccountViewModel

class AccountViewModelFactory(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AccountViewModel(accountRepository, transactionRepository) as T
    }
}