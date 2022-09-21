package com.foru.mainfarahy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
*/
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Arrays;
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
   // private LoginButton loginButton;
    //CallbackManager callbackManager;
   // private static final String EMAIL = "email";
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
*/
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //  FacebookSdk.sdkInitialize(getApplicationContext());
    //    AppEventsLogger.activateApp(getApplication());
        getSupportActionBar().hide();
      //   callbackManager = CallbackManager.Factory.create();




      //  loginButton = (LoginButton) findViewById(R.id.login_button);
     //   loginButton.setVisibility(View.GONE);
       // loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration

        myDb= new DataBaseHelperDate(this);
        Cursor res = myDb.getALData();
        //ok_Button.setVisibility(View.VISIBLE);

// Callback registration
        //ok_Button.setVisibility(View.GONE);
        /*
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
               // Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                //startActivity(intent);
                //finish();
                // in case of sign in with facebook , change login facebook button to basic one
                loginButton.setVisibility(View.GONE);
                ok_Button.setVisibility(View.VISIBLE);

                //in case this account has perivous data sign in ((just for the updated policy))
                if (res!=null && res.getCount()>0){
                     Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
*/    //in case this account has perivous data sign in ((just for the updated policy))


      //  AccessToken accessToken = AccessToken.getCurrentAccessToken();

      //  boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        //if you logged in by fb before singn in
        /*
        if (isLoggedIn){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
*/


        maleName=findViewById(R.id.male_id);
        femaleName=findViewById(R.id.female_id);
        ok_Button=findViewById(R.id.login_id);
    //   ok_Button.setVisibility(View.GONE);
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

// in case this accoumt hase pervious data

        if (res!=null && res.getCount()>0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
            /*
maleName.setVisibility(View.GONE);
femaleName.setVisibility(View.GONE);
dateText.setVisibility(View.GONE);
            TextView congrat_id=findViewById(R.id.congrat_id);
            congrat_id.setText("Sorry \n Policy Update!!!");
            */
           // congrat_id.setVisibility(View.GONE);

        }


    //}

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