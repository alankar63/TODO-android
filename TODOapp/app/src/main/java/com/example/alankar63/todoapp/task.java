package com.example.alankar63.todoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class task extends AppCompatActivity {
    String value;
    EditText Description, Date,Time;
    Button Logout,All_tasks, T_week,T_Today,Add;
    Switch Pending;
    String fi = "true";
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            value = extras.getString("token");
            Log.d("print token",value);
        }
        setContentView(R.layout.activity_task);
        Description = (EditText)findViewById(R.id.editText3);
        Date = (EditText)findViewById(R.id.editText2);
        Add = (Button)findViewById(R.id.button7);
        All_tasks = (Button)findViewById(R.id.button6);
        Time = (EditText)findViewById(R.id.editText);
        Logout = (Button)findViewById(R.id.button3);
        T_week = (Button)findViewById(R.id.button5);
        T_Today = (Button)findViewById(R.id.button4);
        Pending = (Switch)findViewById(R.id.switch1);
        Pending.setChecked(true);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LogoutAsyncTask().execute();
            }
        });
        All_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new All_tasksAsyncTask().execute();
            }
        });
        T_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new T_weekAsyncTask().execute();
            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddAsyncTask().execute();
            }
        });
        T_Today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new T_todayAsyncTask().execute();
            }
        });

        Pending.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fi = "true";
                }else{
                    fi = "false";
                    Log.d("he",fi);
                }
            }
        });
    }

    private class LogoutAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData();
            return null;
        }

        protected void onPostExecute(Double result){

            //Toast.makeText(getApplicationContext(), "You are registered", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        public void postData() {
            // Create a new HttpClient and Post Header


            runOnUiThread(new Runnable() {
                public void run() {

                        //Log.d("success", "true");

                    Toast.makeText(task.this, "logged out successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(task.this,MainActivity.class);
                    startActivity(i);


                }
            });
        }

    }

    private class All_tasksAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData();
            return null;
        }

        protected void onPostExecute(Double result) {

            //Toast.makeText(getApplicationContext(), "You are registered", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        public void postData() {
            // Create a new HttpClient and Post Header


            runOnUiThread(new Runnable() {
                public void run() {

                    Log.d("success", "true");

                    Toast.makeText(task.this,"Fetching all tasks", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(task.this, All_tasks.class);
                    i.putExtra("token", value);
                    Log.d("everything fine",value);
                    startActivity(i);


                }
            });
        }
    }

    private class T_weekAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData();
            return null;
        }

        protected void onPostExecute(Double result) {

            //Toast.makeText(getApplicationContext(), "You are registered", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        public void postData() {
            // Create a new HttpClient and Post Header


            runOnUiThread(new Runnable() {
                public void run() {

                    //Log.d("success", "true");

                    Toast.makeText(task.this, "Fetching this week's tasks", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(task.this, Tasks_this_week.class);
                    i.putExtra("token", value);
                    startActivity(i);


                }
            });
        }


    }

    private class AddAsyncTask extends AsyncTask<String, Integer, Double> {


        @Override
        protected Double doInBackground(String... params) {

            // TODO Auto-generated method stub
            Log.d("yoyo","yyyyyyyyy");
            try {
                postData();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Double result) {

            //Toast.makeText(getApplicationContext(), "You are registered", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        public void postData() throws JSONException, IOException {
            Log.d("good","work");

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2:8000/api/v1/tasks/");

            JSONObject json = new JSONObject();
            json.put("name", Description.getText().toString());

            Log.d("pending","working");



            String date_time =Date.getText().toString() +"T"+ Time.getText().toString()+":00.000000Z";
            Log.d("time23",date_time.toString());
            if(date_time.equalsIgnoreCase("T:00.000000Z")) {
                Log.d("time",date_time.toString());
            }
            else
                json.put("scheduled_date_time", date_time);


            json.put("pending", fi.toString());
            Log.d("111111111","helllllllo");
            StringEntity se = new StringEntity( json.toString());
            httppost.setHeader("Authorization","Token " + value  );
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            Log.d("here", Pending.getText().toString() );
            httppost.setEntity(se);
            // Execute HTTP Post Request
            Log.d("here1", "work");

            HttpResponse response = httpclient.execute(httppost);
            StatusLine status = response.getStatusLine();
            Log.d("status",status.toString());
            Log.d("here2", "here 2 is printing");
            final String temp = EntityUtils.toString(response.getEntity());
            Log.d("value of temp",temp);

            // Create a new HttpClient and Post Header


            runOnUiThread(new Runnable() {
                public void run() {

                    //Log.d("success", "true");

                    Toast.makeText(task.this, "Adding task", Toast.LENGTH_LONG).show();
                   // Intent i = new Intent(task.this, Tasks_this_week.class);
                   // i.putExtra("token", value);
                    //startActivity(i);
                    if(temp.contains("201"))
                        Toast.makeText(task.this, "Task added successfully", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(task.this, "Wrong format ", Toast.LENGTH_LONG).show();

                }
            });



        }


    }

    private class T_todayAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData();
            return null;
        }

        protected void onPostExecute(Double result) {

            //Toast.makeText(getApplicationContext(), "You are registered", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        public void postData() {
            // Create a new HttpClient and Post Header


            runOnUiThread(new Runnable() {
                public void run() {

                    //Log.d("success", "true");

                    Toast.makeText(task.this, "Fetching today's tasks", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(task.this, Tasks_today.class);
                    i.putExtra("token", value);
                    startActivity(i);


                }
            });
        }


    }
}
