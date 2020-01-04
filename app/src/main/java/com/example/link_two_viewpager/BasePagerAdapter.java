package com.example.link_two_viewpager;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * @author hjy
 * @date 2019/11/16
 * @des
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {

    protected List<T> list;
    protected SparseArray<BasePagerHolder<T>> sparseArray;

    public BasePagerAdapter(List<T> list) {
        this.list = list;
        sparseArray = new SparseArray<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public String getIcon(int position) {
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePagerHolder<T> holder = sparseArray.get(getSparsePosition(position));
        if (null == holder) {
            holder = onBindHolder(container);
            holder.setPosition(position);
            holder.bindDada(list.get(position), position);
            sparseArray.put(getSparsePosition(position), holder);
        }
        View view = holder.itemView;
        container.addView(view);
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        sparseArray.clear();
        super.notifyDataSetChanged();
    }

    protected int getSparsePosition(int position) {
        return (position + 10) * 2;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    protected View inflate(int resorce, ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(resorce, viewGroup, false);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    protected abstract BasePagerHolder<T> onBindHolder(ViewGroup container);

    public void onDestroy() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    protected static abstract class BasePagerHolder<D> {
        /**
         * 上下文
         */
        protected Context mContext;
        private int position;

        SparseArray<View> sparseArray;
        View itemView;

        protected BasePagerHolder(View itemView) {
            this.itemView = itemView;
            this.mContext = itemView.getContext();
            sparseArray = new SparseArray<>();
            initView();
        }

        public abstract void initView();

        public View findViewById(int id) {
            View view = sparseArray.get(id);
            if (null == view) {
                view = itemView.findViewById(id);
                sparseArray.append(id, view);
            }
            return view;
        }

        public abstract void bindDada(D t, int position);

        public void onActivityResult(int requestCode, int resultCode, Intent data) {

        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public void onDestroy() {
            sparseArray.clear();
        }
    }
}