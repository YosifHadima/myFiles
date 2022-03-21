package com.example.mainfarahy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyGuestActivity extends AppCompatActivity  {
ListView listView;


ArrayList<GuestClass> myGuest=new ArrayList<>();
TextView totalGustes;
FloatingActionButton floatingActionButton;
EditText searchView;

    DataBaseGehazHelper myDb;

    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_guest);
       // getActionBar().hide();
        getSupportActionBar().hide();
        totalGustes=findViewById(R.id.totalGust_id);
        floatingActionButton=findViewById(R.id.floatingActionButton);
        listView=findViewById(R.id.listView_id);
        myDb=new DataBaseGehazHelper(this);

//in case of click add new member button
floatingActionButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

       // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
         //       .setAction("Action", null).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(MyGuestActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_box_guest, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
TextView textViewName=dialogView.findViewById(R.id.guest_edit_box_id);
EditText editTextNumber=dialogView.findViewById(R.id.editTextNumber);
        Button button =dialogView.findViewById(R.id.guest_buttonOk_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myName=String.valueOf(textViewName.getText());
                String myNumber=String.valueOf(editTextNumber.getText());
                alertDialog.dismiss();
                if(myNumber.trim().equals("")){
                    myNumber="1";
                }
                if (myName.trim().equals("")){
                    Snackbar.make(view, "ضع اسم المدعو", Snackbar.LENGTH_LONG)
                           .setAction("Action", null).show();
                }else {
                    addMember(myName, Integer.parseInt(myNumber),getID(myGuest));
                    updateData( myGuest);

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

//load the Data if found
        LoadData();
//load and update all data on screen
        updateData(myGuest);


//in case of click on update guest item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyGuestActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_box_guest, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                TextView textViewName= dialogView.findViewById(R.id.guest_edit_box_id);
                textViewName.setText(myGuest.get(position).GuestName);
                EditText editTextNumber= dialogView.findViewById(R.id.editTextNumber);
                editTextNumber.setText(myGuest.get(position).GuestTotal);
                Button button =dialogView.findViewById(R.id.guest_buttonOk_id);
                Button buttonCancel =dialogView.findViewById(R.id.guest_buttoncancel_id);
                buttonCancel.setText("حذف");
                button.setBackgroundColor(Color.parseColor("#e3b04b"));
                buttonCancel.setBackgroundColor(Color.parseColor("#e3b04b"));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String myName=String.valueOf(textViewName.getText());
                        String myNumber=String.valueOf(editTextNumber.getText());

                        if(myNumber.trim().equals("")){
                            myNumber="1";
                        }
                        if (myName.trim().equals("")){
                            Snackbar.make(view, "ضع اسم المدعو", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }else {
                          //  update the data given to the spacific memper
                            myGuest.set(position,new GuestClass(myName,myNumber,myGuest.get(position).GuestCheckBox,myGuest.get(position).GuestID));

                            //update all data on screen
                            updateData( myGuest);
                          //  int x;
                           // x=position+1;
                            //Toast.makeText(getApplicationContext(),"pos "+String.valueOf(position),Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(),"x "+String.valueOf(x),Toast.LENGTH_SHORT).show();

                            //Log.d(TAG, "onClick possss saved: "+String.valueOf(x));
                            Log.d(TAG, "onClick possss position to update: "+myGuest.get(position).GuestID);

                            updateData(myGuest.get(position).GuestID,myGuest.get(position).GuestName,myGuest.get(position).GuestTotal,myGuest.get(position).GuestCheckBox,myGuest.get(position).GuestID);
                            //close the dialog box
                            alertDialog.dismiss();
                        }


                    }
                });

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int x;

                        x=position+1;
                       // Log.d(TAG, "onClick possss delete:my "+myGuest.get(position).);

                        Delete(myGuest.get(position).GuestID);
                        myGuest.remove(position);
                        //adapter.notifyDataSetChanged();

                        updateData(myGuest);
                        alertDialog.dismiss();
                    }
                });



            }
        });

    }
//load Data
private void LoadData(){

myGuest=LoadallData();


}

//Add new Guest
private void addMember(String newname,int newnumber ,String MY_ID){
    insertData(newname, String.valueOf(newnumber),"false",MY_ID);
    if(MY_ID.equals("1")){
        MY_ID=getLastIDSQL();
        updateData(MY_ID,newname, String.valueOf(newnumber),"false",MY_ID);

    }
    myGuest.add(new GuestClass(newname,String.valueOf(newnumber),"false",MY_ID));
}

//update info of all Guests
private void updateData(ArrayList<GuestClass> myGuest ){
    totalGustes.setText(totalGuests(myGuest));
     adapter =new MyAdapter(getApplicationContext(),myGuest);
    listView.setAdapter(adapter);
}

//summtion of all Guests
private String totalGuests(ArrayList<GuestClass> myList){
        int total=0;
        for (int i = 0; i < myList.size(); i++) {

            total= Integer.parseInt(myList.get(i).GuestTotal)+total;
        }
        return String.valueOf(total);
    }

private void insertData(String name,String total,String CheckBox,String MY_ID){

        Boolean result = myDb.insertData(name,total,CheckBox,MY_ID);
        if (result== true){
            //  Toast.makeText(this,"Data inserted susccfully",Toast.LENGTH_SHORT).show();
        }else {
            //  Toast.makeText(this,"Data  insertion Faille",Toast.LENGTH_SHORT).show();
        }

    }

private void updateData(String ID,String name,String total,String CheckBox,String MY_ID){
        Boolean result = myDb.updateData(MY_ID,name,total,CheckBox,MY_ID);
        if (result){
            Log.d(TAG, "onClick possss saved: "+String.valueOf(result) +"ID = "+ID+" myID = "+MY_ID );
            //  Toast.makeText(this,"Data updated susccfully",Toast.LENGTH_SHORT).show();
        }else {
            Log.d(TAG, "onClick possss saved: "+String.valueOf(result) +"ID = "+ID+" myID = "+MY_ID );
            //  Toast.makeText(this,"Data  updated Faille",Toast.LENGTH_SHORT).show();
        }

    }
    private String  getLastIDSQL(){
      //  ArrayList<GuestClass> Headers = new ArrayList<>();
        Cursor res = myDb.getALData();
        // StringBuffer stringBuffer=new StringBuffer();
        String myid = null;
        if(res !=null && res.getCount()>0){
            while (res.moveToNext()){
                 myid=new String();
                String name;
                String total;
                String checkbox;
                String ID;
                myid = String.valueOf(res.getString(0));
                name = String.valueOf(res.getString(1));
                total = String.valueOf(res.getString(2));
                checkbox = String.valueOf(res.getString(3));
                ID = String.valueOf(res.getString(4));
                //Toast.makeText(getApplicationContext(),"pos "+myid,Toast.LENGTH_SHORT).show();

                //ID
                //stringBuffer.append(res.getString(0));
                //Name
                //stringBuffer.append(res.getString(1));
                //total
                //stringBuffer.append(res.getString(2));
                //CheckBox
                //stringBuffer.append(res.getString(3));

               // Headers.add(new GuestClass(name,total,checkbox,ID));


            }

        }
        Log.d(TAG, "onClick possss last SQL: "+String.valueOf(myid)  );

        return myid;
    }
    //need to be updated
private ArrayList LoadallData(){
        ArrayList<GuestClass> Headers = new ArrayList<>();
        Cursor res = myDb.getALData();
       // StringBuffer stringBuffer=new StringBuffer();

        if(res !=null && res.getCount()>0){
            while (res.moveToNext()){
                String myid=new String();
                String name;
                String total;
                String checkbox;
                String ID;
                 myid = String.valueOf(res.getString(0));
                 name = String.valueOf(res.getString(1));
                 total = String.valueOf(res.getString(2));
                 checkbox = String.valueOf(res.getString(3));
                ID = String.valueOf(res.getString(4));
                //Toast.makeText(getApplicationContext(),"pos "+myid,Toast.LENGTH_SHORT).show();

                //ID
                //stringBuffer.append(res.getString(0));
                //Name
                //stringBuffer.append(res.getString(1));
                //total
                //stringBuffer.append(res.getString(2));
                //CheckBox
                //stringBuffer.append(res.getString(3));

                Headers.add(new GuestClass(name,total,checkbox,ID));


            }

        }
        return Headers;
    }

private void Delete(String ID){
    Boolean result= myDb.deleteData(ID);
    if (result){
        Log.d(TAG, "onClick possss delete: "+String.valueOf(result)+" for ID "+ID);
        //  Toast.makeText(this,"Data updated susccfully",Toast.LENGTH_SHORT).show();
    }else {
        Log.d(TAG, "onClick possss delete: "+String.valueOf(result)+" for ID "+ID);
        //  Toast.makeText(this,"Data  updated Faille",Toast.LENGTH_SHORT).show();
    }

}

private String  getID(ArrayList<GuestClass> myGuest){

        List<Integer> intValues = new ArrayList<>();
if (myGuest.isEmpty()){
    Log.d(TAG, "possss saved bef: 1");
    if(getLastIDSQL()!=null){
        return getLastIDSQL();
    }else {
        return "1";
    }

}else {

    for (int i = 0; i < myGuest.size(); i++) {
        //System.out.println(i);
        // adding values
        intValues.add(Integer.valueOf(myGuest.get(i).GuestID));
        //Log.d(TAG, "possss saved bbbb: "+String.valueOf(max));
    }


    Integer max = Collections.max(intValues);
    max=max+1;


    return String.valueOf(max);

}


    }

    class MyAdapter extends ArrayAdapter{

        ArrayList<GuestClass> myGuest;

        public MyAdapter(Context context , ArrayList<GuestClass> myGuest ){
            super(context,R.layout.custome_list_view_guest);
            this.myGuest=myGuest;


        }

        @Override
        public int getCount() {
            return myGuest.size();
        }

        @Override
        public Object getItem(int position) {
            return myGuest.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @NonNull
        @Override
        public View getView(int position, View convertView , ViewGroup parent){
            LayoutInflater inflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.custome_list_view_guest,parent,false);

            TextView myName=row.findViewById(R.id.guestName_id);
            TextView myNumber=row.findViewById(R.id.guestTotal_id);
            CheckBox Checkbox=row.findViewById(R.id.checkBox_guest_id);
            myName.setText(myGuest.get(position).GuestName);
            myNumber.setText(myGuest.get(position).GuestTotal);
            Checkbox.setChecked(Boolean.parseBoolean(myGuest.get(position).GuestCheckBox));
Checkbox.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (Checkbox.isChecked()){
            myGuest.set(position,new GuestClass(myGuest.get(position).GuestName,myGuest.get(position).GuestTotal,"true",myGuest.get(position).GuestID));
            updateData( myGuest);
            updateData(myGuest.get(position).GuestID,myGuest.get(position).GuestName,myGuest.get(position).GuestTotal,"true",myGuest.get(position).GuestID);

        }else{
            myGuest.set(position,new GuestClass(myGuest.get(position).GuestName,myGuest.get(position).GuestTotal,"false",myGuest.get(position).GuestID));
            updateData( myGuest);
            updateData(myGuest.get(position).GuestID,myGuest.get(position).GuestName,myGuest.get(position).GuestTotal,"false",myGuest.get(position).GuestID);

        }
    }
});

            notifyDataSetChanged();
            return row;
        }
    }
}