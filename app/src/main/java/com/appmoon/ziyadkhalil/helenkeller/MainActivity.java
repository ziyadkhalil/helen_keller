package com.appmoon.ziyadkhalil.helenkeller;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.appmoon.ziyadkhalil.helenkeller.deck.DeckActivity;
import com.appmoon.ziyadkhalil.helenkeller.model.db.Deck;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  AddDeckActivity.DialogListener{
    ViewModel viewModel;
    RecyclerView recyclerView;
    static int count;
    FloatingActionButton addTagsDeckBtn;
    FloatingActionsMenu floatingMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectViews();
        addListeners();
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        viewModel=ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllDecks().observe(this, new Observer<List<Deck>>() {
            @Override
            public void onChanged(@Nullable List<Deck> decks) {
                Log.d("Changed",String.valueOf(decks.size()));
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //RecyclerView for tag decks
        recyclerView.setHasFixedSize(true);
        //LayoutManager
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        //Adapter
        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("Truth",viewModel.getAllDecks().getValue().get(position).toString());
                Log.d("Truth",String.valueOf(viewModel.getAllDecks()));
                Intent intent = new Intent(getApplicationContext(),DeckActivity.class);
                intent.putExtra("deckID", (viewModel.getAllDeckS().get(position).getId()));
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }){

        });
        viewModel.getAllDecks().observe(this, new Observer<List<Deck>>() {
            @Override
            public void onChanged(@Nullable List<Deck> decks) {
                recyclerAdapter.setDecks(decks);

            }
        });




    }

    private void addListeners() {
        addTagsDeckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDeckActivity addDeckActivity = new AddDeckActivity();
//                addDeckActivity.setPositiveButton();
                addDeckActivity.show(getSupportFragmentManager(),"mmm");
                floatingMenu.collapse();
            }
        });
    }

    private void injectViews() {
        addTagsDeckBtn = this.findViewById(R.id.addTagsDeckBtn);
        floatingMenu = this.findViewById(R.id.floatingMenu);
        recyclerView = findViewById(R.id.decksRecycleView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    @Override
    public void addDeck(String deckName) {
        viewModel.insertDeck(Deck.createNewDeck(deckName));
    }
}
