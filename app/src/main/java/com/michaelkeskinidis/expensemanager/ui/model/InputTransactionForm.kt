package com.michaelkeskinidis.expensemanager.ui.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.michaelkeskinidis.expensemanager.BR
import com.michaelkeskinidis.expensemanager.R
import com.michaelkeskinidis.expensemanager.data.db.converters.BigDecimalConverter
import java.math.BigDecimal

class InputTransactionForm: BaseObservable() {

    private val fields = InputTransactionFields()
    private val errors = InputTransactionErrors()
    val buttonClick: MutableLiveData<InputTransactionFields> = MutableLiveData()

    val formFields get() = fields
    val formErrors get() = errors

    fun isAccountTypeValid(setMessage: Boolean): Boolean {
        val isValid = fields.account.isNotEmpty()
        if (isValid) {
            notifyPropertyChanged(BR.valid)
            errors.account = null
        } else {
            if (setMessage) {
                notifyPropertyChanged(BR.valid)
                errors.account = R.string.account_not_correct
            }
        }
        return isValid
    }

    fun isCategoryTypeValid(setMessage: Boolean): Boolean {
        val isValid = fields.category.isNotEmpty()
        if (isValid) {
            notifyPropertyChanged(BR.valid)
            errors.category = null
        } else {
            if (setMessage) {
                notifyPropertyChanged(BR.valid)
                errors.category = R.string.category_not_correct
            }
        }
        return isValid
    }

    fun isAmountValid(setMessage: Boolean): Boolean {
        val amount = BigDecimalConverter.toBigDecimal(fields.amount) ?: BigDecimal.ZERO

        if (amount > BigDecimal.ZERO) {
            errors.amount = null
            notifyPropertyChanged(BR.valid)
            return true
        } else if (amount < BigDecimal.ZERO) {
            errors.amount = null
            notifyPropertyChanged(BR.valid)
            return true
        } else {
            if (setMessage) {
                notifyPropertyChanged(BR.valid)
                errors.amount = R.string.amount_not_correct
            }
            return false
        }
    }

    @Bindable
    fun isValid(): Boolean {
        notifyPropertyChanged(BR.accountError)
        notifyPropertyChanged(BR.categoryError)
        notifyPropertyChanged(BR.amountError)
        return isAmountValid(false) &&
                isAccountTypeValid(false) &&
                isCategoryTypeValid(false)
    }

    @Bindable
    fun isIncome(): Boolean {
        return fields.isIncome
    }

    fun setIsIncome(isIncome: Boolean) {
        fields.isIncome = isIncome
        notifyPropertyChanged(BR.income)
    }

    @Bindable
    fun getAccount(): String {
        return fields.account
    }

    fun setAccount(account: String) {
        fields.account = account
        notifyPropertyChanged(BR.account)
    }

    @Bindable
    fun getCategory(): String {
        return fields.category
    }

    fun setCategory(category: String) {
        fields.category = category
        notifyPropertyChanged(BR.category)
    }

    @Bindable
    fun getAmount(): String {
        return fields.amount
    }

    fun setAmount(amount: String) {
        fields.amount = amount
        notifyPropertyChanged(BR.amount)
    }

    @Bindable
    fun getAccountError(): Int? {
        return errors.account
    }

    @Bindable
    fun getCategoryError(): Int? {
        return errors.category
    }

    @Bindable
    fun getAmountError(): Int? {
        return errors.amount
    }

    fun onClick() {
        if (isValid()) {
            buttonClick.value = fields
        }
    }
}