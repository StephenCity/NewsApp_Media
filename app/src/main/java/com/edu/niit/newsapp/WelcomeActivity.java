package com.edu.niit.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by xsl on 2016/9/23.
 */

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager vp;
    private Button btn_welcome;

    private int imgs[] = {R.mipmap.welcome_1, R.mipmap.welcome_2, R.mipmap.welcome_3};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ay_welcome);
        //初始化控件
        vp = (ViewPager) findViewById(R.id.vp);
        btn_welcome = (Button) findViewById(R.id.btn_welcome);

        //实现自己的PagerAdapter方法
        MyPagerAdapter pagerAdapter = new MyPagerAdapter();
        vp.setAdapter(pagerAdapter);
        //对滑动进行监听
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {//判断当滑动到第三页时进行操作
                    btn_welcome.setVisibility(View.VISIBLE);//设置控件为可看见
                    btn_welcome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));//窗体跳转
                            finish();//结束本窗体
                        }
                    });
                } else {
                    btn_welcome.setVisibility(View.GONE);//否则不可见
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {//返回页面的数量
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {//实例化页面
            ImageView imageView = new ImageView(WelcomeActivity.this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(imgs[position]);
            container.addView(imageView);//添加页面
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);//移除页面
        }
    }
}
