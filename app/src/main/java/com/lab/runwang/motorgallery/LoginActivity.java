package com.lab.runwang.motorgallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {
    //ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText emailET    = (EditText)findViewById(R.id.editText);
        final EditText passwordET = (EditText)findViewById(R.id.editText2);

        Button loginBTN = (Button)findViewById(R.id.loginBTN);
        Button emptyBTN = (Button)findViewById(R.id.emptyBTN);



        //send a request with email and password to server
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailStr    = emailET.getText().toString();
                String passwordStr = passwordET.getText().toString();

                JSONObject jsonObject = new JSONObject();

                try {

                    jsonObject.put("username", emailStr);
                    jsonObject.put("password", passwordStr);
                    jsonObject.put("admin", false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.0.38:3000/api/auth/login";

                // Request a string response from the provided URL.
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        emailET.setText("That didn't work!");
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(jsObjRequest);






                //方法一
//                // Instantiate the RequestQueue.
//                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//                String url ="http://192.168.0.38:3000/api/health-check";
//
//                // Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                emailET.setText("Response is: "+ response);
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        emailET.setText("That didn't work!");
//                    }
//                });
//
//                // Add the request to the RequestQueue.
//                queue.add(stringRequest);


                //方法二
//                // Instantiate the cache
//                Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
//
//                // Set up the network to use HttpURLConnection as the HTTP client.
//                Network network = new BasicNetwork(new HurlStack());
//
//                // Instantiate the RequestQueue with the cache and network.
//                RequestQueue queue = new RequestQueue(cache, network);;
//                //String url ="http://localhost:3000/api/health-check";
//                String url ="http://192.168.0.38:3000/api/health-check";
//
//
//                queue.start();
//
//                // Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                emailET.setText("Response is: "+ response.substring(0,500));
//                                passwordET.setText("Response is: " + response.substring(0, 500));
//
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        emailET.setText("That didn't work!");
//                    }
//                });
//
//                // Add the request to the RequestQueue.
//                queue.add(stringRequest);

            }
        });


        





        //clear email and password
        emptyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailET.setText(null);
                passwordET.setText(null);

                RequestQueue mRequestQueue;

                // Instantiate the cache
                Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

                // Set up the network to use HttpURLConnection as the HTTP client.
                Network network = new BasicNetwork(new HurlStack());

                // Instantiate the RequestQueue with the cache and network.
                mRequestQueue = new RequestQueue(cache, network);

                // Start the queue
                mRequestQueue.start();

                String url ="http://192.168.0.38:3000/api/health-check";

                // Formulate the request and handle the response.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Do something with the response
                                emailET.setText("Test Result: "+ response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle error
                                emailET.setText("This doesn't work here");
                            }
                        });

                // Add the request to the RequestQueue.
                mRequestQueue.add(stringRequest);

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
