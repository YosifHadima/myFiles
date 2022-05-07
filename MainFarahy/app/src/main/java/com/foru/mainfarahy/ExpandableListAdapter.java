package com.foru.mainfarahy;

import static android.content.ContentValues.TAG;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    DataBaseHelper myDb;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<GehazList>> _listDataChild;
    List<GehazList> selectedHeader= new ArrayList<GehazList>();
    Activity activity;
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<GehazList>> listChildData, Activity activity) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.activity = activity;
        myDb=new DataBaseHelper(activity);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

       // final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        CheckBox checkBoxChild= (CheckBox) convertView.findViewById(R.id.price_id);
        checkBoxChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String header=_listDataHeader.get(groupPosition);
                GehazList child;
                String checkBoxString;
               boolean checkBox;
                String SupTopic;
                String dbID;
                String comment;
                child=_listDataChild.get(_listDataHeader.get(groupPosition)).get(childPosition);
                //String comment= String.valueOf(commentEdit.getText());
                SupTopic=child.GehazName;
                //child.GehazComment=comment;
                comment=child.GehazComment;
                checkBox=child.GehazCheckBox;

                if (checkBoxChild.isChecked()){
                    checkBox=true;
                    checkBoxString="True";
                }else {checkBox=false;
                    checkBoxString="False";}

                child.GehazCheckBox=checkBox;

                List<GehazList> mylist=new ArrayList<GehazList>();
                mylist=_listDataChild.get(header);

                mylist.set(childPosition,child);

                _listDataChild.put(header,mylist);
                //getIDNumber(header,Child) and return the ID
                dbID=getIDNumber(header,SupTopic);
                //DataBaseHelper myDb;
                if (!child.GehazID.equals("")){
                    updateData(child.GehazID,child.GehazID,"Gamal","¨1/1/2022",header,SupTopic,comment,checkBoxString);

                }else {
                    updateData(dbID,"","Gamal","¨1/1/2022",header,SupTopic,comment,checkBoxString);

                }
                 //Log.d(TAG, "Yoooosif: checkbox number is  = okkkkk" +_listDataHeader.get(groupPosition));


                //getGehazInfo(ID)

                // insertData("ٍSamya","Gamal","¨1/1/2022","myess","shewak","No comment","false");
               // Log.d(TAG, "Yoooosif: checkbox number is  = okkkkk");

            }
        });
        TextView txtCommentChild = (TextView) convertView.findViewById(R.id.GehazComment_id);
String comment=_listDataChild.get(this._listDataHeader.get(groupPosition))
        .get(childPosition).GehazComment;
        txtListChild.setText(_listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition).GehazName);

        checkBoxChild.setChecked(_listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition).GehazCheckBox);

        txtCommentChild.setText(comment);
        if (comment.trim().equals(""))
        {
            txtCommentChild.setVisibility(View.GONE);
        }else{
            txtCommentChild.setVisibility(View.VISIBLE);}
        //if (checkBoxChild.equals("True")){
        //    checkBoxChild.setChecked(true);
      //  }else {checkBoxChild.setChecked(false);}
        notifyDataSetChanged();
        return convertView;
    }
    private void updateData(String ID,String myID,String MaleName,String MargeDate,String MainTopic,String SupTopic,String CommentTopic,String checkbox){
        Boolean result = myDb.updateData(ID,myID,MaleName,MargeDate,MainTopic,SupTopic,CommentTopic,checkbox);
        if (result== true){
            //  Toast.makeText(this,"Data updated susccfully",Toast.LENGTH_SHORT).show();
        }else {
            //  Toast.makeText(this,"Data  updated Faille",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    private String getIDNumber(String MainTopic,String SupTopic){
        myDb=new DataBaseHelper(_context.getApplicationContext());
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
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        String Matbakh="مطبخ";
        String hamam="مستلزمات حمام";
        String mafaresh="المفروشات";
        String honeymoon="شهر العسل";
        String aghza="اجهزة كهربائية";
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);

        //lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        ImageView addCutomeGehaze= convertView.findViewById(R.id.plus_id);

        addCutomeGehaze.setImageResource(R.drawable.myplus);
        //not show add putton if header is already implmented
        if (headerTitle.equals(Matbakh) || headerTitle.equals(hamam) ||headerTitle.equals(mafaresh) ||headerTitle.equals(honeymoon) ||headerTitle.equals(aghza)  ){
            addCutomeGehaze.setVisibility(View.GONE);

        }else {
            addCutomeGehaze.setVisibility(View.VISIBLE);

        }

        addCutomeGehaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.gehaz_header_dialog_box, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                TextView GehazeSubheader = dialogView.findViewById(R.id.newHeader_id);
                GehazeSubheader.setHint("ضعي جهازك");
                Button button =dialogView.findViewById(R.id.addNewHeaderButton_id);
                button.setBackgroundColor(Color.parseColor("#e3b04b"));
                //hint color should be added here
                button.setTextColor(Color.parseColor("#ffffff"));
button.setBackground(ContextCompat.getDrawable(_context.getApplicationContext(),R.drawable.main_button_design));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertData("2","","",_listDataHeader.get(groupPosition),String.valueOf(GehazeSubheader.getText()),"","false");

                        String myID=getLastIDSQL();
                      // int idnumeric= Integer.parseInt(myID)+1;
                        //myID= String.valueOf(idnumeric);
                      //  Toast.makeText(_context.getApplicationContext(), myID,Toast.LENGTH_SHORT).show();
                        selectedHeader.add(new GehazList( String.valueOf(GehazeSubheader.getText()),myID));
                        _listDataChild.put(_listDataHeader.get(groupPosition), selectedHeader);
                       // Log.d("myTag", "onClick possss last SQL: "+myID );
                        updateData(myID,myID,"","",_listDataHeader.get(groupPosition),String.valueOf(GehazeSubheader.getText()),"","false");
                        loadData();
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });
            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }

    private void insertData(String myID,String MaleName,String MargeDate,String MainTopic,String SupTopic,String CommentTopic,String checkbox){

        Boolean result = myDb.insertData(myID,MaleName,MargeDate,MainTopic,SupTopic,CommentTopic,checkbox);
        if (result== true){
             // Toast.makeText(_context.getApplicationContext(),"Data inserted susccfully",Toast.LENGTH_SHORT).show();
        }else {
              //Toast.makeText(_context.getApplicationContext(), "Data  insertion Faille",Toast.LENGTH_SHORT).show();
        }

    }

    private String  getLastIDSQL(){
        //  ArrayList<GuestClass> Headers = new ArrayList<>();
        Cursor res = myDb.getALData();

        // StringBuffer stringBuffer=new StringBuffer();
        //Log.d("myTag", "onClick possss last SQL: " );
        String myid =null;
        //insertData("myID","","","_listDataHeader.get(groupPosition)","String.valueOf(GehazeSubheader.getText())","","false");

        if(res !=null && res.getCount()>0){
            //Log.d(TAG, "onClick possss last SQL: not nulll");
            while (res.moveToNext()){
                // Log.d(TAG, "onClick possss last SQL: "+String.valueOf(myid));
                myid = res.getString(0);

              //  Log.d("myTag", "Yoooosif: yeson : "+String.valueOf(myid));
            }

        }

        //  Log.d(TAG, "Yoooosif: yeson : "+String.valueOf(myid));
        //Log.d(TAG, "onClick possss last SQL: "+String.valueOf(myid)  );

        return myid;
    }
    private void loadData(){
        _listDataHeader.clear();
        _listDataChild.clear();
       // _listDataHeader = new ArrayList<String>();
        //_listDataChild = new HashMap<String, List<GehazList>>();
       // int numberOfMain = getCountHeaders();
        //Get all topics
        List<String> Headers = new ArrayList<>();
        Headers=getallHeaders();

        int index=0;
        for (String Topic : Headers) {
            _listDataHeader.add(Topic);
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
            _listDataChild.put(_listDataHeader.get(index), TopicList); // Header, Child data
            index=index+1;
        }
        notifyDataSetChanged();

    }
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

    private String  getID(){
        List<Integer> intValues = new ArrayList<>();
        // Log.d(TAG, "possss saved getID: "+String.valueOf(_listDataChild.size()));

            Log.d(TAG, "possss saved getID: its not empty");
            for (int i = 0; i < _listDataHeader.size(); i++) {
                //System.out.println(i);
                // adding values
                for(int x=0;x<_listDataChild.get(this._listDataHeader.get(i)).size();x++){
                    boolean digitsOnly = TextUtils.isDigitsOnly(_listDataChild.get(this._listDataHeader.get(i)).get(x).GehazID);
                    if (digitsOnly){
                        intValues.add(Integer.valueOf(_listDataChild.get(this._listDataHeader.get(i)).get(x).GehazID));

                    }

                }

                //Log.d(TAG, "possss saved bbbb: "+String.valueOf(max));
            }


            Integer max = Collections.max(intValues);
            max=max+1;


            return String.valueOf(max);





    }

    private boolean CheckEmpty(){
        List<Integer> intValues = new ArrayList<>();
        for (int i = 0; i < _listDataHeader.size(); i++) {
            //System.out.println(i);
            // adding values
            for(int x=0;x<_listDataChild.get(this._listDataHeader.get(i)).size();x++){
                intValues.add(Integer.valueOf(_listDataChild.get(this._listDataHeader.get(i)).get(x).GehazID));
                //Log.d(TAG, "Yoooosif: MainTopic is "+_listDataChild.get(this._listDataHeader.get(i)).get(x).VendorName);
            }

            // Log.d(TAG, "Yoooosif: child number is empty: ");
        }


        double sum = 0;
        for(int i = 0; i < intValues.size(); i++)
            sum += intValues.get(i);

        if (sum>0){
            Log.d(TAG, "Yoooosif: child number is Not empty: "+sum);
            return false;

        }else {
            Log.d(TAG, "Yoooosif: child number is empty: "+sum);
            return true;}
    }
}
