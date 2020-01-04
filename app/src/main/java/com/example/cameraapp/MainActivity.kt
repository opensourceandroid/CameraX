package com.example.cameraapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.link_two_viewpager.TwoViewpagerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_camera.setOnClickListener {
            // startActivity(Intent(MainActivity@this, CameraxActivity::class.java))
            startActivity<CameraxActivity>()
        }


        btn_two_viewpager.setOnClickListener({
            startActivity<TwoViewpagerActivity>()
        })

    }


}


