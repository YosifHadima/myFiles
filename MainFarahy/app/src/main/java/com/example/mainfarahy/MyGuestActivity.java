package com.example.mainfarahy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MyGuestActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
ListView listView;

ArrayList<String> name=new ArrayList<>();
ArrayList<String> number=new ArrayList<>();
TextView totalGustes;
FloatingActionButton floatingActionButton;
SearchView searchView;
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
        searchView=findViewById(R.id.searchView_id);

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
        TextView textViewName= dialogView.findViewById(R.id.guest_edit_box_id);
        EditText editTextNumber= dialogView.findViewById(R.id.editTextNumber);
        Button button =dialogView.findViewById(R.id.guest_buttonOk_id);
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
                    addMember(myName, Integer.parseInt(myNumber));
                    updateData( name ,  number);
                    alertDialog.dismiss();
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


        LoadData();

        updateData(name,number);
        searchView.setOnQueryTextListener(this);


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
                textViewName.setText(name.get(position));
                EditText editTextNumber= dialogView.findViewById(R.id.editTextNumber);
                editTextNumber.setText(number.get(position));
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
                            name.set(position,myName);
                            number.set(position,myNumber);
                           // addMember(myName, Integer.parseInt(myNumber));
                            updateData( name ,  number);
                            alertDialog.dismiss();
                        }


                    }
                });

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name.remove(position);
                        number.remove(position);
                        //adapter.notifyDataSetChanged();
                        updateData(name,number);
                        alertDialog.dismiss();
                    }
                });



            }
        });
      //  MyAdapter adapter =new MyAdapter(getApplicationContext(),name,number);
        //listView.setAdapter(adapter);
    }
private void LoadData(){
    name.add("ماجد");
    name.add("سعيد");
    name.add("مراد");

    number.add("2");
    number.add("3");
    number.add("1");

}
private void addMember(String newname,int newnumber){

    name.add(newname);
    number.add(String.valueOf(newnumber));
}
private void updateData(ArrayList<String> name , ArrayList<String> number){
    totalGustes.setText(totalGuests(number));
     adapter =new MyAdapter(getApplicationContext(),name,number);
    listView.setAdapter(adapter);
}


    private String totalGuests(ArrayList<String> myList){
        int total=0;
        for (int i = 0; i < number.size(); i++) {

            total= Integer.parseInt(number.get(i))+total;
        }
        return String.valueOf(total);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
       // String text=s;
        adapter.getFilter().filter(s);

        return false;
    }

    class MyAdapter extends ArrayAdapter{
        ArrayList<String> name;
        ArrayList<String> number;
        public MyAdapter(Context context , ArrayList<String> name , ArrayList<String> number){
            super(context,R.layout.custome_list_view_guest);
            this.name=name;
            this.number=number;
        }

        @Override
        public int getCount() {
            return name.size();
        }

        @Override
        public Object getItem(int position) {
            return name.get(position);
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

            myName.setText(name.get(position));
            myNumber.setText(number.get(position));
            notifyDataSetChanged();
            return row;
        }
    }
}