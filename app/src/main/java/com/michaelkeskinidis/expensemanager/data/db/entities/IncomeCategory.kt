package com.michaelkeskinidis.expensemanager.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income_category_table")
data class IncomeCategory (
    @PrimaryKey
    val name: String
)