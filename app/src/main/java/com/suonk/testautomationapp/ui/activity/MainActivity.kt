package com.suonk.testautomationapp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.suonk.testautomationapp.databinding.ActivityMainBinding
import com.suonk.testautomationapp.navigation.Coordinator
import com.suonk.testautomationapp.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
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