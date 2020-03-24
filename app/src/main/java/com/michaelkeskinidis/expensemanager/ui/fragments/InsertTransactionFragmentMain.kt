package com.michaelkeskinidis.expensemanager.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.michaelkeskinidis.expensemanager.data.db.entities.ExpenseCategory
import com.michaelkeskinidis.expensemanager.data.db.entities.IncomeCategory
import com.michaelkeskinidis.expensemanager.data.db.entities.Money
import com.michaelkeskinidis.expensemanager.data.db.entities.Transaction
import com.michaelkeskinidis.expensemanager.databinding.FragmentInsertTransactionsBinding
import com.michaelkeskinidis.expensemanager.ui.base.IOScopedFragment
import com.michaelkeskinidis.expensemanager.ui.base.MainScopedFragment
import com.michaelkeskinidis.expensemanager.ui.model.InputTransactionFields
import com.michaelkeskinidis.expensemanager.utilities.getCurrentLocale
import com.michaelkeskinidis.expensemanager.viewmodel.AccountViewModel
import com.michaelkeskinidis.expensemanager.viewmodel.CategoriesViewModel
import com.michaelkeskinidis.expensemanager.viewmodel.InputTransactionViewModel
import com.michaelkeskinidis.expensemanager.viewmodel.factories.AccountViewModelFactory
import com.michaelkeskinidis.expensemanager.viewmodel.factories.CategoriesViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.OffsetDateTime
import java.math.BigDecimal
import java.util.*

class InsertTransactionFragmentMain : IOScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val accountViewModelFactory: AccountViewModelFactory by instance()
    private val categoriesViewModelFactory: CategoriesViewModelFactory by instance()

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var inputTransactionViewModel: InputTransactionViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        inputTransactionViewModel = ViewModelProvider(this).get(InputTransactionViewModel::class.java)
        val binding: FragmentInsertTransactionsBinding = FragmentInsertTransactionsBinding.inflate(inflater, container, false)
        binding.inputTransactionViewModel = inputTransactionViewModel
        return binding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        accountViewModel = ViewModelProvider(this@InsertTransactionFragmentMain.activity!!, accountViewModelFactory).get(AccountViewModel::class.java)
        categoriesViewModel = ViewModelProvider(this@InsertTransactionFragmentMain.activity!!, categoriesViewModelFactory).get(CategoriesViewModel::class.java)
        inputTransactionViewModel.form.buttonClick.observe(viewLifecycleOwner, Observer { inputTransactionFields ->
            val currentLocale = getCurrentLocale(this@InsertTransactionFragmentMain.activity!!)
            runSave(inputTransactionFields, currentLocale)
            this@InsertTransactionFragmentMain.activity!!.onBackPressed()
        })
    }

    private fun runSave(inputTransactionFields: InputTransactionFields, currentLocale: Locale) = coroutineContext.run {
        launch {
            accountViewModel.insertAccount(inputTransactionFields.account)
            val amountBigDecimal = BigDecimal(inputTransactionFields.amount)
            var amount = Money(amountBigDecimal, Currency.getInstance(currentLocale))
            if (inputTransactionFields.isIncome) {
                categoriesViewModel.insertIncomeCategory(IncomeCategory(inputTransactionFields.category))
                amount = if (amount.isMinus) amount.times(-1) else amount
            } else {
                categoriesViewModel.insertExpenseCategory(ExpenseCategory(inputTransactionFields.category))
                amount = if (amount.isPlus) amount.times(-1) else amount
            }

            accountViewModel.insertTransaction(
                Transaction(
                    amount.bigDesicmal,
                    currentLocale,
                    OffsetDateTime.now(),
                    inputTransactionFields.category,
                    inputTransactionFields.account
                )
            )
        }
    }
}
