package com.michaelkeskinidis.expensemanager.data.repositories

import androidx.lifecycle.LiveData
import com.michaelkeskinidis.expensemanager.data.db.entities.Account
import com.michaelkeskinidis.expensemanager.data.db.entities.AccountTransaction

interface AccountRepository {
    suspend fun getAccounts(): LiveData<List<Account>>

    suspend fun saveAccount(name: String)

    suspend fun getAccountTransactions(): LiveData<List<AccountTransaction>>
}