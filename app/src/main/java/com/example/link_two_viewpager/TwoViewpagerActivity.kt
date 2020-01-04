package com.example.link_two_viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cameraapp.R
import kotlinx.android.synthetic.main.activity_two_viewpager.*

class TwoViewpagerActivity : AppCompatActivity() {


    var texts = listOf<String>("高人气图片写真1", "高人气图片写真2", "高人气图片写真xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx3")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_viewpager)

        frontPager.setViewPager(backPager);
        backPager.setViewPager(frontPager);
        backPager.setAdapter(BackPagerAdapter(texts));
        frontPager.setAdapter(FrontPagerAdapter(texts));


    }
}
