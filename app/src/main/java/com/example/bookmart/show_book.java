package com.example.bookmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.bookmart.LoginActivity.username;
import static com.example.bookmart.test.DEL;
import static com.example.bookmart.test.lol;

public class show_book extends AppCompatActivity {
    public static String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book);
        Intent intent=getIntent();
        title=intent.getStringExtra(test.TITLE);
        String description=intent.getStringExtra(test.DESCRIPTION);
        String location=intent.getStringExtra(test.LOCATION);
        int price=intent.getIntExtra(test.PRICE,0);
        long contact=intent.getLongExtra(test.CONTACT,0);
        boolean del=intent.getBooleanExtra(test.DEL,false);
        String user=intent.getStringExtra(test.USER);


        TextView text_title= findViewById(R.id.textView6);
        TextView text_description=findViewById(R.id.textView9);
        TextView text_location=findViewById(R.id.textView13);
        TextView text_price=findViewById(R.id.textView11);
        TextView text_contact=findViewById(R.id.textView15);
        ImageView set_img=findViewById(R.id.imageView);
        TextView user_name=findViewById(R.id.textView7);


        DatabaseHelper db=new DatabaseHelper(this);
        byte[] image=db.get_image(title,contact,price);
        text_title.setText(title);
        text_description.setText(description);
        text_location.setText(location);
        text_contact.setText(""+contact);
        text_price.setText(""+price);
        user_name.setText(user);
        set_img.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        if(del){
            Button btn=findViewById(R.id.button2);
            btn.setVisibility(View.VISIBLE);

        }

    }

    public void delete_card(View view) {
        DatabaseHelper db=new DatabaseHelper(this);
        db.delete_card(username,title);
        Intent intent1=new Intent(this,test.class);
        startActivity(intent1);

    }
}