package com.example.movix;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class popularScreen extends Fragment {
    RecyclerView recyclerView;
//    String[] movieNames;
    String[] movieNames = {
            "The Shawshank Redemption",
            "The Godfather",
            "The Dark Knight",
            "Pulp Fiction",
            "Fight Club",
            "Forrest Gump",
            "Goodfellas",
            "The Matrix",
            "The Lord of the Rings: The Fellowship of the Ring",
            "Inception",
            "The Silence of the Lambs",
            "Star Wars: Episode V - The Empire Strikes Back",
            "Saving Private Ryan",
            "The Avengers",
            "The Departed",
            "Gladiator",
            "Interstellar",
            "The Lion King",
            "The Prestige",
            "The Green Mile",
            "Casablanca",
            "The Terminator",
            "Back to the Future",
            "The Godfather: Part II",
            "Se7en",
            "The Usual Suspects",
            "Schindler's List",
            "The Great Gatsby",
            "The Dark Knight Rises",
            "The Shawshank Redemption",
            "Inglourious Basterds",
            "The Wolf of Wall Street",
            "The Pianist",
            "The Princess Bride",
            "Avatar",
            "The Big Lebowski",
            "Gone with the Wind",
            "Braveheart",
            "Eternal Sunshine of the Spotless Mind",
            "The Social Network",
            "Memento",
            "Jurassic Park",
            "The Truman Show",
            "The Departed",
            "A Clockwork Orange",
            "Blade Runner",
            "The Exorcist",
            "American Beauty",
            "The Sixth Sense",
            "Casino Royale",
            "No Country for Old Men"
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular_screen, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);


        CustomAdapter c = new CustomAdapter(movieNames);
        recyclerView.setAdapter(c);
        return view;

    }
}