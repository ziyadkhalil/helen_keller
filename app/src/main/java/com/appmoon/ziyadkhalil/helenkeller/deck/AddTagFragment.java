package com.appmoon.ziyadkhalil.helenkeller.deck;

import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoon.ziyadkhalil.helenkeller.R;
import com.appmoon.ziyadkhalil.helenkeller.ViewModel;
import com.appmoon.ziyadkhalil.helenkeller.model.db.MTag;
import com.github.lzyzsd.randomcolor.RandomColor;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

public class AddTagFragment extends Fragment {

    View view;
    TextView nfcEnable;
    LinearLayout layoutNFCenabld;
    GridLayout gridLayoutNFCenabled;
    TextInputEditText inputText;
    Button okBtn;
    Button cancelBtn;
    LinearLayout tapTagLayout;
    Button textColor;
    Button backgroundColor;

    long tagID;
    NfcAdapter nfcAdapter;
    Tag writtenTag;
    String content;
    boolean success;
    boolean writingMode;

    DeckActivity deckActivity;
    private MTag myTag;
    String value;

    ViewModel viewModel;
    ColorPickerDialog.Builder backgroundBuilder;
    LiveData<List<MTag>> liveTags;
    private int color;
    private ColorPickerDialog.Builder textBuilder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_tag,container,false);

        injectViews();

        buildColorPickers();

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModel.class);

        nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());



        if(nfcAdapter.isEnabled())
            showViews();
        else
            hideViews();

        setBtnsActionListeners();


        deckActivity = (DeckActivity)getActivity();


        return view;
    }

    private void buildColorPickers() {
        backgroundBuilder = new ColorPickerDialog.Builder(getContext(),AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        backgroundBuilder.setTitle("Background Color");
        backgroundBuilder.setPositiveButton("Confirm", new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                backgroundColor.setBackgroundColor(envelope.getColor());
            }
        });
        backgroundBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        backgroundBuilder.attachBrightnessSlideBar();
        textBuilder = new ColorPickerDialog.Builder(getContext(),AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        textBuilder.setTitle("Background Color");
        textBuilder.setPositiveButton("Confirm", new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                textColor.setBackgroundColor(envelope.getColor());
            }
        });
        textBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        textBuilder.attachBrightnessSlideBar();
    }

    private void setBtnsActionListeners() {
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = inputText.getText().toString();
                myTag = new MTag();
                content = String.valueOf(tagID);
                Log.d("reem",String.valueOf(myTag.getId()));
                deckActivity.enableForegroundDispatchSystem();
                writingMode = true;
                ((DeckActivity)getActivity()).setWritingModeOn();
                setScreen();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DeckActivity)getActivity()).navToFrag(0);
                writingMode = false;
                ((DeckActivity)getActivity()).setWritingModeOff();

            }
        });

        backgroundColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildColorPickers();
                backgroundBuilder.setTitle("Background Color");
                backgroundBuilder.show();
            }

        });
        textColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildColorPickers();
                textBuilder.setTitle("Text Color");
                textBuilder.show();
            }
        });
    }

    private void hideViews() {
        nfcEnable.setVisibility(View.VISIBLE);
        gridLayoutNFCenabled.setVisibility(View.GONE);
        layoutNFCenabld.setVisibility(View.GONE);
        tapTagLayout.setVisibility(View.GONE);

    }

    private void setScreen(){
        if(writingMode){
            tapTagViews();
        }
        else{

            if(nfcAdapter.isEnabled())
                showViews();
            else
                hideViews();
        }

    }

    private void showViews() {
        nfcEnable.setVisibility(View.GONE);
        gridLayoutNFCenabled.setVisibility(View.VISIBLE);
        layoutNFCenabld.setVisibility(View.VISIBLE);
        tapTagLayout.setVisibility(View.GONE);
    }

    private void tapTagViews(){
        nfcEnable.setVisibility(View.GONE);
        gridLayoutNFCenabled.setVisibility(View.GONE);
        layoutNFCenabld.setVisibility(View.GONE);
        tapTagLayout.setVisibility(View.VISIBLE);
    }

    private void injectViews() {
        nfcEnable = view.findViewById(R.id.enableNFC);
        layoutNFCenabld = view.findViewById(R.id.hideLayout);
        gridLayoutNFCenabled = view.findViewById(R.id.hideGrid);
        inputText = view.findViewById(R.id.inputToWrite);
        okBtn = view.findViewById(R.id.okBtn);
        cancelBtn = view.findViewById(R.id.cancelBtn);
        tapTagLayout = view.findViewById(R.id.tapTagLayout);
        backgroundColor = view.findViewById(R.id.backgroundColor);
        textColor = view.findViewById(R.id.text_color);

    }

    private void formatTag(Tag tag, NdefMessage ndefMessage){
        try {
            NdefFormatable ndefFormatable = NdefFormatable.get(tag);
            if (ndefFormatable == null) {
                Toast.makeText(getContext(),":(",Toast.LENGTH_SHORT).show();
                return;
            }
            ndefFormatable.connect();
            ndefFormatable.format(ndefMessage);
//            ((DeckActivity)getActivity()).navToFrag(0);
            Toast.makeText(getContext(),"WRITTENxD",Toast.LENGTH_SHORT).show();
            success = true;

            ndefFormatable.close();

        } catch (Exception e){

        }

    }

    public void writeTag(Tag tag){
        Log.d("zep", String.valueOf(tag.toString()));
        Log.d("zep", "ya bdamni");
        myTag.setDeckID(deckActivity.getCurrentDeck().getId());
        myTag.setTagID(tag.getId().toString());
        myTag.setBackgroundColor(((ColorDrawable)backgroundColor.getBackground()).getColor());
        myTag.setTextColor(((ColorDrawable)textColor.getBackground()).getColor());
        myTag.setText(value);
        tagID = viewModel.insert(myTag);
        NdefMessage ndefMessage = createNdefMessage();
        Log.d("reem",String.valueOf(tagID));
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef == null)
                formatTag(tag, ndefMessage);
            else {
                ndef.connect();
                if(!ndef.isWritable()){
                    Toast.makeText(getActivity(),"Not Writable",Toast.LENGTH_LONG).show();
                    ndef.close();
                    return;
                }
                ndef.writeNdefMessage(ndefMessage);
                ndef.close();
                deckActivity.disableForegroundDispatchSystem();
                Toast.makeText(getActivity(),"WRITTEN",Toast.LENGTH_SHORT).show();
                success = true;
//                ((DeckActivity)getActivity()).navToFrag(0);
            }
        }
        catch (Exception e){

        }
        finally {
                ((DeckActivity) getActivity()).navToFrag(0);
                writtenTag = tag;
                Toast.makeText(getContext(), "Written Successfully", Toast.LENGTH_SHORT).show();
                writingMode  = false;
            ((DeckActivity)getActivity()).setWritingModeOff();
            viewModel.incrementDeckTagNumber(myTag.getDeckID());
            setScreen();


        }
    }



    private NdefRecord createTextRecord(String content){
        try{
            byte[] language;
            language = Locale.getDefault().getLanguage().getBytes("UTF-8");
            final byte[] text = content.getBytes("UTF-8");
            final int languageSize = language.length;
            final int textLength = text.length;
            final ByteArrayOutputStream payload = new ByteArrayOutputStream(1+languageSize+textLength);

            payload.write((byte) (languageSize & 0x1F));
            payload.write(language,0,languageSize);
            payload.write(text, 0,textLength);
            return  new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_URI,new byte[0],payload.toByteArray());
        }
        catch (UnsupportedEncodingException e){

        }
        return null;
    }


    public NdefMessage createNdefMessage(){
        NdefRecord ndefRecord = createTextRecord(String.valueOf(tagID));
        Log.d("reem",String.valueOf(tagID) + "hmmmm");
        NdefRecord firstRecord =  NdefRecord.createUri("zepp://com.appmoon");
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{firstRecord,ndefRecord});
        return  ndefMessage;
    }







}
