package com.example.chatapp;

import java.io.IOException;
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

    public String  endpoint(String data,String requstType){




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
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    result = response.body().string();

                }
            }
        });



        return result;
    }
};
