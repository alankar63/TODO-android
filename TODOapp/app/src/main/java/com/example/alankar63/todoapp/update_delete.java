package com.example.alankar63.todoapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class update_delete extends AppCompatActivity {

    EditText description, dat,tim;
    Button updat,delet;
    String value;
    String id;
    String tem;
    String tempii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        Log.d("work", "nice");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("token");
            id = extras.getString("id");
            Log.d("print id", id);
        }
        description = (EditText)findViewById(R.id.editText6);
        dat = (EditText)findViewById(R.id.editText5);
        tim = (EditText)findViewById(R.id.editText4);
        updat = (Button)findViewById(R.id.button9);
        delet = (Button)findViewById(R.id.button8);

        new GetAsyncTask().execute();




        //se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));



        updat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new updAsyncTask().execute();
            }
        });

        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new delAsyncTask().execute();
            }
        });
    }


    private class GetAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                prepareMovieData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Double result){

            //Toast.makeText(getApplicationContext(), "You are registered", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        private void prepareMovieData() throws IOException {

            Log.d("here", "working now");
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://10.0.2.2:8000/api/v1/tasks/"+id);

            //se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpget.setHeader("Authorization","Token " + value  );
            HttpResponse response = httpclient.execute(httpget);
            Log.d("here2", "here 2 is printing");
            final String temp = EntityUtils.toString(response.getEntity());
            Log.d("value of temp",temp);

            try {
                JSONObject jsonObject = new JSONObject(temp);
                String scheduled_date_time = jsonObject.optString("scheduled_date_time").toString();
                String name = jsonObject.optString("name").toString();
                String pend = jsonObject.optString("pending").toString();
                int iend = scheduled_date_time.indexOf("T");
                int rend = scheduled_date_time.indexOf("Z");

                description.setText(name);
                dat.setText(scheduled_date_time.substring(0 , iend));
                tim.setText(scheduled_date_time.substring(iend+1 , rend));

                //Iterate the jsonArray and print the info of JSONObjects



            } catch (JSONException e) {
                e.printStackTrace();
            }








        }
    }

    private class updAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                postData();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Double result){

            //Toast.makeText(getApplicationContext(), "You are registered", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        public void postData() throws JSONException, IOException {
            // Create a new HttpClient and Post Header
            Log.d("here", "working now");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPut httppost = new HttpPut("http://10.0.2.2:8000/api/v1/tasks/"+id);

            JSONObject json = new JSONObject();
            json.put("name", description.getText().toString());

            Log.d("pending","working");



            String date_time =dat.getText().toString() +"T"+ tim.getText().toString()+"Z";
            json.put("scheduled_date_time", date_time);
            json.put("pending","true");
            StringEntity se = new StringEntity( json.toString());
            httppost.setHeader("Authorization","Token " + value  );
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            httppost.setEntity(se);

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

                    Toast.makeText(update_delete.this, "updating task", Toast.LENGTH_LONG).show();
                    // Intent i = new Intent(task.this, Tasks_this_week.class);
                    // i.putExtra("token", value);
                    //startActivity(i);
                    if(temp.contains("201"))
                        Toast.makeText(update_delete.this, "Task updated successfully", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(update_delete.this, "Wrong format ", Toast.LENGTH_LONG).show();

                }
            });


        }

    }


    private class delAsyncTask extends AsyncTask<String, Integer, Double> {

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
            HttpDelete httppost = new HttpDelete("http://10.0.2.2:8000/api/v1/tasks/"+id);



            try {

                JSONObject json = new JSONObject();
                httppost.setHeader("Authorization","Token " + value  );
                // Execute HTTP Post Request
                Log.d("here1", "work");

                httpclient.execute(httppost);
                Log.d("here2", "here 2 is printing");
                //final String temp = EntityUtils.toString(response.getEntity());
                //Log.i("tag", temp);
                runOnUiThread(new Runnable() {
                    public void run() {

                            //Log.d("success", "true");
                            Toast.makeText(update_delete.this, "task deleted", Toast.LENGTH_LONG).show();


                    }
                });
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

