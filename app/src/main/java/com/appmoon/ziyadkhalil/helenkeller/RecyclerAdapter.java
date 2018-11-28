package com.appmoon.ziyadkhalil.helenkeller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appmoon.ziyadkhalil.helenkeller.model.db.Deck;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {



    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView deckName;
        TextView tagsNumber;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            deckName= itemView.findViewById(R.id.deckName);
            tagsNumber = itemView.findViewById(R.id.tagsNumber);
        }
    }

    private final LayoutInflater layoutInflater;
    private List<Deck> decks;

    RecyclerAdapter(Context context){ layoutInflater = LayoutInflater.from(context);}




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = layoutInflater.inflate(R.layout.recycleview_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.deckName.setText(decks.get(i).getDeckName());
        myViewHolder.tagsNumber.setText(String.valueOf(decks.get(i).getTagsNumber())+" Tags");
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    void setDecks(List<Deck> decks){
        this.decks = decks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return decks==null?0:decks.size();
    }

}
