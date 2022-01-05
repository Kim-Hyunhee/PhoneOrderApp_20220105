package com.nepplus.phoneorderapp_20220105

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.nepplus.phoneorderapp_20220105.models.StoreData
import kotlinx.android.synthetic.main.activity_view_store_detail.*

class ViewStoreDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_store_detail)

        val storeData = intent.getSerializableExtra("store") as StoreData

        txtStoreName.text = storeData.name
        txtPhoneNum.text = storeData.phoneNum
        Glide.with(this).load(storeData.logoURL).into(imgLogo)

        btnCall.setOnClickListener{

            val pl = object : PermissionListener{
                override fun onPermissionGranted() {

                    val myUri = Uri.parse("tel:${storeData.phoneNum}")
                    val myIntent = Intent(Intent.ACTION_CALL, myUri)
                    startActivity(myIntent)

                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                    Toast.makeText(
                        this@ViewStoreDetailActivity,
                        "권한이 거절되어 전화 연결이 불가능합니다.",
                        Toast.LENGTH_SHORT
                    ).show()

                }


            }

            TedPermission.create()
                .setPermissionListener(pl)
                .setPermissions(Manifest.permission.CALL_PHONE)
                .check()

        }

    }
}