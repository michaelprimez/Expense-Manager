package com.michaelkeskinidis.expensemanager.viewmodel

import androidx.lifecycle.ViewModel
import com.michaelkeskinidis.expensemanager.data.db.entities.Transaction
import com.michaelkeskinidis.expensemanager.data.repositories.AccountRepository
import com.michaelkeskinidis.expensemanager.data.repositories.TransactionRepository
import com.michaelkeskinidis.expensemanager.internal.lazyDeferred
import kotlinx.coroutines.runBlocking

class AccountViewModel(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
): ViewModel() {

    suspend fun insertAccount(account: String) = runBlocking { accountRepository.saveAccount(account) }

    suspend fun insertTransaction(transaction: Transaction) = runBlocking { transactionRepository.saveTransaction(transaction) }

    val accounts by lazyDeferred { accountRepository.getAccounts() }

    val transactions by lazyDeferred { transactionRepository.getTransactions() }

    val accountTransactions by lazyDeferred {
        accountRepository.getAccountTransactions()
    }
}