package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

//db import files
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
    EditText smsg,ureceiver;
    TextView rmsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLiteDatabase chatappdb = openOrCreateDatabase("chatappdb",MODE_PRIVATE,null);

        Cursor resultSet = chatappdb.rawQuery("Select * from info",null);
        resultSet.moveToFirst();
        String username = resultSet.getString(0);
        String password = resultSet.getString(1);
        Log.d("chatappdb",username+" " +password);


        chatapp chatapp=new chatapp();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermsg);


        sbtn = findViewById(R.id.sbtn);
        smsg = findViewById(R.id.smsg);
        rbtn= findViewById(R.id.rbtn);
        rmsg = findViewById(R.id.rmsg);
        ureceiver = findViewById(R.id.ureceiver);

        rmsg.setMovementMethod(new ScrollingMovementMethod());


        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_receiver = ureceiver.getText().toString();
                Log.d("smsg","click on btn");
                Log.d("smsg",smsg.getText().toString());

                //making json object of all data like username and passoword to sent to server

                JSONObject data = new JSONObject();
                try{
                    data.put("sender",username);
                    data.put("receiver",username_receiver);
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
                    data.put("receiver",username);

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
                        JSONArray msg = msgs.getJSONArray(i);
                        Log.d("rmsg",msg.toString());
                        strmsgs = strmsgs + msg.get(1).toString() + " : "+ msg.get(3)+" - "+msg.get(4)+"\n";

                    }
                    rmsg.setText(strmsgs);


                }catch (Exception e){
                    Log.d("smsg","error occured in api receiving.");
                }

            }

        });



            }

    }



