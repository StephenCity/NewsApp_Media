package com.edu.niit.newsapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //侧边栏相关
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private String[] titles = {"新闻", "视频", "个人中心"};
    private ListView listView;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Map<String, String>> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉actionbar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();//toolbar和Drawerlayout相关

        ViewPager vp = (ViewPager) findViewById(R.id.news_viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablyout);

        List<String> list = new ArrayList<>();
        list.add("社会新闻");
        list.add("国家新闻");
        list.add("体育新闻");
        list.add("娱乐新闻");
        vp.setAdapter(new MyAdapter(getSupportFragmentManager(),list));

        tabLayout.setupWithViewPager(vp);

    }

    public class MyAdapter extends FragmentPagerAdapter {
        private List<String> list;

        public MyAdapter(FragmentManager fm, List<String> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return NewsFragment.newInstance(list.get(position));
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position);
        }
    }


    //toolbar和drawerlayout相关
    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        listView = (ListView) findViewById(R.id.lv_navdrawer);
        if (toolbar != null) {
            toolbar.setTitle("Toolbar");//设置Toolbar标题
            toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
            setSupportActionBar(toolbar);//取代原本的actionbar
            //getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //toolbar.setNavigationIcon(R.mipmap.ic_ab_drawer);

        }
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, titles);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        startActivity(new Intent(MainActivity.this, MultimediaActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, PersonalActivity.class));
                        break;
                }
            }
        });

        //drawerLayout.addDrawerListener(drawerToggle);
        //创建返回键，并实现打开关/闭监听
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                showDialog(true);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                showDialog(false);
            }
        };
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

    }
    public void showDialog(boolean a){
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(MainActivity.this);

        normalDialog.setTitle("我是一个新闻播报");
        normalDialog.setMessage("这是今日新闻");

        AlertDialog dialog = normalDialog.show();
        if (!a){
            dialog.dismiss();
        }

    }


}
