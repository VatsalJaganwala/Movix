package com.example.movix;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder> {

    private ArrayList<String> providerName;
    private ArrayList<String> logoUrl;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView logo;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.providerName);
            logo = (ImageView) view.findViewById(R.id.providerlogo);
        }

        public TextView getTextView() {
            return textView;
        }
        public ImageView getLogo() {
            return logo;
        }
    }


    public ProviderAdapter(ArrayList<String> providerName,ArrayList<String> logoUrl) {
        this.providerName=providerName;
        this.logoUrl=logoUrl;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.provideradapter, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(providerName.get(position));
        viewHolder.getTextView().setSelected(true);

        Picasso.get().load("https://image.tmdb.org/t/p/original"+logoUrl.get(position)).into(viewHolder.getLogo());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return providerName.size();
    }
}
