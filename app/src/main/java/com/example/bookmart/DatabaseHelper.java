package com.example.bookmart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String REGISTER = "register";
    public static final String F_NAME = "F_name";
    public static final String L_NAME = "L_name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String CONTACT = "contact";
    public static final String PRICE = "price";
    public static final String LOCATION = "location";
    public static final String ADD_BOOK = "add_book";
    public static final String USER = "user";
    public static final int ver=4;

    public DatabaseHelper(@Nullable Context context) {

        super(context,   "All_database.db", null,ver);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_quer= "create table " + REGISTER + " (Id integer primary key autoincrement,"
                + F_NAME + " text," + L_NAME + " text," + EMAIL + " text," + PASSWORD + " text)";


        String create_add_book= "create table " + ADD_BOOK + " (id2 integer primary key autoincrement ,"
                + TITLE + " varchar(50), " + DESCRIPTION + " varchar(500) ,"
                + IMAGE + " blob," + CONTACT + " int(10)," + PRICE + " int(5)," + LOCATION + " text ,"+ USER +" varchar(100))";
        sqLiteDatabase.execSQL(create_add_book);
        sqLiteDatabase.execSQL(create_quer);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldver, int newver) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + REGISTER + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + ADD_BOOK + "'");
        onCreate(sqLiteDatabase);
    }

    public boolean add_new(String F_name,String L_name,String Email,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(F_NAME,F_name);
        cv.put(L_NAME,L_name);
        cv.put(EMAIL,Email);
        cv.put(PASSWORD,password);
        long insert = db.insert(REGISTER, null, cv);
        if(insert==-1){
            return false;
        }
        else {
            return true;
        }


    }

    public boolean check_login(String email,String password){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT id FROM REGISTER  WHERE email=? AND password=?",new String[]{email,password});
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            cursor.close();
            return true;
        }
        return false;
    }

    public boolean check_register(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM REGISTER WHERE TRIM(email) = '"+email.trim()+"'", null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            cursor.close();
            return true;
        }
        return false;
    }

    public boolean add_book(String title,String descrip,byte[] img,int price,long con,String loc,String user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TITLE,title);
        cv.put(DESCRIPTION,descrip);
        cv.put(IMAGE,img);
        cv.put(PRICE,price);
        cv.put(CONTACT,con);
        cv.put(LOCATION,loc);
        cv.put(USER,user);
        long insert = db.insert(ADD_BOOK, null, cv);
        db.close();
        return insert != -1;

    }
    public Bitmap all_details(){
        Log.i("nigga", "bc" );
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM add_book;" , null);
        cursor.moveToFirst();
        String name=cursor.getString(0);
        String dis=cursor.getString(1);
        Log.i("nigga", "all_details: "+name+" "+dis );
        byte[] img=cursor.getBlob(3);
        Bitmap img2 =BitmapFactory.decodeByteArray(img, 0, img.length);

        return img2;

    }

    public ArrayList<Bookdata> noice(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM add_book;" , null);
        ArrayList<Bookdata> bookdata=new ArrayList<Bookdata>();
        try {
            while (cursor.moveToNext()) {
                Bookdata bd=new Bookdata();
                bd.title=cursor.getString(1);
                bd.description=cursor.getString(2);
                bd.image=cursor.getBlob(3);
                bd.price=cursor.getInt(5);
                bd.contact=cursor.getLong(4);
                bd.location=cursor.getString(6);
                bd.user=cursor.getString(7);
                bookdata.add(bd);

            }
        } finally {
            cursor.close();
        }
        return bookdata;
    }


    public String get_name(String email){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM REGISTER WHERE TRIM(email) = '"+email.trim()+"'", null);
        cursor.moveToFirst();
        String Fname=cursor.getString(1);
        String Lname=cursor.getString(2);
        Log.i("noice", "bc");
        return  Fname+" "+Lname;

    }

    public byte[] get_image(String title,long contact,int price){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT image FROM add_book WHERE TRIM(title) = '"+title.trim()+"' and TRIM(contact) ='"+contact+"' and TRIM(price) ='"+price+"'", null);
        cursor.moveToFirst();
        byte[] img=cursor.getBlob(0);
        db.close();
        return img;
    }

    public boolean delete_card(String user,String title){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("delete FROM add_book WHERE TRIM(title) = '"+title.trim()+"' and TRIM(user) ='"+user+"'", null);
        if(cursor.getCount()>0) {
            cursor.close();
            return true;
        }
        return false;
    }
}

