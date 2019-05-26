package com.androidisland.tmb.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf

class PermissionManager {

    companion object {

        fun isMarshmallowOrLater() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

        fun isGranted(
            context: Context,
            permission: String
        ) = ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

        fun shouldShowRationale(
            activity: Activity,
            permission: String
        ) = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)


        internal fun isPermanentlyDenied(
            activity: Activity,
            permission: String
        ) = ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED &&
                !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)

        fun with(context: Context) =
            PermissionRequest(context)
    }

    class PermissionRequest(private val context: Context) {

        private val permissions = mutableSetOf<String>()

        fun permissions(vararg permissions: String): PermissionRequest {
            this.permissions.clear()
            this.permissions.addAll(permissions)
            return this
        }

        fun request(
            listener: (
                granted: List<String>,
                denied: List<String>,
                permanentlyDenied: List<String>
            ) -> Unit
        ) {
            val intent = Intent(context, RuntimePermissionActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtras(
                    bundleOf(
                        PermissionResult.EXTRA_REQUESTED_PERMISSIONS to permissions.toSet().toTypedArray(),
                        PermissionResult.EXTRA_RESULT_RECEIVER to PermissionResult(
                            listener
                        )
                    )
                )
            context.startActivity(intent)
        }
    }
}