package com.example.brill.bdatingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.brill.bdatingapp.fragments.ChatsFragment;
import com.example.brill.bdatingapp.fragments.FriendRequestReceived;
import com.example.brill.bdatingapp.fragments.FriendRequestSended;
import com.example.brill.bdatingapp.fragments.FriendsFragment;
import com.example.brill.bdatingapp.fragments.OnlinUserFragment;
import com.example.brill.bdatingapp.fragments.OnlinUserFragmentChat;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatOnlineFriends extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    CoordinatorLayout layTab;
    SharedPreferences prefrance;

    CircleImageView userPhoto;

    TextView txtuserName, txtGender, txtCity;

    View header;
    NavigationView navigationView;
    String sharid, sharname, sharGender, sharCity, sharUserphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_online_friends);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSharedPrefData();

        // txtuserName.setText(strname.toString());
        //  txtGender.setText(strGender.toString());
        //  txtCity.setText(strAddress.toString());








       /* txtuserName = (TextView)header. findViewById(R.id.txtuserName);
        txtGender = (TextView) header.findViewById(R.id.txtGender);
        txtCity = (TextView) header.findViewById(R.id.txtCity);

        userPhoto=(CircleImageView)header.findViewById(R.id.userphoto);

            txtuserName.setText(sharname.toString());
            txtGender.setText(sharGender.toString());
            txtCity.setText(sharCity.toString());

        if(!sharUserphoto.equals(""))
        {
            String url="http://www.bdating.in/img/members/"+sharUserphoto;
            Log.i("","photo from shared pref======="+url+sharUserphoto);
            Glide.with(getApplicationContext()).load(url).into(userPhoto);
        }


        navigationView.setNavigationItemSelectedListener(this); */

        // application codes

        layTab=(CoordinatorLayout) findViewById(R.id.layTab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);


            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.tab_layout, tabLayout, false);

            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);



            tab.select();

            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#e74c3c"));




        }

        viewPager.setCurrentItem(0);

        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }



    private void getSharedPrefData() {
        prefrance=getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
        sharid= prefrance.getString("id","");

        Log.i("","uuuuuuuuuuuuuuuusharid=================="+sharid);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChatsFragment(),"Chat");
        adapter.addFragment(new OnlinUserFragmentChat(),"Online");
        adapter.addFragment(new FriendsFragment(),"Friends");
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }





    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }



}