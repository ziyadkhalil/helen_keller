package com.appmoon.ziyadkhalil.helenkeller.deck;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appmoon.ziyadkhalil.helenkeller.R;
import com.appmoon.ziyadkhalil.helenkeller.ViewModel;
import com.appmoon.ziyadkhalil.helenkeller.model.db.Deck;
import com.appmoon.ziyadkhalil.helenkeller.model.db.MTag;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    View view;
    TextView noTags;
    FloatingActionButton fab;
    Deck currentDeck;

    RecyclerView tagsRView;
    GridLayoutManager gridLayoutManager;
    TagsRViewAdapter tagsRViewAdapter;

    DialogFragment dialogFragment;
    List<MTag> tags;

    LiveData<List<MTag>> liveTags;
    ViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_deck_main,container,false);

        injectViews();
        injectModels();

        setFabActionListener();

        gridLayoutManager = new GridLayoutManager(getContext(),4);
        tagsRViewAdapter = new TagsRViewAdapter(getContext());
        tagsRView.setLayoutManager(gridLayoutManager);
        tagsRView.setAdapter(tagsRViewAdapter);
        tags = new ArrayList<>();
        MTag tag = new MTag("asd",24);
        tags.add(tag);
        tagsRViewAdapter.setTags(tags, currentDeck.getId());

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModel.class);
        liveTags = viewModel.getAllTags();
        liveTags.observe(this, new Observer<List<MTag>>() {
            @Override
            public void onChanged(@Nullable List<MTag> mTags) {
                tagsRViewAdapter.setTags(mTags,currentDeck.getId());
                Log.d("zep",String.valueOf(tagsRViewAdapter.getItemCount()));
                Log.d("CHANGED","ALo");
                if(mTags.size()>0){
                    noTags.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    private void setFabActionListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DeckActivity)getActivity()).navToFrag(1);
            }
        });
    }

    private void injectModels() {
        currentDeck = ((DeckActivity)getActivity()).getCurrentDeck();
    }

    private void injectViews() {
        noTags = view.findViewById(R.id.noTagsTxtView);
        fab = view.findViewById(R.id.fab);
        tagsRView = view.findViewById(R.id.tagsRView);
    }



    @Override
    public void onResume() {
        super.onResume();


    }
}
