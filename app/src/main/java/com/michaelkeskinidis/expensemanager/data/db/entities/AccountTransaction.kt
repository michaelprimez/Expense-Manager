package com.michaelkeskinidis.expensemanager.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation

/**
 * This class captures the relationship between a [Account] and a user's [Expense]
 */
data class AccountTransaction (
    @Embedded
    val account: Account,
    @Relation(
        parentColumn = "name",
        entityColumn = "account_name"
    )
    val transactions: List<Transaction>
)