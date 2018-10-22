package com.example.demo.moneyTab;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DATA = "https://en.wikipedia.org//w/api.php?action=query&format=json&prop=pageimages|pageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description&gpssearch=Sachin+T&gpslimit=10";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<JSONData> jsonDataList;
    String title = "";
    String source = "";
    String description = "";
    String width = "";
    String height = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jsonDataList = new ArrayList<>();

        loadUrlData();
    }

    private void loadUrlData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject array = jsonObject.getJSONObject("query");
                    JSONArray pagesObj = array.getJSONArray("pages");

                    for (int i = 0; i < pagesObj.length(); i++){
                        try {
                            JSONObject jo = pagesObj.getJSONObject(i);
                            if (jo.getJSONObject("thumbnail") != null) {
                                JSONObject thumbnail = jo.getJSONObject("thumbnail");
                                source = thumbnail.getString("source");
                                width = thumbnail.getString("width");
                                height = thumbnail.getString("height");
                            }
                            if (jo.getJSONObject("terms") != null) {
                                JSONObject terms = jo.getJSONObject("terms");
                                description = terms.getString("description");
                            }


                            if (jo.getString("title") != null) title = jo.getString("title");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        JSONData developers = new JSONData(title,source,description);
                        jsonDataList.add(developers);

                        adapter = new JsonDataAdapter(jsonDataList, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    }} catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
