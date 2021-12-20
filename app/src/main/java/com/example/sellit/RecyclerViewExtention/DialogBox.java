package com.example.sellit.RecyclerViewExtention;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.sellit.R;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;


public class DialogBox extends AppCompatDialogFragment {

    private DialogListener listener;
    public ImageView phnImg;
    public TextView txtTitle, txtDesc;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);
        dialog.setView(view).setTitle("Test Title").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String title =txtTitle.getText().toString();
                String desc = txtDesc.getText().toString(); ;

                listener.applyText(title, desc);


            }
        });



        phnImg = (ImageView) view.findViewById(R.id.phnImg);
        txtTitle = (TextView) view.findViewById(R.id.txt_title);
        txtDesc = (TextView) view.findViewById(R.id.txt_desc);
        return dialog.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {

            listener = (DialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement first");
        }

    }

    public interface DialogListener {
        void applyText(String titl, String desc);
    }
}
