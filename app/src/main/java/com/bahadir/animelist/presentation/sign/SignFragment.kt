package com.bahadir.animelist.presentation.sign

import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bahadir.animelist.R
import com.bahadir.animelist.common.extensions.collectIn
import com.bahadir.animelist.common.extensions.color
import com.bahadir.animelist.common.extensions.gone
import com.bahadir.animelist.common.extensions.hideKeyboard
import com.bahadir.animelist.common.extensions.snackBar
import com.bahadir.animelist.common.extensions.visible
import com.bahadir.animelist.databinding.FragmentSignBinding
import com.bahadir.animelist.delegation.viewBinding
import com.google.android.gms.auth.GoogleAuthException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignFragment : Fragment(R.layout.fragment_sign) {
    private val binding by viewBinding(FragmentSignBinding::bind)
    private val viewModel: SignVM by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIEffect()
        initUIEvent()
        initUIState()
    }

    private fun initUIEvent() {
        with(binding) {
            with(viewModel) {
                cons.setOnClickListener {
                    setEvent(SignUIEvent.HideKeyboard)
                }
                btnSign.setOnClickListener {
                    val email = etEmail.getText()
                    val password = etPassword.getText()
                    val fullName = etFullName.getText()

                    setEvent(SignUIEvent.SignUIClicked(email, password, fullName))
                }
                btnGoogle.setOnClickListener {
                    setEvent(SignUIEvent.GoogleClicked)
                }
                btnStateSignIn.setOnClickListener {
                    setEvent(SignUIEvent.SignUIStateInClicked)
                }
                btnStateRegister.setOnClickListener {
                    setEvent(SignUIEvent.SignUIStateUpClicked)
                }
            }
        }
    }

    private fun initUIEffect() {
        with(viewModel) {
            with(binding) {
                effect.collectIn(viewLifecycleOwner) { effect ->
                    when (effect) {
                        is SignUIEffect.SignStateUp -> {
                            btnStateSignIn.setTextColor(resources.color(R.color.black))
                            btnStateRegister.setTextColor(resources.color(R.color.white))

                            btnStateSignIn.setBackgroundColor(resources.color(R.color.neutral_light))
                            btnStateRegister.setBackgroundColor(resources.color(R.color.green_dark_pastel))

                            btnSign.setText(R.string.sign_up)
                            text.setText(R.string.sign_up_title)
                            text2.setText(R.string.sign_up_text)
                            etFullName.visible()
                            etPasswordAgain.visible()
                        }

                        is SignUIEffect.SignStateIn -> {
                            btnStateRegister.setTextColor(resources.color(R.color.black))
                            btnStateSignIn.setTextColor(resources.color(R.color.white))

                            btnStateSignIn.setBackgroundColor(resources.color(R.color.green_dark_pastel))
                            btnStateRegister.setBackgroundColor(resources.color(R.color.neutral_light))


                            btnSign.setText(R.string.sign_in)
                            text.setText(R.string.sign_in_title)
                            text2.setText(R.string.sign_in_text)
                            etFullName.gone()
                            etPasswordAgain.gone()
                        }

                        is SignUIEffect.GoToGoogleAccountScreen -> {
                            laResult.launch(effect.intent)
                        }

                        is SignUIEffect.ShowError -> {
                            requireView().snackBar(effect.message)

                        }

                        is SignUIEffect.GoToHomeScreen -> {
                            findNavController().navigate(R.id.action_nav_graph)
                        }

                        is SignUIEffect.HideKeyboard -> {
                            requireView().hideKeyboard()
                            focusClear()
                        }
                    }
                }
            }
        }
    }

    private fun initUIState() {
        with(viewModel) {
            state.collectIn(viewLifecycleOwner) { state ->
                binding.progress.isVisible = state.isLoading
            }
        }
    }

    private fun focusClear() {
        with(binding) {
            etPassword.focusClear()
            etFullName.focusClear()
            etPasswordAgain.focusClear()
            etEmail.focusClear()

        }
    }

    private val laResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            activityResult(activityResult)
        }

    private fun activityResult(resultContracts: ActivityResult) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(resultContracts.data)
            val account = task.getResult(ApiException::class.java)
            viewModel.firebaseAuthWithGoogle(account?.idToken.toString())
        } catch (apiException: ApiException) {
            viewModel.setEffect(SignUIEffect.ShowError("ApiException: $apiException"))
        } catch (authException: GoogleAuthException) {
            viewModel.setEffect(SignUIEffect.ShowError("AuthException: $authException"))
        }
    }
}