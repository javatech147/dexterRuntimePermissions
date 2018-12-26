package com.waytojava.dexterruntimepermissions

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnLogin.setOnClickListener {
            checkLocationPermission()
        }
    }

    private fun checkLocationPermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    //snackBar(this@MainActivity, "Location Permission Granted")
                    moveToHomeActivity()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    // If you want this permission mandatory then uncomment this
//                    if (response!!.isPermanentlyDenied) {
//                        showSettingsDialog()
//                    }
                    moveToHomeActivity()
                }
            })
            .withErrorListener { snackBar(this@MainActivity, "Some Error Occurs") }
            .check()
    }

    fun moveToHomeActivity() {
        val homeIntent = Intent(this, HomeActivity::class.java)
        startActivity(homeIntent)
        finish()
    }
}
