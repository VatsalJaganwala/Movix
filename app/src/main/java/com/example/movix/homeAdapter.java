package com.example.movix;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class homeAdapter extends RecyclerView.Adapter<homeAdapter.ViewHolder> {
    ArrayList<String> category =new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();
    Context context;

    public homeAdapter(ArrayList<String> category, ArrayList<String> url, Context context) {
        this.category=category;
        this.url=url;
        this.context=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView category;
        private final RecyclerView homeRecycler;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            category = (TextView) view.findViewById(R.id.category);
            homeRecycler = (RecyclerView) view.findViewById(R.id.categoryRecyclerView);

        }

        public TextView getCategory() {
            return category;
        }
        public RecyclerView getHomeRecycler() {
            return homeRecycler;
        }

    }





    // Create new views (invoked by the layout manager)
    @Override
    public homeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homeadapter, viewGroup, false);

        return new homeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {
        int position = index;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.get(position),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response here
                        Log.d("Response", response);

                        JSONArray jsonArray = getJSONArray(response);
                        try {
                            CustomAdapter c = new CustomAdapter(jsonArray);
                            viewHolder.getHomeRecycler().setLayoutManager(new LinearLayoutManager(context));
                            viewHolder.getHomeRecycler().setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
//                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context,3);
                            if(jsonArray.length()!=0){
                            viewHolder.getCategory().setText(category.get(position));
                            }

                            viewHolder.getHomeRecycler().setAdapter(c);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error here
                        Log.e("Error", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("accept", "application/json");
                headers.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZGM4OTUzZTU5NGI5ODUyZDAzYTQxMjI1ZWNmYTU2MCIsInN1YiI6IjY0ODA3N2VhOTkyNTljMDBhY2NhOWVmYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4Y2qHnJxT3zXsV7eMvQl3jYlIGaY8FZEGuzB2NHsZMM");
                return headers;
            }
        };
        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
    @Override
    public int getItemCount() {
        return category.size();
    }

    private JSONArray getJSONArray(String response){
        JSONArray jsonArray = null;
        try {
            JSONObject jsnobject = new JSONObject(response);
            jsonArray = jsnobject.getJSONArray("results");
            return jsonArray;

        }catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;

    }
}
