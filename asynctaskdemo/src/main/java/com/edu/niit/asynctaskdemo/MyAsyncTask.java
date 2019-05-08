package com.edu.niit.asynctaskdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by annie on 2016/9/29.
 */
public class MyAsyncTask extends AsyncTask<String, Integer, byte[]>
{
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        //把ProgressDialog显示出来
        progressDialog.show();
    }
    @Override
    protected byte[] doInBackground(String... params)
    {
        //通过Apache的HttpClient来访问请求网络中的一张图片

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(params[0]);
        byte[] image = new byte[]{};
        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if(httpEntity != null &&
                    response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                image = EntityUtils.toByteArray(httpEntity);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }
        return image;
    }
    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(byte[] result)
    {
        super.onPostExecute(result);
        // 将doInBackground方法返回的byte[]解码成要给Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0,
                result.length);
        //更新ImageView控件
        imageView.setImageBitmap(bitmap);
        //使ProgressDialog框消失
        progressDialog.dismiss();
    }
}

