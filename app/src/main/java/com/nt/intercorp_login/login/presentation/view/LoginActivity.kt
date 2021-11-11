package com.nt.intercorp_login.login.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.nt.intercorp_login.R
import com.nt.intercorp_login.base.BaseActivity
import com.nt.intercorp_login.databinding.IntercorpLoginActivityLayoutBinding
import com.nt.intercorp_login.form.presentation.view.EntryActivity
import com.nt.intercorp_login.login.presentation.presenter.LoginPresenter
import com.nt.intercorp_login.utils.OperationStatus


class LoginActivity : BaseActivity<LoginView, LoginPresenter>(), LoginView {

    private lateinit var binding: IntercorpLoginActivityLayoutBinding
    private var presenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = IntercorpLoginActivityLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupRegisterButton()
        setupLoginButton()
        setupNumberLogin()
        setupCancelButton()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.intercorp_login_activity_layout
    }

    private fun setupRegisterButton() {
        binding.registerButton.setOnClickListener {
            if (binding.userTextView.text.isNotEmpty() && binding.passwordTextView.text.isNotEmpty()) {
                presenter.setupRegisterButton(
                    binding.userTextView.text.toString(),
                    binding.passwordTextView.text.toString()
                )
            } else {
                showToast(OperationStatus.EMPTY_FIELD)
            }
        }
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            if (binding.userTextView.text.isNotEmpty() && binding.passwordTextView.text.isNotEmpty()) {
                presenter.setupLoginButton(
                    binding.userTextView.text.toString(),
                    binding.passwordTextView.text.toString()
                )
            } else {
                showToast(OperationStatus.EMPTY_FIELD)
            }
        }
    }

    private fun setupNumberLogin() {
        binding.loginPhoneNumber.setOnClickListener {
            setupVisibility()
            binding.sendCode.setOnClickListener {
                if (binding.numberTextView.text.isNotEmpty()) {
                    presenter.setupPhoneLogin(binding.numberTextView.text.toString())
                }
                setupVisibilityForButtons()
            }
            binding.loginButton.setOnClickListener {
                if (binding.codeTextView.text.isNotEmpty()) {
                    presenter.loginWithCode(binding.codeTextView.text.toString())
                }
            }
        }
    }

    private fun setupCancelButton() {
        binding.cancelButton.setOnClickListener {
            goBack()
        }
    }

    override fun showToast(status: OperationStatus) {
        val toast = Toast(applicationContext)

        when (status) {
            OperationStatus.SUCCESS_REGISTER -> {
                toast.apply {
                    setText("Registro completado!")
                    Toast.LENGTH_SHORT
                }.show()
            }
            OperationStatus.SUCCESS_LOGIN -> {
                toast.apply {
                    setText("Bienvenido nuevamente!")
                    Toast.LENGTH_SHORT
                }.show()
            }
            OperationStatus.FAIL_REGISTER -> {
                toast.apply {
                    setText("Algo salio mal, intentelo nuevamente ...")
                    Toast.LENGTH_SHORT
                }.show()
            }
            OperationStatus.FAIL_LOGIN -> {
                toast.apply {
                    setText("Usuario o contraseña incorrectos")
                    Toast.LENGTH_SHORT
                }.show()
            }
            OperationStatus.EMPTY_FIELD -> {
                toast.apply {
                    setText("Por favor completa todos los campos")
                    Toast.LENGTH_SHORT
                }.show()
            }
        }
    }

    override fun startEntryActivity() {
        val intent = Intent(this, EntryActivity::class.java)
        startActivity(intent)
    }

    private fun setupVisibility() {
        binding.apply {
            userTextView.visibility = View.INVISIBLE
            passwordTextView.visibility = View.INVISIBLE
            registerButton.visibility = View.INVISIBLE
            loginButton.visibility = View.INVISIBLE
            loginPhoneNumber.visibility = View.INVISIBLE

            imgUserLogin.setImageResource(R.drawable.cellphone)
            imgPassLogin.setImageResource(R.drawable.message_alert)

            registerButton.isEnabled = false
            userTextView.isEnabled = false
            passwordTextView.isEnabled = false
            loginButton.isEnabled = false

            numberTextView.visibility = View.VISIBLE
            codeTextView.visibility = View.VISIBLE
            cancelButton.visibility = View.VISIBLE
            sendCode.visibility = View.VISIBLE
        }
    }

    private fun goBack() {
        binding.apply {
            userTextView.visibility = View.VISIBLE
            passwordTextView.visibility = View.VISIBLE
            registerButton.visibility = View.VISIBLE
            loginButton.visibility = View.VISIBLE
            loginPhoneNumber.visibility = View.VISIBLE

            imgUserLogin.setImageResource(R.drawable.account_circle)
            imgPassLogin.setImageResource(R.drawable.lock)

            registerButton.isEnabled = true
            userTextView.isEnabled = true
            passwordTextView.isEnabled = true
            loginButton.isEnabled = true

            numberTextView.visibility = View.GONE
            codeTextView.visibility = View.GONE
            cancelButton.visibility = View.GONE
            sendCode.visibility = View.GONE
        }
    }

    private fun setupVisibilityForButtons() {
        binding.apply {
            loginButton.visibility = View.VISIBLE

            loginButton.isEnabled = true

            sendCode.visibility = View.GONE
        }
    }

    override fun getActivity() = this
}