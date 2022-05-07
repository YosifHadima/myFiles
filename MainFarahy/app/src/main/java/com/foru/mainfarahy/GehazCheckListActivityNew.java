package com.foru.mainfarahy;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.View;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class GehazCheckListActivityNew extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<GehazList>> listDataChild;
DataBaseHelper myDb;
    private AdView mAdView;
    private AdView adView;
    FloatingActionButton floatingActionButton;


    ExpandableListView expListView_2;

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
    public void loadadd(){
        // Instantiate an AdView object.
// NOTE: The placement ID from the Facebook Monetization Manager identifies your App.
// To get test ads, add IMG_16_9_APP_INSTALL# to your placement id. Remove this when your app is ready to serve real ads.
        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "724060921940186_730003151345963", AdSize.BANNER_HEIGHT_50);

// Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

// Add the ad view to your activity layout
        adContainer.addView(adView);

// Request an ad
        adView.loadAd();
    }
    public void setStatusColor(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_700,this.getTheme()));
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_700));
        }
    }

    public void initilizeViews(){
        floatingActionButton=findViewById(R.id.floatingActionButton_id);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gehaz_check_list_new);
        loadadd();
        setStatusColor();
        initilizeViews();


        ////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////
        /////////////////CUSTOME ADDING /////////////////////////////////
       /////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GehazCheckListActivityNew.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.gehaz_header_dialog_box, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                //add new header
EditText newHeader=dialogView.findViewById(R.id.newHeader_id);

                Button addheader_button=dialogView.findViewById(R.id.addNewHeaderButton_id);
                addheader_button.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.main_button_design));
                addheader_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        listDataHeader.add(String.valueOf(newHeader.getText()));
                        List<GehazList> GehazListCustome = new ArrayList<GehazList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), GehazListCustome); // Header, Child data
                        //  listAdapter_2 = new ExpandableListAdapterVendor(getApplicationContext(),  listDataHeader, listDataChild,VendorActivity.this);
                        listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild,GehazCheckListActivityNew.this);

                        // setting list adapter

                        expListView.setAdapter(listAdapter);

                        listAdapter.notifyDataSetChanged();

                   
                        alertDialog.dismiss();
                    }
                });
            }
        });


        ///////////////////////////////////////////////////////////
        ////////////////////////////////////////////////
        myDb=new DataBaseHelper(this);

      //  ColorDrawable colorDrawable
        //        = new ColorDrawable(Color.parseColor("#e3b04b"));

        // Set BackgroundDrawable
       // actionBar.setBackgroundDrawable(colorDrawable);
        /*
        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId(String.valueOf(R.string.ADS_2));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
       // mAdView.loadAd(adRequest);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                mAdView.loadAd(adRequest);
            }
        });
*/

///////////////////////////////Hide the floating acction button feature////////////////////
        expListView.setOnScrollListener(new AbsListView.OnScrollListener() {
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
       // Toast.makeText(getApplicationContext(),"scrollY : " +scrollState,Toast.LENGTH_SHORT).show();
        if (scrollState == 0) {
            floatingActionButton.show();
        }else {
            floatingActionButton.hide();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
});
///******************************************************************////////////////////////


        // preparing list data
        int count=getCountHeaders();
        if(count == 0){
            prepareListData();

        }else{
            loadData();
        }


        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild,this);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                getCountHeaders();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getApplicationContext(),
                  //      listDataHeader.get(groupPosition) + " Expanded",
                    //    Toast.LENGTH_SHORT).show();
             //   updateData("1","yoooo","Gamal","¨1/1/2022","kobyat","shewak","No comment","false");

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getApplicationContext(),
                  //      listDataHeader.get(groupPosition) + " Collapsed",
                    //    Toast.LENGTH_SHORT).show();
             //   getAllData();
            }
        });


        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                //Toast.makeText(
                  //      getApplicationContext(),
                    //    listDataHeader.get(groupPosition)
                      //          + " : "
                        //        + listDataChild.get(
                          //      listDataHeader.get(groupPosition)).get(
                            //    childPosition).GehazName, Toast.LENGTH_SHORT)
                        //.show();
                LinearLayout alllist=v.findViewById(R.id.allitem_id);
                String mycustomeID = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).GehazID;
//intilize dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(GehazCheckListActivityNew.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_box_gehaz, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
     //initilize GUI
                EditText commentEdit= (EditText) dialogView.findViewById(R.id.guest_edit_box_id) ;
                Button okButtonDialog= (Button) dialogView.findViewById(R.id.guest_buttonOk_id);
                EditText GehazName = dialogView.findViewById(R.id.editTextTextGehazName) ;
                Button deleteButtonDialog= (Button) dialogView.findViewById(R.id.deleteButton_id);
                ColorDrawable colorDrawable
                        = new ColorDrawable(Color.parseColor("#FB636F"));
                okButtonDialog.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.main_button_design));

                deleteButtonDialog.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.main_button_delete));
              // import data to GUI
              //  deleteButtonDialog.setBackground(colorDrawable);
                commentEdit.setText(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).GehazComment);
                okButtonDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String header=listDataHeader.get(groupPosition);
                        GehazList child;
                        String SupTopic;
                        String dbID;
                        child=listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                        String comment= String.valueOf(commentEdit.getText());
                        SupTopic=child.GehazName;

                        if(!mycustomeID.equals("")){
                            // in case data is bilt in you can just add comment to data
                            SupTopic=String.valueOf(GehazName.getText());
child.GehazName=SupTopic;
                        }


                        child.GehazComment=comment;
                        List<GehazList> mylist=new ArrayList<GehazList>();
                        mylist=listDataChild.get(header);
                        mylist.set(childPosition,child);
                        String myID=child.GehazID;
                        // Log.d(TAG, "Yoooosif: child number is  = "+String.valueOf(childPosition)+" with ID = "+String.valueOf(id));
                        listDataChild.put(header,mylist);
                        //getIDNumber(header,Child) and return the ID
                        dbID=getIDNumber(header,SupTopic);

                        //getGehazInfo(ID)
                        // in case built in data memory
                        if(mycustomeID.equals("")){
if (child.GehazCheckBox){
updateData(dbID,"","Gamal","¨1/1/2022",header,SupTopic,comment,"True");

}else {
    updateData(dbID,"","Gamal","¨1/1/2022",header,SupTopic,comment,"False");
}
                            // insertData("ٍSamya","Gamal","¨1/1/2022","myess","shewak","No comment","false");
                         //   updateData(dbID,"yoooo","Gamal","¨1/1/2022",header,SupTopic,comment,"false");
                        }else {
                            // in case custome data
                            if (child.GehazCheckBox){
                                updateData(myID, myID, "", "", header, SupTopic, comment, "True");

                            }else {
                                updateData(myID, myID, "", "", header, SupTopic, comment, "False");
                            }

                        }
                       // loadData();
                      ////  listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild,GehazCheckListActivityNew.this);

                        // setting list adapter

                      //  expListView.setAdapter(listAdapter);

                     //   listAdapter.notifyDataSetChanged();
                        // expListView.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();

                        alertDialog.dismiss();
                    }
                });

              //  Toast.makeText(getApplicationContext(),mycustomeID,Toast.LENGTH_SHORT).show();
                if(mycustomeID.equals("")){
                    // in case data is bilt in you can just add comment to data


                }else {
                    //in case its custome data you can do whaterver you wish
                    GehazName.setVisibility(View.VISIBLE);

                    deleteButtonDialog.setVisibility(View.VISIBLE);
                    deleteButtonDialog.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.main_button_delete));

                    GehazName.setText(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).GehazName);

                }

                ///////**** Delete feature ***** //////

                Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                       // v.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.main_button_delete));

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        String myID = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).GehazID;
                        listDataChild.get(listDataHeader.get(groupPosition)).remove(childPosition);
                        Delete(myID);
                        listAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
deleteButtonDialog.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


      //  String myID = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).GehazID;
       // v.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.main_button_delete));

        v.startAnimation(animation);
      //  Toast.makeText(getApplicationContext(),"تم الحذف",Toast.LENGTH_SHORT).show();

       // listDataChild.get(listDataHeader.get(groupPosition)).remove(childPosition);


      //  Delete(myID);

      //  listAdapter.notifyDataSetChanged();


        // setting list adapter

    //    expListView.setAdapter(listAdapter);

       // listAdapter.notifyDataSetChanged();
  //      listAdapter.notifyDataSetChanged();
        alertDialog.dismiss();
    }
});

               // int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                //ImageView noteButton = (ImageView) v.findViewById(R.id.note_id);


                return false;
            }
        });






    }
    private void Delete(String ID){
        Boolean result= myDb.deleteData(ID);
        if (result){
            // Log.d(TAG, "onClick possss delete: "+String.valueOf(result)+" for ID "+ID);
            //  Toast.makeText(this,"Data updated susccfully",Toast.LENGTH_SHORT).show();
        }else {
            //  Log.d(TAG, "onClick possss delete: "+String.valueOf(result)+" for ID "+ID);
            //  Toast.makeText(this,"Data  updated Faille",Toast.LENGTH_SHORT).show();
        }

    }
    private void insertData(String myID,String MaleName,String MargeDate,String MainTopic,String SupTopic,String CommentTopic,String checkbox){

        Boolean result = myDb.insertData(myID,MaleName,MargeDate,MainTopic,SupTopic,CommentTopic,checkbox);
  if (result== true){
    //  Toast.makeText(this,"Data inserted susccfully",Toast.LENGTH_SHORT).show();
  }else {
    //  Toast.makeText(this,"Data  insertion Faille",Toast.LENGTH_SHORT).show();
  }

    }

    private void getAllData(){
        Cursor res = myDb.getALData();
        StringBuffer stringBuffer=new StringBuffer();

        if(res !=null && res.getCount()>0){
            while (res.moveToNext()){
                stringBuffer.append(res.getString(0));
                stringBuffer.append(res.getString(1));
                stringBuffer.append(res.getString(2));
                stringBuffer.append(res.getString(3));
                stringBuffer.append(res.getString(4));
                stringBuffer.append(res.getString(5));
                stringBuffer.append(res.getString(6));
            }

        }
        Toast.makeText(this,stringBuffer.toString(),Toast.LENGTH_SHORT).show();
    }

    private void updateData(String ID,String myID,String MaleName,String MargeDate,String MainTopic,String SupTopic,String CommentTopic,String checkbox){
        Boolean result = myDb.updateData(ID,myID,MaleName,MargeDate,MainTopic,SupTopic,CommentTopic,checkbox);
        if (result== true){
          //  Toast.makeText(this,"Data updated susccfully",Toast.LENGTH_SHORT).show();
        }else {
          //  Toast.makeText(this,"Data  updated Faille",Toast.LENGTH_SHORT).show();
        }

    }
    /*
     * Preparing the list data
     */
   private List getallHeaders(){
       List<String> Headers = new ArrayList<>();
       Cursor res = myDb.getALData();
       StringBuffer stringBuffer=new StringBuffer();

       if(res !=null && res.getCount()>0){
           while (res.moveToNext()){
               stringBuffer.append(res.getString(0));
               stringBuffer.append(res.getString(1));
               stringBuffer.append(res.getString(2));
               stringBuffer.append(res.getString(3));
               stringBuffer.append(res.getString(4));
               if (Headers.isEmpty()){
                   Headers.add(res.getString(4));
                   //Log.d(TAG, "Yoooosif: empty and added = "+res.getString(3));
               }else {
                   if (!(Headers.contains(res.getString(4)))) {
                       Headers.add(res.getString(4));
                      // Log.d(TAG, "Yoooosif: not same and added = "+res.getString(3));

                   }

               }
               stringBuffer.append(res.getString(5));
               stringBuffer.append(res.getString(6));
           }

       }
       return Headers;
    }
    private int getCountHeaders(){
       //Toast.makeText(this,String.valueOf(getallHeaders().size()),Toast.LENGTH_SHORT).show();
        return getallHeaders().size();
    }
    private List getDataOfChildheader(String MainTopic){
        List<String> Headers = new ArrayList<>();
        Cursor res = myDb.getALData();
        StringBuffer stringBuffer=new StringBuffer();
        String Topic;
        String Sup;
        if(res !=null && res.getCount()>0){
            while (res.moveToNext()){
                Topic=res.getString(4);
                Sup=res.getString(5);
                Log.d(TAG, "Yoooosif: MainTopic is  = "+MainTopic+ "search for "+Topic +" with "+Sup);
                if (MainTopic.equals(Topic)){
                    Headers.add(Sup);
                }


            }
    }
        return Headers;

    }
    private List getDataOfChildComment(String MainTopic){
        List<String> Headers = new ArrayList<>();
        Cursor res = myDb.getALData();
        StringBuffer stringBuffer=new StringBuffer();
        String Topic;
        String Comment;
        if(res !=null && res.getCount()>0){
            while (res.moveToNext()){
                Topic=res.getString(4);
                Comment=res.getString(6);
                //Log.d(TAG, "Yoooosif: MainTopic is  = "+MainTopic+ "search for "+Topic +" with "+Sup);
                if (MainTopic.equals(Topic)){
                    Headers.add(Comment);
                }


            }
        }
        return Headers;

    }
    private List getDataOfChildCheckBox(String MainTopic){
        List<String> Headers = new ArrayList<>();
        Cursor res = myDb.getALData();
        StringBuffer stringBuffer=new StringBuffer();
        String Topic;
        String CheckBox;
        if(res !=null && res.getCount()>0){
            while (res.moveToNext()){
                Topic=res.getString(4);
                CheckBox=res.getString(7);
                //Log.d(TAG, "Yoooosif: MainTopic is  = "+MainTopic+ "search for "+Topic +" with "+Sup);
                if (MainTopic.equals(Topic)){
                    Headers.add(CheckBox);
                }


            }
        }
        return Headers;

    }
    private List getDataOfChildMYID(String MainTopic){
        List<String> Headers = new ArrayList<>();
        Cursor res = myDb.getALData();
        StringBuffer stringBuffer=new StringBuffer();
        String Topic;
        String myID;
        if(res !=null && res.getCount()>0){
            while (res.moveToNext()){
                Topic=res.getString(4);
                myID=res.getString(1);
                //Log.d(TAG, "Yoooosif: MainTopic is  = "+MainTopic+ "search for "+Topic +" with "+Sup);
                if (MainTopic.equals(Topic)){
                    Headers.add(myID);
                }


            }
        }
        return Headers;

    }
    private String getIDNumber(String MainTopic,String SupTopic){
        String ID="1" ;
        Cursor res = myDb.getALData();
        StringBuffer stringBuffer=new StringBuffer();
        String Topic;
        String Sup;
        String Comment;
        if(res !=null && res.getCount()>0){
            while (res.moveToNext()){
                Topic=res.getString(4);
                Sup=res.getString(5);
                Comment=res.getString(6);

                //Log.d(TAG, "Yoooosif: MainTopic is  = "+MainTopic+ "search for "+Topic +" with "+Sup);
                if (MainTopic.equals(Topic) && SupTopic.equals(Sup) ){
                   ID= res.getString(0);
                   return ID;
                }


            }
        }
        return ID;
    }
    private void loadData(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<GehazList>>();
        int numberOfMain = getCountHeaders();
        //Get all topics
        List<String> Headers = new ArrayList<>();
        Headers=getallHeaders();

        int index=0;
        for (String Topic : Headers) {
            listDataHeader.add(Topic);
            //Log.d(TAG, "Yoooosif: not same and added = "+temp);
            //get all children
            List<String> Children = new ArrayList<>();
            Children=getDataOfChildheader(Topic);

            List<String> Comments=new ArrayList<>();
            Comments=getDataOfChildComment(Topic);

            List<String> CheckBox=new ArrayList<>();
            CheckBox=getDataOfChildCheckBox(Topic);

            List<String> myID = new ArrayList<>();
            myID=getDataOfChildMYID(Topic);

            Log.d(TAG, "Yoooosif: children are  = "+Children );
            List<GehazList> TopicList = new ArrayList<GehazList>();

            // for (String Suptopic : Children) {
            //   TopicList.add(new GehazList(Suptopic));
            //     Log.d(TAG, "Yoooosif: Topic is  = "+Topic +" with "+Suptopic);
            // }
            for (int i = 0; i < Children.size(); i++) {
                // System.out.println(Children.get(i));
                boolean check;
                if (CheckBox.get(i).equals("True")){
                    check=true;
                }else {check=false;}
                TopicList.add(new GehazList(Children.get(i),Comments.get(i),check,myID.get(i)));
            }
            listDataChild.put(listDataHeader.get(index), TopicList); // Header, Child data
            index=index+1;
        }

    }

    private void prepareListData() {

       // listDataHeader = new ArrayList<String>();
        //listDataChild = new HashMap<String, List<GehazList>>();
        String Matbakh="مطبخ";
        String hamam="مستلزمات حمام";
        String mafaresh="المفروشات";
        String honeymoon="شهر العسل";
        String aghza="اجهزة كهربائية";

        insertData("","","",Matbakh,"طقم صيني","","false");
        insertData("","","",Matbakh,"طقم ملاعق و شوك","","false");
        insertData("","","",Matbakh,"اطباق بلاستيك","","false");
        insertData("","","",Matbakh,"طقم سكاكين","","false");
        insertData("","","",Matbakh,"طقم معالق توزيع","","false");
        insertData("","","",Matbakh,"طقم توابل","","false");
        insertData("","","",Matbakh,"علب ثلاجة","","false");
        insertData("","","",Matbakh,"طقم بايركس","","false");
        insertData("","","",Matbakh,"طقم حلل","","false");
        insertData("","","",Matbakh,"طقم صواني فرن","","false");
        insertData("","","",Matbakh,"طواجن فرن","","false");
        insertData("","","",Matbakh,"طقم جيلي","","false");
        insertData("","","",Matbakh,"طقم شاي/قهوة","","false");
        insertData("","","",Matbakh,"طاسات للقلي","","false");
        insertData("","","",Matbakh,"ملاعق خشب/سيليكون","","false");
        insertData("","","",Matbakh,"مبشرة","","false");
        insertData("","","",Matbakh,"مقورة","","false");
        insertData("","","",Matbakh,"لبانة","","false");
        insertData("","","",Matbakh,"كبة","","false");
        insertData("","","",Matbakh,"طقم صواني تقديم","","false");
        insertData("","","",Matbakh,"مصفاة ستانلس","","false");
        insertData("","","",Matbakh,"مصفاة بلاستيك","","false");
        insertData("","","",Matbakh,"زجاجة مياه و اكواب","","false");
        insertData("","","",Matbakh,"براد شاي","","false");
        insertData("","","",Matbakh,"طبق كبير للسلطة","","false");
        insertData("","","",Matbakh,"سلة مهملات","","false");
        insertData("","","",Matbakh,"مقص مطبخ","","false");
        insertData("","","",Matbakh,"شياطة الارز","","false");
        insertData("","","",Matbakh,"مساكات للحاجات السخنة","","false");
        insertData("","","",Matbakh,"حامل مناديل مطبخ","","false");
        insertData("","","",Matbakh,"مريلة مطبخ","","false");
        insertData("","","",Matbakh,"مصفاة صغيرة للسوائل","","false");
        insertData("","","",Matbakh,"لوح تقطيع","","false");
        insertData("","","",Matbakh,"صفاية ملاعق للحوض","","false");
        insertData("","","",Matbakh,"كيتشن ماشين","","false");
        insertData("","","",Matbakh,"مفرمة ثوم","","false");
        insertData("","","",Matbakh,"عصارة ليمون","","false");
        insertData("","","",Matbakh,"عصارة برتقال","","false");
        insertData("","","",Matbakh,"ميزان مطبخ","","false");
        insertData("","","",Matbakh,"فوط مطبخ","","false");
        insertData("","","",Matbakh,"سلاطين صغيرة","","false");
        insertData("","","",Matbakh,"كنكة قهوة","","false");
        insertData("","","",Matbakh,"شفشق عصير","","false");
        insertData("","","",Matbakh,"ولاعة بوتجاز","","false");
        insertData("","","",Matbakh,"مدق للبفتيك","","false");
        insertData("","","",Matbakh,"نشابة عجين","","false");
        insertData("","","",Matbakh,"اطباق ميكروويف","","false");
        insertData("","","",Matbakh,"قطاعة بيتزا","","false");
        insertData("","","",Matbakh,"ملعقة ايس كريم","","false");
        insertData("","","",Matbakh,"قوالب كيك","","false");
        insertData("","","",Matbakh,"مساكة جاتوه","","false");
        insertData("","","",Matbakh,"شيالة بسكويت","","false");
        insertData("","","",Matbakh,"صواني بيتزا","","false");
        insertData("","","",Matbakh,"صينية فطار","","false");
        insertData("","","",Matbakh,"طقم خشاف","","false");
        insertData("","","",Matbakh,"قوالب ثلج","","false");
        insertData("","","",Matbakh,"فتاحة علب","","false");
        insertData("","","",Matbakh,"اقماع","","false");
        insertData("","","",Matbakh,"مضرب بيض يدوي","","false");
        insertData("","","",Matbakh,"فرشاة دهن صواني","","false");
        insertData("","","",Matbakh,"هراسة بطاطس","","false");
        insertData("","","",Matbakh,"بونبونيرة","","false");
        insertData("","","",Matbakh,"كاسات","","false");
        insertData("","","",Matbakh,"طفاية","","false");
        insertData("","","",Matbakh,"منخل دقيق","","false");
        insertData("","","",Matbakh,"برطمانات تخزين","","false");
        insertData("","","",Matbakh,"قشارة بطاطس","","false");
        insertData("","","",Matbakh,"قطاعة تفاح","","false");
        insertData("","","",Matbakh,"مجات نسكافيه","","false");
        insertData("","","",Matbakh,"جوانتي حرارة","","false");
        insertData("","","",Matbakh,"طاسة جريل","","false");
        insertData("","","",Matbakh,"خلاط","","false");
        insertData("","","",Matbakh,"طبق تسالي","","false");
        insertData("","","",Matbakh,"طقم عشاء للاستخدام اليومي","","false");
        insertData("","","",Matbakh,"قدرة فول","","false");
        insertData("","","",Matbakh,"صينية كب كيك","","false");
        insertData("","","",Matbakh,"طقم سيليكون للفرن","","false");
        insertData("","","",Matbakh,"مخرطة ملوخية","","false");
        insertData("","","",Matbakh,"اسياخ شيش طاووق","","false");
        insertData("","","",Matbakh,"ولاعة","","false");
        insertData("","","",Matbakh,"هون","","false");
        insertData("","","",Matbakh,"معالق معيارية","","false");
        insertData("","","",Matbakh,"سباتيولا","","false");
        insertData("","","",Matbakh,"بخاخة زيت","","false");
        insertData("","","",Matbakh,"صفاية معالق","","false");
        insertData("","","",Matbakh,"حامل مجات","","false");


        insertData("","","",hamam,"طقم دواسات","","false");
        insertData("","","",hamam,"مساحة زجاج","","false");
        insertData("","","",hamam,"سلة مهملات صفيرة","","false");
        insertData("","","",hamam,"سبت غسيل","","false");
        insertData("","","",hamam,"طبق بلاستيك للغسيل","","false");
        insertData("","","",hamam,"مشابك","","false");
        insertData("","","",hamam,"منشر غسيل داخلي","","false");
        insertData("","","",hamam,"مشمع غسيل","","false");
        insertData("","","",hamam,"شماعات للدولاب","","false");
        insertData("","","",hamam,"مقشة و جاروف","","false");
        insertData("","","",hamam,"مساحة ارضيات","","false");
        insertData("","","",hamam,"جردل مسح","","false");
        insertData("","","",hamam,"زعافة","","false");
        insertData("","","",hamam,"فرشاة اسنان","","false");
        insertData("","","",hamam,"سلاكة بلاعات","","false");
        insertData("","","",hamam,"فرشاة الوبر للملابس","","false");
        insertData("","","",hamam,"فرشاة تواليت","","false");
        insertData("","","",hamam,"ليفة تنضيف الحوض","","false");
        insertData("","","",hamam,"شموع","","false");
        insertData("","","",hamam,"ورد مجفف","","false");
        insertData("","","",hamam,"معطر حمام","","false");
        insertData("","","",hamam,"منظف ارضيات","","false");
        insertData("","","",hamam,"مبخرة","","false");
        insertData("","","",hamam,"ملمع زجاج","","false");
        insertData("","","",hamam,"ملمع خشب","","false");
        insertData("","","",hamam,"طقم اكسسوارات الحمام","","false");
        insertData("","","",hamam,"ستارة بانيو","","false");
        insertData("","","",hamam,"مطهر للاسطح","","false");

        insertData("","","",mafaresh,"المرتبة","","false");
        insertData("","","",mafaresh,"المخدات","","false");
        insertData("","","",mafaresh,"بطاطين","","false");
        insertData("","","",mafaresh,"لحاف","","false");
        insertData("","","",mafaresh,"اغطية لحاف","","false");
        insertData("","","",mafaresh,"كوفرتة","","false");
        insertData("","","",mafaresh,"مفرش سرير دفاية","","false");
        insertData("","","",mafaresh,"غطاء مرتبة","","false");
        insertData("","","",mafaresh,"طقم ملايات","","false");
        insertData("","","",mafaresh,"بشاكير قطن","","false");
        insertData("","","",mafaresh,"طقم فوط قطن","","false");
        insertData("","","",mafaresh,"برنس حمام","","false");
        insertData("","","",mafaresh,"شبشب حمام","","false");
        insertData("","","",mafaresh,"مفرش سفرة","","false");
        insertData("","","",mafaresh,"مفارش نيش","","false");
        insertData("","","",mafaresh,"مفارش لترابيزة الصالون","","false");
        insertData("","","",mafaresh,"مفرش سرير عروسة","","false");
        insertData("","","",mafaresh,"ملايات سرير اطفال","","false");
        insertData("","","",mafaresh,"كوفرتة اطفال","","false");
        insertData("","","",mafaresh,"بطانية اطفال","","false");
        insertData("","","",mafaresh,"مفارش نيش","","false");
        insertData("","","",mafaresh,"مفارش دواليب","","false");

        insertData("","","",honeymoon,"فساتين","","false");
        insertData("","","",honeymoon,"بلوزات","","false");
        insertData("","","",honeymoon,"بنطلونات","","false");
        insertData("","","",honeymoon,"اكسسوارات","","false");
        insertData("","","",honeymoon,"احذية","","false");
        insertData("","","",honeymoon,"بيجامات","","false");
        insertData("","","",honeymoon,"ملابس داخلية","","false");
        insertData("","","",honeymoon,"لانجيري","","false");
        insertData("","","",honeymoon,"شنط","","false");
        insertData("","","",honeymoon,"احزمة","","false");
        insertData("","","",honeymoon,"قبعة","","false");
        insertData("","","",honeymoon,"فرشاة شعر","","false");
        insertData("","","",honeymoon,"مكواة شعر","","false");
        insertData("","","",honeymoon,"ميكياج","","false");
        insertData("","","",honeymoon,"طلاء اظافر","","false");
        insertData("","","",honeymoon,"منظف وجه","","false");
        insertData("","","",honeymoon,"مرطب","","false");
        insertData("","","",honeymoon,"واقي الشمس","","false");
        insertData("","","",honeymoon,"مرهم تسلخات","","false");
        insertData("","","",honeymoon,"ماكينة ازالة الشعر","","false");
        insertData("","","",honeymoon,"مزيل العرق","","false");
        insertData("","","",honeymoon,"فرشاة اسنان","","false");
        insertData("","","",honeymoon,"مايوه سباحة","","false");
        insertData("","","",honeymoon,"نظارة شمس","","false");
        insertData("","","",honeymoon,"معقم يدين","","false");
        insertData("","","",honeymoon,"مسكن للالام","","false");
        insertData("","","",honeymoon,"اسعافات اولية","","false");
        insertData("","","",honeymoon,"نسخة من عقد الزواج","","false");
        insertData("","","",honeymoon,"بطاقات الهوية الشخصية","","false");
        insertData("","","",honeymoon,"جوازات السفر","","false");
        insertData("","","",honeymoon,"تذاكر الطيران","","false");
        insertData("","","",honeymoon,"كاميرا","","false");
        insertData("","","",honeymoon,"شواحن","","false");
        insertData("","","",honeymoon,"باور بانك","","false");

        insertData("","","",aghza,"ثلاجة","","false");
        insertData("","","",aghza,"بوتجاز","","false");
        insertData("","","",aghza,"غسالة ملابس","","false");
        insertData("","","",aghza,"سخان(غاز او كهرباء)","","false");
        insertData("","","",aghza,"مروحة","","false");
        insertData("","","",aghza,"مكيفات","","false");
        insertData("","","",aghza,"شاشة تلفيزيون","","false");
        insertData("","","",aghza,"شفاط مطبخ","","false");
        insertData("","","",aghza,"فرن كهرباء","","false");
        insertData("","","",aghza,"مكنسة كهربائية","","false");
        insertData("","","",aghza,"مايكروويف","","false");
        insertData("","","",aghza,"غسالة اطباق","","false");
        insertData("","","",aghza,"ديب فريزر","","false");
        insertData("","","",aghza,"مجفف ملابس","","false");
        insertData("","","",aghza,"مبرد مياه","","false");
        insertData("","","",aghza,"غسالة اطفال","","false");
        insertData("","","",aghza,"مكواه بخار","","false");
        insertData("","","",aghza,"كشاف نور","","false");
        insertData("","","",aghza,"صاعق ناموس","","false");
        insertData("","","",aghza,"شفاط حمام","","false");
        insertData("","","",aghza,"ميكروويف","","false");
        insertData("","","",aghza,"خلاط","","false");


        loadData();
        /*
        //number of Main topics
        int numberOfMain = getCountHeaders();
        //Get all topics
        List<String> Headers = new ArrayList<>();
        Headers=getallHeaders();

        int index=0;
        for (String Topic : Headers) {
            listDataHeader.add(Topic);
            //Log.d(TAG, "Yoooosif: not same and added = "+temp);
            //get all children
           List<String> Children = new ArrayList<>();
            Children=getDataOfChildheader(Topic);

            List<String> Comments=new ArrayList<>();
            Comments=getDataOfChildComment(Topic);
            Log.d(TAG, "Yoooosif: children are  = "+Children );
            List<GehazList> TopicList = new ArrayList<GehazList>();
           // for (String Suptopic : Children) {
           //   TopicList.add(new GehazList(Suptopic));
           //     Log.d(TAG, "Yoooosif: Topic is  = "+Topic +" with "+Suptopic);
           // }
            for (int i = 0; i < Children.size(); i++) {
               // System.out.println(Children.get(i));
                TopicList.add(new GehazList(Children.get(i),Comments.get(i)));
            }
            listDataChild.put(listDataHeader.get(index), TopicList); // Header, Child data
            index=index+1;
        }
*/
        //getallHeaders()
        //getCountHeaders()
        //getDataOfChildheader(x)
        //getCountOfChilderenOfHeader(x)
        //associate eache header with the children
/*
        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<GehazList> top250 = new ArrayList<GehazList>();
        top250.add(new GehazList("ليلة سقوت بغداد"));
        top250.add(new GehazList("العار","العار2",true));
        top250.add(new GehazList("محترم الا ربع","محترم الا ربع",false));
        top250.add(new GehazList("ليلة سقوت بغداد","ليلة سقوت بغداد",false));
        top250.add(new GehazList("ليلة سقوت بغداد","ليلة سقوت بغداد",false));

        //top250.add("ليلة سقوت بغداد","ليلة سقوت بغداد",false);
       // top250.add("العار");
        //top250.add("محترم الا ربع");
        //top250.add("الهندي");
        //top250.add("بوشكش");
        //top250.add("اللمبي");
        //top250.add("لا تراجع و لا استسلام");

        List<GehazList> nowShowing = new ArrayList<GehazList>();
        nowShowing.add(new GehazList("The Conjuring","The Conjuring",false));
        nowShowing.add(new GehazList("Despicable Me 2","The Conjuring",false));
        nowShowing.add(new GehazList("Turbo","The Conjuring",false));
        nowShowing.add(new GehazList("The Conjuring","The Conjuring",false));

        nowShowing.add(new GehazList("The Wolverine","The Conjuring",false));

        //nowShowing.add("Despicable Me 2");
        //nowShowing.add("Turbo");
        //nowShowing.add("Grown Ups 2");
        //nowShowing.add("Red 2");
        //nowShowing.add("The Wolverine");

        List<GehazList> comingSoon = new ArrayList<GehazList>();
        comingSoon.add(new GehazList("2 Guns","2 Guns",false));
        comingSoon.add(new GehazList("The Smurfs 2","2 Guns",false));
        comingSoon.add(new GehazList("The Spectacular Now","2 Guns",false));
        comingSoon.add(new GehazList("Europa Report","2 Guns",false));

       // comingSoon.add("The Smurfs 2");
        //comingSoon.add("The Spectacular Now");
        //comingSoon.add("The Canyons");
        //comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);

 */
    }
}