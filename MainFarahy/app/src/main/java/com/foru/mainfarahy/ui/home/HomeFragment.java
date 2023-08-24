package com.foru.mainfarahy.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SearchView;
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
                String phoneNumber =groupDataList.get(groupPosition).getChildren().get(groupPosition).getPhoneNumber();

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
                intent.putExtra("STRING_EXTRA",groupDataList.get(groupPosition).getUserId() );
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
        expandableListAdapter = new ExpandableListAdapter(getActivity(), groupDataList,expandableListView);
        expandableListView.setAdapter(expandableListAdapter);
      //  prepareData();
        EditText etSearch = root.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                expandableListAdapter.filterByName(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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


}