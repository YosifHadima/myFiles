package com.example.mainfarahy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.foru.mainfarahy.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    DataBaseHelper myDb;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<GehazList>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<GehazList>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
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
                 //Log.d(TAG, "Yoooosif: checkbox number is  = okkkkk" +_listDataHeader.get(groupPosition));

                updateData(dbID,"yoooo","Gamal","¨1/1/2022",header,SupTopic,comment,checkBoxString);

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
    private void updateData(String ID,String Femalename,String MaleName,String MargeDate,String MainTopic,String SupTopic,String CommentTopic,String checkbox){
        Boolean result = myDb.updateData(ID,Femalename,MaleName,MargeDate,MainTopic,SupTopic,CommentTopic,checkbox);
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
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

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


}
