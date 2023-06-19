package com.example.movix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.util.List;
import java.util.Map;

public class MovieDetails extends AppCompatActivity {
        String nameValue ,posterUrlValue ,backdropPathValue  ,originalLanguageValue,overviewValue,releaseDateValue,url1;
        int idValue;
    double voteAverageValue;
    int voteCountValue ;
    Boolean isMovie;
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
        isMovie = getIntent().getBooleanExtra("ISMOVIE",true);
        ArrayList<Integer> genreIdsValue;

        if (isMovie){
            url1 = "https://api.themoviedb.org/3/movie/" + idValue ;
        }
        else {
            url1 = "https://api.themoviedb.org/3/tv/" + idValue ;
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
//                            String imdbId = jsonObject.getString("imdb_id");
                            setData(jsonObject);
                            getProvider();


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
    private void getProvider(){
        String url;
        if(isMovie){
            url = "https://api.themoviedb.org/3/movie/" + idValue + "/watch/providers";
        }
        else {
            url = "https://api.themoviedb.org/3/tv/" + idValue + "/watch/providers";
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response here
                        Log.d("Response", response);
                            ArrayList<String> buyProviders = new ArrayList<>();
                            ArrayList<String> adsProviders = new ArrayList<>();
                            ArrayList<String> flatrateProviders = new ArrayList<>();
                            ArrayList<String> rentProviders = new ArrayList<>();
                        ArrayList<String> buyProvidersLogo = new ArrayList<>();
                        ArrayList<String> adsProvidersLogo = new ArrayList<>();
                        ArrayList<String> flatrateProvidersLogo = new ArrayList<>();
                        ArrayList<String> rentProvidersLogo = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject results = jsonObject.getJSONObject("results");
                            JSONObject indiaData = results.getJSONObject("IN");
                            if(indiaData.has("ads")) {
                                JSONArray adsArray = indiaData.getJSONArray("ads");
                                for (int i = 0; i < adsArray.length(); i++) {
                                    JSONObject provider = adsArray.getJSONObject(i);
                                    String providerName = provider.getString("provider_name");
                                    adsProvidersLogo.add(provider.getString("logo_path"));
                                    adsProviders.add(providerName);
                                }
                            }
                            if(indiaData.has("flatrate")) {
                                JSONArray flatrateArray = indiaData.getJSONArray("flatrate");
                                for (int i = 0; i < flatrateArray.length(); i++) {
                                    JSONObject provider = flatrateArray.getJSONObject(i);
                                    String providerName = provider.getString("provider_name");
                                    flatrateProvidersLogo.add(provider.getString("logo_path"));
                                    flatrateProviders.add(providerName);
                                }
                            }
                            if(indiaData.has("rent")) {
                                JSONArray rentArray = indiaData.getJSONArray("rent");
                                for (int i = 0; i < rentArray.length(); i++) {
                                    JSONObject provider = rentArray.getJSONObject(i);
                                    String providerName = provider.getString("provider_name");
                                    rentProviders.add(providerName);
                                    rentProvidersLogo.add(provider.getString("logo_path"));
                                }
                            }
                            if(indiaData.has("buy")) {
                                JSONArray buyArray = indiaData.getJSONArray("buy");
                                for (int i = 0; i < buyArray.length(); i++) {
                                    JSONObject provider = buyArray.getJSONObject(i);
                                    String providerName = provider.getString("provider_name");
                                    buyProviders.add(providerName);
                                    buyProvidersLogo.add(provider.getString("logo_path"));
                                }
                            }


                            ProviderAdapter ads = new ProviderAdapter(adsProviders,adsProvidersLogo);
                            RecyclerView adsRecycler = findViewById(R.id.adsRecycler);
                            adsRecycler.setLayoutManager(new LinearLayoutManager(MovieDetails.this ,LinearLayoutManager.HORIZONTAL,false));
                            adsRecycler.setAdapter(ads);
                            ProviderAdapter stream = new ProviderAdapter(flatrateProviders,flatrateProvidersLogo);
                            RecyclerView flatRecycler = findViewById(R.id.flatRecycler);
                            flatRecycler.setLayoutManager(new LinearLayoutManager(MovieDetails.this ,LinearLayoutManager.HORIZONTAL,false));
                            flatRecycler.setAdapter(stream);
                            ProviderAdapter rent = new ProviderAdapter(rentProviders,rentProvidersLogo);
                            RecyclerView rentRecycler = findViewById(R.id.rentRecycler);
                            rentRecycler.setLayoutManager(new LinearLayoutManager(MovieDetails.this ,LinearLayoutManager.HORIZONTAL,false));
                            rentRecycler.setAdapter(rent);
                            ProviderAdapter buy = new ProviderAdapter(buyProviders,buyProvidersLogo);
                            RecyclerView buyRecycler = findViewById(R.id.buyRecycler);
                            buyRecycler.setLayoutManager(new LinearLayoutManager(MovieDetails.this ,LinearLayoutManager.HORIZONTAL,false));
                            buyRecycler.setAdapter(buy);





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