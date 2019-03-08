package com.example.brill.bdatingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.brill.bdatingapp.adapter.AdapterGetAllUserList;
import com.example.brill.bdatingapp.fragments.UserAboutFragment;
import com.example.brill.bdatingapp.fragments.UserLookingforFragment;
import com.example.brill.bdatingapp.fragments.UserPhtosFragment;
import com.example.brill.bdatingapp.fragments.VisitUserProfileFragment;
import com.example.brill.bdatingapp.gattersatter.GatterGetAllUser;
import com.example.brill.bdatingapp.gattersatter.GatterGetBlockUserList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class VisitUserProfileActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    CoordinatorLayout layTab;
    String strmsg;

    ImageView profile_pic;
    TextView text_vistpf_name,text_vistpf_city_state_country;


    ViewPager viewPager;
    ViewPager viewPager1;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    RequestQueue rq;
    List<SliderUtils> sliderImg;
    com.example.brill.bdatingapp.ViewPagerAdapter viewPagerAdapter1;

    String request_url = "http://www.brilldating.com/android/getAllUserData.php/";



    Button bttn_addfriend,bttn_message,bttn_block,bttn_Like,bttn_info;
    SharedPreferences prefrance;
    FloatingActionButton floatingButton;
    EditText edtmessage;
    Button btnClose,btnSendMsg,btnClose1;
    String cuserid;

    Button btnprej,btnnextj;

    LinearLayout layPop,layPop1;

    String selectuserid,selectusername,selectusercity,selectusergender,selecttype;

    private AdapterGetAllUserList recyclerAdapter;
    private ArrayList<GatterGetAllUser> recyclerModels;
    private ArrayList<GatterGetBlockUserList> recyclerModelsBlock;

    int currentindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnprej = (Button) findViewById(R.id.btnprej);
        btnnextj = (Button) findViewById(R.id.btnnextj);

        BeforeShowChanging();
        VisitUserPhotos();

        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        /*Bundle bundleObject=getIntent().getExtras();

        //Log.i("sadf","userInfo" +recyclerModels.size());
        Intent i1=getIntent();
        final int pos = (int) i1.getExtras().get("position");
        final String classtype = (String) i1.getExtras().get("classtype");

        if (classtype.equals("allUser"))
        {
            recyclerModels=(ArrayList<GatterGetAllUser>)bundleObject.getSerializable("info");
        }
        else if (classtype.equals("block"))
        {
            recyclerModelsBlock= (ArrayList<GatterGetBlockUserList>) bundleObject.getSerializable("info");
        }


        Log.i("sdfsd","userInfointent===="+i1.getExtras().get("position"));

        Log.i("sadf","userIuser namenfo" +recyclerModels.get(pos).getUname());


        btnprej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname=recyclerModels.get(pos+1).getUname();
                text_vistpf_name.setText(uname);
                Log.i("sadf","usesdfsadfrInfo" +recyclerModels.size());
                Log.i("sdfsd","namemeeeeeee=pre==="+uname.toString());
            }
        });

        btnnextj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("sadf","usefsdfsdfrInfo" +recyclerModels.size());
                String uname=recyclerModels.get(pos-1).getUname();
                text_vistpf_name.setText(uname);
                Log.i("sdfsd","namemeeeeeee==next=="+uname.toString());
            }
        });*/

        rq = CustomVolleyRequest.getInstance(this).getRequestQueue();

        sliderImg = new ArrayList<>();

        viewPager1 = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);


        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });






       getVisitUserProfile(selectuserid);





        recyclerModels = new ArrayList<>();











        Log.i("","selectuserid================"+selectuserid);
        Log.i("","selectusername================"+selectusername);

       setTitle(selectusername.toString());


        //floatingButton = findViewById(R.id.floatingButton);
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

            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#e74c3c"));

        }



        bttn_addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              String friend= bttn_addfriend.getText().toString();

                if(friend.equals("Remove Friend"))
                {
                    String userid=selectuserid;
                    String strfriend="RemoveF";
                    addVisitUserFriend(userid,strfriend);
                }
                if(friend.equals("Accept"))
                {
                    String userid=selectuserid;
                    String strfriend="AF";
                    addVisitUserFriend(userid,strfriend);
                }
                if(friend.equals("Cancel Request"))
                {
                    String userid=selectuserid;
                    String strfriend="RemoveRequest";
                    addVisitUserFriend(userid,strfriend);
                }
                else
                {
                    String userid=selectuserid;
                    String strfriend="F1";
                    addVisitUserFriend(userid,strfriend);
                }
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

               // layPop1.setVisibility(View.VISIBLE);

                String block= bttn_block.getText().toString();
                if(block.equals("Blocked")) {
                    String userid = selectuserid;
                    String strBlock = "BR";
                    addVisitUserFriend(userid, strBlock);
                }
                else
                {
                    String userid = selectuserid;
                    String strBlock = "B1";
                    addVisitUserFriend(userid, strBlock);

                }
            }
        });
        bttn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layPop1.setVisibility(View.VISIBLE);

            }
        });

        bttn_Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String like = bttn_Like.getText().toString();
                if(like.equals("Liked"))
                {
                    String userid=selectuserid;
                    String strfriend="RemoveLike";
                    addVisitUserFriend(userid,strfriend);
                }
                else
                {
                    String userid=selectuserid;
                    String strfriend="L1";
                    addVisitUserFriend(userid,strfriend);
                }



            }
        });



        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layPop.setVisibility(View.GONE);

            }
        });
        btnClose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layPop1.setVisibility(View.GONE);

            }
        });


        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strmsg=edtmessage.getText().toString();


                if(strmsg.equals(""))
                {
                    Toasty.info(getApplicationContext(),"Type Your Message", Toast.LENGTH_SHORT).show();
                }
                else {
                    String userid=selectuserid;
                    String strLike="M1";
                    addVisitUserFriend(userid,strLike);
                }
            }
        });



      final  LinearLayout horizontbtn=(LinearLayout)findViewById(R.id.horizontalbutton);
        final  LinearLayout linearmain=(LinearLayout)findViewById(R.id.linearmain);
        final  LinearLayout linearmain2=(LinearLayout)findViewById(R.id.lmrjha);

       // final RelativeLayout relLayout = findViewById(R.id.relLayout);
       /* floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               *//* Intent i=new Intent(VisitUserProfileActivity.this,ImageSlider.class);
                startActivity(i);*//*
              // relLayout.setVisibility(View.VISIBLE);
               // horizontbtn.setVisibility(View.INVISIBLE);


             //  int width=800;
               // int height=800;
                //LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                //linearmain.setLayoutParams(parms);
            }
        });

        floatingButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //relLayout.setVisibility(View.INVISIBLE);
                //Intent i=new Intent(VisitUserProfileActivity.this,ImageSlider.class);
                //startActivity(i);
               // horizontbtn.setVisibility(View.VISIBLE);
                return true;

            }
        });*/


    }

    private void BeforeShowChanging() {

        bttn_addfriend=(Button)findViewById(R.id.bttn_addfriend);
        bttn_message=(Button)findViewById(R.id.bttn_message);
        bttn_block=(Button)findViewById(R.id.bttn_block);
        bttn_Like=(Button)findViewById(R.id.bttn_Like);
        bttn_info=(Button)findViewById(R.id.bttn_info);
        layPop=(LinearLayout) findViewById(R.id.layPop);
        layPop1=(LinearLayout) findViewById(R.id.layPop1);
        btnClose=(Button)findViewById(R.id.btnClose);
        btnClose1=(Button)findViewById(R.id.btnClose1);
        btnSendMsg=(Button)findViewById(R.id.btnSendMsg);




        edtmessage=(EditText) findViewById(R.id.edtmessage);



      final SharedPreferences msharedprefranceAdmin= getSharedPreferences(Configs.UserPrefrance,MODE_PRIVATE);
        cuserid=(msharedprefranceAdmin.getString("id",""));


        text_vistpf_name=(TextView)findViewById(R.id.text_vistpf_name);
        text_vistpf_city_state_country=(TextView)findViewById(R.id.text_vistpf_city_state_country);
        //cover_photo=(ImageView)findViewById(R.id.cover_photo);
        profile_pic=(ImageView)findViewById(R.id.profile_pic);

        prefrance=getSharedPreferences("selectedUser", Context.MODE_PRIVATE);
        selectuserid=(prefrance.getString("selectuserid",""));
        selectusername=(prefrance.getString("selectusername",""));
        selectusercity=(prefrance.getString("selectusercity",""));

        selecttype=(prefrance.getString("selecttype",""));
    }


    private void addVisitUserFriend(final String uid, final String AdBlkLikMsg) {


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_AddBlkMsglik,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","========"+response.toString());
                        if(response.equals("AlreadySent"))
                        {
                            int imgresource=R.drawable.minus;
                            bttn_addfriend.setCompoundDrawablesWithIntrinsicBounds(imgresource,0,0,0);

                            Toasty.info(getApplicationContext(),"Friend Already sent", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("F1"))
                        {
                            int imgresource=R.drawable.minus;
                            bttn_addfriend.setCompoundDrawablesWithIntrinsicBounds(imgresource,0,0,0);
                            bttn_addfriend.setBackgroundColor(Color.WHITE);
                            bttn_addfriend.setText("Cancel Request");


                            Toasty.info(getApplicationContext(),"Friend Request Sent", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("CancelRequest"))
                        {
                            int imgresource=R.drawable.adds;
                            bttn_addfriend.setCompoundDrawablesWithIntrinsicBounds(imgresource,0,0,0);
                            bttn_addfriend.setBackgroundColor(Color.WHITE);
                            bttn_addfriend.setText("Add friend");
                            Toasty.info(getApplicationContext(),"Request Cancelled", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("FADDER"))
                        {

                            int imgresource=R.drawable.minus;
                            bttn_addfriend.setCompoundDrawablesWithIntrinsicBounds(imgresource,0,0,0);

                            bttn_addfriend.setBackgroundColor(Color.WHITE);
                            Toasty.success(getApplicationContext(),"Friend Request Accepted", Toast.LENGTH_SHORT).show();
                            bttn_addfriend.setText("Remove Friend");

                        }

                        else if(response.equals("removeLike"))
                        {
                            int imgresource=R.drawable.like;
                            bttn_Like.setCompoundDrawablesWithIntrinsicBounds(imgresource,0,0,0);

                            bttn_Like.setBackgroundColor(Color.WHITE);
                            bttn_Like.setText("Like");

                            Toasty.info(getApplicationContext(),"Dislike", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("BlockRemove"))
                        {
                            int imgresource=R.drawable.bl;
                            bttn_block.setCompoundDrawablesWithIntrinsicBounds(imgresource,0,0,0);

                            bttn_block.setBackgroundColor(Color.WHITE);
                            bttn_block.setText("Block");


                            Toasty.info(getApplicationContext(),"remove blocked", Toast.LENGTH_SHORT).show();
                        }

                        else if(response.equals("AlreadyBlock"))
                        {

                            Toasty.info(getApplicationContext(),"Already Like", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("B1"))
                        {
                            int imgresource=R.drawable.lock;
                            bttn_block.setCompoundDrawablesWithIntrinsicBounds(imgresource,0,0,0);

                            bttn_block.setBackgroundColor(Color.WHITE);
                            bttn_block.setText("Blocked");


                            Toasty.info(getApplicationContext(),"Blocked", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("AlreadyLike"))
                        {

                            Toasty.info(getApplicationContext(),"Already Like", Toast.LENGTH_SHORT).show();
                        }

                        else if(response.equals("L1"))
                        {
                            int imgresource=R.drawable.dislike;
                            bttn_Like.setCompoundDrawablesWithIntrinsicBounds(imgresource,0,0,0);
                            bttn_Like.setBackgroundColor(Color.WHITE);
                            bttn_Like.setText("Liked");
                            Toasty.info(getApplicationContext(),"Liked", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("M1"))
                        {
                            Toasty.info(getApplicationContext(),"Message sent", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("1message"))
                        {
                            Toasty.error(getApplicationContext(),"For send more message you have to take plan", Toast.LENGTH_SHORT).show();
                        }

                        else if(response.equals("Removed Friend"))
                        {
                            int imgresource=R.drawable.minus;
                            bttn_addfriend.setCompoundDrawablesWithIntrinsicBounds(imgresource,0,0,0);

                            bttn_addfriend.setBackgroundColor(Color.WHITE);
                            Toasty.info(getApplicationContext(),"Removed Friend", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            Toasty.error(getApplicationContext(),"Try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","friendadddsfsadds=========error"+error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


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
                                Toasty.error(getApplicationContext(),"In-Valid email or password", Toast.LENGTH_SHORT).show();

                                text_vistpf_name.setText(selectusername);

                                Log.i("","selectusergenderselectusergenderselectusergender"+selectusergender);

                                if(selectusergender.equals("male") || selectusergender.equals("Male")  || !selectusergender.equals("null") || !selectusergender.equals(null))
                                {
                                    String url= Configs.profilephoto+"nophoto-men.jpg";
                                    Log.i("","gendergendergendergendergender======"+url);

                                   // Glide.with(getApplicationContext()).load(url).into(profile_pic);

                                }
                                else if(selectusergender.equals("female") || selectusergender.equals("Female"))
                                {
                                    String url= Configs.profilephoto+"nophoto-women.jpg";
                                    Log.i("","gendergendergendergendergender======"+url);

                                    //Glide.with(getApplicationContext()).load(url).into(cover_photo);
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
                                editor.putString("friend",userJson.getString("friend"));
                                editor.putString("likes",userJson.getString("likes"));
                                editor.putString("blocks",userJson.getString("blocks"));
                                editor.commit();


                                Log.i("","friendfriendfriendfriendfriend======="+userJson.getString("blocks"));


                                text_vistpf_name.setText(prefrance.getString("name",""));
                                text_vistpf_city_state_country.setText(prefrance.getString("city",""));

                                if(!prefrance.getString("profile_pic","").equals(""))
                                {

                                    //Glide.with(getApplicationContext()).load(url).into(profile_pic);
                                }
                                if(prefrance.getString("profile_pic","").equals("null") || prefrance.getString("profile_pic","").equals("NULL"))
                                {


                                    if(prefrance.getString("gender","").equals("male") || prefrance.getString("gender","").equals("Male"))
                                    {


                                    }
                                    else if(prefrance.getString("gender","").equals("female") || prefrance.getString("gender","").equals("Female"))
                                    {


                                       // Glide.with(getApplicationContext()).load(url).into(profile_pic);
                                    }
                                    else {

                                    }



                                }



                                if(!prefrance.getString("cover_pic","").equals(""))
                                {

                                }

                                if(!selecttype.equals(selectuserid+"FriendRequest"))
                                {
                                    if(prefrance.getString("friend","").equals("friend"))
                                    {
                                        int imgresource=R.drawable.adds;
                                        bttn_addfriend.setCompoundDrawablesWithIntrinsicBounds(imgresource,0,0,0);
                                        bttn_addfriend.setBackgroundColor(Color.WHITE);
                                        bttn_addfriend.setText("Remove Friend");
                                    }

                                    if(prefrance.getString("friend","").equals("friendRequest"))
                                    {
                                        int imgresource=R.drawable.minus;
                                        bttn_addfriend.setCompoundDrawablesWithIntrinsicBounds(imgresource,0,0,0);
                                        bttn_addfriend.setBackgroundColor(Color.WHITE);
                                        bttn_addfriend.setText("Cancel Request");
                                    }
                                }
                                else
                                {
                                    bttn_addfriend.setText("Accept");

                                }





                                if(prefrance.getString("likes","").equals("liked"))
                                {
                                    int imgresource=R.drawable.dislike;
                                    bttn_addfriend.setCompoundDrawablesWithIntrinsicBounds(imgresource,0,0,0);
                                    bttn_addfriend.setBackgroundColor(Color.WHITE);
                                    bttn_Like.setText("Liked");
                                }

                                if(prefrance.getString("blocks","").equals("blocked"))
                                {

                                    bttn_block.setText("Blocked");
                                }


                              /*  if(!prefrance.getString("friendReq","").equals("null"))
                                {
                                    bttn_addfriend.setText("Request sended");
                                }
*/

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
                params.put("cuser", cuserid);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new UserAboutFragment(), "    About Me    ");
        adapter.AddFragment(new UserLookingforFragment(), "    Looking For    ");
        adapter.AddFragment(new VisitUserProfileFragment(), "    Profile    ");

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

    private void VisitUserPhotos() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String photourl="http://www.brilldating.com/img/photo/";


                        try {

                            if(response.equals("0"))
                            {
                                Toasty.error(VisitUserProfileActivity.this,"In-Valid email or password",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                JSONObject obj = new JSONObject(response);
                                JSONObject userJson = obj.getJSONObject("0");

                                String photo1=userJson.getString("photo1");

                                String photo2=userJson.getString("photo2");

                                String photo3=userJson.getString("photo3");
                                String photo4=userJson.getString("photo4");
                                String photo5=userJson.getString("photo5");
                                String photo6=userJson.getString("photo6");
                                String photo7=userJson.getString("photo7");
                                String photo8=userJson.getString("photo8");
                                String photo9=userJson.getString("photo9");

                                String imgarr[]={photourl+photo1,photourl+photo2,photourl+photo3,photourl+photo4,photourl+photo5,photourl+photo6,photourl+photo7,photourl+photo8,photourl+photo9};

                                for (int i = 0; i < imgarr.length; i++)
                                {
                                    SliderUtils sliderUtils = new SliderUtils();
                                    sliderUtils.setSliderImageUrl(imgarr[i]);
                                    sliderImg.add(sliderUtils);
                                    Log.i("df", "photo1======sdfsdf========" + imgarr[i]);
                                }



                                Log.i("","photo1=============="+photo1);
                                Log.i("","photo1=============="+photo2);
                                Log.i("","photo1=============="+photo3);
                                Log.i("","photo1=============="+photo4);
                                Log.i("","photo1=============="+photo5);
                                Log.i("","photo1=============="+photo6);
                                Log.i("","photo1=============="+photo7);
                                Log.i("","photo1=============="+photo8);
                                Log.i("","photo1=============="+photo9);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        viewPagerAdapter1 = new com.example.brill.bdatingapp.ViewPagerAdapter(sliderImg, VisitUserProfileActivity.this);

                        viewPager1.setAdapter(viewPagerAdapter1);

                        dotscount = viewPagerAdapter1.getCount();
                        dots = new ImageView[dotscount];

                        for(int i = 0; i < dotscount; i++){

                            dots[i] = new ImageView(VisitUserProfileActivity.this);
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                            params.setMargins(8, 0, 8, 0);

                            sliderDotspanel.addView(dots[i], params);

                        }

                        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                }},


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(VisitUserProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","profile error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserPhotos", "yes");
                params.put("userid",selectuserid);

                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
