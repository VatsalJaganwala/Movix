package com.example.movix;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class homeScreen extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    ArrayList<String> category =new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();
    RecyclerView homeRecyclerView;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
       if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy gfgPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
             StrictMode.setThreadPolicy(gfgPolicy);
        }
        Random random = new Random();
        category.add("Now Playing"); url.add("https://api.themoviedb.org/3/movie/now_playing?region=in");
        category.add("Top TV Shows"); url.add("https://api.themoviedb.org/3/tv/top_rated?language=en-US%7Chi&page=1");
        category.add("Top Action Movies"); url.add("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en&region=in&sort_by=popularity.desc&watch_region=in&with_genres=28");
        category.add("Top Dramas"); url.add("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en&page=1&region=in&sort_by=popularity.desc&watch_region=in&with_genres=18");
        category.add("Mystery Thriller"); url.add("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=hi%7Cen&page=1&region=in&sort_by=popularity.desc&watch_region=in&with_genres=9648%7C53");
        homeRecyclerView = view.findViewById(R.id.homeRecycletView);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeAdapter homeAdapter = new homeAdapter(category,url,getContext());
        homeRecyclerView.setAdapter(homeAdapter);


        return view;
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