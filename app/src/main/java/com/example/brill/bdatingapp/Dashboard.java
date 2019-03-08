package com.example.brill.bdatingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.brill.bdatingapp.fragments.AllFragment;
import com.example.brill.bdatingapp.fragments.ChatListFragment;
import com.example.brill.bdatingapp.fragments.ChatsFragment;
import com.example.brill.bdatingapp.fragments.FriendsFragment;
import com.example.brill.bdatingapp.fragments.NearByFragment;
import com.example.brill.bdatingapp.fragments.OnlinUserFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    CoordinatorLayout layTab;
    SharedPreferences prefrance;

    CircleImageView userPhoto;
    ImageView imageView;

    TextView txtuserName,txtGender,txtCity;

    View header;
    NavigationView navigationView;
    String sharid,sharname,sharGender,sharCity,sharUserphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);





        getSharedPrefData();

        GetUserDetailMethod(sharid);




       // txtuserName.setText(strname.toString());
      //  txtGender.setText(strGender.toString());
      //  txtCity.setText(strAddress.toString());







        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(this);
         header=navigationView.getHeaderView(0);
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
        adapter.addFragment(new AllFragment(),"All Users");
            adapter.addFragment(new NearByFragment(),"Nearby");
            adapter.addFragment(new OnlinUserFragment(),"Online");
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            System.exit(0);
            System.gc();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            final SharedPreferences msharedprefranceAdmin= getSharedPreferences(Configs.UserPrefrance,MODE_PRIVATE);
            msharedprefranceAdmin.edit().clear().commit();

            Intent intent=new Intent(Dashboard.this,LoginActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        if (id == R.id.change_Password) {


            Intent intent=new Intent(Dashboard.this,ChangePasswordActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent=new Intent(Dashboard.this,UserProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {
            Intent intent=new Intent(Dashboard.this,SearchActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.photos) {
            Intent intent=new Intent(Dashboard.this,AddPhotos.class);
            intent.putExtra("id",sharid);
            startActivity(intent);
        }
        else if (id == R.id.all_users) {
            Intent intent=new Intent(Dashboard.this,ChatOnlineFriends.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_mambership) {
            Intent intent=new Intent(Dashboard.this,Membership_plans.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_adv_search) {
            Intent intent=new Intent(Dashboard.this,AdvanceSearchActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_message) {
            Intent intent=new Intent(getApplicationContext(),UserMessages.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_sms) {
            Intent intent=new Intent(getApplicationContext(),ActivitySMSList.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_feedback) {
            Intent intent=new Intent(getApplicationContext(),FeedbackActivity.class);
            intent.putExtra("id",sharid);
            startActivity(intent);
        }
        else if (id == R.id.nav_friend_req) {
            Intent intent=new Intent(Dashboard.this,NearByActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_ProfileVisitors) {
            Intent intent=new Intent(Dashboard.this,ProfileVisitors.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_block_user) {
            Intent intent=new Intent(Dashboard.this,BlockUserList.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_likes) {
            Intent intent=new Intent(Dashboard.this,Like.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_online) {
            Intent intent=new Intent(Dashboard.this,FriendRequest.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_notifi) {
            Intent intent=new Intent(Dashboard.this,NotifiactionActivity.class);
            intent.putExtra("id",sharid);
            intent.putExtra("name",sharname);
            startActivity(intent);
        }
        /*else if (id == R.id.searchBtn1111) {
            Intent intent=new Intent(Dashboard.this,SearchActivity.class);
            startActivity(intent);
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }










    private void GetUserDetailMethod(final String sharid) {





        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","uuuuuuuuuuuuuuu"+response.toString());

                        try {

                            if(response.equals("0"))
                            {
                                Toasty.error(Dashboard.this,"In-Valid email or password",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                JSONObject obj = new JSONObject(response);
                                JSONObject userJson = obj.getJSONObject("0");
                                SharedPreferences.Editor editor=prefrance.edit();
                                    editor.putString("name",userJson.getString("name"));
                                    editor.putString("email",userJson.getString("email"));
                                    editor.putString("phone",userJson.getString("phone"));
                                    editor.putString("gender",userJson.getString("gender"));
                                    editor.putString("birthday",userJson.getString("birthday"));
                                    editor.putString("profile_pic",userJson.getString("profile_pic"));
                                    editor.putString("cover_pic",userJson.getString("cover_pic"));
                                    editor.putString("login",userJson.getString("login"));
                                    editor.putString("status",userJson.getString("status"));
                                    editor.putString("creation",userJson.getString("creation"));
                                    editor.putString("reg_status",userJson.getString("reg_status"));

                                    //editor.putString("user_status",userJson.getString("user_status"));

                                    editor.putString("country_code",userJson.getString("country_code"));
                                    editor.putString("state_code",userJson.getString("state_code"));
                                    editor.putString("city_code",userJson.getString("city_code"));
                                    editor.putString("country",userJson.getString("country"));
                                    editor.putString("state",userJson.getString("state"));
                                    editor.putString("city",userJson.getString("city"));
                                    editor.putString("looking",userJson.getString("looking"));
                                    editor.putString("rel_status",userJson.getString("rel_status"));
                                    editor.putString("smoke",userJson.getString("smoke"));
                                    editor.putString("drink",userJson.getString("drink"));
                                    editor.putString("education",userJson.getString("education"));
                                    editor.putString("language",userJson.getString("language"));
                                    editor.putString("minage",userJson.getString("minage"));
                                    editor.putString("maxage",userJson.getString("maxage"));
                                    editor.putString("eye",userJson.getString("eye"));
                                    editor.putString("skin",userJson.getString("skin"));
                                    editor.putString("work",userJson.getString("work"));
                                    editor.putString("interest",userJson.getString("interest"));
                                    editor.putString("look_rel",userJson.getString("look_rel"));
                                    editor.putString("about",userJson.getString("about"));
                                    editor.putString("partner",userJson.getString("partner"));
                                    editor.putString("user",userJson.getString("user"));
                                    editor.commit();





                                sharname= prefrance.getString("name","");
                                sharGender= prefrance.getString("gender","");
                                sharCity= prefrance.getString("city","");
                                sharUserphoto= prefrance.getString("profile_pic","");


                                Log.i("","lookingouuuuuuuuuuuuuuu"+userJson.getString("looking"));

                                setNavigationData(header);

                            }








                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","profile error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("loginDtl", sharid);

                return params;
            }


        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setNavigationData(View header) {
         txtuserName = (TextView)header. findViewById(R.id.txtuserName);
        txtGender = (TextView) header.findViewById(R.id.txtGender);
        txtCity = (TextView) header.findViewById(R.id.txtCity);

        ImageView edit = (ImageView)findViewById(R.id.edit_profile_img);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this,UserProfileActivity.class));
            }
        });

        userPhoto=(CircleImageView)header.findViewById(R.id.userphoto);


        if(!sharCity.equals(""))
        {

            txtCity.setText(sharCity.toString());

        }


            txtuserName.setText(sharname.toString());
            txtGender.setText(sharGender.toString());


        if(!sharUserphoto.equals(""))
        {
            String url= Configs.profilephoto+sharUserphoto;
            Log.i("","photo from shared pref======="+url+sharUserphoto);
            Glide.with(Dashboard.this).load(url).into(userPhoto);
        }


        navigationView.setNavigationItemSelectedListener(this);
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }



}
