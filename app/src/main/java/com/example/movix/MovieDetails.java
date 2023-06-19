package com.example.movix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MovieDetails extends AppCompatActivity {
        String nameValue ,posterUrlValue ,backdropPathValue  ,originalLanguageValue,overviewValue,releaseDateValue,url1;
        int idValue;
    double voteAverageValue;
    int voteCountValue ;
    JSONObject moviejson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy gfgPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);
        }


        idValue = getIntent().getIntExtra("id", 0);
        Boolean isMovie = getIntent().getBooleanExtra("ISMOVIE",true);
        ArrayList<Integer> genreIdsValue;

        if (isMovie){
            url1 = "https://api.themoviedb.org/3/movie/" + idValue + "/external_ids";
        }
        else {
            url1 = "https://api.themoviedb.org/3/tv/" + idValue + "/external_ids";
        }
        Toast.makeText(this, url1, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response here
                        Log.d("Response", response);
                        String jsonString =response;

                        try {
                            JSONObject jsonObject = new JSONObject(jsonString);
                            String imdbId = jsonObject.getString("imdb_id");
                            getDetails(imdbId);

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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
    private void getDetails(String imdbId){
        String url = "https://api.themoviedb.org/3/find/"+imdbId+"?external_source=imdb_id";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response here
                        JSONObject explrObject = new JSONObject();
                        Log.d("Response", response);
                        String jsonString = response;

                        // Parse the JSON string
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(jsonString);

                        // Extract data from movie_results or tv_results
                        JSONArray movieResults = jsonObject.getJSONArray("movie_results");
                        JSONArray tvResults = jsonObject.getJSONArray("tv_results");

                        if (movieResults.length() > 0) {

                            explrObject = movieResults.getJSONObject(0);

                        } else if (tvResults.length() > 0) {
                            explrObject = tvResults.getJSONObject(0);
                        }
                        setData(explrObject);



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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void setData(JSONObject explrObject) throws JSONException {

        if(explrObject.has("title")) {
            nameValue=explrObject.getString("title");

        }
        else {
            nameValue=explrObject.getString("name");
        }
        posterUrlValue=explrObject.getString("poster_path");
        idValue=explrObject.getInt("id");
            backdropPathValue=explrObject.getString("backdrop_path");
//            try{
//                JSONArray genreIdsArray = explrObject.getJSONArray("genre_ids");
//                ArrayList<Integer> genre = new ArrayList<>();
//                for (int j = 0; j < genreIdsArray.length(); j++) {
//                    genre.add(genreIdsArray.getInt(i));
//                }
//                genreIdsList.add(genre);
//            }
//            catch (JSONException e){
//                e.printStackTrace();
//                Log.e("Genre Failed", "CustomAdapter: "+ name.get(name.size()-1) );
//
//            }
            originalLanguageValue=explrObject.getString("original_language");
            overviewValue=explrObject.getString("overview");
            voteAverageValue= explrObject.getDouble("vote_average");
            voteCountValue=explrObject.getInt("vote_count");
        TextView name = findViewById(R.id.name);
        name.setText(nameValue);
        TextView overview = findViewById(R.id.overview);
        overview.setText(overviewValue);

        TextView showMoreLess = findViewById(R.id.showMoreLess);
        int lineCount = overview.getLineCount();
        int lineHeight = overview.getLineHeight();
        int textViewHeight = overview.getHeight();
        int maxLines = 4;

        boolean isDataLessThanFourLines = (lineCount <= maxLines) && (lineHeight * lineCount <= textViewHeight);
        if (isDataLessThanFourLines){
            showMoreLess.setVisibility(View.GONE);
        }
        showMoreLess.setOnClickListener(new View.OnClickListener() {
        Boolean isExpanded = false;
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    overview.setMaxLines(4);
                    overview.setEllipsize(TextUtils.TruncateAt.END);
                    showMoreLess.setText("Show More");

                } else {
                    overview.setMaxLines(Integer.MAX_VALUE);
                    overview.setEllipsize(null);
                    showMoreLess.setText("Show Less");

                }
                isExpanded = !isExpanded;
            }
        });

    ImageView poster = findViewById(R.id.poster);
    posterUrlValue = "https://image.tmdb.org/t/p/original"+ posterUrlValue;
    Picasso.get().load(posterUrlValue).into(poster);
    ImageView backdrop = findViewById(R.id.backdrop);
        backdropPathValue = "https://image.tmdb.org/t/p/original"+ backdropPathValue;
        Picasso.get().load(backdropPathValue).into(backdrop);

    }
}