package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import org.json.JSONObject;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




public class SignupActivity extends AppCompatActivity {


    EditText username;
    EditText email;
    EditText phone;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        chatapp chatapp=new chatapp();





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

                String myResponse=chatapp.endpoint(data.toString(),"signup");



//                JSONObject res=  JSONObject (JSONObject copyFrom,
//                        String[] myResponse);



                Toast.makeText(SignupActivity.this,  myResponse, Toast.LENGTH_SHORT).show();


                //SignupActivity.this.runOnUiThread(new Runnable() {
                  //  @Override
                    //public void run() {
                      //  result.setText(myResponse);
                    //}
                //});




            }




        });
    }
}

