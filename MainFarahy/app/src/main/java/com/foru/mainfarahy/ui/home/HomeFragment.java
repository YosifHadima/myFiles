package com.foru.mainfarahy.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.foru.mainfarahy.R;
import com.foru.mainfarahy.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private DatabaseReference databaseReference;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<GroupData> groupDataList;
    private List<GroupData> filteredData;
    private BootstrapButton makeup_Button,weddingplanner_Button,hairStylistButton,weddingvenue_Button,athelier_Button,veildesigner_Button,videographer_Button;
    private boolean makeup_flag=true;
    private boolean weddingVenue_flag=true;
    private boolean weddingplanner_flag=true;
    private boolean athelier_flag=true;
    private boolean hairstylist_flag=true;
    private boolean veildesigner_flag=true;
    private boolean videographer_flag=true;
    private ProgressBar progressBar;


    private int currentIndex = 0;
    private boolean isEnglish = true;
    private Handler handler = new Handler();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        makeup_Button = root.findViewById(R.id.makeup_id);
        weddingplanner_Button = root.findViewById(R.id.weddingPlanner_id);
        hairStylistButton = root.findViewById(R.id.hairStylist_id);
        weddingvenue_Button = root.findViewById(R.id.weddingvenue_id);
        athelier_Button = root.findViewById(R.id.athelier_id);
        veildesigner_Button = root.findViewById(R.id.veildesigner_id);
        videographer_Button = root.findViewById(R.id.videographer_id);

        // Initialize the ProgressBar
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
       // final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        ExpandableListView expandableListView = root.findViewById(R.id.expandableListView);
        groupDataList = new ArrayList<>();
        //ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), groupData, childData);
       // expandableListView.setAdapter(listAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //String childTitle = childData.get(groupData.get(groupPosition)).get(childPosition).get("childTitle");
                //Toast.makeText(getActivity(), "Selected: " + childTitle, Toast.LENGTH_SHORT).show();
                //String phoneNumber =groupDataList.get(groupPosition).getPhoneNumber();
                String phoneNumber="";

                if (makeup_flag && athelier_flag && hairstylist_flag && veildesigner_flag&&videographer_flag&&weddingplanner_flag&&weddingVenue_flag ){
                     phoneNumber =groupDataList.get(groupPosition).getPhoneNumber();

                }else {
                     phoneNumber =filteredData.get(groupPosition).getPhoneNumber();
                }
                // Specify the SIM slot index (0 for SIM1, 1 for SIM2)
                int simSlotIndex = 0;

                // Check if the device is running Android 10 or earlier
                if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.Q) {
                    makeCallUsingSimSlot(phoneNumber, simSlotIndex);
                } else {
                    // For Android 11 and later, open the dialer with the number filled in
                    openDialer(phoneNumber);
                }
                return true;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Create an Intent
                Intent intent = new Intent(getActivity(), RetriveActivity.class);

                // Put the string as an extra in the Intent
             //   intent.putExtra("STRING_EXTRA",groupDataList.get(groupPosition).getUserId() );
                if (makeup_flag && athelier_flag && hairstylist_flag && veildesigner_flag&&videographer_flag&&weddingplanner_flag&&weddingVenue_flag){
                    intent.putExtra("STRING_EXTRA",groupDataList.get(groupPosition).getUserId() );

                }else {
                    intent.putExtra("STRING_EXTRA",filteredData.get(groupPosition).getUserId() );
                }



                //String x= groupDataList.get(groupPosition).getUserId();
                //Log.e("my joe childPosition", groupDataList.get(groupPosition).getUserId());
                // Start the new activity
                startActivity(intent);


                return true; // Returning true ensures the default behavior (expansion/collapse) doesn't occur
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupDataList.clear();
/*
                for (DataSnapshot childSnapshot : groupSnapshot.child("children").getChildren()) {
                    ChildData childData = new ChildData();
                    childData.setPhoneNumber( childSnapshot.child("phoneNumber").getValue(String.class));
                    childDataList.add(childData);
                }

*/


                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    GroupData groupData = new GroupData();
                 //   Log.e("my joe id", String.valueOf( groupSnapshot.getKey()));
                    String userID =groupSnapshot.getKey();
                    groupData.setStoreName(groupSnapshot.child(userID).child("StoreName").getValue(String.class));
                    groupData.setImageUrl(groupSnapshot.child(userID).child("profileImage").getValue(String.class));
                    groupData.setBusinessName(groupSnapshot.child(userID).child("BusinessName").getValue(String.class));
                    groupData.setviewsCount(groupSnapshot.child(userID).child("myviews").getValue(String.class));
                    groupData.setUserId(userID);
                    groupData.setPhoneNumber(groupSnapshot.child(userID).child("phoneNumber").getValue(String.class));
                    /*
                    List<ChildData> childDataList = new ArrayList<>();
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        ChildData childData = new ChildData();
                        String phoneNumber= childSnapshot.child(userID).child("phoneNumber").getValue(String.class);



                        if (phoneNumber!=null){
                            childData.setPhoneNumber(phoneNumber);
                            Log.e("my joe setPhoneNumber", String.valueOf(childDataList.size()));
                        }

                        childDataList.add(childData);
                    }

                    groupData.setChildren(childDataList);
                    */
                    groupDataList.add(groupData);
                }
                progressBar.setVisibility(ProgressBar.GONE);
                expandableListAdapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseRead", "Failed to read data.", databaseError.toException());
            }
        });
        filteredData = new ArrayList<>();
        expandableListAdapter = new ExpandableListAdapter(getActivity(), groupDataList,expandableListView);
        expandableListView.setAdapter(expandableListAdapter);

      //  prepareData();
        EditText etSearch = root.findViewById(R.id.etSearch);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            filterByName(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        videographer_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear all previouse buttons click
                makeup_flag=true;
                weddingVenue_flag=true;
                weddingplanner_flag=true;
                athelier_flag=true;
                hairstylist_flag=true;
                veildesigner_flag=true;

                ClearAllButtons();
                videographer_flag=!videographer_flag;
                videographer_Button.setShowOutline(videographer_flag);
                expandableListView.setAdapter(expandableListAdapter);

                if (!videographer_flag){
                    filteredData = new ArrayList<>();
                    //  expandableListAdapter.filterByName("hair stylist");
                    filterByName("video grapher");
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), filteredData,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }else{
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), groupDataList,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }
                expandableListAdapter.notifyDataSetChanged();


            }
        });

        veildesigner_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear all previouse buttons click
                makeup_flag=true;
                weddingVenue_flag=true;
                weddingplanner_flag=true;
                athelier_flag=true;
                hairstylist_flag=true;

                videographer_flag=true;
                ClearAllButtons();
                veildesigner_flag=!veildesigner_flag;
                veildesigner_Button.setShowOutline(veildesigner_flag);
                expandableListView.setAdapter(expandableListAdapter);

                if (!veildesigner_flag){
                    filteredData = new ArrayList<>();
                    //  expandableListAdapter.filterByName("hair stylist");
                    filterByName("veil designer");
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), filteredData,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }else{
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), groupDataList,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }
                expandableListAdapter.notifyDataSetChanged();


            }
        });
        makeup_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear all previouse buttons click

                weddingVenue_flag=true;
                weddingplanner_flag=true;
                athelier_flag=true;
                hairstylist_flag=true;
                veildesigner_flag=true;
                videographer_flag=true;
                ClearAllButtons();
                makeup_flag=!makeup_flag;
                makeup_Button.setShowOutline(makeup_flag);
                expandableListView.setAdapter(expandableListAdapter);

                if (!makeup_flag){
                    filteredData = new ArrayList<>();
                    //  expandableListAdapter.filterByName("hair stylist");
                    filterByName("makeup");
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), filteredData,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }else{
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), groupDataList,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }
                expandableListAdapter.notifyDataSetChanged();


            }
        });
        weddingplanner_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear all previouse buttons click
                makeup_flag=true;
                weddingVenue_flag=true;

                athelier_flag=true;
                hairstylist_flag=true;
                veildesigner_flag=true;
                videographer_flag=true;
                ClearAllButtons();
                weddingplanner_flag=!weddingplanner_flag;
                weddingplanner_Button.setShowOutline(weddingplanner_flag);
                expandableListView.setAdapter(expandableListAdapter);

                if (!weddingplanner_flag){
                    filteredData = new ArrayList<>();
                    //  expandableListAdapter.filterByName("hair stylist");
                    filterByName("wedding planner");
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), filteredData,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }else{
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), groupDataList,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }
                expandableListAdapter.notifyDataSetChanged();


            }
        });
        weddingvenue_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear all previouse buttons click
                makeup_flag=true;

                weddingplanner_flag=true;
                athelier_flag=true;
                hairstylist_flag=true;
                veildesigner_flag=true;
                videographer_flag=true;
                ClearAllButtons();
                weddingVenue_flag=!weddingVenue_flag;
                weddingvenue_Button.setShowOutline(weddingVenue_flag);
                expandableListView.setAdapter(expandableListAdapter);

                if (!weddingVenue_flag){
                    filteredData = new ArrayList<>();
                    //  expandableListAdapter.filterByName("hair stylist");
                    filterByName("wedding Venue");
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), filteredData,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }else{
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), groupDataList,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }
                expandableListAdapter.notifyDataSetChanged();


            }
        });
        athelier_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear all previouse buttons click
                makeup_flag=true;
                weddingVenue_flag=true;
                weddingplanner_flag=true;
                hairstylist_flag=true;
                veildesigner_flag=true;
                videographer_flag=true;
                ClearAllButtons();
                athelier_flag=!athelier_flag;
                athelier_Button.setShowOutline(athelier_flag);
                expandableListView.setAdapter(expandableListAdapter);

                if (!athelier_flag){
                    filteredData = new ArrayList<>();
                    //  expandableListAdapter.filterByName("hair stylist");
                    //filterByName("hair stylist");
                    filterByName("athelier");
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), filteredData,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }else{
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), groupDataList,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }
                expandableListAdapter.notifyDataSetChanged();


            }
        });

        hairStylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear all previouse buttons click
                makeup_flag=true;
                weddingVenue_flag=true;
                weddingplanner_flag=true;
                athelier_flag=true;
                veildesigner_flag=true;
                videographer_flag=true;
                ClearAllButtons();
                hairstylist_flag=!hairstylist_flag;
                hairStylistButton.setShowOutline(hairstylist_flag);
                expandableListView.setAdapter(expandableListAdapter);
                if (!hairstylist_flag){
                    filteredData = new ArrayList<>();
                  //  expandableListAdapter.filterByName("hair stylist");
                    filterByName("hair stylist");
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), filteredData,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }else{
                    expandableListAdapter = new ExpandableListAdapter(getActivity(), groupDataList,expandableListView);
                    expandableListView.setAdapter(expandableListAdapter);
                }
                expandableListAdapter.notifyDataSetChanged();

            }
        });

        groupDataList.clear();
        startWordChangingProcess();
        return root;
    }
private void ClearAllButtons(){
    makeup_Button.setShowOutline(true);
    weddingplanner_Button.setShowOutline(true);
    hairStylistButton.setShowOutline(true);
    weddingvenue_Button.setShowOutline(true);
    athelier_Button.setShowOutline(true);
    veildesigner_Button.setShowOutline(true);
    videographer_Button.setShowOutline(true);



}    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);

        binding = null;
    }

    private void openDialer(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri uri = Uri.fromParts("tel", phoneNumber, null);
        intent.setData(uri);
        startActivity(intent);
    }
    private void makeCallUsingSimSlot(String phoneNumber, int simSlotIndex) {
        Intent intent = new Intent(Intent.ACTION_CALL);

        Uri uri = Uri.fromParts("tel", phoneNumber, null);
        intent.setData(uri);

        // Specify the SIM slot index as an extra (0 for SIM1, 1 for SIM2)
        intent.putExtra("com.android.phone.force.slot", true);
        intent.putExtra("com.android.phone.extra.slot", simSlotIndex);

        startActivity(intent);
    }

    public void filterByName(String query) {

        filteredData.clear();
        Log.d("joe Filter", "cleared changed: " );
        if (query.isEmpty()) {
            filteredData.addAll(groupDataList);
            Log.d("joe added", "Text add: " );
        } else {
            Log.d("joe Filter", "Text elsee: " +filteredData.size() );
            for (GroupData group : groupDataList) {
                //Log.d("joe Filter", group.getBusinessNacme() );
                if (group.getBusinessName().toLowerCase().contains(query.toLowerCase())) {
                    Log.d("joe Filter", "Text changed: "+group.getBusinessName() );
                    Log.d("joe Filter", "Text changed: "+group.getPhoneNumber() );

                    filteredData.add(group);
                }
            }
        }

    }

    private void startWordChangingProcess() {
        handler.postDelayed(wordChangeRunnable, 5000);
    }

    private Runnable wordChangeRunnable = new Runnable() {
        @Override
        public void run() {
            changeWordAndAnimate();
            handler.postDelayed(this, 5000);
        }
    };

    private void changeWordAndAnimate() {
      //  final String word;
        final String makeup_word;
        final String weddingVenue_word;
        final String weddingplanner_word;
        final String athelier_word;
        final String hairstylist_word;
        final String veildesigner_word;
        final String videographer_word;

        if (isEnglish) {
          //  word = "makeup artist";
            makeup_word= "makeup artist";
            weddingVenue_word= "wedding venue";
            weddingplanner_word= "wedding planner";
            athelier_word= "athelier";
            hairstylist_word= " hair stylist";
            videographer_word= "video grapher";
            veildesigner_word= "veil designer";

        } else {
           // word = "خبيرة تجميل";
            makeup_word= "خبيرة تجميل";
            weddingVenue_word= "قاعة افراح";
            weddingplanner_word= "منظم حفلات الزفاف";
            athelier_word= "اتيليه";
            hairstylist_word= " مصفف الشعر";
            videographer_word= "مصور فيديو";
            veildesigner_word= "مصمم الحجاب";
        }

        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(1000);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                makeup_Button.setText(makeup_word);
                weddingplanner_Button.setText(weddingplanner_word);
                hairStylistButton.setText(hairstylist_word);
                weddingvenue_Button.setText(weddingVenue_word);
                athelier_Button.setText(athelier_word);
                veildesigner_Button.setText(veildesigner_word);
                videographer_Button.setText(videographer_word);
                AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                fadeIn.setDuration(1000);
                makeup_Button.startAnimation(fadeIn);
                weddingplanner_Button.startAnimation(fadeIn);
                hairStylistButton.startAnimation(fadeIn);
                weddingvenue_Button.startAnimation(fadeIn);
                athelier_Button.startAnimation(fadeIn);
                veildesigner_Button.startAnimation(fadeIn);
                videographer_Button.startAnimation(fadeIn);
               // isEnglish = !isEnglish;
               // currentIndex = (currentIndex + 1) % englishWords.length;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(1000);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                makeup_Button.setText(makeup_word);
                weddingplanner_Button.setText(weddingplanner_word);
                hairStylistButton.setText(hairstylist_word);
                weddingvenue_Button.setText(weddingVenue_word);
                athelier_Button.setText(athelier_word);
                veildesigner_Button.setText(veildesigner_word);
                videographer_Button.setText(videographer_word);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(() -> {
                    makeup_Button.startAnimation(fadeOut);
                    weddingplanner_Button.startAnimation(fadeOut);
                    hairStylistButton.startAnimation(fadeOut);
                    weddingvenue_Button.startAnimation(fadeOut);
                    athelier_Button.startAnimation(fadeOut);
                    veildesigner_Button.startAnimation(fadeOut);
                    videographer_Button.startAnimation(fadeOut);
                }, 3000);


                isEnglish = !isEnglish;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        makeup_Button.startAnimation(fadeIn);
        weddingplanner_Button.startAnimation(fadeIn);
        hairStylistButton.startAnimation(fadeIn);
        weddingvenue_Button.startAnimation(fadeIn);
        athelier_Button.startAnimation(fadeIn);
        veildesigner_Button.startAnimation(fadeIn);
        videographer_Button.startAnimation(fadeIn);


    }


}