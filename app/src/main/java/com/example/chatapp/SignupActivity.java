package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




public class SignupActivity extends AppCompatActivity {

    Button signup;
    EditText username;
    EditText email;
    EditText phone;
    EditText password;
    EditText cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        chatapp chatapp=new chatapp();



        signup = findViewById(R.id.signup);
        cpassword = findViewById(R.id.cpassword);
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


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //disabling button at start

                    //check if password and cpassword are same
                    if(!((cpassword.getText().toString()).equals(password.getText().toString()))){
                        throw new Exception("password and confirm password do not match!");
                    }

                    //making json object of all data like username,email, passoword to sent to server

                    JSONObject data = new JSONObject();
                    try{
                    data.put("username",username.getText().toString());
                    data.put("email",email.getText().toString());
                    data.put("phone",phone.getText().toString());
                    data.put("password",password.getText().toString());
                    }catch(JSONException e){
                    e.printStackTrace();
                    }

                    //sending request to server
                    String myResponse=chatapp.endpoint(data.toString(),"signup");
                    JSONObject response = new JSONObject(myResponse);

                    //handling if response is fail
                   Log.d("myapp",response.toString());
                   if(!response.getBoolean("status")){
                       throw new Exception(response.getString("msg"));
                   }

                   //showing success message if everything is okay
                    Toast.makeText(SignupActivity.this,  response.getString("msg"), Toast.LENGTH_SHORT).show();


                   startActivity(new Intent(getApplicationContext(),MainActivity.class));


                }catch (Exception e){
                    Toast.makeText(SignupActivity.this,  e.getMessage(), Toast.LENGTH_SHORT).show();
                }


                //trying post request

//                String myResponse=chatapp.endpoint(data.toString(),"signup");
//
//                try {
//                    JSONObject response = new JSONObject(myResponse);
//                    Log.d("myapp",response.getString("msg"));
//                    if(response.getBoolean("status")){
//                        Toast.makeText(SignupActivity.this,  "true", Toast.LENGTH_SHORT).show();
//
//                    }else{
//                        Toast.makeText(SignupActivity.this,  "false", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


//                JSONObject res=  JSONObject (JSONObject copyFrom,
//                        String[] myResponse);

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


