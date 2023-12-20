package com.itis.hw.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.itis.hw.R
import com.itis.hw.databinding.FragmentProfileBinding
import com.itis.hw.di.ServiceLocator
import com.itis.hw.models.Session
import com.itis.hw.ui.MainActivity
import com.itis.hw.utils.ParamKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtons()

        lifecycleScope.launch(Dispatchers.IO) {
            val userEmail = ServiceLocator.getGson().fromJson(
                ServiceLocator.getPrefs().getString(ParamKeys.SESSION_KEY, null),
                Session::class.java
            ).userEmail

            val user = ServiceLocator.getDbInstance().userDao().getUserByEmail(userEmail)
            withContext(Dispatchers.Main) {
                with(binding) {
                    user?.let {
                        emailTv.text = "${emailTv.text} ${user.email}"
                        nameTv.text = "${nameTv.text} ${user.name}"
                        phoneTv.text = "${phoneTv.text} ${user.phone}"
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        childFragmentManager.setFragmentResultListener(ParamKeys.CHANGE_DATA_DIALOG_FRAGMENT_RESULT_KEY, this) { _, result ->
            val newPhone = result.getString(ParamKeys.NEW_PHONE_KEY)
            if(newPhone?.isNotEmpty() == true) {
                binding.phoneTv.text = "${binding.phoneTv.text.toString().split(':')[0]}: $newPhone"
            }

        }
    }

    private fun initButtons() {
        with(binding) {
            dataChangeBtn.setOnClickListener {
                DialogDataChangeFragment().show(childFragmentManager, DialogDataChangeFragment.DIALOG_DATA_CHANGE_FRAGMENT_TAG)
            }

            exitBtn.setOnClickListener {
                ServiceLocator.getPrefs().edit().remove(ParamKeys.SESSION_KEY).apply()

                (requireActivity() as? MainActivity)?.hideBottomNavigationView()

                findNavController().navigate(R.id.action_profileFragment_to_authenticationFragment)
            }

            deleteProfileBtn.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val userEmail = ServiceLocator.getGson().fromJson(
                        ServiceLocator.getPrefs().getString(ParamKeys.SESSION_KEY, null),
                        Session::class.java
                    ).userEmail

                    ServiceLocator.getDbInstance().movieUserDao().deleteAllUserCrossRefs(userEmail)
                    ServiceLocator.getDbInstance().userDao().deleteUser(userEmail)
                    ServiceLocator.getPrefs().edit().remove(ParamKeys.SESSION_KEY).apply()
                }

                (requireActivity() as? MainActivity)?.hideBottomNavigationView()

                findNavController().navigate(R.id.action_profileFragment_to_authenticationFragment)
            }
        }
    }

}