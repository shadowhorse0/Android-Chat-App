package com.example.chatapp;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class chatapp{

    public  String result;
    public boolean request_complete;

    public String  endpoint(String data,String requstType) throws InterruptedException {
        request_complete = false;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("request_type",requstType)
                .addFormDataPart("data",data)
                .build();

        String url = "http://139.59.8.238/requests/endpoint.php";

        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("myapp",e.toString());
                Log.d("myapp","error occured in postr request");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    request_complete = true;
                    result = response.body().string();
                    Log.d("myapp",result);

                }
            }
        });

        while(!request_complete){
            TimeUnit.SECONDS.sleep(1);
        }
        return result;

    }
};
