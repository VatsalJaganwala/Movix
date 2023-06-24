package com.example.movix;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;


public class recentScreen extends Fragment {
    String temp;
    RecyclerView searchRecycler;
    TextView searchText;
    EditText searchBar;



    public recentScreen() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String url = "https://api.themoviedb.org/3/movie/now_playing?region=in";
        View view= inflater.inflate(R.layout.fragment_recent_screen, container, false);
        searchRecycler = view.findViewById(R.id.searchRecyclerView);
        searchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        searchRecycler.setLayoutManager(gridLayoutManager);
        makeRequest(url);
        searchText = view.findViewById(R.id.searchTextView);
        searchBar = view.findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String url;
//                Toast.makeText(getContext(), String.valueOf(s.length()), Toast.LENGTH_SHORT).show();
                if(s.length()==0) {
                    url = "https://api.themoviedb.org/3/movie/now_playing?region=in";
                    searchText.setText("Currently in Cinemas");
                }
                else{

                    url = "https://api.themoviedb.org/3/search/multi?query="+s+"&include_adult=false&language=en-US&page=1?region=in";
                    searchText.setText("Result for " +s);
                }
                    makeRequest(url);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });

        return view;
    }
    void makeRequest(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response here
                        Log.d("Response", response);
                        temp = response;
                        processResponce(temp);


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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
    private void processResponce(String response){
        try {
            JSONObject jsnobject = new JSONObject(response);
            JSONArray jsonArray = jsnobject.getJSONArray("results");
            CustomAdapter c = new CustomAdapter(jsonArray);
            searchRecycler.setAdapter(c);


        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

}