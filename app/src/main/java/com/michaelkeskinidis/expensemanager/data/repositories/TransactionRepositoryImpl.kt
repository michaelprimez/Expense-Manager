package com.michaelkeskinidis.expensemanager.data.repositories

import androidx.lifecycle.LiveData
import com.michaelkeskinidis.expensemanager.data.db.daos.TransactionDao
import com.michaelkeskinidis.expensemanager.data.db.entities.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionRepositoryImpl(
    private val tranactionDao: TransactionDao
) : TransactionRepository {

    override suspend fun saveTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            tranactionDao.upsert(transaction)
        }
    }

    override suspend fun getTransactions(): LiveData<List<Transaction>> {
        return withContext(Dispatchers.IO) {
            return@withContext tranactionDao.getTransactions()
        }
    }
}