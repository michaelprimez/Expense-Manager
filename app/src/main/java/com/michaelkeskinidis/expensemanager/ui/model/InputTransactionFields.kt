package com.michaelkeskinidis.expensemanager.ui.model

import java.math.BigDecimal

class InputTransactionFields(
    var isIncome: Boolean = false,
    var account: String = "",
    var category: String = "",
    var amount: String = BigDecimal.ZERO.toString()
)