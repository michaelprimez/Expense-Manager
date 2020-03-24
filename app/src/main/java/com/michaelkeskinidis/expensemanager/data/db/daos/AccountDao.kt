package com.michaelkeskinidis.expensemanager.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.michaelkeskinidis.expensemanager.data.db.entities.Account
import com.michaelkeskinidis.expensemanager.data.db.entities.AccountTransaction

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(account: Account)

    @Insert
    fun insertAll(accounts: ArrayList<Account>)

    @Query("SELECT * FROM account_table WHERE name = :name")
    fun getAccountByName(name: String): LiveData<Account>

    @Query("SELECT * FROM account_table")
    fun getAccounts(): LiveData<List<Account>>

    @Transaction
    @Query("SELECT * FROM account_table WHERE name = :name")
    fun getAccountTransactionByName(name: String): LiveData<List<AccountTransaction>>

    @Transaction
    @Query("SELECT * FROM account_table")
    fun getAccountTransactions(): LiveData<List<AccountTransaction>>
}