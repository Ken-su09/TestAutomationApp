package com.suonk.testautomationapp.ui.fragments.main_pages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.FragmentUserBinding
import com.suonk.testautomationapp.ui.activity.MainActivity
import com.suonk.testautomationapp.ui.adapters.DevicesListAdapter
import com.suonk.testautomationapp.viewmodels.AutomationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    //region ========================================== Val or Var ==========================================

    private var binding: FragmentUserBinding? = null
    private val viewModel: AutomationViewModel by activityViewModels()

    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        initializeUI()
        return binding?.root
    }

    //region ============================================== UI ==============================================

    private fun initializeUI() {
        getUserFromDatabase()
        clickToEditUser()
    }

    private fun clickToEditUser() {
        binding?.editUserIcon?.setOnClickListener {
            (activity as MainActivity).startEditUserDetails()
        }
    }

    //endregion

    private fun getUserFromDatabase() {
        viewModel.user.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                binding?.apply {
                    userNameValue.text = "${user.firstName} ${user.lastName}"
                    userEmailValue.text = user.email

                    userPhoneNumberValue.text = user.phoneNumber
                    userBirthDateValue.text = user.birthDate.toString()

                    if (user.img != null) {
                        userImage.setImageBitmap(user.img)
                    }
                }
            }
        })

        viewModel.address.observe(viewLifecycleOwner, { address ->
            if (address != null) {
                binding?.apply {
                    userAddressValue.text =
                        "${address.streetCode} ${address.street}, ${address.postalCode}"
                    userCityValue.text = address.city
                    userCountryValue.text = address.country
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}