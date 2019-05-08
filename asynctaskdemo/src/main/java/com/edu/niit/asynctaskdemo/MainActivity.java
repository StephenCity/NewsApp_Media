package com.edu.niit.asynctaskdemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private ImageView imageView;
    private ProgressDialog progressDialog;
    private final String IMAGE_PATH ="http://www.baidu.com/img/bd_logo1.png";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.loadButton);
        imageView = (ImageView)findViewById(R.id.imageView);
        // 弹出ProgressDialog
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("提示信息");
        progressDialog.setMessage("正在下载中，请稍后......");
        //下面的设置表示不能取消这个弹出框，等下载完成之后再让弹出框消失
        progressDialog.setCancelable(false);
        //设置ProgressDialog样式为圆圈的形式
        progressDialog.setProgressStyle(ProgressDialog.
                STYLE_SPINNER);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                //在UI Thread当中实例化AsyncTask对象，并调用execute方法
                new MyAsyncTask().execute(IMAGE_PATH);
            }
        });
    }

}
