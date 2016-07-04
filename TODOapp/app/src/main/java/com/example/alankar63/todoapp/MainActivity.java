package com.example.alankar63.todoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    EditText Username, Password;
    Button Login,Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = (EditText)findViewById(R.id.username);
        Password = (EditText)findViewById(R.id.password);
        Login = (Button)findViewById(R.id.button);
        Register = (Button)findViewById(R.id.button2);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyAsyncTask().execute();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LogAsyncTask().execute();
            }
        });

    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

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
            Log.d("here", "working now");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2:8000/api/v1/users/");



            try {

                JSONObject json = new JSONObject();
                json.put("username", Username.getText().toString());
                json.put("password", Password.getText().toString());
                StringEntity se = new StringEntity( json.toString());
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                Log.d("here", Password.getText().toString() );
                httppost.setEntity(se);
                // Execute HTTP Post Request
                Log.d("here1", "work");

                HttpResponse response = httpclient.execute(httppost);
                Log.d("here2", "here 2 is printing");
                final String temp = EntityUtils.toString(response.getEntity());
                Log.i("tag", temp);
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (temp.contains("already"))
                            //Log.d("success", "true");
                            Toast.makeText(MainActivity.this, "Username already exists", Toast.LENGTH_LONG).show();


                        else
                            //Log.d("success", "false");
                            Toast.makeText(MainActivity.this, "successfully registered", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    private class LogAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData();
            return null;
        }

        protected void onPostExecute(Double result){

           // Toast.makeText(getApplicationContext(), "logged in successfully", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        public void postData() {
            // Create a new HttpClient and Post Header
            Log.d("here", "working now");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2:8000/api/v1/api-token-auth/");




            try {

                JSONObject json = new JSONObject();
                json.put("username", Username.getText().toString());
                json.put("password", Password.getText().toString());
                StringEntity se = new StringEntity( json.toString());
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                Log.d("here", Password.getText().toString() );
                httppost.setEntity(se);
                // Execute HTTP Post Request
                Log.d("here1", "work");

                HttpResponse response = httpclient.execute(httppost);
                Log.d("here2", "here 2 is printing");
                final String temp = EntityUtils.toString(response.getEntity());
;               Log.d("value of temp",temp);



                runOnUiThread(new Runnable() {
                    public void run() {
                        if(temp.contains("non_field_errors"))
                            Toast.makeText(MainActivity.this, "Invalid login credentials", Toast.LENGTH_LONG).show();
                        else {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(temp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String token = null;
                            try {
                                token = jsonObject.getString("token");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(MainActivity.this, "logged in successfully", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(MainActivity.this, task.class);
                            i.putExtra("token", token);
                            startActivity(i);
                        }}
                });


                Log.i("tag", temp);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}



