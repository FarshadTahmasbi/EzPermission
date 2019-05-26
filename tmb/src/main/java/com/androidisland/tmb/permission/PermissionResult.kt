package com.androidisland.tmb.permission

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver

internal class PermissionResult(private val listener: (List<String>, List<String>, List<String>) -> Unit) :
    ResultReceiver(Handler(Looper.getMainLooper())) {

    companion object {
        const val CODE_PERMISSION = 1313
        const val EXTRA_RESULT_RECEIVER = "result_receiver"
        const val EXTRA_REQUESTED_PERMISSIONS = "requested_permissions"
        const val EXTRA_GRANTED_PERMISSIONS = "granted_permissions"
        const val EXTRA_DENIED_PERMISSIONS = "denied_permissions"
        const val EXTRA_PERMANENTLY_DENIED_PERMISSIONS = "permanently_denied_permissions"
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        super.onReceiveResult(resultCode, resultData)
        var granted = ArrayList<String>()
        var denied = ArrayList<String>()
        var permanentlyDenied = ArrayList<String>()
        resultData?.apply {
            granted = getStringArrayList(EXTRA_GRANTED_PERMISSIONS) ?: ArrayList()
            denied = getStringArrayList(EXTRA_DENIED_PERMISSIONS) ?: ArrayList()
            permanentlyDenied = getStringArrayList(EXTRA_PERMANENTLY_DENIED_PERMISSIONS) ?: ArrayList()
        }
        listener.invoke(granted, denied, permanentlyDenied)
    }
}