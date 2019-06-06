package com.androidisland.easypermission

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.ResultReceiver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf

internal class RuntimePermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notGranted = getNotGrantedPermissions()
        if (notGranted.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                getNotGrantedPermissions(),
                PermissionResult.CODE_PERMISSION
            )
        } else {
            sendResult(ArrayList(getGrantedPermissions().toList()), ArrayList(), ArrayList())
        }
    }

    private fun sendResult(
        granted: ArrayList<String>,
        denied: ArrayList<String>,
        permanentlyDenied: ArrayList<String>
    ) {
        val resultReceiver =
            intent.getParcelableExtra<ResultReceiver>(PermissionResult.EXTRA_RESULT_RECEIVER)
        resultReceiver.send(
            PermissionResult.CODE_PERMISSION,
            bundleOf(
                PermissionResult.EXTRA_GRANTED_PERMISSIONS to granted,
                PermissionResult.EXTRA_DENIED_PERMISSIONS to denied,
                PermissionResult.EXTRA_PERMANENTLY_DENIED_PERMISSIONS to permanentlyDenied
            )
        )
        finish()
    }

    private fun getRequestedPermissions() =
        intent.getStringArrayExtra(PermissionResult.EXTRA_REQUESTED_PERMISSIONS)

    private fun getGrantedPermissions() =
        getRequestedPermissions().filter {
            EasyPermission.isGranted(
                this,
                it
            )
        }.toTypedArray()

    private fun getNotGrantedPermissions() =
        getRequestedPermissions().filter {
            !EasyPermission.isGranted(
                this,
                it
            )
        }.toTypedArray()


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val granted = ArrayList<String>()
        granted.addAll(getGrantedPermissions())
        val denied = ArrayList<String>()
        val permanentlyDenied = ArrayList<String>()
        permissions.forEachIndexed { index, permission ->
            if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                granted += permission
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    permanentlyDenied += permission
                } else {
                    denied += permission
                }
            }
        }
        sendResult(granted, denied, permanentlyDenied)
    }
}