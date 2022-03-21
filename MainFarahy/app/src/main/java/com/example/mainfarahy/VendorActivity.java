package com.example.mainfarahy;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VendorActivity extends AppCompatActivity {
    ExpandableListAdapterVendor listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<GehazList>> listDataChild;
    DataBaseHelperVendor myDb;
    FloatingActionButton floatingActionButton;
boolean weddingPlanner_clicked,weedingVen_clicked ,videoGrapher_clicked, athlie_Button_clicked,veil_clicked,makeupArtist_clicked,photographer_clicked,hairStylest_clicked =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        myDb=new DataBaseHelperVendor(this);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);


        // preparing list data
        int count=getCountHeaders();
        if(count == 0){
            prepareListData();

        }else{
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<GehazList>>();
           // loadData();

        }
        // prepareListData();

        listAdapter = new ExpandableListAdapterVendor(this, listDataHeader, listDataChild);

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
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_box_gehaz, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                EditText commentEdit= (EditText) dialogView.findViewById(R.id.guest_edit_box_id) ;
                commentEdit.setText(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).GehazComment);
                Button okButtonDialog= (Button) dialogView.findViewById(R.id.guest_buttonOk_id);
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
        List<GehazList> weedingVen = new ArrayList<GehazList>();
        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        weedingVen_clicked=true;
        alertDialog.dismiss();
    }
});


                weddingPlanner_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDataHeader.add("Wedding Planner");
                        List<GehazList> weedingVen = new ArrayList<GehazList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild);
                        // setting list adapter
                        expListView.setAdapter(listAdapter);
                        weddingPlanner_clicked=true;
                        alertDialog.dismiss();
                    }
                });


                athlie_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDataHeader.add("Athelier");
                        List<GehazList> weedingVen = new ArrayList<GehazList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild);
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
                        List<GehazList> weedingVen = new ArrayList<GehazList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild);
                        // setting list adapter
                        expListView.setAdapter(listAdapter);
                        photographer_clicked=true;
                        alertDialog.dismiss();
                    }
                });
                hairStylest_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDataHeader.add("Hair Stylest");
                        List<GehazList> weedingVen = new ArrayList<GehazList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild);
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
                        List<GehazList> weedingVen = new ArrayList<GehazList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild);
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
                        List<GehazList> weedingVen = new ArrayList<GehazList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1), weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild);
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
                        List<GehazList> weedingVen = new ArrayList<GehazList>();
                        listDataChild.put(listDataHeader.get(listDataHeader.size()-1) ,weedingVen); // Header, Child data
                        listAdapter = new ExpandableListAdapterVendor(getApplicationContext(), listDataHeader, listDataChild);
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
        String Matbakh="مطبخ";
        String hamam="مستلزمات حمام";
        String mafaresh="المفروشات";

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
        insertData("","","",Matbakh,"مدق للبتيك","","false");
        insertData("","","",Matbakh,"نشابة عجين","","false");
        insertData("","","",Matbakh,"اطباق ميكروويف","","false");
        insertData("","","",Matbakh,"قطاعة بيتزا","","false");
        insertData("","","",Matbakh,"معلقة ايس كريم","","false");
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
        insertData("","","",Matbakh,"فرشاة بيض","","false");
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

        insertData("","","",mafaresh,"بطاطين","","false");
        insertData("","","",mafaresh,"لحاف","","false");
        insertData("","","",mafaresh,"اغطية لحاف","","false");
        insertData("","","",mafaresh,"كوفرتة","","false");
        insertData("","","",mafaresh,"مفرش سرسر دفاية","","false");
        insertData("","","",mafaresh,"غطاء مرتبة","","false");
        insertData("","","",mafaresh,"طقم ملايات","","false");
        insertData("","","",mafaresh,"بشاكير قطن","","false");
        insertData("","","",mafaresh,"طقم فوط قطن","","false");
        insertData("","","",mafaresh,"برنس حمام","","false");
        insertData("","","",mafaresh,"شبشب حمام","","false");
        insertData("","","",mafaresh,"مفرش سفرة","","false");
        insertData("","","",mafaresh,"مفارش نيش","","false");
        insertData("","","",mafaresh,"مفارش لترابيزة الصالون","","false");
        insertData("","","",mafaresh,"","","false");
        insertData("","","",mafaresh,"","","false");
        insertData("","","",mafaresh,"","","false");
        insertData("","","",mafaresh,"","","false");
        insertData("","","",mafaresh,"","","false");
        insertData("","","",mafaresh,"","","false");


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