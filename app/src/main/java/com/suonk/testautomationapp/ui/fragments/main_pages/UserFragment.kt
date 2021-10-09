package com.suonk.testautomationapp.ui.fragments.main_pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.FragmentUserBinding
import com.suonk.testautomationapp.ui.activity.MainActivity
import com.suonk.testautomationapp.ui.adapters.DevicesListAdapter
import com.suonk.testautomationapp.viewmodels.AutomationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var binding: FragmentUserBinding? = null
    private val viewModel: AutomationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        initializeUI()
        return binding?.root
    }

    private fun initializeUI() {
        getUserFromDatabase()
    }

    private fun getUserFromDatabase() {
        viewModel.userAndAddress(1).observe(viewLifecycleOwner, { userAndAddress ->
            val user = userAndAddress.user
            val address = userAndAddress.address

            binding?.apply {
                userNameValue.text = "${user.firstName} ${user.lastName}"
                userEmailValue.text = user.email
                userPhoneNumberValue.text = user.phoneNumber
                userEmailValue.text = user.email
                userBirthDateValue.text = user.birthDate.toString()
                userEmailValue.text = user.email

                userAddressValue.text = "${address.streetCode} ${address.street}, ${address.postalCode}"
                userCityValue.text = address.city
                userCountryValue.text = address.country
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}