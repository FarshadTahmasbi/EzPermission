package com.androidisland.ezpermission.sample.view.activity

import android.Manifest
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidisland.ezpermission.EzPermission
import com.androidisland.ezpermission.sample.R
import kotlinx.android.synthetic.main.activity_sample.*


class SampleActivity : AppCompatActivity() {

    private val permissionPattern = ".*\\.(.+)".toRegex()

    private val permissions = arrayOf(
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.GET_ACCOUNTS,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_SMS
    )


    private val permissionAdapter = PermissionAdapter {
        when (it) {
            R.id.goBtn -> {
                openPermissionDialog()
            }
            R.id.settings_txt -> {
                startActivity(EzPermission.appDetailSettingsIntent(this))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        with(permissionList) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = permissionAdapter
            itemAnimator = null
            val decoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
            addItemDecoration(decoration)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_clear) {
            permissionAdapter.clear()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openPermissionDialog() {
        val permissionList = mutableListOf<String>()
        AlertDialog.Builder(this)
            .setMultiChoiceItems(
                permissions.map { permissionPattern.matchEntire(it)?.groups?.get(1)?.value }.toTypedArray(),
                null
            ) { dialog, which, isChecked ->
                if (isChecked) permissionList += permissions[which]
                else permissionList -= permissions[which]
            }
            .setTitle(R.string.title_choose_permissions)
            .setPositiveButton(R.string.title_req_btn) { _, _ ->
                askPermissions(permissionList)
            }
            .setNegativeButton(R.string.title_cancel_btn, null)
            .create()
            .show()
    }

    private fun askPermissions(permissionList: MutableList<String>) {
        EzPermission.with(this)
            .permissions(permissionList)
            .request { granted, denied, permanentlyDenied ->
                permissionList.forEach {
                    if (granted.contains(it)) {
                        permissionAdapter.add(PermissionModel(it, PermissionModel.GRANTED))
                    } else if (denied.contains(it)) {
                        permissionAdapter.add(PermissionModel(it, PermissionModel.DENIED))

                    } else if (permanentlyDenied.contains(it)) {
                        permissionAdapter.add(PermissionModel(it, PermissionModel.PERMANENTLY_DENIED))
                    }
                }
            }
    }
}
