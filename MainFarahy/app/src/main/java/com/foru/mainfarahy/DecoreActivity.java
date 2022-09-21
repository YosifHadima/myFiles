package com.foru.mainfarahy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.felix.imagezoom.ImageZoom;

public class DecoreActivity extends AppCompatActivity {
GridView grid;
    ArrayList images = new ArrayList<>(Arrays.asList(R.drawable.decore_1,R.drawable.decore_3,R.drawable.decore_4,R.drawable.decore_5,R.drawable.decore_6,R.drawable.decore_7,R.drawable.decore_8,R.drawable.decore_9,R.drawable.decore_10,R.drawable.decore_11,R.drawable.decore_13,R.drawable.decore_14));
    RecyclerView recyclerView;
    private AdView mAdView;
    private AdSize getAdSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density; int adWidth = (int) (widthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth); }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decore);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView=findViewById(R.id.recyclerView);
        AdView adView = new AdView(this);

        // adView.setAdSize(AdSize.FULL_BANNER);
        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        adView.setAdUnitId(String.valueOf(R.string.ADS_7));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                mAdView.loadAd(adRequest);
            }
        });

        getSupportActionBar().hide();
        //ArrayAdapter<Integer> adapter=new ArrayAdapter<Integer>(this,R.layout.simple_list_items_1,data);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        // Sending reference and data to Adapter
        Adapter adapter = new Adapter(DecoreActivity.this, images);

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(adapter);
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        ArrayList images;
        Context context;

        // Constructor for initialization
        public Adapter(Context context, ArrayList images) {
            this.context = context;
            this.images = images;
        }

        @NonNull
        @Override
        public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflating the Layout(Instantiates list_item.xml layout file into View object)
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, parent, false);

            // Passing view to ViewHolder
            Adapter.ViewHolder viewHolder = new Adapter.ViewHolder(view);
            return viewHolder;
        }

        // Binding data to the into specified position
        @Override
        public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
            // TypeCast Object to int type
            int res = (int) images.get(position);
            holder.images.setImageResource(res);
        }

        @Override
        public int getItemCount() {
            // Returns number of items currently available in Adapter
            return images.size();
        }

        // Initializing the Views
        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView images;

            public ViewHolder(View view) {
                super(view);
                images = (ImageView) view.findViewById(R.id.grid_item_image);
            }
        }
    }
}