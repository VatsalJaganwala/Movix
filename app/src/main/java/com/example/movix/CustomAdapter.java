package com.example.movix;

import android.content.Intent;
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

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

//    private String[] movieNames;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> posterUrl = new ArrayList<>();
    private ArrayList<Integer> idList = new ArrayList<>();
    private ArrayList<Boolean> isMovie = new ArrayList<>();



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
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject explrObject = jsonArray.getJSONObject(i);
            if(explrObject.has("title")) {
                this.name.add(explrObject.getString("title"));
//                this.releaseDateList.add(explrObject.getString("release_date"));
                isMovie.add(true);
            }
            else {
            this.name.add(explrObject.getString("name"));
//            this.releaseDateList.add(explrObject.getString("first_air_date"));
                isMovie.add(false);
            }
            this.posterUrl.add(explrObject.getString("poster_path"));
            this.idList.add(explrObject.getInt("id"));


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
    public void onBindViewHolder(ViewHolder viewHolder, final int index) {
        int position = index;
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(name.get(position));
        String url ="https://image.tmdb.org/t/p/w185"+ posterUrl.get(position) ;
        Picasso.get().load(url).into(viewHolder.getMovieposterView());
        viewHolder.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), name.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),MovieDetails.class);
                intent.putExtra("id", idList.get(position));
                intent.putExtra("ISMOVIE", isMovie.get(position));
                v.getContext().startActivity(intent);
            }
        });


    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return name.size();
    }

}
