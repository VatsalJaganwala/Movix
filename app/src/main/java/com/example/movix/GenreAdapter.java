package com.example.movix;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
    private ArrayList<String> genreList;

    public GenreAdapter(ArrayList<String> genreList) {
        this.genreList = genreList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String genre = genreList.get(position);
        holder.genreTextView.setText(genre);
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView genreTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            genreTextView = itemView.findViewById(R.id.genreTextView);
        }
    }
}

