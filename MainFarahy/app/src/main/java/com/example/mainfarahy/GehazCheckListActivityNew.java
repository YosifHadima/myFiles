package com.example.mainfarahy;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.Toast;
public class GehazCheckListActivityNew extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<GehazList>> listDataChild;
DataBaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gehaz_check_list_new);
        myDb=new DataBaseHelper(this);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        int count=getCountHeaders();
        if(count == 0){
            prepareListData();

        }else{
            loadData();

        }
       // prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(GehazCheckListActivityNew.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_box_gehaz, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                EditText commentEdit= (EditText) dialogView.findViewById(R.id.edit_box_id) ;
                commentEdit.setText(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).GehazComment);
                Button okButtonDialog= (Button) dialogView.findViewById(R.id.buttonOk_id);
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
                    child.GehazComment=comment;
                    List<GehazList> mylist=new ArrayList<GehazList>();
                    mylist=listDataChild.get(header);
                    mylist.set(childPosition,child);
                   // Log.d(TAG, "Yoooosif: child number is  = "+String.valueOf(childPosition)+" with ID = "+String.valueOf(id));
                    listDataChild.put(header,mylist);
                    //getIDNumber(header,Child) and return the ID
                    dbID=getIDNumber(header,SupTopic);

                    //getGehazInfo(ID)

                   // insertData("ٍSamya","Gamal","¨1/1/2022","myess","shewak","No comment","false");
                    updateData(dbID,"yoooo","Gamal","¨1/1/2022",header,SupTopic,comment,"false");
                        // expListView.setAdapter(listAdapter);
                    alertDialog.dismiss();
                }
            });
               // int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                //ImageView noteButton = (ImageView) v.findViewById(R.id.note_id);


                return false;
            }
        });



    }

    private void insertData(String Femalename,String MaleName,String MargeDate,String MainTopic,String SupTopic,String CommentTopic,String checkbox){

        Boolean result = myDb.insertData(Femalename,MaleName,MargeDate,MainTopic,SupTopic,CommentTopic,checkbox);
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

    private void updateData(String ID,String Femalename,String MaleName,String MargeDate,String MainTopic,String SupTopic,String CommentTopic,String checkbox){
        Boolean result = myDb.updateData(ID,Femalename,MaleName,MargeDate,MainTopic,SupTopic,CommentTopic,checkbox);
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
                TopicList.add(new GehazList(Children.get(i),Comments.get(i),check));
            }
            listDataChild.put(listDataHeader.get(index), TopicList); // Header, Child data
            index=index+1;
        }

    }

    private void prepareListData() {

       // listDataHeader = new ArrayList<String>();
        //listDataChild = new HashMap<String, List<GehazList>>();
        insertData("ٍSamya","Gamal","¨1/1/2022","مطابخ","شوك","","false");
        insertData("ٍSamya","Gamal","¨1/1/2022","مطابخ","سكاكين","","false");
        insertData("ٍSamya","Gamal","¨1/1/2022","مطابخ","معالق","","false");

        insertData("ٍSamya","Gamal","¨1/1/2022","yosif","gzam","","false");
        insertData("ٍSamya","Gamal","¨1/1/2022","yosif","lbs","","false");
        insertData("ٍSamya","Gamal","¨1/1/2022","yosif","mmm","","false");
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