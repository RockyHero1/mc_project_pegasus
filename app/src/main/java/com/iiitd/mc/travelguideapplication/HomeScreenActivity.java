package com.iiitd.mc.travelguideapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iiitd.mc.travelguideapplication.model.Places;

import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity {

    private RecyclerView rv;
    private DatabaseReference db;
    private HomeScreenAdapter ahs;
    private ArrayList<Places> listPlaces;
    private ImageButton userLogin;

    private EditText et_search;
    private Button but_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        rv = findViewById(R.id.recyclerViewList);

        et_search = findViewById(R.id.et_search);
        but_search = findViewById(R.id.but_search);

        but_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Uri uri = Uri.parse(" http://maps.google.com/maps?q=hospital&mrt=yp&sll=lat,lon&output=kml"); // missing 'http://' will cause crashed
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                Intent intent = new Intent(HomeScreenActivity.this, CheckInActivity.class);
//                intent.setData(uri);
                startActivity(intent);
            }
        });


        db = FirebaseDatabase.getInstance().getReference("PlacesInformation");
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        listPlaces = new ArrayList<>();
        ahs = new HomeScreenAdapter(this, listPlaces);
        rv.setAdapter(ahs);

        userLogin = findViewById(R.id.userLogin);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Places places = dataSnapshot.getValue(Places.class);
                    System.out.println("Place name = "+places.getPlaceName());
                    System.out.println("Place URL = "+places.getURL());
                    listPlaces.add(places);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });





    }


}