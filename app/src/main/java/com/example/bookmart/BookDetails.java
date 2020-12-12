package com.example.bookmart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BookDetails extends AppCompatActivity {
    RelativeLayout relativeLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Intent intent=getIntent();

        ImageView image=  findViewById(R.id.show_img);
        Toast.makeText(this,  "Image", Toast.LENGTH_SHORT).show();
        dis(image);

    }
    public void dis(ImageView images){
        Toast.makeText(this,  "Intent", Toast.LENGTH_SHORT).show();
        DatabaseHelper db=new DatabaseHelper(this);

        Bitmap bmimage = db.all_details();
        images.setImageBitmap(bmimage);
    }
    public void CreateCardViewProgrammatically(){

        CardView cardview = new CardView(this);

        ActionBar.LayoutParams layoutparams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT
        );

        cardview.setLayoutParams(layoutparams);

        cardview.setRadius(10);

        cardview.setPadding(25, 25, 25, 25);

        cardview.setCardBackgroundColor(Color.WHITE);

        cardview.setMaxCardElevation(30);

        cardview.setMaxCardElevation(6);

        TextView textview = new TextView(this);

        textview.setLayoutParams(layoutparams);

        textview.setText("CardView Programmatically");

        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);

        textview.setTextColor(Color.WHITE);

        textview.setPadding(25,25,25,25);

        textview.setGravity(Gravity.CENTER);

        cardview.addView(textview);

        relativeLayout.addView(cardview);

    }


}