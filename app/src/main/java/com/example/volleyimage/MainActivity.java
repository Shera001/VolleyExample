package com.example.volleyimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    private RecyclerView recyclerView;

    private MyAdapter adapter;
    private ArrayList<Item> items;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.nbc));
            getWindow().setStatusBarColor(getResources().getColor(R.color.nbc));
        }

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        items = new ArrayList<>();

        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();

        parseVolley();
    }

    private void parseVolley() {
        String url = getResources().getString(R.string.key);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("hits");

                            for (int i=0; i<array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                String image = object.getString("webformatURL");
                                String title = object.getString("user");
                                int likeCount = object.getInt("likes");

                                items.add(new Item(image, title, String.valueOf(likeCount)));
                            }

                            adapter = new MyAdapter(MainActivity.this, items);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(MainActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }

    @Override
    public void onItemClick(int position, byte[] img) {
        Item item = items.get(position);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("img", img);
        startActivity(intent);
    }
}