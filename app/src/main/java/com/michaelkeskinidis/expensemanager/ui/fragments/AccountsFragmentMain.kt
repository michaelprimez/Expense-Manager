package com.michaelkeskinidis.expensemanager.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelkeskinidis.expensemanager.R
import com.michaelkeskinidis.expensemanager.ui.adapters.AccountTransactionsAdapter
import com.michaelkeskinidis.expensemanager.ui.base.MainScopedFragment
import com.michaelkeskinidis.expensemanager.viewmodel.AccountViewModel
import com.michaelkeskinidis.expensemanager.viewmodel.factories.AccountViewModelFactory
import kotlinx.android.synthetic.main.fragment_accounts.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class AccountsFragmentMain : MainScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: AccountViewModelFactory by instance()
    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_accounts, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        accountViewModel = ViewModelProvider(this@AccountsFragmentMain.activity!!, viewModelFactory).get(AccountViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        val recyclerView: RecyclerView = list as RecyclerView
        val accountTransactions = accountViewModel.accountTransactions.await()
        accountTransactions.observe(viewLifecycleOwner, Observer { accountTransaction ->
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@AccountsFragmentMain.context)
                adapter = AccountTransactionsAdapter(accountTransaction)
            }
        })
    }
}
