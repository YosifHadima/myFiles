package com.foru.mainfarahy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class FoodHenaActivity extends AppCompatActivity {
ListView listView;
String FileName="myFood";
    private AdView mAdView;
    ArrayList<FoodClass> myFoodList=new ArrayList<>();
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
        setContentView(R.layout.activity_food_hena);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getSupportActionBar().hide();
        AdView adView = new AdView(this);

        // adView.setAdSize(AdSize.FULL_BANNER);
        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        adView.setAdUnitId(String.valueOf(R.string.ADS_6));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                mAdView.loadAd(adRequest);
            }
        });

        listView=findViewById(R.id.food_list_id);
        myFoodList.add(new FoodClass(R.drawable.frakh,"البروتين","كفتة - كباب - شيش طاووق - فراخ بانيه - بيكاتا بالمشروم - ديك رومي - لحمة باردة - سجق - شاورما\n",""));
        myFoodList.add(new FoodClass(R.drawable.nashwyat,"النشويات","ارز بالخلطة - معكرونة بشاميل - فتة - لازانيا - معكرونة بالخضار - جلاش باللحمة المفرومة - جلاش بالخضار - محاشي \n",""));
        myFoodList.add(new FoodClass(R.drawable.sandwich_1,"السندوتشات","سندوتشات كبدة - سندوتشات روزبيف - سندوتشات بانيه - سندوتشات سجق - سندوتشات رومي مدخن - سندوتشات شاورما - سندوتشات تونا - سندوتشات برجر - سندوتشات كفتة - حواوشي - سندوتشات جبن\n",""));
        myFoodList.add(new FoodClass(R.drawable.salata,"السلطات"," سلطة خضراء - فتوش - تبولة - زبادي بالخيار - بطاطس بالمايونيز - كول سلو- فاصوليا خضراء - سلطة تونا - سلطة جرجير - ثومية - طحينة - بابا غنوج - مخلل\n",""));
        myFoodList.add(new FoodClass(R.drawable.halwyat,"الحلويات"," تورتة الحنة - جاتوه سواريه - حلويات شرقية - كب كيك - كيك بوبس - كوكيز - ام علي\n",""));
        myFoodList.add(new FoodClass(R.drawable.fast_food_2,"اضافات اخرى","سمبوسك - كبيبة - سبرينج رولز - ميني بيتزا - معجنات محشوة - ساليزونات - باتونساليه\n ",""));

        //myFoodList.add(new FoodClass("حلويات","",false));
        loadData();
        MyAdapter adapter=new MyAdapter(getApplicationContext(),myFoodList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodClass tempMyFoodList;
                tempMyFoodList=myFoodList.get(position);
                /// here is the dialog box shoul appear
                AlertDialog.Builder builder = new AlertDialog.Builder(FoodHenaActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_box_gehaz, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                //initilize GUI
                EditText commentEdit= (EditText) dialogView.findViewById(R.id.guest_edit_box_id) ;
                commentEdit.setText(tempMyFoodList.comment);
                Button okButtonDialog= (Button) dialogView.findViewById(R.id.guest_buttonOk_id);
                EditText GehazName = dialogView.findViewById(R.id.editTextTextGehazName) ;
                Button deleteButtonDialog= (Button) dialogView.findViewById(R.id.deleteButton_id);
                ColorDrawable colorDrawable
                        = new ColorDrawable(Color.parseColor("#FB636F"));
                okButtonDialog.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.main_button_design));

                deleteButtonDialog.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.main_button_delete));
                // import data to GUI
                okButtonDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tempMyFoodList.comment= String.valueOf(commentEdit.getText());
                        alertDialog.dismiss();
                        myFoodList.set(position,tempMyFoodList);
                        // save
                        SharedPreferences sharedPreferences=getSharedPreferences(FileName,Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        switch (position){
                            case 0:
                                editor.putString("frakh",String.valueOf(commentEdit.getText()));
                                break;
                            case 1:
                                editor.putString("nashwyat",String.valueOf(commentEdit.getText()));
                                break;
                            case 2:
                                editor.putString("sandwich",String.valueOf(commentEdit.getText()));
                                break;
                            case 3:
                                editor.putString("salata",String.valueOf(commentEdit.getText()));
                                break;
                            case 4:
                                editor.putString("halwyat",String.valueOf(commentEdit.getText()));
                                break;
                            case 5:
                                editor.putString("fastfood",String.valueOf(commentEdit.getText()));
                                break;

                        }
                       // editor.putString("frakh",String.valueOf(commentEdit.getText()));
                        editor.apply();
                       // editor.commit();
                        adapter.notifyDataSetChanged();
                    }
                });




            }
        });

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_fade);
        listView.startAnimation(animation);
    }
private void loadData(){
    SharedPreferences sharedPreferences= getSharedPreferences(FileName,Context.MODE_PRIVATE);
    String defaultValue="";
    String mycomment="";
    for(int i=0 ; i<myFoodList.size();i++){
        FoodClass tempfood=myFoodList.get(i);
        switch (i){
            case 0:
                mycomment=sharedPreferences.getString("frakh",defaultValue);
                break;
            case 1:
                mycomment= sharedPreferences.getString("nashwyat",defaultValue);

                break;
            case 2:
                mycomment= sharedPreferences.getString("sandwich",defaultValue);
                break;
            case 3:
                mycomment= sharedPreferences.getString("salata",defaultValue);
                break;
            case 4:
                mycomment= sharedPreferences.getString("halwyat",defaultValue);
                break;
            case 5:
                mycomment= sharedPreferences.getString("fastfood",defaultValue);
                break;

        }
        tempfood.comment=mycomment;
        myFoodList.set(i,tempfood);
    }

}
    class MyAdapter extends ArrayAdapter{
        ArrayList<FoodClass> myFoodList;
        public MyAdapter(Context context,ArrayList<FoodClass> myFoodList){
            super(context,R.layout.custome_list_food_layout);
            this.myFoodList=myFoodList;
        }
        @Override
        public int getCount() {
            return myFoodList.size();
        }

        @Override
        public Object getItem(int position) {
            return myFoodList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.custome_list_food_layout,parent,false);
            TextView foodText = row.findViewById(R.id.food_catg_id);
            TextView subFood=row.findViewById(R.id.foodnames_id);
            ImageView foodImage=row.findViewById(R.id.food_image_id);
            ImageView noteImage=row.findViewById(R.id.note_id);

            //TextView commentText= row.findViewById(R.id.GehazComment_id);
            //CheckBox checBox=row.findViewById(R.id.price_id);
            //checBox.setVisibility(View.GONE);
            foodText.setText(myFoodList.get(position).FoodCatgName);
            subFood.setText(myFoodList.get(position).SubFoodName);
            foodImage.setImageResource(myFoodList.get(position).Image);
           // checBox.setText(myFoodList.get(position).GehazCheckBox)
noteImage.setImageResource(R.drawable.reminder_yellow_2);



            if (myFoodList.get(position).comment.trim().isEmpty()){
                noteImage.setVisibility(View.GONE);
            }else {
                noteImage.setVisibility(View.VISIBLE);
            }

            return row;
        }
    }
}