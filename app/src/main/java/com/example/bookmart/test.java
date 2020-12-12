package com.example.bookmart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.style.ClickableSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.bookmart.LoginActivity.username;

public class test extends AppCompatActivity {
    public static final String TITLE="title";
    public static final String DESCRIPTION="DESCRIPTION";
    public static final String LOCATION="location";
    public static final String PRICE="price";
    public static final String CONTACT="contact";
    public static final String IMG="img";
    public static final String USER="user";
    public static final String DEL="del";
    public static Bitmap lol;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent=getIntent();
        linearLayout = (LinearLayout) findViewById(R.id.linear1);
//        CreateCardViewProgrammatically();
//        for(int i=0 ;i<5;i++){
//        bc();
//        }

        DatabaseHelper db=new DatabaseHelper(this);
        ArrayList<Bookdata> alldata=db.noice();
        for(Bookdata x:alldata){
            book_card(x.getTitle(),x.getDescription(),x.getImage(),x.getPrice(),x.getContact(),x.getLocation(),x.getUser());

        }

    }

    public void book_card(final String title, final String description, final byte[] img, final int price, final long contact, final String location,final String user){
        CardView cardView= new CardView(test.this);
//        ActionBar.LayoutParams layoutparams = new ActionBar.LayoutParams(
//                ActionBar.LayoutParams.MATCH_PARENT,
//                500
//        );
        ActionBar.LayoutParams layoutparams = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        cardView.setLayoutParams(layoutparams);
        cardView.setRadius(105);

        cardView.setPadding(200,200,200,200);
        cardView.setPaddingRelative(200,200,200,200);
        cardView.getUseCompatPadding();
        cardView.setCardBackgroundColor(Color.WHITE);


        cardView.setMaxCardElevation(30);

        cardView.setMaxCardElevation(6);
        cardView.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(test.this,""+price,Toast.LENGTH_SHORT).show();
//                Toast.makeText(test.this,""+contact,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(test.this,show_book.class);
                intent.putExtra(TITLE,title);
                intent.putExtra(DESCRIPTION,description);
                intent.putExtra(LOCATION,location);
                intent.putExtra(CONTACT,contact);
                intent.putExtra(PRICE,price);
                intent.putExtra(USER,user);
                boolean set=false;
                if(user.equals(username)){
                    set=true;
                }
                intent.putExtra(DEL,set);

                startActivity(intent);
            }
        });


        LinearLayout.LayoutParams para_hor=new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        LinearLayout linearLayout_horizontal=new LinearLayout(test.this);
        linearLayout_horizontal.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout_horizontal.setLayoutParams(para_hor);



        ImageView imageView=new ImageView(this);
        LinearLayout.LayoutParams para=new LinearLayout.LayoutParams(
                500,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        imageView.setLayoutParams(para);
        Bitmap img2 = BitmapFactory.decodeByteArray(img, 0, img.length);
        imageView.setImageBitmap(img2);


        LinearLayout.LayoutParams para_ver=new LinearLayout.LayoutParams(
                345,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        LinearLayout linearLayout_vertical=new LinearLayout(this);
        linearLayout_vertical.setOrientation(LinearLayout.VERTICAL);
//        linearLayout_vertical.setLayoutParams(para_ver);

        LinearLayout.LayoutParams para_txt=new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                200
        );
        TextView textView1=new TextView(this);
        textView1.setLayoutParams(para_txt);
        textView1.setText("Title: \n"+title);
        TextView textView2=new TextView(this);
        textView2.setLayoutParams(para_txt);
        textView2.setText("Description: \n"+description);
        TextView textView3=new TextView(this);
        textView3.setLayoutParams(para_txt);
        textView3.setText("Price: \n"+String.valueOf(price));

        LinearLayout.LayoutParams para_new=new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        LinearLayout linearLayout_new=new LinearLayout(this);
        linearLayout_new.setOrientation(LinearLayout.VERTICAL);
        linearLayout_new.setLayoutParams(para_new);
        linearLayout_new.setPadding(20,40,20,20);
        linearLayout_new.setClickable(true);




        linearLayout_vertical.addView(textView1);
        linearLayout_vertical.addView(textView2);
        linearLayout_vertical.addView(textView3);
        linearLayout_horizontal.addView(imageView);
        linearLayout_horizontal.addView(linearLayout_vertical);
        cardView.addView(linearLayout_horizontal);
        linearLayout_new.addView(cardView);
        linearLayout.addView(linearLayout_new);


    }


    public void change_addbook(View view) {
        Intent intent = new Intent(this, AddBook.class);
        startActivity(intent);
    }
}