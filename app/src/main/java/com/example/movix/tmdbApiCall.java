package com.example.movix;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class tmdbApiCall {
    private RequestQueue requestQueue;
    private String url;
    public tmdbApiCall(Context context,String url){
        requestQueue = Volley.newRequestQueue(context);
        this.url = url;
    }

    public void makeRequest(Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                successListener,
                errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("accept", "application/json");
                headers.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZGM4OTUzZTU5NGI5ODUyZDAzYTQxMjI1ZWNmYTU2MCIsInN1YiI6IjY0ODA3N2VhOTkyNTljMDBhY2NhOWVmYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4Y2qHnJxT3zXsV7eMvQl3jYlIGaY8FZEGuzB2NHsZMM");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

}
