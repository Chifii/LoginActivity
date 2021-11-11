package com.nt.intercorp_login.form.presentation.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.nt.intercorp_login.R
import com.nt.intercorp_login.base.BaseActivity
import com.nt.intercorp_login.databinding.IntercorpEntryFormActivityLayout2Binding
import com.nt.intercorp_login.form.presentation.presenter.EntryPresenter
import com.nt.intercorp_login.login.presentation.view.LoginActivity
import com.nt.intercorp_login.utils.OperationStatus

class EntryActivity : BaseActivity<EntryView, EntryPresenter>(), EntryView {

    private lateinit var binding: IntercorpEntryFormActivityLayout2Binding
    private var presenter = EntryPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = IntercorpEntryFormActivityLayout2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupFinishButton()
        setupLogOutButton()

        Thread.sleep(1000)
        binding.motionLayout.transitionToEnd()
    }

    override fun getLayoutResourceId(): Int = R.layout.intercorp_entry_form_activity_layout2

    private fun setupFinishButton() {

        binding.entryFormFinishButton.setOnClickListener {
            if (binding.entryFormUserName.text.isNotEmpty() &&
                binding.entryFormUserLastname.text.isNotEmpty() &&
                binding.entryFormAge.text.isNotEmpty() &&
                binding.entryFormDate.text.isNotEmpty()
            ) {
                presenter.saveUserData(
                    binding.entryFormUserName.text.toString(),
                    binding.entryFormUserLastname.text.toString(),
                    Integer.valueOf(binding.entryFormAge.text.toString()),
                    binding.entryFormDate.text.toString()
                )
            } else {
                showToast(OperationStatus.EMPTY_FIELD)
            }
            binding.motionLayout.transitionToEnd()
        }
    }

    override fun showToast(status: OperationStatus) {
        val toast = Toast(applicationContext)
        when (status) {
            OperationStatus.SUCCESS_SAVE_DATA -> {
                toast.apply {
                    setText("Tus datos fueron guardados correctamente!")
                    Toast.LENGTH_SHORT
                }.show()
            }
            OperationStatus.FAIL_SAVE_DATA -> {
                toast.apply {
                    setText("Hubo un error :sad_parrot:")
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

    override fun cleanFields() {
        binding.apply {
            entryFormUserName.text.clear()
            entryFormUserLastname.text.clear()
            entryFormAge.text.clear()
            entryFormDate.text.clear()
        }
    }

    override fun startActivity() {
        finishFlow()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun setupLogOutButton() {
        binding.entryFormLogoutButton.setOnClickListener {
            presenter.logout()
        }
    }
}