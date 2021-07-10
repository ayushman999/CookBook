package com.ayushman999.cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.ayushman999.cookbook.Fragments.RandomFragment;
import com.ayushman999.cookbook.Fragments.SavedFragment;
import com.ayushman999.cookbook.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
BottomNavigationView bottomNavigationView;
FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottom_nav);
        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container,new RandomFragment())
                    .commit();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        frameLayout=findViewById(R.id.frame_container);

    }
    BottomNavigationView.OnNavigationItemSelectedListener listener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;
            switch (item.getItemId())
            {
                case R.id.random_icn:
                {
                    fragment=new RandomFragment();
                    break;
                }
                case R.id.search_icn:
                {
                    fragment=new SearchFragment();
                    break;
                }
                case R.id.saved_icn:
                {
                    fragment=new SavedFragment();
                    break;
                }
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
            return true;
        }
    };
}