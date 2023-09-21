package com.foru.mainfarahy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.foru.mainfarahy.ui.home.GroupData;
import com.foru.mainfarahy.ui.home.RetriveActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

//import com.facebook.ads.AdSize;
//import com.facebook.ads.AdView;
//import com.facebook.ads.AudienceNetworkAds;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import render.animations.Attention;
import render.animations.Render;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;

public class MainActivity extends AppCompatActivity {
LinearLayout CheckList;
LinearLayout GuestList;
LinearLayout VendorLayout;
    LinearLayout AppointmentLayout;
    ImageView LoveHeart;
    TextView anding;
    ConstraintLayout gra;
    TextView dot_1,dot_2,date_1,date_2,date_3,until;
    int YearNow;
    int MonthNow;
    int DayNow;
    DataBaseHelperDate myDb;
    int YearWedding=0;
    int MonthWedding=0;
    int DayWedding=0;
    TextView yearText;
    TextView monthText;
    TextView daysText;
    private List<GroupData> groupDataList;

    private DatabaseReference databaseReference;

    TextView weddingday;
    TextView bridename;
    TextView bridManName;
    private AdView mAdView;
    LottieAnimationView lottieAnimationView;
    DataBaseHelper myDbGehaz;
    LinearLayout LoveInfo;
    private AdView adView;
    Handler handler = new Handler();
    int randomeAdSelect;
    Runnable runnable;
    int delay = 1*1000; //Delay for 15 seconds.  One second = 1000 milliseconds.

    @Override
    protected void onResume() {
        //start handler as activity become visible

        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                render.start();
                handler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

// If onPause() is not included the threads will double up when you
// reload the activity

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }
   /*
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
    */
    /*
    public void loadadd(){
        // Instantiate an AdView object.
// NOTE: The placement ID from the Facebook Monetization Manager identifies your App.
// To get test ads, add IMG_16_9_APP_INSTALL# to your placement id. Remove this when your app is ready to serve real ads.
AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "724060921940186_730002981345980", AdSize.BANNER_HEIGHT_50);

// Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

// Add the ad view to your activity layout
        adContainer.addView(adView);

// Request an ad
        adView.loadAd();
    }
    */
   private AdSize getAdSize() {
       Display display = getWindowManager().getDefaultDisplay();
       DisplayMetrics outMetrics = new DisplayMetrics();
       display.getMetrics(outMetrics);
       float widthPixels = outMetrics.widthPixels;
       float density = outMetrics.density; int adWidth = (int) (widthPixels / density);

       return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth); }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    //    loadadd();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        groupDataList = new ArrayList<>();
        myDb= new DataBaseHelperDate(this);
        myDbGehaz= new DataBaseHelper(this);
        dot_1=findViewById(R.id.dot_1);
        dot_2=findViewById(R.id.dot_2);
        date_1=findViewById(R.id.date_1);
        date_2=findViewById(R.id.date_2);
        date_3=findViewById(R.id.date_3);
        until=findViewById(R.id.until);
        getSupportActionBar().hide();
        lottieAnimationView=findViewById(R.id.animationView);

        AdView adView = new AdView(this);

        //adView.setAdSize(AdSize.BANNER);
        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
//ca-app-pub-3940256099942544/6300978111
        adView.setAdUnitId(String.valueOf(R.string.ADS_1));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                mAdView.loadAd(adRequest);
            }
        });

        yearText=findViewById(R.id.year_id);
        monthText=findViewById(R.id.months_id);
        daysText=findViewById(R.id.days_id);
        anding=findViewById(R.id.anding_id);
        gra=findViewById(R.id.gra);
weddingday=findViewById(R.id.weddingday);
bridename=findViewById(R.id.bridname);
bridManName=findViewById(R.id.brideManName_id);

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
        Intent intent=new Intent(MainActivity.this,BottomNavigationActivity.class);
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
LoveInfo=findViewById(R.id.infoheart_id);
        LoveHeart = findViewById(R.id.imageheart_id);
        LoveHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.love_dialog, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                EditText manName=dialogView.findViewById(R.id.manName_id);
                EditText womanNAme=dialogView.findViewById(R.id.womanName_id);
                manName.setText(bridManName.getText());
                womanNAme.setText(bridename.getText());

                Button ok_Button =dialogView.findViewById(R.id.button_ok);
                DatePicker datePicker=dialogView.findViewById(R.id.dataPicker);
                Calendar calendar=Calendar.getInstance();
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker mydatePicker, int year, int monthOfYear, int dayOfMonth) {
                     //   Toast.makeText(getApplicationContext(),mydatePicker.getDayOfMonth()+"-"+mydatePicker.getMonth()+"-"+mydatePicker.getYear(),Toast.LENGTH_SHORT).show();
                         YearNow = calendar.get(Calendar.YEAR);
                         MonthNow = calendar.get(Calendar.MONTH);
                         DayNow=calendar.get(Calendar.DAY_OF_MONTH);
                      //  Toast.makeText(getApplicationContext(),YearNow+"-"+MonthNow+"-"+DayNow,Toast.LENGTH_SHORT).show();

                        YearWedding=mydatePicker.getYear();
                        DayWedding=mydatePicker.getDayOfMonth();
                        MonthWedding=mydatePicker.getMonth();




                    }
                });

                ok_Button.setOnClickListener(new View.OnClickListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        //insertData("sana","bebo","2023","3","7");
                        String YearResult=String.valueOf(YearWedding);
                        String MonthResult=String.valueOf(MonthWedding);
                        String DayResult=String.valueOf(DayWedding);
                        MonthResult= String.valueOf(Integer.valueOf(MonthResult)+1);

                        if(YearResult.length()==1)
                        {YearResult="0"+YearResult;}

                        if(MonthResult.length()==1)
                        {MonthResult="0"+MonthResult;}

                        if(DayResult.length()==1)
                        {DayResult="0"+DayResult;}

                        ///////////////////////////////////////////////////////////////////

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                        Date birth = null;
                        try {//"2022-01-01"
                          //  Toast.makeText(getApplicationContext(),"Years : "+YearResult+"Months : "+MonthResult+ "days : "+DayResult,Toast.LENGTH_SHORT).show();
                        String wedd=YearResult+"-"+MonthResult+"-"+DayResult;
                            birth = sdf.parse(wedd);
                           // Toast.makeText(getApplicationContext(),wedd,Toast.LENGTH_SHORT).show();

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date now = new Date(System.currentTimeMillis());

                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(birth.getTime()- now.getTime()  );
                        int y = c.get(Calendar.YEAR)-1970;
                        int m = c.get(Calendar.MONTH);
                        int d = c.get(Calendar.DAY_OF_MONTH)-1;


    //Toast.makeText(getApplicationContext(),"Years : "+y+"Months : "+m+ "days : "+d,Toast.LENGTH_SHORT).show();
    //abl el sana
                        if (y<0 || m<0 || d<0){
                            Snackbar.make(v, "ضعي توقيت مناسب", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                        }else {

                            yearText.setText(String.valueOf(y));
                            monthText.setText(String.valueOf(m));
                            daysText.setText(String.valueOf(d));
                            weddingday.setText(YearWedding+"/"+MonthResult+"/"+DayWedding);

                            bridename.setText(womanNAme.getText());
                            bridManName.setText(manName.getText());
                            MonthResult= String.valueOf(Integer.valueOf(MonthResult)-1);
                            updateData(String.valueOf(womanNAme.getText()),String.valueOf(manName.getText()),String.valueOf(YearWedding),MonthResult,String.valueOf(DayWedding));
                            alertDialog.dismiss();

                        }






/**

                        if ((YearWedding-YearNow) <0){
                            Snackbar.make(v, "ضعي توقيت مناسب", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//nfs el sana shar adeem
                        }else if ((MonthWedding-MonthNow)<0 &&(DayWedding-DayNow) <0 &&(YearWedding-YearNow) <0){
                            Snackbar.make(v, "ضعي توقيت مناسب", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    //nfs el sana shahr odam yoom abl
                            //nfs el sana shahr odam yoom odam
                        }else if((MonthWedding-MonthNow)>0 ){
                            //save and update
                            String YearResult=String.valueOf(YearWedding-YearNow);
                            String MonthResult=String.valueOf(MonthWedding-MonthNow);
                            String DayResult=String.valueOf(DayWedding);

                            int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - DayNow;
                          DayResult = String.valueOf(Integer.valueOf(DayResult)+maxDay);
                            MonthResult= String.valueOf(Integer.valueOf(MonthResult)-1);

                            if(YearResult.length()==1)
                            {YearResult="0"+YearResult;}

                            if(MonthResult.length()==1)
                            {MonthResult="0"+MonthResult;}

                            if(DayResult.length()==1)
                            {DayResult="0"+DayResult;}

                            yearText.setText(YearResult);
                            monthText.setText(MonthResult);
                            daysText.setText(DayResult);
                            weddingday.setText(YearWedding+"/"+MonthWedding+"/"+DayWedding);

                            bridename.setText(womanNAme.getText());
                            bridManName.setText(manName.getText());
                            alertDialog.dismiss();
                            //sana odam sana shahr wara yoom abl
                            //sana odam sana shahr wara yoom ba3d
                        }else if ((MonthWedding-MonthNow)<0 ){



                            String YearResult=String.valueOf(YearWedding-YearNow);
                            String MonthResult=String.valueOf(MonthNow-MonthWedding);
                            String DayResult=String.valueOf(DayWedding);

                            int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - DayNow;
                            DayResult = String.valueOf(Integer.valueOf(DayResult)+maxDay);

                            MonthResult= String.valueOf(Integer.valueOf(MonthResult)-1);

                            if(YearResult.length()==1)
                            {YearResult="0"+YearResult;}

                            if(MonthResult.length()==1)
                            {MonthResult="0"+MonthResult;}

                            if(DayResult.length()==1)
                            {DayResult="0"+DayResult;}

                            yearText.setText(YearResult);
                            monthText.setText(MonthResult);
                            daysText.setText(DayResult);
                            weddingday.setText(YearWedding+"/"+MonthWedding+"/"+DayWedding);
                            bridename.setText(womanNAme.getText());
                            bridManName.setText(manName.getText());
                            alertDialog.dismiss();
                            //nfs el sana shahr odam yoom odam
                        }else if ((MonthWedding-MonthNow)==0 ){


                            String DayResult;
                            String YearResult=String.valueOf(YearWedding-YearNow);
                            String MonthResult=String.valueOf(MonthNow-MonthWedding);
                            if (DayWedding>DayNow)
                            {
                                 DayResult=String.valueOf(DayWedding-DayNow);
                            }else {
                                 DayResult=String.valueOf(DayNow-DayWedding);

                            }


                           // int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - DayNow;
                            //DayResult = String.valueOf(Integer.valueOf(DayResult)+maxDay);
                           // MonthResult= String.valueOf(Integer.valueOf(MonthResult)-1);

                            if(YearResult.length()==1)
                            {YearResult="0"+YearResult;}

                            if(MonthResult.length()==1)
                            {MonthResult="0"+MonthResult;}

                            if(DayResult.length()==1)
                            {DayResult="0"+DayResult;}

                            yearText.setText(YearResult);
                            monthText.setText(MonthResult);
                            daysText.setText(DayResult);

                            weddingday.setText(YearWedding+"/"+MonthWedding+"/"+DayWedding);
                            bridename.setText(womanNAme.getText());
                            bridManName.setText(manName.getText());
                            alertDialog.dismiss();
                            //nfs el sana shahr odam yoom odam
                        }



*/






                        //alertDialog.dismiss();
                    }
                });
            }
        });
        // get Hight and width
        double di = Double.parseDouble(getScreenSize());
        //Toast.makeText(getApplicationContext(),String.valueOf(getScreenSize()),Toast.LENGTH_SHORT).show();
//Density = sqrt((wp * wp) + (hp * hp)) / di
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int hp = displayMetrics.heightPixels;
        int wp = displayMetrics.widthPixels;

        double Density = Math.sqrt((wp * wp) + (hp * hp)) / di;
       // Toast.makeText(getApplicationContext(),String.valueOf(Density),Toast.LENGTH_SHORT).show();
//372.49di



        loadallData();
     if (myDbGehaz.getALData().getCount()<=0)
     {
        ShowIntro("", "هنا يتم عرض العد التنازلي ليوم الفرح", R.id.infoheart_id, 1);


     }
       // setSize(Density);
        // Create Render Class
         render = new Render(MainActivity.this);

// Set Animation
        render.setAnimation(Attention.Pulse(gra));
        render.setDuration(6000);
        //render.start();

        LinearLayout HenaLayout;
        HenaLayout=findViewById(R.id.hena_id);
        HenaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,HenaActivity.class);
                startActivity(intent);
            }
        });



        loadADs();
        findViewById(R.id.centerImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RetriveActivity.class);
                intent.putExtra("STRING_EXTRA",groupDataList.get(randomeAdSelect).getUserId() );

                startActivity(intent);
            }
        });
    }
    Render render;
private void loadADs(){
    databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            groupDataList.clear();
/*
                for (DataSnapshot childSnapshot : groupSnapshot.child("children").getChildren()) {
                    ChildData childData = new ChildData();
                    childData.setPhoneNumber( childSnapshot.child("phoneNumber").getValue(String.class));
                    childDataList.add(childData);
                }

*/


            for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                GroupData groupData = new GroupData();
                //   Log.e("my joe id", String.valueOf( groupSnapshot.getKey()));
                String userID =groupSnapshot.getKey();
                groupData.setStoreName(groupSnapshot.child(userID).child("StoreName").getValue(String.class));
                groupData.setImageUrl(groupSnapshot.child(userID).child("profileImage").getValue(String.class));
                groupData.setBusinessName(groupSnapshot.child(userID).child("BusinessName").getValue(String.class));
                groupData.setviewsCount(groupSnapshot.child(userID).child("myviews").getValue(String.class));
                groupData.setUserId(userID);
                groupData.setPhoneNumber(groupSnapshot.child(userID).child("phoneNumber").getValue(String.class));


                String EndDate=groupSnapshot.child(userID).child("endDate").getValue(String.class);
                String Status =groupSnapshot.child(userID).child("status").getValue(String.class);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                if(Status!=null){
                    if (Status.equals("Active")){
                        if (EndDate!=null) {
                            try {
                                Date givenDate = dateFormat.parse(EndDate);
                                Date currentDate = new Date();

                                long differenceInMillis = givenDate.getTime() - currentDate.getTime();
                                long differenceInDays = TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);

                                if (differenceInDays >= 0) {

                                    groupDataList.add(groupData);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }


            }
            if (!groupDataList.isEmpty()){
                // Create a new Random object
                Random random = new Random();
                //select random ads
                 randomeAdSelect=random.nextInt(groupDataList.size());

                ImageView imageView = findViewById(R.id.centerImage);
                // groupDataList.get(0).getImageUrl()

                Glide.with(getApplicationContext())
                        .load(groupDataList.get(randomeAdSelect).getImageUrl())
                        .into(imageView);

                TextView myTextAd=findViewById(R.id.myTextAd);
                myTextAd.setText(groupDataList.get(randomeAdSelect).getStoreName());

                TextView myTextAd2=findViewById(R.id.myTextAd2);
                myTextAd2.setText(groupDataList.get(randomeAdSelect).getBusinessName());
                if (groupDataList.get(randomeAdSelect).getImageUrl()!=null){

                    findViewById(R.id.Ad_Image2).setVisibility(View.VISIBLE);
                    findViewById(R.id.Ad_Image1).setVisibility(View.VISIBLE);
                }
            }



            //display image here
        }



        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e("FirebaseRead", "Failed to read data.", databaseError.toException());
        }
    });
}
   public void CloseAd(View view){
     findViewById(R.id.Ad_Image2).setVisibility(View.GONE);
     findViewById(R.id.Ad_Image1).setVisibility(View.GONE);

   }
    private void   loadallData(){
        //ArrayList<GuestClass> Headers = new ArrayList<>();
        Cursor res = myDb.getALData();
        // StringBuffer stringBuffer=new StringBuffer();

        if(res !=null && res.getCount()>0){
            while (res.moveToNext()){
                String myid=new String();
                String Femalename;
                String MaleName;
                String MARGE_DATE_YEAR;
                String MARGE_DATE_MONTH;
                String MARGE_DATE_DAY;
                myid = String.valueOf(res.getString(0));
                Femalename = String.valueOf(res.getString(1));
                MaleName = String.valueOf(res.getString(2));
                MARGE_DATE_YEAR = String.valueOf(res.getString(3));
                MARGE_DATE_MONTH = String.valueOf(res.getString(4));
                MARGE_DATE_DAY = String.valueOf(res.getString(5));
                bridename.setText(Femalename);
                bridManName.setText(MaleName);



                //////////////////////////////////////////////////
                String YearResult=String.valueOf(MARGE_DATE_YEAR);
                String MonthResult=String.valueOf(MARGE_DATE_MONTH);
                String DayResult=String.valueOf(MARGE_DATE_DAY);


                MonthResult= String.valueOf(Integer.valueOf(MonthResult)+1);

                if(YearResult.length()==1)
                {YearResult="0"+YearResult;}

                if(MonthResult.length()==1)
                {MonthResult="0"+MonthResult;}

                if(DayResult.length()==1)
                {DayResult="0"+DayResult;}

                ///////////////////////////////////////////////////////////////////

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Date birth = null;
                try {//"2022-01-01"
                    //  Toast.makeText(getApplicationContext(),"Years : "+YearResult+"Months : "+MonthResult+ "days : "+DayResult,Toast.LENGTH_SHORT).show();
                    String wedd=YearResult+"-"+MonthResult+"-"+DayResult;
                    birth = sdf.parse(wedd);
                    // Toast.makeText(getApplicationContext(),wedd,Toast.LENGTH_SHORT).show();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date now = new Date(System.currentTimeMillis());

                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(birth.getTime()- now.getTime()  );
                int y = c.get(Calendar.YEAR)-1970;
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH)-1;


                //Toast.makeText(getApplicationContext(),"Years : "+y+"Months : "+m+ "days : "+d,Toast.LENGTH_SHORT).show();
                //abl el sana

if (y<=0 && m<=0 && d<=0){
    lottieAnimationView.setVisibility(View.VISIBLE);
}else {
    lottieAnimationView.setVisibility(View.GONE);
}
                    yearText.setText(String.valueOf(y));
                    monthText.setText(String.valueOf(m));
                    daysText.setText(String.valueOf(d));
                    weddingday.setText(MARGE_DATE_YEAR+"/"+MonthResult+"/"+MARGE_DATE_DAY);







            }

        }


      //  return Headers;
    }

    private void insertData(String Femalename,String MaleName,String MARGE_DATE_YEAR,String MARGE_DATE_MONTH,String MARGE_DATE_DAY){

        Boolean result = myDb.insertData(Femalename,MaleName,MARGE_DATE_YEAR,MARGE_DATE_MONTH,MARGE_DATE_DAY);
        if (result== true){
            //  Toast.makeText(this,"Data inserted susccfully",Toast.LENGTH_SHORT).show();
        }else {
            //  Toast.makeText(this,"Data  insertion Faille",Toast.LENGTH_SHORT).show();
        }

    }

    private void updateData(String Femalename,String MaleName,String MARGE_DATE_YEAR,String MARGE_DATE_MONTH,String MARGE_DATE_DAY){
        Boolean result = myDb.updateData("1",Femalename,MaleName,MARGE_DATE_YEAR,MARGE_DATE_MONTH,MARGE_DATE_DAY);
        if (result){
           // Log.d(TAG, "onClick possss saved: "+String.valueOf(result) +"ID = "+ID+" myID = "+MY_ID );
            //  Toast.makeText(this,"Data updated susccfully",Toast.LENGTH_SHORT).show();
        }else {
         //   Log.d(TAG, "onClick possss saved: "+String.valueOf(result) +"ID = "+ID+" myID = "+MY_ID );
            //  Toast.makeText(this,"Data  updated Faille",Toast.LENGTH_SHORT).show();
        }

    }

    public void onAdsClicked(View view){
        String websiteUrl = "https://play.google.com/store/apps/details?id=com.foru.adsfarahy"; // Replace with your desired website URL
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
        startActivity(intent);
    }

    private void ShowIntro(String title, String text, int viewId, final int type) {

        new GuideView.Builder(this)
                .setTitle(title)
                .setContentText(text)
                .setTargetView((LinearLayout)findViewById(viewId))
                .setContentTextSize(20)//optional
                .setTitleTextSize(2)//optional
                .setDismissType(GuideView.DismissType.anywhere) //optional - default dismissible by TargetView
                .setGuideListener(new GuideView.GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        if (type == 1) {
                            ShowIntro("", "يمكنك تعديل موعد الفرح", R.id.infoheart_id, 6);
                        } else if (type == 6) {
                            ShowIntro("", "هنا يوجد جميع قائمة طلبات جهاز العروسة", R.id.CheckList_id, 2);
                        } else if (type == 2) {
                            ShowIntro("", "هنا يمكنك تحديد منبه او اشعار لتنبيهك بها لاحقا ", R.id.Appointment_id, 4);
                        } else if (type == 4) {
                            ShowIntro("", "هنا يمكنك كتابة قائمة المدعوين و تحديد من تم دعوته", R.id.guest_id, 3);
                        } else if (type == 3) {
                            ShowIntro("", "هنا مخصص لعمل المقارنات بالاسعار", R.id.vendor_id, 5);
                        }
                    }
                })
                .build()
                .show();
    }
    private String getScreenSize() {
        Point point = new Point();
        ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealSize(point);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width=point.x;
        int height=point.y;
        double wi=(double)width/(double)displayMetrics.xdpi;
        double hi=(double)height/(double)displayMetrics.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        return String.valueOf(Math.round((Math.sqrt(x+y)) * 10.0) / 10.0);
    }

    public void setSize(double di){
        /*
        //Yosif & Kho5a
        bridManName.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) ((float) di*20/372.5));
        bridename.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) ((float) di*20/372.5));
        anding.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) ((float) di*20/372.5));
        //1:5:20
        yearText.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) ((float) di*20/372.5));
        monthText.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) ((float) di*20/372.5));
        daysText.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) ((float) di*20/372.5));
        dot_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) ((float) di*20/372.5));
        dot_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) ((float) di*20/372.5));

        //years months days
        date_1.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) ((float) di*8/372.5));
        date_2.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) ((float) di*8/372.5));
        date_3.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) ((float) di*8/372.5));

        //until our wedding
        until.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) ((float) di*12/372.5));

        // 2/2/2022

        weddingday.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) ((float) di*10/372.5));

        LoveInfo.setPadding(0, (int) ((float) di*90/372.5), 0, 0);
        //Toast.makeText(getApplicationContext(),String.valueOf( (float) ((float) di*20/372.5)),Toast.LENGTH_SHORT).show();
        //LoveInfo.setm
       //  Log.d("yosif", "onClick possss saved: "+String.valueOf((float) ((float) di*20/372.5)) );
*/

        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                (float) ((float) di*70/372.5),
                r.getDisplayMetrics()
        );


        float px2 = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                (float) ((float) di*30/372.5),
                r.getDisplayMetrics()
        );

gra.setPadding((int) px, (int) px2, (int) px, 0);

    }
}