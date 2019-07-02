package com.androidisland.ezpermission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf


class EzPermission {

    companion object {

        /**
         * Checks if runtime permissions are needed or not
         * @return true if android version is M or later
         */
        fun isMarshmallowOrLater() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

        /**
         *Checks if the permission is already granted
         * @return true if permission is granted
         */
        fun isGranted(
            context: Context,
            permission: String
        ) = ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

        /**
         * Checks whether you should show UI with rationale for requesting a permission
         * @return true if need a rationale for request
         */
        fun shouldShowRationale(
            activity: Activity,
            permission: String
        ) = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)


        internal fun isPermanentlyDenied(
            activity: Activity,
            permission: String
        ) = ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED &&
                !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)

        /**
         * Creates an intent to open app settings activity, it come in handy
         * in case that permission is permanently denied and you need
         * to inform and redirect user to app settings
         * @return an intent that redirects user to app settings
         */
        fun appDetailSettingsIntent(context: Context): Intent {
            return Intent().apply {
                action = ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", context.packageName, null)
            }
        }

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

        fun permissions(permissions: Collection<String>): PermissionRequest {
            this.permissions.clear()
            this.permissions.addAll(permissions)
            return this
        }

        fun request(
            listener: (
                granted: Set<String>,
                denied: Set<String>,
                permanentlyDenied: Set<String>
            ) -> Unit
        ) {
            if (permissions.none { permission ->
                    !isGranted(context, permission)
                }) {
                //Already have all permissions, no need to start activity
                listener.invoke(permissions.toSet(), setOf(), setOf())
            } else {
                val intent = Intent(context, EzPermissionActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtras(
                        bundleOf(
                            EzResult.EXTRA_REQUESTED_PERMISSIONS to permissions.distinct().toTypedArray(),
                            EzResult.EXTRA_RESULT_RECEIVER to EzResult(
                                listener
                            )
                        )
                    )
                context.startActivity(intent)
            }
        }
    }
}