package com.michaelkeskinidis.expensemanager.viewmodel

import com.michaelkeskinidis.expensemanager.ui.model.InputTransactionForm
import com.michaelkeskinidis.expensemanager.viewmodel.InputTransactionViewModel
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class InputTrasactionViewModelTest {

    @Test
    fun fieldsWithoutAmount_formIsNotCorrect() {
        val vm = InputTransactionViewModel()
        val form: InputTransactionForm = vm.form
        form.formFields.account = "TestAcount"
        form.formFields.category = "TestCategory"
        assertFalse("Form is not valid", form.isValid())
    }

    @Test
    fun fieldsWithoutAccount_formIsNotCorrect() {
        val vm = InputTransactionViewModel()
        val form: InputTransactionForm = vm.form
        form.formFields.category = "TestCategory"
        form.formFields.amount = "100"
        assertFalse("Form is not valid", form.isValid())
    }

    @Test
    fun fieldsWithoutCategory_formIsNotCorrect() {
        val vm = InputTransactionViewModel()
        val form: InputTransactionForm = vm.form
        form.formFields.account = "TestAcount"
        form.formFields.amount = "100"
        assertFalse("Form is not valid", form.isValid())
    }

    @Test
    fun fieldsWithAll_formIsCorrect() {
        val vm = InputTransactionViewModel()
        val form: InputTransactionForm = vm.form
        form.formFields.account = "TestAcount"
        form.formFields.category = "TestCategory"
        form.formFields.amount = "100"
        assertTrue("Form is not valid", form.isValid())
    }
}