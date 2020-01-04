package com.example.link_two_viewpager;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cameraapp.R;

import java.util.List;

/**
 * @author hjy
 * @date 2019/11/16
 * @des
 */
public class FrontPagerAdapter extends BasePagerAdapter<String> {


    int[] images = {
            R.mipmap.front1,
            R.mipmap.front2,
            R.mipmap.front3
    };


    public FrontPagerAdapter(List<String> list) {
        super(list);
    }

    @Override
    protected BasePagerHolder<String> onBindHolder(ViewGroup container) {
        return new BasePagerHolder<String>(inflate(R.layout.layout_front_item, container)) {
            private ImageView image;

            @Override
            public void initView() {
                image = (ImageView) findViewById(R.id.image);
            }

            @Override
            public void bindDada(String t, int position) {
                image.setBackgroundResource(images[position]);
            }
        };
    }
}
