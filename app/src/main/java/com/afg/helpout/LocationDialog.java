package com.afg.helpout;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * The LocationDialog Class
 *
 * TODO Complete Documentation
 */
public class LocationDialog extends AppCompatDialogFragment {

    // Initializing Variables
    private EditText town;
    private EditText state;
    private LocationDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        // Initialize Pop Up Screen
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        // Get References for each EditText
        town = view.findViewById(R.id.edit_town);
        state = view.findViewById(R.id.edit_state);

        // Create Pop Up Screen
        builder.setView(view).setTitle("Set Location").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String town = LocationDialog.this.town.getText().toString();
                String state = LocationDialog.this.state.getText().toString();
                listener.applyTexts(town, state);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (LocationDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ExampleDialogListener.");
        }
    }

    public interface LocationDialogListener{
        void applyTexts(String town, String state);
    }

}
