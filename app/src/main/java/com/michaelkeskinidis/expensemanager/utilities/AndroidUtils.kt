package com.michaelkeskinidis.expensemanager.utilities

import android.content.Context
import android.os.Build
import java.util.*


fun getCurrentLocale(context: Context): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.getResources().getConfiguration().getLocales().get(0)
    } else {
        context.getResources().getConfiguration().locale
    }
}