package com.edu.niit.newsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PersonalActivity extends Activity {
    public static final int CHOOSE_PICTURE = 0;
    public static final int TAKE_PICTURE = 1;
    public static final int CROP_SMALL_PIC = 2;
    private Uri uri;
    private ImageView img_head;
    private Bitmap bitmap;//头像bitmap
    String sdStatus = Environment.getExternalStorageState();//表示用于开发的手机现有的SD卡挂载状态
    private static String path = "/sdcard/myHead/";//sd路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity);
        img_head = (ImageView) findViewById(R.id.img_head);
        img_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoosePicDialog();
            }
        });
    }

    private void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("上传头像");
        String[] items = {"本地图片", "拍照"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case CHOOSE_PICTURE://打开本地相册
                        Intent localIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        localIntent.setType("image/*");
                        startActivityForResult(localIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE://拍照
                        if (sdStatus.equals(Environment.MEDIA_MOUNTED)) {//判断sd卡是否存在
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath(), "head.jpg"));
                            // 指定照片保存路径（SD卡），head.jpg为一个临时文件，每次拍照后这个图片都会被替换
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(cameraIntent, TAKE_PICTURE);
                        } else {
                            Toast.makeText(PersonalActivity.this, "sd卡不存在", Toast.LENGTH_SHORT).show();
                        }

                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {//如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(uri); //开始对图片进行剪裁处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData());//开始对图片进行剪裁处理
                    break;
                case CROP_SMALL_PIC:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        bitmap = extras.getParcelable("data");
                        if (bitmap != null) {
                            setPicToView(bitmap);//把刚才选择剪裁的图片显示在界面上
                            img_head.setImageBitmap(bitmap);
                        }
                    }
                    break;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
        剪彩图片的方法实现
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//跳转到Android系统自带的一个图片剪裁页面
        intent.setDataAndType(uri, "image/*");
        //设置剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);//是否将数据保留在Bitmap中返回
        startActivityForResult(intent, CROP_SMALL_PIC);
    }

    //保存剪裁之后的图片数据
    private void setPicToView(Bitmap mBitmap) {
        FileOutputStream fileOutputStream = null;
        File file = new File(path);
        file.mkdir();//创建文件
        String fileName = path + "head.jpg";
        try {
            fileOutputStream = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);//把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                fileOutputStream.flush();//刷新此输出流并强制将所有缓冲的输出字节被写出
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
