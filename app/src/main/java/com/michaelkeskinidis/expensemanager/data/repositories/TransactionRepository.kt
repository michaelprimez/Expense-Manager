package com.michaelkeskinidis.expensemanager.data.repositories

import androidx.lifecycle.LiveData
import com.michaelkeskinidis.expensemanager.data.db.entities.Account
import com.michaelkeskinidis.expensemanager.data.db.entities.AccountTransaction
import com.michaelkeskinidis.expensemanager.data.db.entities.Transaction

interface TransactionRepository {

    suspend fun saveTransaction(transaction: Transaction)

    suspend fun getTransactions(): LiveData<List<Transaction>>
}