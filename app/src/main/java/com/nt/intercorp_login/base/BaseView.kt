package com.nt.intercorp_login.base

interface BaseView {
    fun showErrorView(statusCode: Int?)
    fun showLoadingView()
    fun showLayoutView()
}