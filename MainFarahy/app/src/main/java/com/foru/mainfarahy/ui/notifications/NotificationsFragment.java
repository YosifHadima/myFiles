package com.foru.mainfarahy.ui.notifications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.foru.mainfarahy.R;
import com.foru.mainfarahy.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       // final TextView textView = binding.textNotifications;
        TextView textView= root.findViewById(R.id.text_notifications);
        textView.setText(" نافذتك لعرض ونشر خدمات فرحك، جذب الزبائن وتوسيع نطاق عملك بسهولة.     ");

        CardView cardViewAds=root.findViewById(R.id.mycard);
        cardViewAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String websiteUrl = "https://play.google.com/store/apps/details?id=com.foru.adsfarahy"; // Replace with your desired website URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
                startActivity(intent);
            }
        });
      //  notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}