package com.example.mainfarahy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
LinearLayout CheckList;
LinearLayout GuestList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        CheckList=findViewById(R.id.CheckList_id);
        CheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GehazCheckListActivityNew.class);
                startActivity(intent);
            }
        });

        GuestList=findViewById(R.id.guest_id);
        GuestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MyGuestActivity.class);
               startActivity(intent);
            }
        });


    }
}