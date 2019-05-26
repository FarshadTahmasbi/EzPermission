package com.androidisland.sample

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.androidisland.tmb.permission.PermissionManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionBtn.setOnClickListener {
            PermissionManager.with(this)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA)
                .request { granted, denied, permanentlyDenied ->
                    Log.d("test123", "granted -> $granted, denied ->$denied, permanentDeny -> $permanentlyDenied")
                }
        }
    }
}
