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

                Runnable r = new Runnable() {
                    public void run() {
                        try{
                            //disabling button at start
                            signup.setText("Processing...");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    signup.setEnabled(false);
                                }
                            });


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
                            String myResponse=chatapp.endpoint(data.toString(),"signup",SignupActivity.this);
                            JSONObject response = new JSONObject(myResponse);

                            //handling if response is fail
                            Log.d("myapp",response.toString());
                            if(!response.getBoolean("status")){
                                throw new Exception(response.getString("msg"));
                            }

                            //showing success message if everything is okay
                            SignupActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Log.d("UI thread", "I am the UI thread");
                                    signup.setText("SIGN_IN");
                                    try {
                                        Toast.makeText(SignupActivity.this,  response.getString("msg"), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));


                        }catch (Exception e){
                            SignupActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Log.d("UI thread", "I am the UI thread");
                                    signup.setText("CREATE ACCOUNT");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            signup.setEnabled(true);
                                        }
                                    });
                                    Toast.makeText(SignupActivity.this,  e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                };

                new Thread(r).start();

            }
        });
    }
}


