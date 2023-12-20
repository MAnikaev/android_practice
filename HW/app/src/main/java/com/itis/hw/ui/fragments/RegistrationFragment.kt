package com.itis.hw.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.GsonBuilder
import com.itis.hw.R
import com.itis.hw.data.entity.UserEntity
import com.itis.hw.databinding.FragmentProfileBinding
import com.itis.hw.databinding.FragmentRegistrationBinding
import com.itis.hw.di.ServiceLocator
import com.itis.hw.models.Session
import com.itis.hw.ui.MainActivity
import com.itis.hw.utils.Hasher
import com.itis.hw.utils.ParamKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private val binding: FragmentRegistrationBinding by viewBinding(FragmentRegistrationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkIsUserAuthorized()
        initButtons()
    }

    private fun checkIsUserAuthorized() {

        val sessionString = ServiceLocator.getPrefs().getString(ParamKeys.SESSION_KEY, null) ?: return
        val session = ServiceLocator.getGson()
            .fromJson(sessionString, Session::class.java)

        if(session?.expires?.isBefore(LocalDateTime.now()) == true) {
            ServiceLocator.getPrefs().edit().remove(ParamKeys.SESSION_KEY).apply()
            return
        }

        (requireActivity() as? MainActivity)?.showBottomNavigationView()
        findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
    }

    private fun initButtons() {
        with(binding) {
            goToAuthenticationTv.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_authenticationFragment)
            }

            registrationBtn.setOnClickListener {
                val email = emailEt.text.toString()
                val name = nameEt.text.toString()
                val phone = phoneEt.text.toString()
                val passwordHash = Hasher.getHash(passwordEt.text.toString())
                val user = UserEntity(
                    email = email,
                    name = name,
                    phone = phone,
                    passwordHash = passwordHash
                )

                lifecycleScope.launch(Dispatchers.IO) {
                    ServiceLocator.getDbInstance().userDao().createUser(user)
                }

                findNavController().navigate(R.id.action_registrationFragment_to_authenticationFragment)
            }
        }
    }
}