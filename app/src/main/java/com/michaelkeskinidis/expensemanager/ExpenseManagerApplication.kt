package com.michaelkeskinidis.expensemanager

import android.app.Application
import com.facebook.stetho.Stetho
import com.michaelkeskinidis.expensemanager.data.db.ExpenseManagerDatabase
import com.michaelkeskinidis.expensemanager.data.repositories.*
import com.michaelkeskinidis.expensemanager.viewmodel.factories.AccountViewModelFactory
import com.michaelkeskinidis.expensemanager.viewmodel.factories.CategoriesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ExpenseManagerApplication: Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ExpenseManagerApplication))

        bind() from singleton { ExpenseManagerDatabase(instance()) }
        bind() from singleton { instance<ExpenseManagerDatabase>().accountDao() }
        bind() from singleton { instance<ExpenseManagerDatabase>().transactionDao() }
        bind() from singleton { instance<ExpenseManagerDatabase>().expenseCategoryDao() }
        bind() from singleton { instance<ExpenseManagerDatabase>().incomeCategoryDao() }

        bind<AccountRepository>() with singleton { AccountRepositoryImpl(instance()) }
        bind<TransactionRepository>() with singleton { TransactionRepositoryImpl(instance()) }
        bind<CategoriesRepositoryImpl>() with singleton { CategoriesRepositoryImpl(instance(), instance()) }

        bind() from provider { AccountViewModelFactory(instance(), instance()) }
        bind() from provider { CategoriesViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }
}