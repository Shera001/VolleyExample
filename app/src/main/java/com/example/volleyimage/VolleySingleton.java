package com.example.volleyimage;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private Context context;
    private static VolleySingleton instance;
    private  RequestQueue requestQueue;

    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context.getApplicationContext());
        }

        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
