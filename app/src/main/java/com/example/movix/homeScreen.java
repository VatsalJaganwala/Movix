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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
//        category.add("Now Playing"); url.add("https://api.themoviedb.org/3/movie/now_playing?region=in");
//        category.add("Top TV Shows"); url.add("https://api.themoviedb.org/3/tv/top_rated?language=en-US%2Chi&page=1");
        category.add("Action-Packed Thrillers"); url.add("28%7C53");
        category.add("Sci-Fi Adventure Quests"); url.add("878%7C12");
        category.add("Comedic Crime Capers"); url.add("35%7C80");
        category.add("Epic Fantasy Wars"); url.add("14%7C10752");
        category.add("Romantic Mystery Nights"); url.add("10749%7C9648");
        category.add("Family Drama Chronicles"); url.add("10751%7C18");
        category.add("Historical War Epics"); url.add("36%7C10752");
        category.add("Music-Fueled Romances"); url.add("10402%7C10749");
        category.add("Sci-Fi Thriller Expeditions"); url.add("878%7C53");
        category.add("Comedy Crime Solvers"); url.add("35%7C80");
        category.add("Action Fantasy Adventures"); url.add("28%7C14");
        category.add("Mystery Drama Enigmas"); url.add("9648%7C18");
        category.add("Sci-Fi Horror Encounters"); url.add("878%7C27");
        category.add("Romantic Historical Journeys"); url.add("10749%7C36");
        category.add("Action Crime Investigations"); url.add("28%7C80");
        category.add("Thrilling War Mysteries"); url.add("53%7C9648");
        category.add("Fantasy Family Escapades"); url.add("14%7C10751");
        category.add("Sci-Fi Crime Pursuits"); url.add("878%7C80");
        category.add("Romantic Adventure Voyages"); url.add("10749%7C12");
        category.add("Historical Crime Chronicles"); url.add("36%7C80");
        category.add("Action Drama Epics"); url.add("28%7C18");
        category.add("Mystery Thriller Intrigues"); url.add("9648%7C53");
        category.add("Sci-Fi Fantasy Quests"); url.add("878%7C14");
        category.add("Romantic Comedy Escapades"); url.add("10749%7C35");
        category.add("Family War Chronicles"); url.add("10751%7C10752");
        category.add("Thrilling Crime Adventures"); url.add("53%7C80");
        category.add("Action Mystery Expeditions"); url.add("28%7C9648");
        category.add("Fantasy Drama Enchantments"); url.add("14%7C18");
        category.add("Romantic Sci-Fi Affairs"); url.add("10749%7C878");
        category.add("Family Thriller Mysteries"); url.add("10751%7C9648");
        category.add("War Crime Chronicles"); url.add("10752%7C80");
        category.add("Action Fantasy Battles"); url.add("28%7C14");
        category.add("Sci-Fi Mystery Escapades"); url.add("878%7C9648");
        category.add("Comedic Drama Chronicles"); url.add("35%7C18");
        category.add("Historical Thriller Enigmas"); url.add("36%7C53");
        category.add("Romantic Crime Pursuits"); url.add("10749%7C80");
        category.add("Action Adventure Quests"); url.add("28%7C12");
        category.add("Sci-Fi War Epics"); url.add("878%7C10752");
        category.add("Comedy Mystery Adventures"); url.add("35%7C9648");
        category.add("Family Drama Journeys"); url.add("10751%7C18");
        category.add("Historical Adventure Expeditions"); url.add("36%7C12");
        category.add("Romantic Thriller Pursuits"); url.add("10749%7C53");
        category.add("Action Crime Enigmas"); url.add("28%7C80");
        category.add("War Drama Chronicles"); url.add("10752%7C18");
        category.add("Sci-Fi Thriller Escapades"); url.add("878%7C53");
        category.add("Comedic Crime Pursuits"); url.add("35%7C80");
        category.add("Action Fantasy Journeys"); url.add("28%7C14");
        category.add("War Mystery Chronicles"); url.add("10752%7C9648");
        category.add("Romantic Drama Escapades"); url.add("10749%7C18");
        category.add("Sci-Fi Adventure Mysteries"); url.add("878%7C9648");
        for (int i=0;i<url.size();i++)
        {

            int randomNumber = random.nextInt(50);
//            int randomNumber = 1;
            url.set(i,"https://api.themoviedb.org/3/discover/movie?include_adult=false&language=en%2Chi&page="+String.valueOf(randomNumber)+"&region=in&sort_by=popularity.desc&watch_region=in&with_genres="+url.get(i));


        }
        shuffleListsTogether();
        while (category.size() > 20) {
            category.remove(category.size() - 1);
            url.remove(url.size()-1);
        }



        homeRecyclerView = view.findViewById(R.id.homeRecycletView);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeAdapter homeAdapter = new homeAdapter(category,url,getContext());
        homeRecyclerView.setAdapter(homeAdapter);



        return view;
    }
    public void shuffleListsTogether() {
        if (category.size() != url.size()) {
            throw new IllegalArgumentException("Lists must have the same size.");
        }

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < category.size(); i++) {
            indices.add(i);
        }

        Collections.shuffle(indices);

        List<String> shuffledCategories = new ArrayList<>();
        List<String> shuffledUrls = new ArrayList<>();

        for (int index : indices) {
            shuffledCategories.add(category.get(index));
            shuffledUrls.add(url.get(index));
        }

        category.clear();
        category.addAll(shuffledCategories);

        url.clear();
        url.addAll(shuffledUrls);
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