package com.appmoon.ziyadkhalil.helenkeller.deck;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appmoon.ziyadkhalil.helenkeller.R;
import com.appmoon.ziyadkhalil.helenkeller.model.db.MTag;

import java.util.ArrayList;
import java.util.List;

public class TagsRViewAdapter extends RecyclerView.Adapter<TagsRViewAdapter.TagViewHolder> {

    public static class TagViewHolder extends RecyclerView.ViewHolder{
        TextView tagText;
        CoordinatorLayout layout;
        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            tagText = itemView.findViewById(R.id.tagText);
            layout = itemView.findViewById(R.id.tagBackground);
        }
    }

    private LayoutInflater layoutInflater;
    private List<MTag> tags;

    TagsRViewAdapter(Context context){layoutInflater=LayoutInflater.from(context);}

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.recycleview_tag,viewGroup,false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder tagViewHolder, int i) {
        tagViewHolder.layout.setBackgroundColor(tags.get(i).getBackgroundColor());
        tagViewHolder.tagText.setTextColor(tags.get(i).getTextColor());
        tagViewHolder.tagText.setText(tags.get(i).getText());
    }

    @Override
    public int getItemCount() {
        return tags==null?0:tags.size();
    }

    public void setTags(List<MTag> tags, int deckID) {
        this.tags = new ArrayList<>();
        for (MTag tag:
           tags  ) {
            if(tag.getDeckID()==deckID)
                this.tags.add(tag);
        }
//        this.tags=tags;
        notifyDataSetChanged();
    }
}
