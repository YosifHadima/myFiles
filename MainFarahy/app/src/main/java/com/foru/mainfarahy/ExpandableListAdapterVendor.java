package com.foru.mainfarahy;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterVendor extends BaseExpandableListAdapter {
    DataBaseHelperVendor myDb;
    static final int PICK_CONTACT=2;
    private final Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<VendorList>> _listDataChild;
   List<VendorList> selectedHeader= new ArrayList<VendorList>();
    //List<VendorList> selectedHeader;
    Activity activity;
    TextView textViewName;
    EditText phonenumber;
    public ExpandableListAdapterVendor(Context context, List<String> listDataHeader, HashMap<String, List<VendorList>> listChildData, Activity activity) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.activity = activity;
        myDb=new DataBaseHelperVendor(activity);
        //Log.d(TAG, "onCreate yyyy: ");

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

        ImageView moneyImage=convertView.findViewById(R.id.money_id);
        moneyImage.setImageResource(R.drawable.money);

String myPrice=_listDataChild.get(this._listDataHeader.get(groupPosition))
        .get(childPosition).VendorPrice;
if (myPrice.isEmpty()){
    //priceText.setText(_listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition).);
    priceText.setText("اضافة سعر");
}else {
    priceText.setText(_listDataChild.get(this._listDataHeader.get(groupPosition))
            .get(childPosition).VendorPrice);
}
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

    private void updateData(String ID,String MAIN_TOPIC,String NAME_VENDOR,String VENDOR_COMMENT,String VENDOR_PRICE,String VENDOR_PHONE,String MY_ID){
        Boolean result = myDb.updateData(ID,MAIN_TOPIC,NAME_VENDOR,VENDOR_COMMENT,VENDOR_PRICE,VENDOR_PHONE,MY_ID);
        if (result){
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
    private void insertData(String MAIN_TOPIC,String NAME_VENDOR,String VENDOR_COMMENT,String VENDOR_PRICE,String VENDOR_PHONE,String MY_ID){

        Boolean result = myDb.insertData(MAIN_TOPIC,NAME_VENDOR,VENDOR_COMMENT,VENDOR_PRICE,VENDOR_PHONE,MY_ID);
        if (result== true){
            // Toast.makeText(this,"Data inserted susccfully",Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(this,"Data  insertion Faille",Toast.LENGTH_SHORT).show();
        }

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
                //loadData();

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.add_vendor_dialog, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                ///////////
                //CheckEmpty();
                ////////
                notifyDataSetChanged();
                 textViewName=dialogView.findViewById(R.id.guest_edit_box_id);
                textViewName.setHint(_listDataHeader.get(groupPosition) );
                String MainTopic=_listDataHeader.get(groupPosition);
                 phonenumber=dialogView.findViewById(R.id.addphone_id);

                Log.d(TAG, "Yoooosif: MainTopic is");
                EditText editTextNumber=dialogView.findViewById(R.id.editTextNumber);
                Button button =dialogView.findViewById(R.id.guest_buttonOk_id);
                button.setBackgroundColor(Color.parseColor("#e3b04b"));
                button.setBackground(ContextCompat.getDrawable(_context.getApplicationContext(),R.drawable.main_button_design));
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
String myID=getID();
                            Log.d(TAG, "Yoooosif: child number is  = "+myID);
                            //selectedHeader.add(new VendorList(myName,"",price,phone,MainTopic,myID));



                           // insertData(MainTopic,myName,"",price,phone);
                           // myDb=new DataBaseHelperVendor(activity);
                            //////////////////////////////
                            //////////SAVE IN SQL/////////
                            insertData(MainTopic,myName,"",price,phone,myID);
                            //check that new ID same as lastID in SQL

                                //Log.d(TAG, "onClick: ");
                                int lastID = Integer.parseInt(getLastIDSQL());

                                if (String.valueOf(lastID).equals(myID))
                                {

                                    selectedHeader.add(new VendorList(myName,"",price,phone,MainTopic,String.valueOf(lastID)));

                                }else {

                                    updateData(String.valueOf(lastID),MainTopic,myName,"",price,phone,String.valueOf(lastID));
                                    selectedHeader.add(new VendorList(myName,"",price,phone,MainTopic,String.valueOf(lastID)));

                                    // selectedHeader.set(selectedHeader.size(),new VendorList(myName,"",price,phone,MainTopic,String.valueOf(lastID)));
                                }


                            _listDataChild.put(_listDataHeader.get(groupPosition), selectedHeader);
                            loadData();
                            //Refresh();
                            notifyDataSetChanged();


                        }


                    }


                });

                Button buttonCancel =dialogView.findViewById(R.id.guest_buttoncancel_id);
                buttonCancel.setBackgroundColor(Color.parseColor("#e3b04b"));
                buttonCancel.setBackground(ContextCompat.getDrawable(_context.getApplicationContext(),R.drawable.main_button_design));
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });


                ImageView contactImage=dialogView.findViewById(R.id.contact_id);
                contactImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        activity.startActivityForResult(intent, PICK_CONTACT);
                    }
                });
            }
        });

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
notifyDataSetChanged();
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
    private boolean CheckEmpty(){
        List<Integer> intValues = new ArrayList<>();
        for (int i = 0; i < _listDataHeader.size(); i++) {
            //System.out.println(i);
            // adding values
            for(int x=0;x<_listDataChild.get(this._listDataHeader.get(i)).size();x++){
                intValues.add(Integer.valueOf(_listDataChild.get(this._listDataHeader.get(i)).get(x).myID));
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


    private String  getID(){
        List<Integer> intValues = new ArrayList<>();
       // Log.d(TAG, "possss saved getID: "+String.valueOf(_listDataChild.size()));
        if (CheckEmpty()) {
            Log.d(TAG, "Yoooosif: child number and last id :"+getLastIDSQL());
            if (getLastIDSQL() != null) {
                return getLastIDSQL();
            } else {
                return "1";
            }
        }else {
            Log.d(TAG, "possss saved getID: its not empty");
            for (int i = 0; i < _listDataHeader.size(); i++) {
                //System.out.println(i);
                // adding values
                for(int x=0;x<_listDataChild.get(this._listDataHeader.get(i)).size();x++){
                    intValues.add(Integer.valueOf(_listDataChild.get(this._listDataHeader.get(i)).get(x).myID));

                }

                //Log.d(TAG, "possss saved bbbb: "+String.valueOf(max));
            }


            Integer max = Collections.max(intValues);
            max=max+1;


            return String.valueOf(max);

        }



    }

    private String  getLastIDSQL(){
        //  ArrayList<GuestClass> Headers = new ArrayList<>();
        Cursor res = myDb.getALData();
        // StringBuffer stringBuffer=new StringBuffer();
        //Log.d(TAG, "onClick possss last SQL: " );
        String myid =null;
        if(res !=null && res.getCount()>0){
            //Log.d(TAG, "onClick possss last SQL: not nulll");
            while (res.moveToNext()){
               // Log.d(TAG, "onClick possss last SQL: "+String.valueOf(myid));
                myid = res.getString(0);

             //   Log.d(TAG, "Yoooosif: yeson : "+String.valueOf(myid));
            }

        }
      //  Log.d(TAG, "Yoooosif: yeson : "+String.valueOf(myid));
         //Log.d(TAG, "onClick possss last SQL: "+String.valueOf(myid)  );

        return myid;
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
    private void loadData(){
        //  listDataHeader = new ArrayList<String>();
        //listDataChild = new HashMap<String, List<VendorList>>();
       // int numberOfMain = getCountHeaders();
        //Get all topics

        List<String> Headers = new ArrayList<>();

        _listDataHeader = new ArrayList<String>();
        Headers=getallHeaders();

        int index=0;
        for (String Topic : Headers) {
            _listDataHeader.add(Topic);
            //Log.d(TAG, "Yoooosif: not same and added = "+temp);
            //get all children

            List<VendorList> Children = new ArrayList<>();
            Children=getDataOfChildheader(Topic);

            List<VendorList> TopicList = new ArrayList<VendorList>();

            for (int i = 0; i < Children.size(); i++) {
                // System.out.println(Children.get(i));

                TopicList.add(new VendorList(Children.get(i).VendorName,Children.get(i).VendorComment,Children.get(i).VendorPrice,Children.get(i).VendorPhonenumber,Children.get(i).MainTopicName,Children.get(i).myID));
                //  Log.d(TAG, "Yoooosif: child number is dele load = "+Children.get(i).myID);
            }

            _listDataChild.put(_listDataHeader.get(index), TopicList); // Header, Child data
            index=index+1;
        }


    }

    //for call import phone feature
    public  void onActivityResult(String name, String num) {
        Log.d("MyAdapter", "onActivityResult");
       // Toast.makeText(activity, name,Toast.LENGTH_SHORT).show();
        textViewName.setText(name);
        phonenumber.setText(num);
    }

}
