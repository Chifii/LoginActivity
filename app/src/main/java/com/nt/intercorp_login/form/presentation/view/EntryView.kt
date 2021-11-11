package com.nt.intercorp_login.form.presentation.view

import com.nt.intercorp_login.base.BaseView
import com.nt.intercorp_login.utils.OperationStatus

interface EntryView : BaseView {
    fun showToast(status: OperationStatus)
    fun cleanFields()
    fun startActivity()
}