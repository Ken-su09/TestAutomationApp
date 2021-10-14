package com.suonk.testautomationapp.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.suonk.testautomationapp.R
import com.suonk.testautomationapp.databinding.ActivityMainBinding
import com.suonk.testautomationapp.navigation.Coordinator
import com.suonk.testautomationapp.navigation.Navigator
import com.suonk.testautomationapp.viewmodels.AutomationViewModel
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 100
    }

    private var binding: ActivityMainBinding? = null
    private lateinit var circleImageView: CircleImageView

    @Inject
    lateinit var navigator: Navigator
    private lateinit var coordinator: Coordinator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        navigator.activity = this
        coordinator = Coordinator(navigator)
        startSplashScreen()
    }

    //region ======================================= Start Fragments ========================================

    private fun startSplashScreen() {
        coordinator.showSplashScreen()
//        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
    }

    fun startMainScreen() {
        coordinator.showMainScreen()
    }

    fun startDeviceDetails() {
        coordinator.showDeviceDetails()
    }

    fun startEditUserDetails() {
        coordinator.showEditUserDetails()
    }

    //endregion

    fun openGalleryForImage(civ: CircleImageView) {
        circleImageView = civ
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            circleImageView.setImageURI(data?.data)
        }
    }
}