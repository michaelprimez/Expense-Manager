package com.michaelkeskinidis.expensemanager.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.michaelkeskinidis.expensemanager.data.db.entities.Transaction

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(transaction: Transaction): Long

    @Query("SELECT * FROM transaction_table ORDER BY date DESC")
    fun getTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transaction_table WHERE id = :id")
    fun getTransactionById(id: Long): LiveData<Transaction>
}