package com.nt.intercorp_login.login.presentation.presenter

import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nt.intercorp_login.base.BasePresenter
import com.nt.intercorp_login.login.presentation.view.LoginView
import com.nt.intercorp_login.utils.OperationStatus
import java.util.concurrent.TimeUnit

class LoginPresenter(
    private var view: LoginView
) : BasePresenter<LoginView>() {

    private lateinit var auth: FirebaseAuth

    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    fun setupLoginButton(user: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            user, password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                view.startEntryActivity()
                view.showToast(OperationStatus.SUCCESS_LOGIN)
            } else {
                view.showToast(OperationStatus.FAIL_LOGIN)
            }
        }
    }

    fun setupRegisterButton(user: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            user, password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                view.showToast(OperationStatus.SUCCESS_REGISTER)
                view.startEntryActivity()
            } else {
                view.showToast(OperationStatus.FAIL_REGISTER)
            }
        }
    }

    fun setupPhoneLogin(number: String) {
        auth = Firebase.auth
        startPhoneNumberVerification(number)
    }

    private var callbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    view.showToast(OperationStatus.FAIL_LOGIN)
                } else if (e is FirebaseTooManyRequestsException) {
                    view.showToast(OperationStatus.FAIL_LOGIN)
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
            }
        }

    private fun startPhoneNumberVerification(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(view.getActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(view.getActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = task.result?.user
                    view.showToast(OperationStatus.SUCCESS_LOGIN)
                    view.startEntryActivity()
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        view.showToast(OperationStatus.FAIL_LOGIN)
                    }
                }
            }
    }

    fun loginWithCode(code: String) {
        verifyPhoneNumberWithCode(storedVerificationId, code)
    }
}