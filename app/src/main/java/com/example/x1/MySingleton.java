package com.example.x1;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private static volatile MySingleton INSTANCE;
    private RequestQueue requestQueue;
    private static Context ctx;

    public MySingleton(Context context) {
        ctx = context.getApplicationContext();
        requestQueue = getRequestQueue();
    }

    public static MySingleton getInstance(Context context){
        if (INSTANCE == null){
            synchronized (MySingleton.class){
                if (INSTANCE == null){
                    INSTANCE = new MySingleton(context);
                }
            }
        }
        return INSTANCE;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

}
