package com.michaelkeskinidis.expensemanager.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigInteger

@Entity(tableName = "account_table")
data class Account (
    @PrimaryKey
    val name: String
)