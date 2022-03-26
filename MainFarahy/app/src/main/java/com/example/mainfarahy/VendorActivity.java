package com.example.mainfarahy;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class VendorActivity extends AppCompatActivity {
    ExpandableListAdapterVendor listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader=new ArrayList<String>();
    HashMap<String, List<VendorList>> listDataChild= new HashMap<String, List<VendorList>>();
    DataBaseHelperVendor myDb;
    FloatingActionButton floatingActionButton;
boolean weddingPlanner_clicked,weedingVen_clicked ,videoGrapher_clicked, athlie_Button_clicked,veil_clicked,makeupArtist_clicked,photographer_clicked,hairStylest_clicked =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        myDb=new DataBaseHelperVendor(this);
        // get the listview
        expListView =  findViewById(R.id.lvExp);


        // preparing list data
        //int count=getCountHeaders();
      //  listDataHeader = new ArrayList<String>();
        //listDataChild = new HashMap<String, List<VendorList>>();

        // prepareListData();
        loadData();
        //Log.d(TAG, "onCreate yyyy: "+listDataChild.size());
        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild,this);
     // listAdapter=new ExpandableListAdapterVendor(this,listDataHeader,listDataChild, this);
        // setting list adapter

        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                 //"Group Clicked " + listDataHeader.get(groupPosition),
                 //Toast.LENGTH_SHORT).show();
                // Adding child data

                //List<GehazList> selectedHeader = new ArrayList<GehazList>();
               // selectedHeader.add(new GehazList("ليلة سقوت بغداد"));
               // listDataHeader.add(listDataHeader.get(groupPosition));
                //listDataChild.put(listDataHeader.get(groupPosition), selectedHeader);
                getCountHeaders();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getApplicationContext(),
                //      listDataHeader.get(groupPosition) + " Expanded",
                //    Toast.LENGTH_SHORT).show();
                //   updateData("1","yoooo","Gamal","¨1/1/2022","kobyat","shewak","No comment","false");

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getApplicationContext(),
                //      listDataHeader.get(groupPosition) + " Collapsed",
                //    Toast.LENGTH_SHORT).show();
                //   getAllData();
            }
        });


        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

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
                AlertDialog.Builder builder = new AlertDialog.Builder(VendorActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.update_vendor_dialogbox, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                //link to comment
                EditText commentEdit= (EditText) dialogView.findViewById(R.id.guest_edit_box_id) ;
                //link to name
                EditText nameVendor= (EditText) dialogView.findViewById(R.id.name_edit_box_id) ;
                //link to phone number
                EditText phoneVendor= (EditText) dialogView.findViewById(R.id.addphone_id) ;
                //link to price
                EditText priceVendor=  dialogView.findViewById(R.id.editTextNumber) ;


                commentEdit.setText(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).VendorComment);
                nameVendor.setText(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).VendorName);
                phoneVendor.setText(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).VendorPhonenumber);
                String price=listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).VendorPrice;
                if (TextUtils.isDigitsOnly(price)){
                    priceVendor.setText(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).VendorPrice);
                }
String ID =listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).myID;
                Log.d(TAG, "Yoooosif: child number is dele not = "+ID);
                ////////////////////////////////////////////////////////////////////////////////
                ///////UPDATE BUTTON////////////////////////////////////////////////////////////
                Button okButtonDialog= (Button) dialogView.findViewById(R.id.guest_buttonOk_id);
                okButtonDialog.setBackgroundColor(Color.parseColor("#e3b04b"));
                okButtonDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String header=listDataHeader.get(groupPosition);
                        VendorList child;
                        child=listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                        String comment= String.valueOf(commentEdit.getText());
                        String mynameVendor=String.valueOf(nameVendor.getText());
                        String myphoneVendor=String.valueOf(phoneVendor.getText());
                        String mypriceVendor=String.valueOf(priceVendor.getText());
                        if (TextUtils.isDigitsOnly(mypriceVendor)){

                        }else {
                            mypriceVendor="اضافة سعر";
                        }
                        if (myphoneVendor.isEmpty()){
                            myphoneVendor="";
                        }

                        //SupTopic=child.VendorName;
                        child.VendorComment=comment;
                        child.VendorName=mynameVendor;
                        child.VendorPrice=mypriceVendor;
                        child.VendorPhonenumber=myphoneVendor;
                        //Log.d(TAG, "Yoooosif: price   = "+price);
                        if (TextUtils.isDigitsOnly(price)){
                            priceVendor.setText(price);
                        }
                        priceVendor.setText(price);
                        List<VendorList> mylist=new ArrayList<VendorList>();
                        mylist=listDataChild.get(header);
                        mylist.set(childPosition,child);
                         //Log.d(TAG, "Yoooosif: child number is  = "+child.myID);
                        listDataChild.put(header,mylist);

                        //getIDNumber(header,Child) and return the ID
                        //dbID=getIDNumber(header,child);
                        updateData(child.myID,header,mynameVendor,comment,mypriceVendor,myphoneVendor,child.myID);
                        //getGehazInfo(ID)

                        //////////////////////////////////////////////////////////////////////////////////////
                        //////////UPDATE TO SQL DATABASE/////////////////////////////////////////////////////
                       //updateData(getID(),header,mynameVendor,comment,mypriceVendor,myphoneVendor,getID());
                        ///////////////////////////////////////////////////////////////////////////////////
                        listAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });


                ////////////////////////////////////////////////////////////////////////////////
                ///////DELETE BUTTON////////////////////////////////////////////////////////////
                Button cancelButtonDialog= (Button) dialogView.findViewById(R.id.guest_buttoncancel_id);
                cancelButtonDialog.setBackgroundColor(Color.parseColor("#e3b04b"));
                cancelButtonDialog.setText("حذف");
                cancelButtonDialog.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        //delete subVendor
        listDataChild.get(listDataHeader.get(groupPosition)).remove(childPosition);
        //update the adabter
        Log.d(TAG, "Yoooosif: child number is dele = "+ID);
        Delete(ID);
        listAdapter.notifyDataSetChanged();
       // close the dialog;
        alertDialog.dismiss();
    }
});
/////////////////////////////////////////////////////////////////////////////////////////////////
                return false;
            }
        });







        floatingActionButton=findViewById(R.id.floatingActionButton_id);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set new header


                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(VendorActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.vendor_box_dialog, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button weedingVen_Button =dialogView.findViewById(R.id.weedingVen_id);
                Button weddingPlanner_Button=dialogView.findViewById(R.id.weddingPlanner_id);
                Button athlie_Button=dialogView.findViewById(R.id.athlie_id);
                Button photographer_Button=dialogView.findViewById(R.id.photographer_id);
                Button hairStylest_Button=dialogView.findViewById(R.id.hairStylest_id);
                Button makeupArtist_Button=dialogView.findViewById(R.id.makeupArtist_id);
                Button veil_Button=dialogView.findViewById(R.id.veil_id);
                Button videoGrapher_Button=dialogView.findViewById(R.id.videoGrapher_id);
                List<String> Headers = new ArrayList<>();
                Headers=getallHeaders();


                for (String Topic : Headers) {
                    if (Topic.equals("Wedding Venue")){
                        weedingVen_Button.setVisibility(View.GONE);
                    }

                    if (Topic.equals("Wedding Planner")){
                        weddingPlanner_Button.setVisibility(View.GONE);
                    }
                    if (Topic.equals("Athelier")){
                        athlie_Button.setVisibility(View.GONE);
                    }
                    if (Topic.equals("Photographer")){
                        photographer_Button.setVisibility(View.GONE);
                    }
                    if (Topic.equals("Hair Stylist")){
                        hairStylest_Button.setVisibility(View.GONE);
                    }
                    if (Topic.equals("Makeup Artist")){
                        makeupArtist_Button.setVisibility(View.GONE);
                    }
                    if (Topic.equals("Veil Designer")){
                        veil_Button.setVisibility(View.GONE);
                    }
                    if (Topic.equals("Video Grapher")){
                        videoGrapher_Button.setVisibility(View.GONE);
                    }

                }
                if (weedingVen_clicked){
                    weedingVen_Button.setVisibility(View.GONE);
                }
                if (weddingPlanner_clicked){
                    weddingPlanner_Button.setVisibility(View.GONE);
                }
                if (athlie_Button_clicked){
                    athlie_Button.setVisibility(View.GONE);
                }
                if (photographer_clicked){
                    photographer_Button.setVisibility(View.GONE);
                }
                if (hairStylest_clicked){
                    hairStylest_Button.setVisibility(View.GONE);
                }
                if (makeupArtist_clicked){
                    makeupArtist_Button.setVisibility(View.GONE);
                }
                if (veil_clicked){
                    veil_Button.setVisibility(View.GONE);
                }
                if (videoGrapher_clicked){
                    videoGrapher_Button.setVisibility(View.GONE);
                }

                weedingVen_Button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        listDataHeader.add("Wedding Venue");
        List<VendorList> weedingVen = new ArrayList<VendorList>();
        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(),  listDataHeader, listDataChild,VendorActivity.this);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        weedingVen_clicked=true;
        listAdapter.notifyDataSetChanged();
        alertDialog.dismiss();
    }
});


                weddingPlanner_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDataHeader.add("Wedding Planner");
                        List<VendorList> weedingVen = new ArrayList<VendorList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild,VendorActivity.this);
                        // setting list adapter
                        expListView.setAdapter(listAdapter);
                        weddingPlanner_clicked=true;
                        listAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });


                athlie_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDataHeader.add("Athelier");
                        List<VendorList> weedingVen = new ArrayList<VendorList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild,VendorActivity.this);
                        // setting list adapter
                        expListView.setAdapter(listAdapter);
                        athlie_Button_clicked=true;
                        alertDialog.dismiss();
                    }
                });
                photographer_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDataHeader.add("Photographer");
                        List<VendorList> weedingVen = new ArrayList<VendorList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild,VendorActivity.this);
                        // setting list adapter
                        expListView.setAdapter(listAdapter);
                        photographer_clicked=true;
                        alertDialog.dismiss();
                    }
                });
                hairStylest_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDataHeader.add("Hair Stylist");
                        List<VendorList> weedingVen = new ArrayList<VendorList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild,VendorActivity.this);
                        // setting list adapter
                        expListView.setAdapter(listAdapter);
                        hairStylest_clicked=true;
                        alertDialog.dismiss();
                    }
                });
                makeupArtist_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDataHeader.add("Makeup Artist");
                        List<VendorList> weedingVen = new ArrayList<VendorList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild,VendorActivity.this);
                        // setting list adapter
                        expListView.setAdapter(listAdapter);
                        makeupArtist_clicked=true;
                        alertDialog.dismiss();
                    }
                });
                veil_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDataHeader.add("Veil Designer");
                        List<VendorList> weedingVen = new ArrayList<VendorList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild,VendorActivity.this);
                        // setting list adapter
                        expListView.setAdapter(listAdapter);
                        veil_clicked=true;
                        alertDialog.dismiss();
                    }
                });
                videoGrapher_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDataHeader.add("Video Grapher");
                        List<VendorList> weedingVen = new ArrayList<VendorList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1) ,weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild,VendorActivity.this);
                        // setting list adapter
                        expListView.setAdapter(listAdapter);
                        videoGrapher_clicked=true;
                        alertDialog.dismiss();
                    }
                });




                Button buttonCancel =dialogView.findViewById(R.id.guest_buttoncancel_id);
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
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
    private void insertData(String MAIN_TOPIC,String NAME_VENDOR,String VENDOR_COMMENT,String VENDOR_PRICE,String VENDOR_PHONE,String MY_ID){

        Boolean result = myDb.insertData(MAIN_TOPIC,NAME_VENDOR,VENDOR_COMMENT,VENDOR_PRICE,VENDOR_PHONE,MY_ID);
        if (result== true){
             // Toast.makeText(this,"Data inserted susccfully",Toast.LENGTH_SHORT).show();
        }else {
              //Toast.makeText(this,"Data  insertion Faille",Toast.LENGTH_SHORT).show();
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

    private void updateData(String ID,String MAIN_TOPIC,String NAME_VENDOR,String VENDOR_COMMENT,String VENDOR_PRICE,String VENDOR_PHONE,String MY_ID){
        Boolean result = myDb.updateData(ID,MAIN_TOPIC,NAME_VENDOR,VENDOR_COMMENT,VENDOR_PRICE,VENDOR_PHONE,MY_ID);
        if (result== true){
            //  Toast.makeText(this,"Data updated susccfully",Toast.LENGTH_SHORT).show();
        }else {
            //  Toast.makeText(this,"Data  updated Faille",Toast.LENGTH_SHORT).show();
        }

    }
    /*
     * Preparing the list data
     */
    private String  getID(){

        if(getLastIDSQL()!=null){
            return getLastIDSQL();
        }else {
            return "1";
        }



    }

    private String  getLastIDSQL(){
        //  ArrayList<GuestClass> Headers = new ArrayList<>();
        Cursor res = myDb.getALData();
        // StringBuffer stringBuffer=new StringBuffer();
        Log.d(TAG, "onClick possss last SQL: " );
        String myid =null;
        if(res !=null && res.getCount()>0){
            //Log.d(TAG, "onClick possss last SQL: not nulll");
            while (res.moveToNext()){
                // Log.d(TAG, "onClick possss last SQL: "+String.valueOf(myid));
                myid = res.getString(0);
                //   Log.d(TAG, "onClick possss last SQL: "+String.valueOf(myid));
            }

        }
        Log.d(TAG, "onClick possss last SQL: "+String.valueOf(myid)  );

        return myid;
    }
    private List getallHeaders(){
        List<String> Headers = new ArrayList<>();
        Cursor res = myDb.getALData();
        StringBuffer stringBuffer=new StringBuffer();

        if(res !=null && res.getCount()>0){
            while (res.moveToNext()){
                String ID = res.getString(0);
                String MainTopic=res.getString(1);
                String VendorName=res.getString(2);
               String Comment=res.getString(3);
               String Price=res.getString(4);
               String PhoneNumber=res.getString(5);

                if (Headers.isEmpty()){
                    Headers.add(MainTopic);
                   // Log.d(TAG, "Yoooosif: empty and added = "+MainTopic);
                }else {
                    if (!(Headers.contains(MainTopic))) {
                        Headers.add(MainTopic);
                     //    Log.d(TAG, "Yoooosif: not same and added = "+MainTopic);

                    }

                }

            }

        }
        return Headers;
    }
    private int getCountHeaders(){
        //Toast.makeText(this,String.valueOf(getallHeaders().size()),Toast.LENGTH_SHORT).show();
        return getallHeaders().size();
    }
    private List getDataOfChildheader(String MainTopic){
        List<VendorList> Headers = new ArrayList<>();
        Cursor res = myDb.getALData();
        StringBuffer stringBuffer=new StringBuffer();

        if(res !=null && res.getCount()>0){
            while (res.moveToNext()){
                String ID = res.getString(0);
                String myMainTopic=res.getString(1);
                String VendorName=res.getString(2);
                String Comment=res.getString(3);
                String Price=res.getString(4);
                String PhoneNumber=res.getString(5);
                String myID=res.getString(6);
              //  Log.d(TAG, "Yoooosif: MainTopic is  = "+MainTopic+ "search for "+myMainTopic);
                if (MainTopic.equals(myMainTopic)){
                    VendorList vendorList =new VendorList();
                    vendorList.VendorPhonenumber=PhoneNumber;
                    vendorList.VendorPrice=Price;
                    vendorList.VendorComment=Comment;
                    vendorList.VendorName=VendorName;
                    vendorList.MainTopicName=myMainTopic;
                    vendorList.myID=myID;
                   // Log.d(TAG, "Yoooosif: yeson is  = "+myID+ "search for "+vendorList.myID);
                    Headers.add(vendorList);
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
    private String getIDNumber(String MainTopic,VendorList SupTopic){
        String ID="1" ;
        Cursor res = myDb.getALData();
        StringBuffer stringBuffer=new StringBuffer();
        String Topic;
        String Sup;
        String Comment;
        if(res !=null && res.getCount()>0){
            while (res.moveToNext()){
               // String ID = res.getString(0);
                String myMainTopic=res.getString(1);
                String VendorName=res.getString(2);
              //  String Comment=res.getString(3);
                String Price=res.getString(4);
                String PhoneNumber=res.getString(5);




                Sup=res.getString(5);

                //Log.d(TAG, "Yoooosif: MainTopic is  = "+MainTopic+ "search for "+Topic +" with "+Sup);
                if (MainTopic.equals(myMainTopic) && SupTopic.equals(Sup) ){
                    ID= res.getString(0);
                    return ID;
                }


            }
        }
        return ID;
    }

    private void loadData(){
      //  listDataHeader = new ArrayList<String>();
        //listDataChild = new HashMap<String, List<VendorList>>();
        int numberOfMain = getCountHeaders();
        //Get all topics
        List<String> Headers = new ArrayList<>();
        Headers=getallHeaders();

        int index=0;
        for (String Topic : Headers) {
            listDataHeader.add(Topic);
            //Log.d(TAG, "Yoooosif: not same and added = "+temp);
            //get all children

            List<VendorList> Children = new ArrayList<>();
            Children=getDataOfChildheader(Topic);

            //List<String> Comments=new ArrayList<>();
            //Comments=getDataOfChildComment(Topic);

            //List<String> CheckBox=new ArrayList<>();
            //CheckBox=getDataOfChildCheckBox(Topic);
            //Log.d(TAG, "Yoooosif: children are  = "+Children );
            List<VendorList> TopicList = new ArrayList<VendorList>();

            // for (String Suptopic : Children) {
            //   TopicList.add(new GehazList(Suptopic));
            //     Log.d(TAG, "Yoooosif: Topic is  = "+Topic +" with "+Suptopic);
            // }
            for (int i = 0; i < Children.size(); i++) {
                // System.out.println(Children.get(i));

                TopicList.add(new VendorList(Children.get(i).VendorName,Children.get(i).VendorComment,Children.get(i).VendorPrice,Children.get(i).VendorPhonenumber,Children.get(i).MainTopicName,Children.get(i).myID));
              //  Log.d(TAG, "Yoooosif: child number is dele load = "+Children.get(i).myID);
            }

            listDataChild.put(listDataHeader.get(index), TopicList); // Header, Child data
            index=index+1;
        }


    }

    private void prepareListData() {

        // listDataHeader = new ArrayList<String>();
        //listDataChild = new HashMap<String, List<GehazList>>();


        insertData("yessss","oooooom","","500","011111","1");


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