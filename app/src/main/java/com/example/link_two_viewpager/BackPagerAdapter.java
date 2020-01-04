package com.example.link_two_viewpager;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cameraapp.R;

import java.util.List;

/**
 * @author hjy
 * @date 2019/11/16
 * @des
 */
public class BackPagerAdapter extends BasePagerAdapter<String> {


    int[] images = {
            R.mipmap.back1,
            R.mipmap.back2,
            R.mipmap.back3
    };

    String[] texts = {
            "高人气图片写真1",
            "高人气图片写真2",
            "高人气图片写真3",
    };


    public BackPagerAdapter(List<String> list) {
        super(list);
    }

    @Override
    protected BasePagerHolder<String> onBindHolder(ViewGroup container) {
        return new BasePagerHolder<String>(inflate(R.layout.layout_back_item, container)) {
            private ImageView image;
            private TextView text;

            @Override
            public void initView() {
                image = (ImageView) findViewById(R.id.image);
                text = (TextView) findViewById(R.id.text);
            }

            @Override
            public void bindDada(String t, int position) {
                image.setBackgroundResource(images[position]);
                text.setText(texts[position]);
            }
        };
    }
}
