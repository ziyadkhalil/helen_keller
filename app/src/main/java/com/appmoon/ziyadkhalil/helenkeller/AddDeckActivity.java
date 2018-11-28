package com.appmoon.ziyadkhalil.helenkeller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

public class AddDeckActivity extends AppCompatDialogFragment {
    EditText editText;
    Button okButton;
    Button cancelButton;
    String inputText;
    AlertDialog alertDialog;
    View view;
    DialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.activity_add_deck,null);
        injectViews();
        builder.setView(view);
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

         alertDialog = builder.create();

        return alertDialog;



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "implement interface");
        }
    }

    private void injectViews() {
        editText = view.findViewById(R.id.editText);
    }

    public void setPositiveButton() {
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        inputText = editText.getText().toString();
                        if(inputText.equals("")){
                            Toast.makeText(getContext(),"Enter a valid name",Toast.LENGTH_SHORT).show();


                        }
                        else if (false){
                            //TODO: check if name already exists
                        }
                        else {
                            listener.addDeck(inputText);
                            dismiss();
                        }

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        setPositiveButton();
    }

    public interface DialogListener{
        void addDeck(String deckName);
    }
}
