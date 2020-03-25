package com.michaelkeskinidis.expensemanager.data.repositories

import androidx.lifecycle.LiveData
import com.michaelkeskinidis.expensemanager.data.db.daos.AccountDao
import com.michaelkeskinidis.expensemanager.data.db.entities.Account
import com.michaelkeskinidis.expensemanager.data.db.entities.AccountTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepositoryImpl(
    private val accountDao: AccountDao
): AccountRepository {

    override suspend fun getAccounts(): LiveData<List<Account>> {
        return withContext(Dispatchers.IO) {
            return@withContext accountDao.getAccounts()
        }
    }

    override suspend fun saveAccount(name: String) {
        withContext(Dispatchers.IO) {
            accountDao.upsert(Account(name))
        }
    }

    override suspend fun getAccountTransactions(): LiveData<List<AccountTransaction>> {
        return withContext(Dispatchers.IO) {
            return@withContext accountDao.getAccountTransactions()
        }
    }
}