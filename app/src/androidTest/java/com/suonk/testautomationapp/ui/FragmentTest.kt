package com.suonk.testautomationapp.ui

import androidx.test.filters.MediumTest
import com.suonk.testautomationapp.ui.activity.MainActivity
import com.suonk.testautomationapp.ui.fragments.SplashScreenFragment
import com.suonk.testautomationapp.ui.fragments.main_pages.MainFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class FragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testSplashScreenFragment() {
        launchFragmentInHiltContainer<SplashScreenFragment> {
            (this.activity as MainActivity).startMainScreen()
        }
    }

    @Test
    fun testMainFragment() {
        launchFragmentInHiltContainer<MainFragment> {

        }
    }
}