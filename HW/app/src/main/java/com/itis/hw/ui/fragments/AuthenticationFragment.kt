package com.itis.hw.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.itis.hw.R
import com.itis.hw.data.entity.UserEntity
import com.itis.hw.databinding.FragmentAdditionBinding
import com.itis.hw.databinding.FragmentAuthenticationBinding
import com.itis.hw.di.ServiceLocator
import com.itis.hw.models.Session
import com.itis.hw.ui.MainActivity
import com.itis.hw.utils.Hasher
import com.itis.hw.utils.LocalDateTimeAdapter
import com.itis.hw.utils.ParamKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.TemporalAmount

class AuthenticationFragment : Fragment(R.layout.fragment_authentication) {
    private val binding: FragmentAuthenticationBinding by viewBinding(FragmentAuthenticationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtons()
    }

    private fun initButtons() {
        with(binding) {
            goToRegistrationTv.setOnClickListener {
                findNavController().navigate(R.id.action_authenticationFragment_to_registrationFragment)
            }
            confirmBtn.setOnClickListener {
                val email = emailEt.text.toString()
                val passwordHash = Hasher.getHash(passwordEt.text.toString())

                lifecycleScope.launch(Dispatchers.IO) {
                    val user = ServiceLocator.getDbInstance().userDao().getUserByEmail(email)

                    withContext(Dispatchers.Main) {
                        if(user != null && user.passwordHash == passwordHash) {
                            (requireActivity() as? MainActivity)?.showBottomNavigationView()

                            val sessionString =  ServiceLocator.getGson().toJson(Session(
                                userEmail = email,
                                expires = LocalDateTime.now().plusMinutes(30)
                            ))

                            ServiceLocator.getPrefs().edit()
                                .putString(ParamKeys.SESSION_KEY, sessionString)
                                .apply()

                            findNavController().navigate(R.id.action_authenticationFragment_to_mainFragment)
                        } else {
                            AlertDialog.Builder(requireContext())
                                .setTitle(getString(R.string.authentication_alert_dialog_title))
                                .setPositiveButton("Ок"
                                ) { dialogInterface, _ -> dialogInterface.dismiss() }
                                .show()
                        }
                    }
                }
            }
        }
    }
}