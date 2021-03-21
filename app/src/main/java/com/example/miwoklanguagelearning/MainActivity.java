package com.example.miwoklanguagelearning;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import adapter.CategoryAdapter;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.view_pager);

        CategoryAdapter adapter = new CategoryAdapter(this);

        viewPager2.setAdapter(adapter);

        tabLayout = findViewById(R.id.miwok_tabs);

//

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText(R.string.category_numbers);
                } else if (position == 1) {
                    tab.setText(R.string.category_family);
                } else if (position == 2) {
                    tab.setText(R.string.category_colors);
                } else {
                    tab.setText(R.string.category_phrases);
                }
            }
        }).attach();


    }


}