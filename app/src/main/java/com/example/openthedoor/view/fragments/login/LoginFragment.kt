package com.example.openthedoor.view.fragments.login


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.openthedoor.R
import com.example.openthedoor.utils.extensions.ERROR_CODE_EMPTY_PASSWORD
import com.example.openthedoor.utils.extensions.ERROR_CODE_EMPTY_PHONE_FIELD
import com.example.openthedoor.view.fragments.base.BaseFragment
import com.example.openthedoor.viewModel.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_login
    lateinit var userViewModel: UserViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachViewModel()
        btnLogin.setOnClickListener {
            userViewModel.login(etPhonenumber.text.toString(), etPassword.text.toString())
        }
    }

    private fun attachViewModel() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        with(userViewModel) {
            snackbarMessage.observe(viewLifecycleOwner, Observer {
                val msg = it?.getContentIfNotHandled()
                if (!msg.isNullOrEmpty()) showToast(msg)
            })
            progressBar.observe(viewLifecycleOwner, Observer {
                val show = it?.getContentIfNotHandled()
                if (show != null)
                    showProgressDialog(show)
            })

            validationResponse.observe(viewLifecycleOwner, Observer {
                val show = it?.getContentIfNotHandled()
                if (show != null) {
                    when (show.errorCode) {
                        ERROR_CODE_EMPTY_PHONE_FIELD -> {
                            etPhonenumber.error = show.errorMessage
                        }
                        ERROR_CODE_EMPTY_PASSWORD -> {
                            etPassword.error = show.errorMessage
                        }
                    }
                }
            })

            loginResponse.observe(viewLifecycleOwner, Observer {
                val show = it?.getContentIfNotHandled()
                if (show != null) {
                    appPreferences.setAuthenticationToken(show.token)
                    appPreferences.setUser(show)
                    moveToGlobalNavigationActivity()
                }
            })
        }
    }
}
