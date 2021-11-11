package com.nt.intercorp_login.form.presentation.presenter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nt.intercorp_login.base.BasePresenter
import com.nt.intercorp_login.form.core.domain.EntryModel
import com.nt.intercorp_login.form.presentation.view.EntryView
import com.nt.intercorp_login.utils.OperationStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EntryPresenter(
    private var view: EntryView
) : BasePresenter<EntryView>() {

    private val scope by lazy { CoroutineScope(Job() + Dispatchers.IO) }

    fun saveUserData(userName: String, userLastName: String, userAge: Int, userBirthDay: String) {
        scope.launch {
            val database = FirebaseDatabase.getInstance().getReference("Users")
            val user = EntryModel(userName, userLastName, userAge, userBirthDay)

            database.child(userName).setValue(user).addOnSuccessListener {
                view.cleanFields()
                view.showToast(OperationStatus.SUCCESS_SAVE_DATA)
            }.addOnFailureListener {
                view.showToast(OperationStatus.FAIL_SAVE_DATA)
            }

        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        view.startActivity()
    }
}