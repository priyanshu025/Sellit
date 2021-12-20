package com.example.sellit.RecyclerViewExtention;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sellit.R;

public class CustomDialogClass extends Dialog implements View.OnClickListener{

    public Activity c;
    public Dialog d;
    public ImageView phnImg;
    public TextView txtTitle, txtDesc;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        phnImg = (ImageView) findViewById(R.id.phnImg);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtDesc = (TextView) findViewById(R.id.txt_desc);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
