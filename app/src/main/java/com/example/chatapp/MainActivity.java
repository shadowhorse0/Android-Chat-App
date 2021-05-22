package com.example.chatapp;

//db import files
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

public class MainActivity extends AppCompatActivity {
    Button signin;
    EditText signin_username;
    EditText signin_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //creating table info in db
        SQLiteDatabase chatappdb = openOrCreateDatabase("chatappdb",MODE_PRIVATE,null);
        chatappdb.execSQL("CREATE TABLE IF NOT EXISTS 'info'('Username' VARCHAR,'Password' VARCHAR);");
        chatappdb.execSQL("DELETE FROM 'info'");
        chatappdb.execSQL("VACUUM");



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        chatapp chatapp=new chatapp();

        signin = findViewById(R.id.signin);
        signin_username = findViewById(R.id.signin_username);
        signin_password = findViewById(R.id.signin_password);

        //trrryyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = signin_username.getText().toString();
                String password = signin_password.getText().toString();


                Runnable r = new Runnable() {
                    public void run() {
                        Log.d("myapp","working thread");
                        try{
                            //disabling button at start
                            signin.setText("Processing...");

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    signin.setEnabled(false);
                                }
                            });



                            //making json object of all data like username and passoword to sent to server

                            JSONObject data = new JSONObject();
                            try{
                                data.put("signin_username",username);


                                data.put("signin_password",password);
                            }catch(JSONException e){
                                e.printStackTrace();
                            }

                            //sending request to server
                            String myResponse=chatapp.endpoint(data.toString(),"signin");
                            JSONObject response = new JSONObject(myResponse);

                            //handling if response is fail
                            if(!response.getBoolean("status")){
                                throw new Exception(response.getString("msg"));
                            }

                            Log.d("chatappdb",username+" "+password);
                            chatappdb.execSQL("INSERT INTO info VALUES('"+username+"','"+password+"');");


                            //showing success message if everything is okay
                            //
                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        Toast.makeText(MainActivity.this,  response.getString("msg"), Toast.LENGTH_SHORT).show();
                                        //redirecting to welcome page

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                signin.setEnabled(true);
                                            }
                                        });
                                        signin.setText("SIGN_IN");
                                        startActivity(new Intent(getApplicationContext(),usermsg.class));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });



                        }catch (Exception e){
                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Log.d("UI thread", "I am the UI thread");
                                    signin.setText("SIGN_IN");

                                    //dusdgv
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            signin.setEnabled(true);
                                        }
                                    });


                                    Toast.makeText(MainActivity.this,  e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                           // Toast.makeText(MainActivity.this,  e.getMessage(), Toast.LENGTH_SHORT).show();
                        }




                    }
                };

                new Thread(r).start();


            }




        });






        //intent for signup page
        findViewById(R.id.textCreateAccount).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }



        });
    }

}