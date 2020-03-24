package com.michaelkeskinidis.expensemanager.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.michaelkeskinidis.expensemanager.R
import com.michaelkeskinidis.expensemanager.databinding.ActivityMainBinding
import com.michaelkeskinidis.expensemanager.viewmodel.AccountViewModel
import com.michaelkeskinidis.expensemanager.viewmodel.factories.AccountViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext
import androidx.databinding.DataBindingUtil.setContentView

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var navController: NavController
    private val viewModelFactory: AccountViewModelFactory by instance()
    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this, viewModelFactory).get(AccountViewModel::class.java)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(toolbar)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        fab.setOnClickListener { view ->
            if(navController.currentDestination?.id != R.id.InsertTransactionFragment) {
                navController.navigate(R.id.InsertTransactionFragment)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
