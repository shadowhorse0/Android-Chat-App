package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class usermsg extends AppCompatActivity {
    Button sbtn;
    Button rbtn;
    EditText smsg;
    TextView rmsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        chatapp chatapp=new chatapp();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermsg);


        sbtn = findViewById(R.id.sbtn);
        smsg = findViewById(R.id.smsg);
        rbtn= findViewById(R.id.rbtn);
        rmsg = findViewById(R.id.rmsg);

        rmsg.setMovementMethod(new ScrollingMovementMethod());


        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("smsg","click on btn");
                Log.d("smsg",smsg.getText().toString());

                //making json object of all data like username and passoword to sent to server

                JSONObject data = new JSONObject();
                try{
                    data.put("sender","vaibhav");
                    data.put("receiver","pranav");
                    data.put("msg",smsg.getText().toString());


                }catch(JSONException e){
                    e.printStackTrace();
                }
                Log.d("smsg",data.toString());

                try {
                    //sending request to server
                    String myResponse = chatapp.endpoint(data.toString(), "smsg");
                    Log.d("smsg",myResponse);



                }catch (Exception e){
                    Log.d("smsg","error occured in api sent.");
                }
            }
        });

        //receive msg on click listener
        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject data = new JSONObject();
                try{
                    data.put("receiver","pranav");

                }catch(JSONException e){
                    e.printStackTrace();
                }
                Log.d("smsg",data.toString());



                try {
                    //sending request to server
                    String myResponse = chatapp.endpoint(data.toString(), "rmsg");
                    JSONObject response = new JSONObject(myResponse);
                    Log.d("rmsg",myResponse);
                    JSONArray msgs = response.getJSONArray("msgs");
                    String  strmsgs = "";
                    for(int i = 0; i < msgs.length(); i++)
                    {
                        Log.d("rmsg",msgs.get(i).toString());
                        strmsgs = strmsgs + msgs.get(i).toString() + "\n";

                    }
                    rmsg.setText(strmsgs);


                }catch (Exception e){
                    Log.d("smsg","error occured in api receiving.");
                }

            }

        });



            }

    }



