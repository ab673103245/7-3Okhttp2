package qianfeng.a7_3okhttp2;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        okHttpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build();

    }

    public void json(View view) { // json字符串的上传


        //可百度一下MediaType的类型。 application/json;charset=utf-8
        MediaType jsonType = MediaType.parse("application/json;charset=utf-8");

        JSONObject json = null;
        try {
            json = new JSONObject();

            json.put("username","Yongzhen");
            json.put("password","456");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonType, json.toString());
        Request request = new Request.Builder()
                .post(body)
                .url("http://10.16.153.35:8080/upload")
                .build();
        
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                {
                    Log.d("google-my:", "onResponse: " + response.body().string());
                }
            }
        });

    }


    public void pic(View view) { // 图片的上传

        //可百度一下MediaType的类型。 application/octet-stream
        MediaType jsonType = MediaType.parse("application/octet-stream");


        RequestBody body = RequestBody.create(jsonType, new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"a8.jpg"));

        Request request = new Request.Builder()
                .post(body)
                .url("http://10.16.153.35:8080/upload")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful())
                {
                    Log.d("google-my:", "onResponse: " + response.body().string());
                }
            }
        });
    }
}
