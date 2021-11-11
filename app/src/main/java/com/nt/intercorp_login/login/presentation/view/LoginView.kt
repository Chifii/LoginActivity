package com.nt.intercorp_login.login.presentation.view

import com.nt.intercorp_login.base.BaseView
import com.nt.intercorp_login.utils.OperationStatus

interface LoginView: BaseView {
    fun showToast(status: OperationStatus)
    fun startEntryActivity()
    fun getActivity(): LoginActivity
}