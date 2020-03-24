package com.michaelkeskinidis.expensemanager.viewmodel

import android.view.View.OnFocusChangeListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.michaelkeskinidis.expensemanager.ui.model.InputTransactionForm

class InputTransactionViewModel(
    val form: InputTransactionForm = InputTransactionForm(),
    var onFocusAccountType: OnFocusChangeListener = OnFocusChangeListener { view, focused ->
        if (!focused) {
            form.isAccountTypeValid(true)
        }
    },
    var onFocusCategoryType: OnFocusChangeListener = OnFocusChangeListener { view, focused ->
        if (!focused) {
            form.isCategoryTypeValid(true)
        }
    },
    var onFocusAmount: OnFocusChangeListener = OnFocusChangeListener { view, focused ->
        if (!focused) {
            form.isAmountValid(true)
        }
    }
): ViewModel() {

    companion object {
        @JvmStatic
        @BindingAdapter("error")
        fun setError(editText: AppCompatEditText, strOrResId: Any?) {
            if (strOrResId is Int) {
                editText.error = editText.context.getString((strOrResId as Int?)!!)
            } else {
                editText.error = strOrResId as String?
            }
        }

        @JvmStatic
        @BindingAdapter("onFocus")
        fun bindFocusChange(editText: AppCompatEditText, onFocusChangeListener: OnFocusChangeListener?) {
            if (editText.onFocusChangeListener == null) {
                editText.onFocusChangeListener = onFocusChangeListener
            }
        }
    }
}