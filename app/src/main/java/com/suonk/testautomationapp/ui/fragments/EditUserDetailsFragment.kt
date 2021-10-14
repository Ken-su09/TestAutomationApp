package com.suonk.testautomationapp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.FragmentEditUserDetailsBinding
import com.suonk.testautomationapp.models.data.Address
import com.suonk.testautomationapp.models.data.User
import com.suonk.testautomationapp.ui.activity.MainActivity
import com.suonk.testautomationapp.viewmodels.AutomationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class EditUserDetailsFragment : Fragment() {

    //region ========================================== Val or Var ==========================================

    private var binding: FragmentEditUserDetailsBinding? = null
    private val viewModel: AutomationViewModel by activityViewModels()

    private lateinit var currentUser: User
    private lateinit var currentAddress: Address

    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditUserDetailsBinding.inflate(inflater, container, false)
        initializeUI()
        return binding?.root
    }

    //region ============================================== UI ==============================================

    private fun initializeUI() {
        getUserFromDatabase()
        saveUserClick()
        changeImageClick()
        backClick()

        binding?.userEmailValue?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                checkEmailConstantly()
            }
        })
    }

    //endregion

    //region ============================================ Clicks ============================================

    private fun saveUserClick() {
        binding?.saveUserIcon?.setOnClickListener {
            if (checkIfFieldIsEmpty()) {
                if (checkEmailValidation()) {
                    if (checkPostalCode()) {
                        viewModel.updateUser(
                            User(
                                Address(
                                    binding?.userCityValue?.text?.toString()!!,
                                    binding?.userCountryValue?.text?.toString()!!,
                                    binding?.userPostalCodeValue?.text?.toString()?.toInt()!!,
                                    binding?.userAddressValue?.text?.toString()!!,
                                    "",
                                ),
                                binding?.userBirthDateValue?.text?.toString()?.toLong()!!,
                                binding?.userNameValue?.text?.toString()!!,
                                binding?.userLastNameValue?.text?.toString()!!,
                                binding?.userImage?.drawable?.toBitmap(),
                                binding?.userPhoneNumberValue?.text?.toString()!!,
                                binding?.userEmailValue?.text?.toString()!!,
                                1
                            )
                        )
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.postal_code_is_not_valid),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.email_is_not_valid),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.check_if_fields_empty),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun backClick() {
        binding?.apply {
            back.setOnClickListener {
                if (checkIfUserHasBeenEdited()) {
                    MaterialAlertDialogBuilder(activity as MainActivity, R.style.AlertDialogTheme)
                        .setTitle(getString(R.string.alert_dialog_title_back))
                        .setMessage(getString(R.string.alert_dialog_message))
                        .setPositiveButton(getString(R.string.alert_dialog_positive_button)) { _, _ ->
                            activity?.supportFragmentManager?.popBackStack()
                        }
                        .setNegativeButton(getString(R.string.alert_dialog_negative_button)) { dialogInterface, _ ->
                            dialogInterface.cancel()
                            dialogInterface.dismiss()
                        }
                        .show()
                } else {
                    activity?.supportFragmentManager?.popBackStack()
                }
            }
        }
    }

    private fun changeImageClick() {
        binding?.userImage?.setOnClickListener {
            (activity as MainActivity).openGalleryForImage(binding?.userImage!!)
        }
    }

    //endregion

    //region ========================================= Check Fields =========================================

    private fun checkEmailValidation(): Boolean {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        val userEmail = binding?.userEmailValue?.text.toString()

        return userEmail.trim().matches(emailPattern.toRegex())
    }

    private fun checkPostalCode(): Boolean {
        val postalCodePattern = Pattern.compile("^(F-)?((2[A|B])|[0-9]{2})[0-9]{3}$")

        val postalCode = binding?.userPostalCodeValue?.text.toString()

        return postalCode.trim().matches(postalCodePattern.toRegex())
    }

    private fun checkIfFieldIsEmpty(): Boolean {
        return binding?.userNameValue?.text?.isNotEmpty()!! && binding?.userNameValue?.text?.toString() != "" &&
                binding?.userLastNameValue?.text?.isNotEmpty()!! && binding?.userLastNameValue?.text?.toString() != "" &&
                binding?.userEmailValue?.text?.isNotEmpty()!! && binding?.userEmailValue?.text?.toString() != "" &&
                binding?.userPhoneNumberValue?.text?.isNotEmpty()!! && binding?.userPhoneNumberValue?.text?.toString() != "" &&
                binding?.userBirthDateValue?.text?.isNotEmpty()!! && binding?.userBirthDateValue?.text?.toString() != "" &&

                binding?.userAddressValue?.text?.isNotEmpty()!! && binding?.userAddressValue?.text?.toString() != "" &&
                binding?.userPostalCodeValue?.text?.isNotEmpty()!! && binding?.userPostalCodeValue?.text?.toString() != "" &&
                binding?.userCityValue?.text?.isNotEmpty()!! && binding?.userCityValue?.text?.toString() != "" &&
                binding?.userCountryValue?.text?.isNotEmpty()!! && binding?.userCountryValue?.text?.toString() != ""
    }

    private fun checkEmailConstantly() {
        val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        val userEmail = binding?.userEmailValue?.text.toString()

        if (userEmail.trim().matches(emailPattern.toRegex())) {
            binding?.loginEmailValidation?.visibility = View.VISIBLE
            binding?.loginEmailValidation?.setImageDrawable(
                AppCompatResources.getDrawable(
                    (activity as MainActivity),
                    R.drawable.ic_check_email
                )
            )
        } else {
            if (userEmail.isEmpty()) {
                binding?.loginEmailValidation?.visibility = View.INVISIBLE
            } else {
                binding?.loginEmailValidation?.visibility = View.VISIBLE
                binding?.loginEmailValidation?.setImageDrawable(
                    AppCompatResources.getDrawable(
                        (activity as MainActivity),
                        R.drawable.ic_check_email_cross
                    )
                )
            }
        }
    }

    private fun checkIfUserHasBeenEdited(): Boolean {
        return currentUser.firstName != binding?.userNameValue?.text.toString() ||
                currentUser.lastName != binding?.userLastNameValue?.text.toString() ||

                currentUser.email != binding?.userEmailValue?.text.toString() ||
                currentUser.phoneNumber != binding?.userPhoneNumberValue?.text.toString() ||
                currentUser.birthDate.toString() != binding?.userBirthDateValue?.text.toString() ||
                currentUser.img != binding?.userImage?.drawable?.toBitmap() ||

                currentAddress.postalCode.toString() != binding?.userPostalCodeValue?.text.toString() ||
                currentAddress.city != binding?.userCityValue?.text.toString() ||
                "${currentAddress.streetCode} ${currentAddress.street}" != binding?.userAddressValue?.text.toString() ||
                currentAddress.country != binding?.userCountryValue?.text.toString()
    }

    //endregion

    private fun getUserFromDatabase() {
        viewModel.user.observe(viewLifecycleOwner, { user ->
            if(user != null){
                currentUser = user
                currentAddress = user.address

                binding?.apply {
                    userNameValue.setText(user.firstName)
                    userLastNameValue.setText(user.lastName)

                    userEmailValue.setText(user.email)
                    userPhoneNumberValue.setText(user.phoneNumber)
                    userBirthDateValue.setText("${user.birthDate}")

                    if (user.img != null) {
                        userImage.setImageBitmap(user.img)
                    }

                    userAddressValue.setText("${user.address.streetCode} ${user.address.street}")
                    userPostalCodeValue.setText("${user.address.postalCode}")
                    userCityValue.setText(user.address.city)
                    userCountryValue.setText(user.address.country)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}