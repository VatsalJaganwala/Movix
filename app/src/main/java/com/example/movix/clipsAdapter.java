package com.example.movix;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class clipsAdapter extends RecyclerView.Adapter<clipsAdapter.ViewHolder> {

    private ArrayList<String> clipKeys;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final WebView clipView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            clipView = view.findViewById(R.id.ClipView);
        }

        public WebView getClipView() {
            return clipView;
        }

    }


    public clipsAdapter(ArrayList<String> clipKey) {
        this.clipKeys=clipKey;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.clip_adapter, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getClipView().getSettings().setJavaScriptEnabled(true);
        String url = "https://www.themoviedb.org/video/play?key="+clipKeys.get(position);


        viewHolder.getClipView().loadUrl(url);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return clipKeys.size();
    }
}
