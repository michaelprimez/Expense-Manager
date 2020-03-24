package com.michaelkeskinidis.expensemanager.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.michaelkeskinidis.expensemanager.data.db.entities.ExpenseCategory

@Dao
interface ExpenseCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(expenseCategory: ExpenseCategory)

    @Query("SELECT * FROM expense_category_table WHERE name = :name")
    fun getExpenseCategoryByName(name: String): LiveData<ExpenseCategory>

    @Query("SELECT * FROM expense_category_table")
    fun getExpenseCategories(): LiveData<List<ExpenseCategory>>
}