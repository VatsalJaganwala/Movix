package com.example.movix;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    private ArrayList<String> name;
    private ArrayList<String> character;


    public CastAdapter(ArrayList<String> name,ArrayList<String> character ) {
        this.name = name;
        this.character=character;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.getNameTextView().setText(name.get(position));
        holder.getCharacterTextView().setText(character.get(position));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView characterTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.castName);
            characterTextView = itemView.findViewById(R.id.character);
        }
        TextView getNameTextView(){ return nameTextView;}
        TextView getCharacterTextView(){return characterTextView;}

    }
}

