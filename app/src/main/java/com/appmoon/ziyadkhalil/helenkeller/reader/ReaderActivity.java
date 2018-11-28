package com.appmoon.ziyadkhalil.helenkeller.reader;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoon.ziyadkhalil.helenkeller.R;
import com.appmoon.ziyadkhalil.helenkeller.ViewModel;
import com.appmoon.ziyadkhalil.helenkeller.model.db.MTag;
import com.appmoon.ziyadkhalil.helenkeller.model.db.TagDao;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class ReaderActivity extends AppCompatActivity {
    NfcAdapter adapter;
    CoordinatorLayout layout;
    TextView text;
    TextToSpeech tts;
    private boolean canSpeak;
    private ViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_tag);
        injectViews();
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        adapter = NfcAdapter.getDefaultAdapter(this);
        if(adapter!=null&&adapter.isEnabled()){
            Intent intent = getIntent();
            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if(parcelables!=null&&parcelables.length>0){
                readRecords( (NdefMessage)parcelables[0]);
            }


            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status==TextToSpeech.SUCCESS) {

                        int result = tts.setLanguage(Locale.US);
                        if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS","Language not Supported");

                        }
                        speak();
                        canSpeak = true;
                    }
                }
            });
            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {

                }

                @Override
                public void onDone(String utteranceId) {
                    finish();
                }

                @Override
                public void onError(String utteranceId) {

                }
            });
        }

    }

    private void injectViews() {
        layout = findViewById(R.id.readLayout);
        text = findViewById(R.id.readText);
        }


    private void speak() {
        String txt = text.getText().toString();
        tts.speak(txt,TextToSpeech.QUEUE_FLUSH,null,TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);

    }

    private void readRecords(NdefMessage parcelable) {
        NdefRecord[] records = parcelable.getRecords();
        if(records!=null&&records.length>0){

            String content = getTextFromNdefRecord(records[1]);
            System.out.println(content);
            Log.d("pls",content);
            MTag tag = viewModel.getTagByID(Long.valueOf(content));
            System.out.println(tag);
//            Log.d("pls",tag.toString());
            text.setText(tag.getText());
            layout.setBackgroundColor(tag.getBackgroundColor());
            text.setTextColor(tag.getTextColor());
        }
    }

    public String getTextFromNdefRecord(NdefRecord ndefRecord)
    {
        String tagContent = null;
        try {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1,
                    payload.length - languageSize - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("getTextFromNdefRecord", e.getMessage(), e);
        }
        return tagContent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.stop();
        tts.shutdown();
    }
}
