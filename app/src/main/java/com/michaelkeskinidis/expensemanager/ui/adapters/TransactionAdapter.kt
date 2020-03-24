package com.michaelkeskinidis.expensemanager.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.michaelkeskinidis.expensemanager.R
import com.michaelkeskinidis.expensemanager.data.db.entities.Money
import com.michaelkeskinidis.expensemanager.data.db.entities.Transaction
import com.michaelkeskinidis.expensemanager.databinding.ItemTransactionBinding
import com.michaelkeskinidis.expensemanager.ui.adapters.RecycleViewStates.STATE_LOADING
import com.michaelkeskinidis.expensemanager.ui.adapters.RecycleViewStates.VIEW_TYPE_ITEM
import java.util.*


class TransactionAdapter(val transactionList: List<Transaction>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                return TransactionViewHolder(ItemTransactionBinding.inflate(layoutInflater, parent, false))
            } else -> return ViewHolderEmpty(layoutInflater.inflate(R.layout.view_empty, parent, false) as ConstraintLayout)
        }

    }

    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when(viewType) {
            VIEW_TYPE_ITEM -> {
                (holder as TransactionViewHolder).bind(transactionList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return if (transactionList.isEmpty()) 1 else transactionList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (transactionList.isEmpty()) state else VIEW_TYPE_ITEM
    }

    class TransactionViewHolder(private val binding: ItemTransactionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.transaction = transaction
            binding.money = Money(transaction.amount, Currency.getInstance(transaction.locale))
            binding.executePendingBindings()
        }
    }

    class ViewHolderEmpty(val itemLoading: ConstraintLayout) : RecyclerView.ViewHolder(itemLoading)
}