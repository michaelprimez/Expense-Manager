package com.michaelkeskinidis.expensemanager.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelkeskinidis.expensemanager.R
import com.michaelkeskinidis.expensemanager.data.db.entities.AccountTransaction
import com.michaelkeskinidis.expensemanager.data.db.entities.Money
import com.michaelkeskinidis.expensemanager.databinding.ItemAccountTransactionBinding
import com.michaelkeskinidis.expensemanager.ui.adapters.RecycleViewStates.STATE_LOADING
import com.michaelkeskinidis.expensemanager.ui.adapters.RecycleViewStates.VIEW_TYPE_ITEM
import java.math.BigDecimal
import java.util.*


class AccountTransactionsAdapter(private val accountTransactionList: List<AccountTransaction>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @RecycleViewStates.State
    protected var state = STATE_LOADING

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when(viewType) {
            STATE_LOADING -> {
                return ViewHolderEmpty(layoutInflater.inflate(R.layout.view_loading, parent, false) as ConstraintLayout)
            }
            VIEW_TYPE_ITEM -> {
                return AccountTransactionViewHolder(ItemAccountTransactionBinding.inflate(layoutInflater, parent, false))
            } else -> return ViewHolderEmpty(layoutInflater.inflate(R.layout.view_empty, parent, false) as ConstraintLayout)
        }

    }

    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when(viewType) {
            VIEW_TYPE_ITEM -> {
                (holder as AccountTransactionViewHolder).bind(accountTransactionList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return if (accountTransactionList.isEmpty()) 1 else accountTransactionList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (accountTransactionList.isEmpty()) state else VIEW_TYPE_ITEM
    }

    class AccountTransactionViewHolder(private val binding: ItemAccountTransactionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(accountTransaction: AccountTransaction) {

            if (accountTransaction.transactions.isEmpty()) {
                return
            }
            val totalAmount = accountTransaction.transactions.map {
                    expense -> expense.amount
            }.reduce { init, expense ->
                init + expense
            }

            binding.accountTransaction = accountTransaction
            binding.money = Money(
                totalAmount,
                Currency.getInstance(accountTransaction.transactions[0].locale)
            )
            binding.transactionList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = TransactionAdapter(accountTransaction.transactions)
            }

            binding.executePendingBindings()
        }
    }

    class ViewHolderEmpty(val itemLoading: ConstraintLayout) : RecyclerView.ViewHolder(itemLoading)
}