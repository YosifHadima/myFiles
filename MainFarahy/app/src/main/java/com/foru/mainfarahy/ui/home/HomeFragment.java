package com.foru.mainfarahy.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
                return true;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (parent.isGroupExpanded(groupPosition)) {
                    //Log.e("my joe childPosition", "yes");

                    parent.collapseGroup(groupPosition);
                } else {
                 //   Log.e("my joe childPosition", "No");

                    parent.expandGroup(groupPosition);
                }

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
                    groupData.setUserId(userID);

                    List<ChildData> childDataList = new ArrayList<>();
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        ChildData childData = new ChildData();
                        String phoneNumber= childSnapshot.child(userID).child("phoneNumber").getValue(String.class);

                        childData.setPhoneNumber(phoneNumber);

                        if (phoneNumber!=null){
                            Log.e("my joe setPhoneNumber",phoneNumber );
                        }

                        childDataList.add(childData);
                    }

                    groupData.setChildren(childDataList);
                    groupDataList.add(groupData);
                }

                expandableListAdapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseRead", "Failed to read data.", databaseError.toException());
            }
        });
        expandableListAdapter = new ExpandableListAdapter(getActivity(), groupDataList);
        expandableListView.setAdapter(expandableListAdapter);
      //  prepareData();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}