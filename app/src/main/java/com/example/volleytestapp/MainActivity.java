package com.example.volleytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

//    RequestQueue mRequestQueue;
//    RequestQueue mRequestQueueArray;
    RequestQueue mSingleRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mRequestQueue = Volley.newRequestQueue(this);
//        mRequestQueueArray = Volley.newRequestQueue(this);

        mSingleRequestQueue = VolleySingleton.getInstance().getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://official-joke-api.appspot.com/random_joke", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("RESPONSEVALIDDATA", response + "");

                try {
                    int jokeID = response.getInt("id");
                    Toast.makeText(MainActivity.this, jokeID + "", Toast.LENGTH_SHORT).show();

                    String setup = response.getString("setup");
                    Toast.makeText(MainActivity.this, setup, Toast.LENGTH_SHORT).show();

                    String punchLine = response.getString("punchline");
                    Toast.makeText(MainActivity.this, punchLine, Toast.LENGTH_LONG).show();

                } catch (JSONException e){
                    Toast.makeText(MainActivity.this, "Ohh, Connection Lost!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RESPONSEERROR", error.getMessage());
            }
        });

    mSingleRequestQueue.add(jsonObjectRequest);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://official-joke-api.appspot.com/random_ten", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("ARRAYRESPONSE", response +"");
                for(int index = 0; index < response.length(); index++){
                    try {
                        JSONObject jokeJsonObject = response.getJSONObject(index);
                        Log.i(" ",jokeJsonObject.getString("setup") + " - " + jokeJsonObject.getString("punchline"));
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ARRAYRESPONSE", error.getMessage());
            }
        });
    mSingleRequestQueue.add(jsonArrayRequest);
    }
}