package com.foru.mainfarahy.ui.home;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.foru.mainfarahy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;  // Add this field

    private Context context;
    private List<GroupData> groupDataList;
   // private List<GroupData> filteredData;


    public ExpandableListAdapter(Context context, List<GroupData> groupDataList,ExpandableListView listView) {
        this.context = context;
        this.groupDataList = groupDataList;
        this.expandableListView = listView;
     //   this.filteredData = new ArrayList<>(groupDataList);

    }

    @Override
    public int getGroupCount() {
        return groupDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
      //  return groupDataList.get(groupPosition).getChildren().size();
        return 1;

    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupDataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupDataList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
      //  String groupTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_group_header, null);
        }
     //   Log.e("my joe groupPosition", String.valueOf(groupPosition));
LinearLayout linearLayout=convertView.findViewById(R.id.linearGroup);
        TextView groupTitleTextView = convertView.findViewById(R.id.groupTitleTextView);
        TextView groupSubtitleTextView = convertView.findViewById(R.id.groupSubtitleTextView);
         ImageView groupImageView = convertView.findViewById(R.id.groupImageView);
        ImageView arrowImageView = convertView.findViewById(R.id.arrowid);

      //  Log.e("my joe childPosition", groupTitle);
        groupTitleTextView.setText(groupDataList.get(groupPosition).getStoreName());
        groupSubtitleTextView.setText(groupDataList.get(groupPosition).getBusinessName());
        arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded) {
                    expandableListView.collapseGroup(groupPosition);
                } else {
                    expandableListView.expandGroup(groupPosition);
                }
            }
        });
if (isExpanded){
    linearLayout.setPadding(4,4,4,0);
}else {
    linearLayout.setPadding(4,4,4,4);
}
      //  groupImageView.setImageResource(R.drawable.ic_contact);



        String imageUrl = groupDataList.get(groupPosition).getImageUrl();

        if (imageUrl != null) {
            Glide.with(convertView.getContext().getApplicationContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                    .error(R.drawable.common_google_signin_btn_icon_dark)
                    .into(groupImageView);
        }else{
           // groupImageView.setImageResource(R.drawable.ic_contact);

        }

        //groupImageView.setImageResource(R.drawable.ic_contact);
        //.setImageResource(R.drawable.ic_contact);
        // Animate the group expansion/collapse

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_child_item, null);
        }
        // Retrieve the ChildData object
      //  ChildData childData = groupDataList.get(groupPosition).getChildren().get(groupPosition);

      //  Log.e("my joe groupPosition", String.valueOf(groupPosition));
        //Log.e("my joe childPosition", String.valueOf(childPosition));

        TextView childTitleTextView = convertView.findViewById(R.id.childTitleTextView);
        TextView childSubtitleTextView = convertView.findViewById(R.id.childSubtitleTextView);
        ImageView childImageView = convertView.findViewById(R.id.childImageView);

       childTitleTextView.setText(groupDataList.get(groupPosition).getPhoneNumber());
   //     childSubtitleTextView.setText("childSubtitle");

     childImageView.setImageResource(R.drawable.ic_call);
        childImageView.setVisibility(View.VISIBLE);
// Animate the group expansion/collapse


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
/*
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

                    filteredData.add(group);
                }
            }
        }
   //force reflecting filter on the exbandable list view
        adapter= new ExpandableListAdapter(this.context, filteredData,expandableListView);
        expandableListView.setAdapter(adapter);
        notifyDataSetChanged();

    }
*/
}