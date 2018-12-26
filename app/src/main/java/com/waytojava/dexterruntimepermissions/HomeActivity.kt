package com.waytojava.dexterruntimepermissions

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        title = "User home page"

        btnCapturePhoto.setOnClickListener {
            checkForLocationStorageCameraPermission()
        }
    }

    private fun checkForLocationStorageCameraPermission() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        openCamera()
                    }

                    if (report.isAnyPermissionPermanentlyDenied) {
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            })
            .withErrorListener { snackBar(this@HomeActivity, "Some Error Occurs") }
            .onSameThread()
            .check()
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permission")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("Goto Settings") { dialog, which ->
            dialog!!.cancel()
            openSettings()
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog!!.cancel() }
        builder.show()
    }

    private fun openSettings() {
        val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        settingsIntent.data = uri
        startActivity(settingsIntent)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 53)
    }
}
