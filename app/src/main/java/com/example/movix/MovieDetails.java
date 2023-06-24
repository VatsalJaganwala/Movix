package com.example.movix;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MovieDetails extends AppCompatActivity {
        String nameValue ,posterUrlValue ,backdropPathValue  ,originalLanguageValue,overviewValue,releaseDateValue,url1,runtimeValue;
        int idValue;
    double voteAverageValue;
    int voteCountValue ;
    ArrayList<String> genre = new ArrayList<>();
    ArrayList<String> CastName = new ArrayList<>();
    ArrayList<String> CastCharacter = new ArrayList<>();
    ArrayList<String> Director = new ArrayList<>();
    ArrayList<String> Creater = new ArrayList<>();
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
//                            getClips();
                            setOtherDetails();
                            getCredits();
                            getSimilar();


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
    private void setOtherDetails(){
        TextView rating = findViewById(R.id.rating);
//        Toast.makeText(this, String.valueOf(voteAverageValue), Toast.LENGTH_SHORT).show();
        rating.setText(String.valueOf(voteAverageValue));
        if(runtimeValue!=null){

            TextView runtime = findViewById(R.id.runtime);
            runtime.setText(getTimeFormat(Integer.parseInt(runtimeValue)));
        }
        else {
            TableRow runtimeData = findViewById(R.id.runtimeData);
            runtimeData.setVisibility(View.GONE);
        }
        TextView releaseDate = findViewById(R.id.releasedate);
        releaseDate.setText(String.valueOf(releaseDateValue));
        RecyclerView genreRecyclerView = findViewById(R.id.genreRecyclerView);
        GenreAdapter ad = new GenreAdapter(genre);
        genreRecyclerView.setLayoutManager(new LinearLayoutManager(MovieDetails.this,LinearLayoutManager.HORIZONTAL,false));
        genreRecyclerView.setAdapter(ad);



    }
    private void getSimilar(){
        ArrayList<String> RecommandationText= new ArrayList<>();
        ArrayList<String> link = new ArrayList<>();
        String text = "People who like "+nameValue+" also like these";
        RecommandationText.add(text);
        String url;
        if(isMovie){
            url ="https://api.themoviedb.org/3/movie/"+idValue+"/recommendations?language=en-US&page=1";
        }
        else {
            url ="https://api.themoviedb.org/3/tv/"+idValue+"/recommendations?language=en-US&page=1";
        }
        link.add(url);
        homeAdapter recommand = new homeAdapter(RecommandationText,link,MovieDetails.this);
        RecyclerView recommandationRecyclerView = findViewById(R.id.recommandationRecyclerView);
        recommandationRecyclerView.setLayoutManager(new LinearLayoutManager(MovieDetails.this));
        recommandationRecyclerView.setAdapter(recommand);
    }
    private void getCredits(){
        TextView directorField = findViewById(R.id.DirectorField);
        String url ;
        if(isMovie){
            url = "https://api.themoviedb.org/3/movie/" + idValue + "/credits?language=en-US";
        }
        else {
            url = "https://api.themoviedb.org/3/tv/" + idValue + "/credits?language=en-US";
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response here
                        Log.d("Response", response);
                        String jsonString =response;

                        try {
                            JSONObject jsonObject = new JSONObject(jsonString);
                            JSONArray castJsonArray = jsonObject.getJSONArray("cast");
                            for (int i=0;i<castJsonArray.length();i++){
                                JSONObject castJsonObject = castJsonArray.getJSONObject(i);
                                if(castJsonObject.getString("known_for_department").equals("Acting"))
                                {
                                    CastName.add(castJsonObject.getString("name"));
                                    CastCharacter.add(castJsonObject.getString("character"));
//                                    Toast.makeText(MovieDetails.this,castJsonObject.getString("name") , Toast.LENGTH_SHORT).show();
                                }
                            }
                            JSONArray crewJsonArray = jsonObject.getJSONArray("crew");
                            for (int i=0;i<crewJsonArray.length();i++){
                                JSONObject crewJsonObject = crewJsonArray.getJSONObject(i);
                                if(crewJsonObject.getString("job").equals("Director"))
                                {
                                    Director.add(crewJsonObject.getString("name"));
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(!Director.isEmpty())
                        {
                            directorField.setText("DIRECTOR");
                        }
                        else if(!Creater.isEmpty()){
                            directorField.setText("CREATED BY");
                            Director.addAll(Creater);
                        }

                        RecyclerView directorView = findViewById(R.id.directorRecyclerView);
                        GenreAdapter director = new GenreAdapter(Director);
                        directorView.setLayoutManager(new LinearLayoutManager(MovieDetails.this,LinearLayoutManager.HORIZONTAL,false));
                        directorView.setAdapter(director);

                        RecyclerView castRecycler = findViewById(R.id.castRecyclerView);
                        CastAdapter cast = new CastAdapter(CastName,CastCharacter);
                        castRecycler.setLayoutManager(new LinearLayoutManager(MovieDetails.this,LinearLayoutManager.HORIZONTAL,false));
                        castRecycler.setAdapter(cast);



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
    private void getClips(){
        String url;
        if(isMovie) {
            url="https://api.themoviedb.org/3/movie/"+idValue+"/videos";
        }
        else {
            url="https://api.themoviedb.org/3/tv/"+idValue+"/videos";
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response here
                        ArrayList<String> ClipKeys = new ArrayList<>();
                        Log.d("Response", response);
                        String jsonString =response;

                        try {
                            JSONObject json = new JSONObject(jsonString);
                            JSONArray resultsArray = json.getJSONArray("results");
                            for (int i = 0; i < resultsArray.length(); i++) {
                                JSONObject result = resultsArray.getJSONObject(i);
                                String key = result.getString("key");
                                ClipKeys.add(key);
//                                Toast.makeText(MovieDetails.this, ClipKeys.get(ClipKeys.size()-1), Toast.LENGTH_SHORT).show();
                            }
                                Toast.makeText(MovieDetails.this, String.valueOf(resultsArray.length()), Toast.LENGTH_SHORT).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RecyclerView clipsRecycler = findViewById(R.id.videoClipsRecycler);
                        clipsRecycler.setLayoutManager(new LinearLayoutManager(MovieDetails.this));
                        clipsAdapter clipAdapter = new clipsAdapter(ClipKeys);
                        clipsRecycler.setAdapter(clipAdapter);
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
                        TextView providerNotAvailable = findViewById(R.id.providerNotAvailable);
                            providerNotAvailable.setText("Currently this movie/series is not streaming on any platform");
                            TextView adsTextview = findViewById(R.id.adsTextView);
                            TextView rentTextView =findViewById(R.id.rentTextView);
                            TextView streamTextView =findViewById(R.id.streamTextView);
                            TextView buyTextView =findViewById(R.id.buyTextView);
                            Boolean providerAvailable = false;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject results = jsonObject.getJSONObject("results");
                            JSONObject indiaData = results.getJSONObject("IN");
                            if(indiaData.has("ads")) {
                                providerAvailable = true;
                                adsTextview.setText("ADS");
                                providerNotAvailable.setVisibility(View.GONE);
                                JSONArray adsArray = indiaData.getJSONArray("ads");
                                for (int i = 0; i < adsArray.length(); i++) {
                                    JSONObject provider = adsArray.getJSONObject(i);
                                    String providerName = provider.getString("provider_name");
                                    adsProvidersLogo.add(provider.getString("logo_path"));
                                    adsProviders.add(providerName);
                                }
                            }
                            if(indiaData.has("flatrate")) {
                                providerAvailable = true;
                                streamTextView.setText("STREAM");
                                providerNotAvailable.setVisibility(View.GONE);
                                JSONArray flatrateArray = indiaData.getJSONArray("flatrate");
                                for (int i = 0; i < flatrateArray.length(); i++) {
                                    JSONObject provider = flatrateArray.getJSONObject(i);
                                    String providerName = provider.getString("provider_name");
                                    flatrateProvidersLogo.add(provider.getString("logo_path"));
                                    flatrateProviders.add(providerName);
                                }
                            }
                            if(indiaData.has("rent")) {
                                providerAvailable = true;
                                rentTextView.setText("RENT");
                                providerNotAvailable.setVisibility(View.GONE);
                                JSONArray rentArray = indiaData.getJSONArray("rent");
                                for (int i = 0; i < rentArray.length(); i++) {
                                    JSONObject provider = rentArray.getJSONObject(i);
                                    String providerName = provider.getString("provider_name");
                                    rentProviders.add(providerName);
                                    rentProvidersLogo.add(provider.getString("logo_path"));
                                }
                            }
                            if(indiaData.has("buy")) {
                                providerAvailable = true;
                                buyTextView.setText("BUY");
                                providerNotAvailable.setVisibility(View.GONE);
                                JSONArray buyArray = indiaData.getJSONArray("buy");
                                for (int i = 0; i < buyArray.length(); i++) {
                                    JSONObject provider = buyArray.getJSONObject(i);
                                    String providerName = provider.getString("provider_name");
                                    buyProviders.add(providerName);
                                    buyProvidersLogo.add(provider.getString("logo_path"));
                                }
                            }
//                            if(!providerAvailable){
//                                String message="Not available";
//                                if(isMovie){
//                                    message = "Currently this movie is not streaming on any platform";
//                                }
//                                else{
//                                    message = "Currently this TV/Web series is not streaming on any platform";
//                                }
//                                providerNotAvailable.setText(message);
//                            }
////



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
            releaseDateValue = explrObject.getString("release_date");

        }
        else {
            nameValue=explrObject.getString("name");
            releaseDateValue = explrObject.getString("first_air_date");
            try{
                JSONArray createrArray = explrObject.getJSONArray("created_by");
                for (int j = 0; j < createrArray.length(); j++) {
                    JSONObject createrObject = createrArray.getJSONObject(j);
                    Creater.add(createrObject.getString("name"));
//                    Toast.makeText(this, genre.get(genre.size()-1), Toast.LENGTH_SHORT).show();
                }

            }
            catch (JSONException e){
                e.printStackTrace();
                Log.e("Genre Failed", "CustomAdapter: "+ nameValue );

            }
        }
        if(explrObject.has("runtime")) {
            runtimeValue = explrObject.getString("runtime");
        }
        posterUrlValue=explrObject.getString("poster_path");
        idValue=explrObject.getInt("id");
            backdropPathValue=explrObject.getString("backdrop_path");
            try{
                JSONArray genreArray = explrObject.getJSONArray("genres");
                for (int j = 0; j < genreArray.length(); j++) {
                    JSONObject genreObject = genreArray.getJSONObject(j);
                    genre.add(genreObject.getString("name"));
//                    Toast.makeText(this, genre.get(genre.size()-1), Toast.LENGTH_SHORT).show();
                }

            }
            catch (JSONException e){
                e.printStackTrace();
                Log.e("Genre Failed", "CustomAdapter: "+ nameValue );

            }
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
    public static String getTimeFormat(int minutes) {
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;
        return String.format(Locale.getDefault(), "%02d hrs %02d min", hours, remainingMinutes);
    }

}
