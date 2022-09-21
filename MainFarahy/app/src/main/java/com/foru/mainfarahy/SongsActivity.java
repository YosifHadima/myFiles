package com.foru.mainfarahy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lunger.draglistview.DragListAdapter;
import com.lunger.draglistview.DragListView;


import java.util.ArrayList;


public class SongsActivity extends AppCompatActivity {
    private DragListView mDragListView;
    private ArrayList<String> mDataList;
    MyAdapter adapter;
    FloatingActionButton floatingActionButton;
    private TextView hintPage;
    private AdView mAdView;
    private AdSize getAdSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density; int adWidth = (int) (widthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth); }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveArray();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        getSupportActionBar().hide();

        findView();
        initData();
        initDragListView();
        AdView adView = new AdView(this);

        // adView.setAdSize(AdSize.FULL_BANNER);
        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        adView.setAdUnitId(String.valueOf(R.string.ADS_5));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                mAdView.loadAd(adRequest);
            }
        });
        mDragListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SongsActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_box_guest, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                TextView textViewName= dialogView.findViewById(R.id.guest_edit_box_id);
                textViewName.setText(mDataList.get(position));
                textViewName.setHint("اضافة اغنية");
                EditText editTextNumber= dialogView.findViewById(R.id.editTextNumber);
                editTextNumber.setVisibility(View.GONE);
                Button button =dialogView.findViewById(R.id.guest_buttonOk_id);
                Button buttonCancel =dialogView.findViewById(R.id.guest_buttoncancel_id);
                TextView X_imag=dialogView.findViewById(R.id.textView);
                TextView headerText=dialogView.findViewById(R.id.header_id);
                headerText.setText("اضافة اغنية");
                X_imag.setVisibility(View.GONE);
                buttonCancel.setText("حذف");
                button.setBackgroundColor(Color.parseColor("#e3b04b"));
                button.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.main_button_design));
              //  buttonCancel.setBackgroundColor(Color.parseColor("#e3b04b"));
                buttonCancel.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.main_button_delete));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDataList.set(position,textViewName.getText().toString());

                        alertDialog.dismiss();
                    }
                });

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDataList.remove(position);
                       // initDragListView();
                        adapter.notifyDataSetChanged();

                        alertDialog.dismiss();
                    }
                });
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SongsActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_box_guest, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                TextView textViewName= dialogView.findViewById(R.id.guest_edit_box_id);
                textViewName.setText("");
                textViewName.setHint("اضافة اغنية");
                EditText editTextNumber= dialogView.findViewById(R.id.editTextNumber);
                editTextNumber.setVisibility(View.GONE);
                Button button =dialogView.findViewById(R.id.guest_buttonOk_id);
                Button buttonCancel =dialogView.findViewById(R.id.guest_buttoncancel_id);
                TextView X_imag=dialogView.findViewById(R.id.textView);
                TextView headerText=dialogView.findViewById(R.id.header_id);
                headerText.setText("اضافة اغنية");
                X_imag.setVisibility(View.GONE);
                buttonCancel.setText("حذف");
                button.setBackgroundColor(Color.parseColor("#e3b04b"));
                button.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.main_button_design));
                //  buttonCancel.setBackgroundColor(Color.parseColor("#e3b04b"));
                buttonCancel.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.main_button_delete));

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDataList.add(textViewName.getText().toString());
                        hintPage.setVisibility(View.GONE);
                        alertDialog.dismiss();
                    }
                });

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        alertDialog.dismiss();
                    }
                });
            }
        });
        mDragListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    floatingActionButton.show();
                }else {
                    floatingActionButton.hide();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        loadArray(getApplicationContext());
        if (!mDataList.isEmpty()){
            hintPage.setVisibility(View.GONE);
        }
    }
    public boolean saveArray()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();
        /* sKey is an array */
        mEdit1.putInt("Status_size", mDataList.size());

        for(int i=0;i<mDataList.size();i++)
        {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, mDataList.get(i));
        }

        return mEdit1.commit();
    }
    public void loadArray(Context mContext)
    {
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
        mDataList.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            mDataList.add(mSharedPreference1.getString("Status_" + i, null));
        }

    }

    private void findView() {
        mDragListView = (DragListView) findViewById(R.id.lv);
       floatingActionButton= findViewById(R.id.floatingActionButtonSongs);
       hintPage = findViewById(R.id.hint_song_id);
    }

    private void initData() {
        mDataList = new ArrayList<>();
        /*
        for (int i = 0; i < 50; i++) {
            mDataList.add("Category" + i);
        }
        */
    }
    private void initDragListView() {

        adapter=new MyAdapter(this, mDataList);
       // mDragListView.setDragListAdapter(new MyAdapter(this, mDataList));
        mDragListView.setDragListAdapter(adapter);
        //设置点击item哪个部位可触发拖拽（可不设置，默认是item任意位置长按可拖拽）
        mDragListView.setDragger(R.id.iv_move);

        //song_linear_id
        //设置item悬浮背景色
        mDragListView.setItemFloatColor("#e3b04b");
        //设置item悬浮透明度
        mDragListView.setItemFloatAlpha(0.65f);
        //设置拖拽响应回调
        mDragListView.setMyDragListener(new DragListView.MyDragListener() {
            @Override
            public void onDragFinish(int srcPositon, int finalPosition) {
            //    Toast.makeText(SongsActivity.this,
              //          "beginPosition : " + srcPositon + "...endPosition : " + finalPosition,
                //        Toast.LENGTH_LONG).show();
            }
        });
    }

    class MyAdapter extends DragListAdapter {

        public MyAdapter(Context context, ArrayList<String> arrayTitles) {
            super(context, arrayTitles);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            /***
             * 在这里尽可能每次都进行实例化新的，这样在拖拽ListView的时候不会出现错乱.
             * 具体原因不明，不过这样经过测试，目前没有发现错乱。虽说效率不高，但是做拖拽LisView足够了。
             */
            view = LayoutInflater.from(SongsActivity.this).inflate(
                    R.layout.drag_list_item, null);

            TextView textView = (TextView) view.findViewById(R.id.tv_name);
            textView.setText(mDataList.get(position));
            ImageView imageView= view.findViewById(R.id.iv_move);
            imageView.setImageResource(R.drawable.menu_1_ic);
            return view;
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}