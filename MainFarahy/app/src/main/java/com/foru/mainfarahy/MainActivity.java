package com.foru.mainfarahy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;

public class MainActivity extends AppCompatActivity {
LinearLayout CheckList;
LinearLayout GuestList;
LinearLayout VendorLayout;
    LinearLayout AppointmentLayout;
    ImageView LoveHeart;
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
    TextView weddingday;
    TextView bridename;
    TextView bridManName;
    private AdView mAdView;
    LottieAnimationView lottieAnimationView;
    DataBaseHelper myDbGehaz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        myDb= new DataBaseHelperDate(this);
        myDbGehaz= new DataBaseHelper(this);

        getSupportActionBar().hide();
        lottieAnimationView=findViewById(R.id.animationView);

        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId(String.valueOf(R.string.ADS_1));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
       // mAdView.loadAd(adRequest);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                mAdView.loadAd(adRequest);
            }
        });
        yearText=findViewById(R.id.year_id);
        monthText=findViewById(R.id.months_id);
        daysText=findViewById(R.id.days_id);
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
     loadallData();
     if (myDbGehaz.getALData().getCount()<=0)
     {
        ShowIntro("", "هنا يتم عرض العد التنازلي ليوم الفرح", R.id.infoheart_id, 1);


     }

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

}