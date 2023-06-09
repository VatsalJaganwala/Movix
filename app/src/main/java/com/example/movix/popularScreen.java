package com.example.movix;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class popularScreen extends Fragment {
    RecyclerView recyclerView;
//    String[] movieNames;
//    String[] movieNames = {
//            "The Shawshank Redemption",
//            "The Godfather",
//            "The Dark Knight",
//            "Pulp Fiction",
//            "Fight Club",
//            "Forrest Gump",
//            "Goodfellas",
//            "The Matrix",
//            "The Lord of the Rings: The Fellowship of the Ring",
//            "Inception",
//            "The Silence of the Lambs",
//            "Star Wars: Episode V - The Empire Strikes Back",
//            "Saving Private Ryan",
//            "The Avengers",
//            "The Departed",
//            "Gladiator",
//            "Interstellar",
//            "The Lion King",
//            "The Prestige",
//            "The Green Mile",
//            "Casablanca",
//            "The Terminator",
//            "Back to the Future",
//            "The Godfather: Part II",
//            "Se7en",
//            "The Usual Suspects",
//            "Schindler's List",
//            "The Great Gatsby",
//            "The Dark Knight Rises",
//            "The Shawshank Redemption",
//            "Inglourious Basterds",
//            "The Wolf of Wall Street",
//            "The Pianist",
//            "The Princess Bride",
//            "Avatar",
//            "The Big Lebowski",
//            "Gone with the Wind",
//            "Braveheart",
//            "Eternal Sunshine of the Spotless Mind",
//            "The Social Network",
//            "Memento",
//            "Jurassic Park",
//            "The Truman Show",
//            "The Departed",
//            "A Clockwork Orange",
//            "Blade Runner",
//            "The Exorcist",
//            "American Beauty",
//            "The Sixth Sense",
//            "Casino Royale",
//            "No Country for Old Men"
//    };
    ArrayList<String> movieNames = new ArrayList<>();
    ArrayList<String> porterUrl = new ArrayList<>();
    String temp;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular_screen, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy gfgPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);
        }
        TextView textView = view.findViewById(R.id.textView3);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        String url = "https://api.themoviedb.org/3/movie/popular?language=en-US&page=1&region=in";

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

// Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

//        Toast.makeText(getContext(), temp, Toast.LENGTH_SHORT).show();
        textView.setText("Vatsal"+temp );


        Toast.makeText(getContext(),    String.valueOf(movieNames.size()), Toast.LENGTH_SHORT).show();

        return view;

    }
    private void processResponce(String response){
        try {
            JSONObject jsnobject = new JSONObject(response);
            JSONArray jsonArray = jsnobject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                movieNames.add(explrObject.getString("title"));
                porterUrl.add(explrObject.getString("poster_path"));

            }
            if(movieNames!=null){
                CustomAdapter c = new CustomAdapter(movieNames,porterUrl);
                recyclerView.setAdapter(c);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

}