package com.androidisland.ezpermission.sample.view.activity

data class PermissionModel(val permission: String, val result: Int) {

    companion object {
        const val GRANTED = 1
        const val DENIED = 2
        const val PERMANENTLY_DENIED = 3
    }

    fun isGranted() = result == GRANTED
    fun isPermanentlyDenied() = result == PERMANENTLY_DENIED
}