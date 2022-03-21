package com.example.mainfarahy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
LinearLayout CheckList;
LinearLayout GuestList;
LinearLayout VendorLayout;
    LinearLayout AppointmentLayout;
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

VendorLayout=findViewById(R.id.vendor_id);
VendorLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this,VendorActivity.class);
        startActivity(intent);
    }
});

        AppointmentLayout=findViewById(R.id.Appointment_id);
        AppointmentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent=new Intent(MainActivity.this,AppointmentsActivity.class);
                //startActivity(intent);
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", false);
                intent.putExtra("rrule", "FREQ=DAILY");
                intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                intent.putExtra("title", "");
                startActivity(intent);
            }
        });
    }
}