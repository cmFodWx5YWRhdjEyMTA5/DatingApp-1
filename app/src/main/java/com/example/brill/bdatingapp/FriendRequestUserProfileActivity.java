package com.example.brill.bdatingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.brill.bdatingapp.adapter.AdapterGetAllUserList;
import com.example.brill.bdatingapp.fragments.UserAboutFragment;
import com.example.brill.bdatingapp.fragments.UserLookingforFragment;
import com.example.brill.bdatingapp.fragments.UserPhtosFragment;
import com.example.brill.bdatingapp.fragments.VisitUserProfileFragment;
import com.example.brill.bdatingapp.gattersatter.GatterGetAllUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class FriendRequestUserProfileActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    CoordinatorLayout layTab;
    String strmsg;

    ImageView cover_photo,profile_pic;
    TextView text_vistpf_name,text_vistpf_city_state_country;


    Button bttn_addfriend,bttn_message,bttn_block,bttn_Like;
    SharedPreferences prefrance;

    EditText edtmessage;
    Button btnClose,btnSendMsg;
    String cuserid;

    Button btnpre,btnnext;


    LinearLayout layPop;

    String selectuserid,selectusername,selectusercity,selectusergender,selectusertype;

    private AdapterGetAllUserList recyclerAdapter;




    int currentindex;
    private ArrayList<GatterGetAllUser> recyclerModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        BeforeShowChanging();





        getVisitUserProfile(selectuserid);





        recyclerModels = new ArrayList<>();











        Log.i("","selectuserid================"+selectuserid);
        Log.i("","selectusername================"+selectusername);

        setTitle(selectusername.toString());



        layTab=(CoordinatorLayout) findViewById(R.id.layTab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.tab_layout_tab, tabLayout, false);

            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);



            tab.select();

            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#373737"));

        }



        bttn_addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String userid=selectuserid;
                    String strfriend="AF";
                    addVisitUserFriend(userid,strfriend);

            }
        });

        bttn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String userid=selectuserid;
                String strfriend=selectuserid;
                addVisitUserFriend(userid);*/
                layPop.setVisibility(View.VISIBLE);

            }
        });


        bttn_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid=selectuserid;
                String strBlock="B1";
                addVisitUserFriend(userid,strBlock);
            }
        });

        bttn_Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid=selectuserid;
                String strLike="L1";
                addVisitUserFriend(userid,strLike);
            }
        });



        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layPop.setVisibility(View.GONE);

            }
        });


        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               strmsg=edtmessage.getText().toString();


                if(strmsg.equals(""))
                {
                    Toasty.info(getApplicationContext(),"Type Your Message",Toast.LENGTH_SHORT).show();
                }
                else {
                    String userid=selectuserid;
                    String strLike="M1";
                    addVisitUserFriend(userid,strLike);
                }
            }
        });


        btnpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // currentindex=currentindex+1;
               /* if(currentindex<arrayList.size()){

                    Log.i("","previous item listis========="+arrayList.get(currentindex).getUname());
                   // textName.setText(""+arrayList.get(currentindex).getUname());
                }
                else{
                    currentindex=currentindex-1;
                }*/








            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void BeforeShowChanging() {

        bttn_addfriend=(Button)findViewById(R.id.bttn_addfriend);
        bttn_message=(Button)findViewById(R.id.bttn_message);
        bttn_block=(Button)findViewById(R.id.bttn_block);
        bttn_Like=(Button)findViewById(R.id.bttn_Like);
        layPop=(LinearLayout) findViewById(R.id.layPop);
        btnClose=(Button)findViewById(R.id.btnClose);
        btnSendMsg=(Button)findViewById(R.id.btnSendMsg);

        btnpre=(Button)findViewById(R.id.btnpre);
        btnnext=(Button)findViewById(R.id.btnnext);



        edtmessage=(EditText) findViewById(R.id.edtmessage);



        final SharedPreferences msharedprefranceAdmin= getSharedPreferences(Configs.UserPrefrance,MODE_PRIVATE);
        cuserid=(msharedprefranceAdmin.getString("id",""));


        text_vistpf_name=(TextView)findViewById(R.id.text_vistpf_name);
        text_vistpf_city_state_country=(TextView)findViewById(R.id.text_vistpf_city_state_country);
        cover_photo=(ImageView)findViewById(R.id.cover_photo);
        profile_pic=(ImageView)findViewById(R.id.profile_pic);




        prefrance=getSharedPreferences("selectedUser", Context.MODE_PRIVATE);

        selectuserid=(prefrance.getString("selectuserid",""));
        selectusername=(prefrance.getString("selectusername",""));
        selectusercity=(prefrance.getString("selectusercity",""));











    }


    private void addVisitUserFriend(final String uid,final String AdBlkLikMsg) {


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_AddBlkMsglik,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","add friend response============="+response.toString());
                        if(response.equals("AlreadySent"))
                        {
                            Toasty.info(getApplicationContext(),"Friend Already sent",Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("F1"))
                        {
                            Toasty.info(getApplicationContext(),"Friend Request Sended",Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("AlreadyBlock"))
                        {
                            Toasty.info(getApplicationContext(),"Already Like",Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("B1"))
                        {
                            Toasty.info(getApplicationContext(),"Like",Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("AlreadyLike"))
                        {
                            Toasty.info(getApplicationContext(),"Already Like",Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("L1"))
                        {
                            Toasty.info(getApplicationContext(),"Liked",Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("M1"))
                        {
                            Toasty.info(getApplicationContext(),"Message sent",Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("1message"))
                        {
                            Toasty.info(getApplicationContext(),"For send more message you have to take plan",Toast.LENGTH_SHORT).show();
                        }

                        else if(response.equals("Removed Friend"))
                        {
                            Toasty.info(getApplicationContext(),"Removed Friend",Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            Toasty.info(getApplicationContext(),"Try again",Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.i("","friendadddsfsadds========="+AdBlkLikMsg+cuserid+uid);

                if(AdBlkLikMsg.equals("M1"))
                {
                    params.put("message", strmsg);
                }


                params.put("currentid", cuserid);
                params.put("vuserid", uid);
                params.put("addAs", AdBlkLikMsg);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getVisitUserProfile(final String sharid) {

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_VISITERPROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","visit user profile data"+response.toString());

                        try {

                            if(response.equals("0"))
                            {
                                Toasty.error(getApplicationContext(),"In-Valid email or password",Toast.LENGTH_SHORT).show();

                                text_vistpf_name.setText(selectusername);

                                Log.i("","selectusergenderselectusergenderselectusergender"+selectusergender);

                                if(selectusergender.equals("male") || selectusergender.equals("Male") || !selectusergender.equals(null))
                                {
                                    String url= Configs.profilephoto+"nophoto-men.jpg";
                                    Log.i("","gendergendergendergendergender======"+url);

                                    Glide.with(getApplicationContext()).load(url).into(profile_pic);

                                }
                                else if(selectusergender.equals("female") || selectusergender.equals("Female"))
                                {
                                    String url= Configs.profilephoto+"nophoto-women.jpg";
                                    Log.i("","gendergendergendergendergender======"+url);

                                    Glide.with(getApplicationContext()).load(url).into(cover_photo);
                                }
                                else {

                                }
                            }
                            else
                            {
                                JSONObject obj = new JSONObject(response);
                                JSONObject userJson = obj.getJSONObject("0");
                                SharedPreferences.Editor editor=prefrance.edit();
                                editor.putString("name",userJson.getString("name"));
                                editor.putString("email",userJson.getString("email"));
                                editor.putString("phone",userJson.getString("phone"));
                                editor.putString("password",userJson.getString("password"));
                                editor.putString("gender",userJson.getString("gender"));
                                editor.putString("birthday",userJson.getString("birthday"));
                                editor.putString("profile_pic",userJson.getString("profile_pic"));
                                editor.putString("cover_pic",userJson.getString("cover_pic"));
                                editor.putString("login",userJson.getString("login"));
                                editor.putString("status",userJson.getString("status"));
                                editor.putString("creation",userJson.getString("creation"));
                                editor.putString("reg_status",userJson.getString("reg_status"));

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


                                Log.i("","friendfriendfriendfriendfriend======="+userJson.getString("friend"));


                                text_vistpf_name.setText(prefrance.getString("name",""));
                                text_vistpf_city_state_country.setText(prefrance.getString("country","")+" "+prefrance.getString("state","")+" "+prefrance.getString("city",""));

                                if(!prefrance.getString("profile_pic","").equals(""))
                                {
                                    String url= Configs.profilephoto+prefrance.getString("profile_pic","");
                                    Log.i("","photo from shared pref======="+url);
                                    Glide.with(getApplicationContext()).load(url).into(profile_pic);




                                }
                                 if(prefrance.getString("profile_pic","").equals("null") || prefrance.getString("profile_pic","").equals("NULL"))
                                 {


                                    if(prefrance.getString("gender","").equals("male") || prefrance.getString("gender","").equals("Male"))
                                    {
                                        String url= Configs.profilephoto+"nophoto-men.jpg";
                                        Log.i("","gendergendergendergendergender======"+url);

                                        Glide.with(getApplicationContext()).load(url).into(profile_pic);
                                        url="";
                                    }
                                    else if(prefrance.getString("gender","").equals("female") || prefrance.getString("gender","").equals("Female"))
                                    {
                                        String url= Configs.profilephoto+"nophoto-women.jpg";
                                        Log.i("","gendergendergendergendergender======"+url);

                                        Glide.with(getApplicationContext()).load(url).into(profile_pic);
                                    }
                                    else {

                                    }



                                }



                                if(!prefrance.getString("cover_pic","").equals(""))
                                {
                                    String url= Configs.coverphoto+prefrance.getString("cover_pic","");
                                    Log.i("","cover photo photo from shared pref======="+url);
                                    Glide.with(getApplicationContext()).load(url).into(cover_photo);
                                }




                            }








                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","profile error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("loginDtl", sharid);
                params.put("cuser", cuserid);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new VisitUserProfileFragment(), "User profile");
        adapter.AddFragment(new UserPhtosFragment(), "Photos");
        adapter.AddFragment(new UserAboutFragment(), "About Me");
        adapter.AddFragment(new UserLookingforFragment(), "Looking for");

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

        public void AddFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public boolean onSupportNavigateUp()
    {
        prefrance=getSharedPreferences("selectedUser", Context.MODE_PRIVATE);
        prefrance.edit().clear().commit();
        finish();

        return true;

    }






}
