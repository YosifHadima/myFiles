package com.example.mainfarahy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    EditText dateText;
    EditText maleName;
    EditText femaleName;
    Button ok_Button;
    final Calendar myCalendar= Calendar.getInstance();
    DataBaseHelperDate myDb;
    String WeedingYear;
    String WeedingMonth;
    String WeedingDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        myDb= new DataBaseHelperDate(this);
        Cursor res = myDb.getALData();

        if (res!=null && res.getCount()>0){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        maleName=findViewById(R.id.male_id);
        femaleName=findViewById(R.id.female_id);
        ok_Button=findViewById(R.id.login_id);
        ok_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (maleName.getText().toString().trim().equals("") ||femaleName.getText().toString().trim().equals("") ||dateText.getText().toString().trim().equals("")){
                    Snackbar.make(v, "ضعي البيانات كاملة", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                }else {
                    insertData(femaleName.getText().toString().trim(),maleName.getText().toString().trim(),WeedingYear,WeedingMonth,WeedingDay);
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        dateText=(EditText) findViewById(R.id.my_wedding_day_id);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(LoginActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        dateText.setText(dateFormat.format(myCalendar.getTime()));

       // Toast.makeText(getApplicationContext(),String.valueOf(myCalendar.get(Calendar.MONTH)),Toast.LENGTH_SHORT).show();
        WeedingYear=String.valueOf(myCalendar.get(Calendar.YEAR));
        WeedingMonth=String.valueOf(myCalendar.get(Calendar.MONTH));
        WeedingDay=String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void insertData(String Femalename,String MaleName,String MARGE_DATE_YEAR,String MARGE_DATE_MONTH,String MARGE_DATE_DAY){

        Boolean result = myDb.insertData(Femalename,MaleName,MARGE_DATE_YEAR,MARGE_DATE_MONTH,MARGE_DATE_DAY);
        if (result== true){
            //  Toast.makeText(this,"Data inserted susccfully",Toast.LENGTH_SHORT).show();
        }else {
            //  Toast.makeText(this,"Data  insertion Faille",Toast.LENGTH_SHORT).show();
        }

    }
}