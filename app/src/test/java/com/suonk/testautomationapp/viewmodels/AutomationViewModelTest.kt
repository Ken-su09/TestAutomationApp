package com.suonk.testautomationapp.viewmodels

import com.suonk.testautomationapp.models.data.Address
import com.suonk.testautomationapp.models.data.User
import com.suonk.testautomationapp.repositories.FakeAutomationAppRepository
import org.junit.Before
import org.junit.Test

class AutomationViewModelTest {

    private lateinit var viewModel: AutomationViewModel

    @Before
    fun setup() {
        viewModel = AutomationViewModel(FakeAutomationAppRepository())
    }

    @Test
    fun `update user with empty fields, returns error`() {
        viewModel.updateUser(
            User(
                Address(
                    "Issy-les-Moulineaux",
                    "France",
                    92130,
                    "rue Michelet",
                    "2B"
                ),
                813766371000,
                "",
                "Doe",
                null,
                "456-509(1313)",
                "john.doe@hotmail.com",
                1
            )
        )
    }
}