package com.example.mainfarahy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterVendor extends BaseExpandableListAdapter {
    DataBaseHelperVendor myDb;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<VendorList>> _listDataChild;
    List<VendorList> selectedHeader= new ArrayList<VendorList>();;
    Activity activity;
    public ExpandableListAdapterVendor(Context context, List<String> listDataHeader,
                                 HashMap<String, List<VendorList>> listChildData, Activity activity) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.activity = activity;
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
            convertView = infalInflater.inflate(R.layout.list_item_vendor, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        TextView priceText=  convertView.findViewById(R.id.price_id);
priceText.setText(_listDataChild.get(this._listDataHeader.get(groupPosition))
        .get(childPosition).VendorPrice);

        TextView txtCommentChild = (TextView) convertView.findViewById(R.id.GehazComment_id);
        String comment=_listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition).VendorComment;
        txtListChild.setText(_listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition).VendorName);
//priceText.setText(_listDataChild.get(this._listDataHeader.get(groupPosition))
      //  .get(childPosition).);
        //priceText.setText("اضافة سعر");
String phoneNumber =_listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition).VendorPhonenumber;


        ImageView imageView_call=convertView.findViewById(R.id.imageView_call_id);
        if (!phoneNumber.equals("")){
            imageView_call.setImageResource(R.drawable.ic_call_on);

        }else {
            imageView_call.setImageResource(R.drawable.ic_call);
        }

        imageView_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!phoneNumber.equals("")){
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+phoneNumber));
                    activity.startActivity(intent);
                }


               // if(ActivityCompat.checkSelfPermission(v.getRootView().getContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
//ActivityCompat.requestPermissions((Activity) v.getRootView().getContext(),new String[]{Manifest.permission.CALL_PHONE},1);
              //  }else{
                  //  v.getRootView().getContext().startActivity(intent);
                //}

            }
        });


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
        myDb=new DataBaseHelperVendor(_context.getApplicationContext());
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
            convertView = infalInflater.inflate(R.layout.list_group_vendor, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        ImageView imageView=convertView.findViewById(R.id.plus_id);
        imageView.setImageResource(R.drawable.myplus);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //should updated this step

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.add_vendor_dialog, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                TextView textViewName=dialogView.findViewById(R.id.guest_edit_box_id);
                textViewName.setHint(_listDataHeader.get(groupPosition) );

                EditText phonenumber=dialogView.findViewById(R.id.addphone_id);


                EditText editTextNumber=dialogView.findViewById(R.id.editTextNumber);
                Button button =dialogView.findViewById(R.id.guest_buttonOk_id);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String myName=String.valueOf(textViewName.getText());
                        String price=String.valueOf(editTextNumber.getText());
                        String phone=String.valueOf(phonenumber.getText());
                        alertDialog.dismiss();
                        if(price.trim().equals("")){
                            price="اضافة سعر";
                        }
                        if (myName.trim().equals("")){
                            Snackbar.make(view, "ضع اسم ", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }else {
                          //  addMember(myName, Integer.parseInt(myNumber),getID(myGuest));
                         //   updateData( myGuest);
                            selectedHeader.add(new VendorList(myName,"",price,phone));
                            _listDataChild.put(_listDataHeader.get(groupPosition), selectedHeader);
                            notifyDataSetChanged();

                        }


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
