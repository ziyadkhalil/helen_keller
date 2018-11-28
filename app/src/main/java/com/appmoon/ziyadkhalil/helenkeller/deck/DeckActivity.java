package com.appmoon.ziyadkhalil.helenkeller.deck;

import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.appmoon.ziyadkhalil.helenkeller.R;
import com.appmoon.ziyadkhalil.helenkeller.ViewModel;
import com.appmoon.ziyadkhalil.helenkeller.model.db.Deck;
import com.appmoon.ziyadkhalil.helenkeller.model.db.MTag;

import java.util.Arrays;
import java.util.List;

public class DeckActivity  extends AppCompatActivity {
    SectionStatePagerAdapter pagerAdapter;
    ViewPager viewPager;


    Deck currentDeck;
    private boolean writing;
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);
        nfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        viewPager = findViewById(R.id.container);
        pagerAdapter = new SectionStatePagerAdapter(getSupportFragmentManager());
        setupPager();
        int deckID = getIntent().getIntExtra("deckID",32);
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        Log.d("truth",viewModel.getAllDecks().toString());
        currentDeck = viewModel.getDeckByID(deckID);
        setTitle(currentDeck.getDeckName());
        viewPager.setCurrentItem(0);
    }

    private void setupPager() {
        pagerAdapter.addFragment(new MainFragment(),"Main Fragment");
        pagerAdapter.addFragment(new AddTagFragment(),"Add MTag Fragment");
        viewPager.setAdapter(pagerAdapter);
    }


    public Deck getCurrentDeck() {
        return currentDeck;
    }

    public void navToFrag(int fragmentNumber){
        viewPager.setCurrentItem(fragmentNumber);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("tag", "bdan");
        if(intent.hasExtra(NfcAdapter.EXTRA_TAG)&&writing) {
            android.nfc.Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Log.d("tag", Arrays.toString(tag.getId()));
            ((AddTagFragment) pagerAdapter.getItem(1)).writeTag(tag);
        }
    }


    void enableForegroundDispatchSystem(){
        Intent intent = new Intent(this,DeckActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(),0,intent,0);
        IntentFilter[] filters = new IntentFilter[]{};
        nfcAdapter.enableForegroundDispatch(this,pendingIntent,filters,null);
    }

    void disableForegroundDispatchSystem(){
        nfcAdapter.disableForegroundDispatch(this);
    }



    public void setWritingModeOn() {
        this.writing=true;
    }

    public void setWritingModeOff() {
        this.writing=false;
    }


    @Override
    protected void onPause() {
        super.onPause();
        disableForegroundDispatchSystem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableForegroundDispatchSystem();
    }


}
