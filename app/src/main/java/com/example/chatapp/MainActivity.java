package com.example.chatapp;

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
                try{
                    //disabling button at start
                    signin.setText("Processing...");


                    //making json object of all data like username and passoword to sent to server

                    JSONObject data = new JSONObject();
                    try{
                        data.put("signin_username",signin_username.getText().toString());


                        data.put("signin_password",signin_password.getText().toString());
                    }catch(JSONException e){
                        e.printStackTrace();
                    }

                    //sending request to server
                    String myResponse=chatapp.endpoint(data.toString(),"signin");
                    JSONObject response = new JSONObject(myResponse);

                    //handling if response is fail
                    Log.d("myapp",response.toString());
                    if(!response.getBoolean("status")){
                        throw new Exception(response.getString("msg"));
                    }

                    //showing success message if everything is okay
                    Toast.makeText(MainActivity.this,  response.getString("msg"), Toast.LENGTH_SHORT).show();





                }catch (Exception e){
                    Toast.makeText(MainActivity.this,  e.getMessage(), Toast.LENGTH_SHORT).show();
                }






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