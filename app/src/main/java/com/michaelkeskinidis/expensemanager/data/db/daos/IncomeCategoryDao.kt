package com.michaelkeskinidis.expensemanager.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.michaelkeskinidis.expensemanager.data.db.entities.IncomeCategory

@Dao
interface IncomeCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(incomeCategory: IncomeCategory)

    @Query("SELECT * FROM income_category_table WHERE name = :name")
    fun getIncomeCategoryById(name: String): LiveData<IncomeCategory>

    @Query("SELECT * FROM income_category_table")
    fun getIncomeCategories(): LiveData<List<IncomeCategory>>
}