package com.example.bookmart;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import static com.example.bookmart.LoginActivity.username;

public class AddBook extends AppCompatActivity implements LocationListener {
//    -----------------------------------------------------------------------------------------------------------------------------
    LocationManager locationManager;
    TextView textView5;
    public static List<Address> addresses;

    private static final int PERMISSION_CODE = 1000;
    private static final int SELECT_CODE = 2000;
    Button camera_btn;
    ImageView display_img;
    Uri image_uri;
    Bitmap bitmap_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        textView5 = findViewById(R.id.textView5);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationEnabled();
        getLocation();

        Intent intent=getIntent();
        display_img=findViewById(R.id.imagetoadd);
    }

//    -------------------------------------------------------------------
private void locationEnabled() {
    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    boolean gps_enabled = false;
    boolean network_enabled = false;
    try {
        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    } catch (Exception e) {
        e.printStackTrace();
    }
    try {
        network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    } catch (Exception e) {
        e.printStackTrace();
    }
    if (!gps_enabled && !network_enabled) {
        new AlertDialog.Builder(AddBook.this)
                .setTitle("Enable GPS Service")
                .setMessage("We need your GPS location to show Near Places around you.")
                .setCancelable(false)
                .setPositiveButton("Enable", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                .setNegativeButton("Cancel", null)
                .show();
    }
}

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            textView5.setText(addresses.get(0).getAddressLine(0));

        } catch (Exception e) {
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this,  "Location Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this,  "Location Disabled", Toast.LENGTH_SHORT).show();
    }
//------------------------------------------------------------------------------------------------------


    public void take_pic(View view) {
        Intent image_intent=new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        if(image_intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(image_intent,PERMISSION_CODE);
        }
    }
    public void select_pic(View view) {
        Intent select_img=new Intent(Intent.ACTION_PICK);
        select_img.setType("image/*");
        startActivityForResult(select_img,SELECT_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap_img = (Bitmap) extras.get("data");

            Bitmap bitmap_new = Bitmap.createScaledBitmap(bitmap_img,
                    800, 1000, true);
            bitmap_img=bitmap_new;
            display_img.setImageBitmap(bitmap_img);
        }
        else if (requestCode == SELECT_CODE && resultCode == RESULT_OK){
            Uri picked_img=data.getData();
            InputStream inputStream= null;
            try {
                inputStream = getContentResolver().openInputStream(picked_img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap_img= BitmapFactory.decodeStream(inputStream);

            Bitmap bitmap_new = Bitmap.createScaledBitmap(bitmap_img, 800, 1000, true);
            bitmap_img=bitmap_new;
            display_img.setImageBitmap(bitmap_img);
        }
    }
    public static byte[] getBytes(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }


    public void save_details(View view) {
        EditText title=findViewById(R.id.title);
        EditText description=findViewById(R.id.description);
        EditText cost=findViewById(R.id.cost);
        EditText contact=findViewById(R.id.contact);
        String loc;
        loc = addresses.get(0).getAddressLine(0);
        Toast.makeText(this,  ""+Long.parseLong(contact.getText().toString()), Toast.LENGTH_SHORT).show();
        byte[] img=getBytes(bitmap_img);
        DatabaseHelper db = new DatabaseHelper(this);
        try {
            db.add_book(title.getText().toString(),description.getText().toString(),img,Integer.parseInt(cost.getText().toString()),
                Long.parseLong(contact.getText().toString()),loc,username);
        Toast.makeText(this,  "Book Posted", Toast.LENGTH_SHORT).show();
        Log.d("damn", "save_details: bc");
        } catch (NumberFormatException e) {
            Toast.makeText(this,  "Unable to insert"+e, Toast.LENGTH_LONG).show();
        }
        finally {
            Intent intent=new Intent(this,test.class);
            startActivity(intent);
        }
    }

}
