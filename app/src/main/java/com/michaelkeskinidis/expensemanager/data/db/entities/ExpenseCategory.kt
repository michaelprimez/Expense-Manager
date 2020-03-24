package com.michaelkeskinidis.expensemanager.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_category_table")
data class ExpenseCategory (
    @PrimaryKey
    val name: String
)