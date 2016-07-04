package com.example.alankar63.todoapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tasks_this_week extends AppCompatActivity {
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        Log.d("work", "nice");

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            value = extras.getString("token");
            Log.d("print token",value);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        new MAsyncTask().execute();
        //prepareMovieData();
    }

    private class MAsyncTask extends AsyncTask<String, Integer, Double> {

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
            HttpGet httpget = new HttpGet("http://10.0.2.2:8000/api/v1/tasks/?days=7");

            //se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpget.setHeader("Authorization","Token " + value  );
            HttpResponse response = httpclient.execute(httpget);
            Log.d("here2", "here 2 is printing");
            final String temp = EntityUtils.toString(response.getEntity());
            Log.d("value of temp",temp);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(temp);
                jsonObject = jsonObject.getJSONObject("data");
                JSONArray jsonArray = jsonObject.getJSONArray("tasks");
                Log.d("jsonarray",jsonArray.toString());

                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObj = jsonArray.getJSONObject(i);

                    String scheduled_date_time = jsonObj.optString("scheduled_date_time").toString();
                    String name = jsonObj.optString("name").toString();
                    String pend = jsonObj.optString("pending").toString();

                    Log.d("name",name);
                    scheduled_date_time = scheduled_date_time.replaceAll("T", "  ");
                    scheduled_date_time = scheduled_date_time.replaceAll("Z","");
                    Movie movie = new Movie(name,"pending " + pend,scheduled_date_time);
                    movieList.add(movie);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }







            mAdapter.notifyDataSetChanged();
        }
    }

}
