package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {
    TextView result;
    EditText username;
    EditText email;
    EditText phone;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        result = findViewById(R.id.response);
        username = findViewById(R.id.username);
        email= findViewById(R.id.email);
        phone= findViewById(R.id.phone);
        password = findViewById(R.id.password);

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.textSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        findViewById(R.id.singup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject data = new JSONObject();
                try{
                    data.put("username",username.getText().toString());
                    data.put("email",email.getText().toString());
                    data.put("phone",phone.getText().toString());
                    data.put("password",password.getText().toString());
                }catch(JSONException e){
                    e.printStackTrace();
                }

                //trying post request
                OkHttpClient client = new OkHttpClient();

                String url = "https://reqres.in/api/users?page=2";

                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                            if(response.isSuccessful()){
                                final String myResponse = response.body().string();
                                SignupActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        result.setText(myResponse);
                                    }
                                });
                            }
                    }
                });


            }




        });
    }
}