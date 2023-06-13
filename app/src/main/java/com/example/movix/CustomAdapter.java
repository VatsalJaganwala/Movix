package com.example.movix;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

//    private String[] movieNames;
    private ArrayList<String> movieNames = new ArrayList<>();
    private ArrayList<String> posterUrl = new ArrayList<>();
    private ArrayList<Integer> movieId = new ArrayList<>();
    private JSONArray movieDetails;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView movieposter;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.movieName);
            movieposter = (ImageView) view.findViewById(R.id.movieposter);

        }

        public TextView getTextView() {
            return textView;
        }
        public ImageView getMovieposterView(){
            return movieposter;
        }
    }



    public CustomAdapter(JSONArray jsonArray) throws JSONException {
        movieDetails = jsonArray;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject explrObject = jsonArray.getJSONObject(i);
            if(explrObject.has("title")) {
                this.movieNames.add(explrObject.getString("title"));
            }
            else {
            this.movieNames.add(explrObject.getString("name"));
            }
            this.posterUrl.add(explrObject.getString("poster_path"));
            this.movieId.add(explrObject.getInt("id"));

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.moviecard, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(movieNames.get(position));
        String url ="https://image.tmdb.org/t/p/w185"+ posterUrl.get(position) ;
        Picasso.get().load(url).into(viewHolder.getMovieposterView());
        viewHolder.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), movieNames.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),MovieDetails.class);
                v.getContext().startActivity(intent);
            }
        });
//        start

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movieNames.size();
    }

}
