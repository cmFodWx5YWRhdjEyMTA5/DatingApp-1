package com.example.brill.bdatingapp;

import android.app.Activity;

import es.dmoral.toasty.Toasty;
import instamojo.library.InstapayListener;
import instamojo.library.InstamojoPay;
import instamojo.library.Config;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.crystal.crystalrangeseekbar.widgets.BubbleThumbSeekbar;
import com.example.brill.bdatingapp.adapter.AdapterQuickSearchUserList;
import com.example.brill.bdatingapp.gattersatter.GatterGetQuickUserList;

import org.florescu.android.rangeseekbar.RangeSeekBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdvanceSearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7;
    RadioButton radMale,radFemale;
    RadioGroup radGroup;
    BubbleThumbSeekbar rangeSeekBar;

    CheckBox chkOpenrelation,chkLivein,chkOneNight,chkFriendShip,chkOnlyDating,chkGetFood,chkLongTerm,chkGoOutParty,chkGoHiking,chkSecoundMarriage,chkGoForMovie,chkDrinkTeaCoffee;

    ProgressBar progressBar;
    JsonArrayRequest jsonArrayReq;
    RequestQueue requestQueue;

    private AdapterQuickSearchUserList recyclerAdapter;
    private ArrayList<GatterGetQuickUserList> recyclerModels;

    List<GatterGetQuickUserList> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerlayoutManager;

    private static final int LOAD_LIMIT = 15;
    private String lastId = "0";

    private boolean itShouldLoadMore = true;

    Button edtSearchuser,btnQSearch;

    Boolean isinternetpresent;
    ConnectDetector cd;

    String reslength="0";

    GridView grid;
    int nextdata=0;
    String sharid;
    SharedPreferences prefrance;

    LinearLayout layAdvSearch;

    String seekingGender="";


    JSONArray resultCountry,resultState,resultCity;
    private ArrayList<String> countrys,states,citys;

    Spinner spinner_edit_gen,spinner_edit_country,spinner_edit_state,spinner_edit_city,spinner_edit_lookingfor,spinner_edit_relationship,spinner_edit_smoking,spinner_edit_drinking,spinner_edit_qualification,spinner_edit_language,spinner_edit_minage,spinner_edit_maxage,spinner_edit_eyecolor,spinner_edit_skincolor,spinner_edit_workas,spinner_edit_interests;

    //RangeSeekBar rangeSeekBar;
    String upopenrel,uplivein,uponlydating,upfriendship,uplongterm,uponenight,uptea,upfood,upparty,upsecmarrage,upmovie,uphiking;

    public AdvanceSearchActivity() {
        // Required empty public constructor
    }

    String sharname,strGender,sharphone,sharUserphoto,strAgeCrit,strMinAge,strMaxAge,strAge,strEdu,strDob,strKnowLang,strCountry,strIntrest,strState,strSmoking,strCity,strDrink,strRelation,strEyeColor,strLookfor,strSkinColor,strLookRel,strWorkas,strAbout,strParent;

    

    
    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
       pay.put("send_sms", true);
      pay.put("send_email", true);
 } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }
    
    InstapayListener listener;

    
    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                Toasty.info(getApplicationContext(), response, Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onFailure(int code, String reason) {
                Toasty.error(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advancesearch);
        // Call the function callInstamojo to start payment here

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefrance=getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
        sharid= prefrance.getString("id","");

        findviewbyidmethod();

        spinner_edit_country.setOnItemSelectedListener(this);

        spinner_edit_state.setOnItemSelectedListener(this);

        spinner_edit_city.setOnItemSelectedListener(this);


        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        edtSearchuser=(Button) findViewById(R.id.edtadvsearch);
        layAdvSearch=(LinearLayout) findViewById(R.id.layAdvSearch);
        btnQSearch=(Button) findViewById(R.id.btnAdvanceSearch);
        rangeSeekBar=(BubbleThumbSeekbar) findViewById(R.id.rangseek);
        recyclerModels = new ArrayList<>();
        recyclerAdapter = new AdapterQuickSearchUserList(recyclerModels,this);
        GetDataAdapter1 = new ArrayList<>();


        recyclerView = (RecyclerView) findViewById(R.id.loadmore_recycler_view);

        recyclerlayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(recyclerlayoutManager);
        recyclerView.setHasFixedSize(true);
        //we can now set adapter to recyclerView;


        recyclerView.setAdapter(recyclerAdapter);

        GetDataAdapter1 = new ArrayList<>();

        getAllSearchUsers();

        edtSearchuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetCountryData();

                countrys=new ArrayList<String>();
                states=new ArrayList<String>();
                citys=new ArrayList<String>();

                layAdvSearch.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });

        btnQSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scountry= spinner_edit_country.getSelectedItem().toString();
                String sstate= spinner_edit_state.getSelectedItem().toString();
                String scity= spinner_edit_city.getSelectedItem().toString();
                String minage= rangeSeekBar.getSelectedMinValue().toString();
                String maxage= rangeSeekBar.getSelectedMaxValue().toString();
                String seeking=seekingGender;




                if(chkOpenrelation.isChecked())
                {
                    upopenrel="openrelationship,";
                }
                else{
                    upopenrel="";
                }

                if(chkLivein.isChecked())
                {
                    uplivein="livein,";
                }
                else {
                    uplivein="";
                }
                if(chkOneNight.isChecked())
                {
                    uponenight="onenightstand,";
                }
                else {
                    uponenight="";
                }
                if(chkFriendShip.isChecked())
                {
                    upfriendship="friendship,";
                }
                else {
                    upfriendship="";
                }
                if(chkOnlyDating.isChecked())
                {
                    uponlydating="onlydating,";
                }
                else {
                    uponlydating="";
                }
                if(chkGetFood.isChecked())
                {
                    upfood="food,";
                }
                else {
                    upfood="";
                }
                if(chkLongTerm.isChecked())
                {
                    uplongterm="longterm,";
                }
                else {
                    uplongterm="";
                }
                if(chkGoOutParty.isChecked())
                {
                    upparty="party,";
                }
                else {
                    upparty="";
                }
                if(chkGoHiking.isChecked())
                {
                    uphiking="hiking,";
                }
                else {
                    uphiking="";
                }
                if(chkSecoundMarriage.isChecked())
                {
                    upsecmarrage="secondmarriage,";
                }
                else {
                    upsecmarrage="";
                }
                if(chkGoForMovie.isChecked())
                {
                    upmovie="movie,";
                }
                else {
                    upmovie="";
                }
                if(chkDrinkTeaCoffee.isChecked())
                {
                    uptea="tea";
                }
                else {
                    uptea="";
                }







                if(scountry.equals(""))
                {
                    Toasty.info(getApplicationContext(),"Select You Seeking gender For",Toast.LENGTH_SHORT).show();

                }
               else if(sstate.equals(""))
                {
                    Toasty.info(getApplicationContext(),"Select You Seeking gender For",Toast.LENGTH_SHORT).show();

                }
                else if(scity.equals(""))
                {
                    Toasty.info(getApplicationContext(),"Select You Seeking gender For",Toast.LENGTH_SHORT).show();

                }
                else if(minage.equals(""))
                {
                    Toasty.info(getApplicationContext(),"Select You Seeking gender For",Toast.LENGTH_SHORT).show();

                }
                else if(maxage.equals(""))
                {
                    Toasty.info(getApplicationContext(),"Select You Seeking gender For",Toast.LENGTH_SHORT).show();

                }
                else  if(seeking.equals(""))
                {
                    Toasty.info(getApplicationContext(),"Select You Seeking gender For",Toast.LENGTH_SHORT).show();

                }
                 else
                {

                    Log.i("","SearchUsers==================scountry"+scountry);
                    Log.i("","SearchUsers==================sstate"+sstate);
                    Log.i("","SearchUsers==================scity"+scity);
                    Log.i("","SearchUsers==================minage"+minage);
                    Log.i("","SearchUsers==================maxage"+maxage);
                    Log.i("","SearchUsers==================seeking"+seeking);
                    Log.i("","SearchUsers==================upopenrel"+upopenrel);
                    Log.i("","SearchUsers==================uplivein"+uplivein);
                    Log.i("","SearchUsers==================uponenight"+uponenight);



                    filter(scountry,sstate,scity,minage,maxage,seeking,upopenrel,uplivein,uponenight,upfriendship,uponlydating,upfood,uplongterm,upparty,uphiking,upsecmarrage,upmovie,uptea);
                }


            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            // for this tutorial, this is the ONLY method that we need, ignore the rest
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Recycle view scrolling downwards...
                    // this if statement detects when user reaches the end of recyclerView, this is only time we should load more
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        // remember "!" is the same as "== false"
                        // here we are now allowed to load more, but we need to be careful
                        // we must check if itShouldLoadMore variable is true [unlocked]
                        if (itShouldLoadMore) {
                            loadMoredta();
                        }
                    }

                }
            }
        });


        onClickMethod();

    }

    private void onClickMethod() {
        radMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radMale.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                radFemale.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));

                radMale.setTextColor(getResources().getColor(R.color.colorWhite));
                radFemale.setTextColor(getResources().getColor(R.color.colorPrimary));

                seekingGender="male";
            }
        });
        radFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radFemale.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                radMale.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));

                radFemale.setTextColor(getResources().getColor(R.color.colorWhite));
                radMale.setTextColor(getResources().getColor(R.color.colorPrimary));
                seekingGender="female";
            }
        });

      /*  radGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radMale.isChecked()==true)
                {
                }
                else if(radFemale.isChecked()==true)
                {
                    radMale.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));

                }
                else {
                    radMale.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));

                }

            }
        });*/

        chkOpenrelation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chkOpenrelation.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    chkOpenrelation.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    chkOpenrelation.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    chkOpenrelation.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });

        chkLivein.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chkLivein.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    chkLivein.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    chkLivein.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    chkLivein.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });
        chkOneNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chkOneNight.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    chkOneNight.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    chkOneNight.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    chkOneNight.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });
        chkFriendShip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chkFriendShip.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    chkFriendShip.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    chkFriendShip.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    chkFriendShip.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });

        chkOnlyDating.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chkOnlyDating.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    chkOnlyDating.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    chkOnlyDating.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    chkOnlyDating.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });
        chkGetFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chkGetFood.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    chkGetFood.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    chkGetFood.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    chkGetFood.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });

        chkLongTerm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chkLongTerm.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    chkLongTerm.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    chkLongTerm.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    chkLongTerm.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });
        chkGoOutParty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chkGoOutParty.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    chkGoOutParty.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    chkGoOutParty.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    chkGoOutParty.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });

        chkGoHiking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chkGoHiking.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    chkGoHiking.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    chkGoHiking.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    chkGoHiking.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });
        chkSecoundMarriage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chkSecoundMarriage.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    chkSecoundMarriage.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    chkSecoundMarriage.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    chkSecoundMarriage.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });



        chkGoForMovie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chkGoForMovie.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    chkGoForMovie.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    chkGoForMovie.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    chkGoForMovie.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });
        chkDrinkTeaCoffee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chkDrinkTeaCoffee.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    chkDrinkTeaCoffee.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    chkDrinkTeaCoffee.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    chkDrinkTeaCoffee.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });


    }

    private void findviewbyidmethod() {
        radGroup=(RadioGroup) findViewById(R.id.radGroup);
        radMale=(RadioButton)findViewById(R.id.radMale);
        radFemale=(RadioButton)findViewById(R.id.radFemale);

        chkOpenrelation=(CheckBox) findViewById(R.id.chkOpenrelation);

                chkLivein=(CheckBox)findViewById(R.id.chkLivein);


        chkOneNight=(CheckBox) findViewById(R.id.chkOneNight);
                chkFriendShip=(CheckBox) findViewById(R.id.chkFriendShip);
        chkOnlyDating=(CheckBox) findViewById(R.id.chkOnlyDating);
                chkGetFood=(CheckBox) findViewById(R.id.chkGetFood);
        chkLongTerm=(CheckBox) findViewById(R.id.chkLongTerm);
                chkGoOutParty=(CheckBox) findViewById(R.id.chkGoOutParty);
        chkGoHiking=(CheckBox) findViewById(R.id.chkGoHiking);
                chkSecoundMarriage=(CheckBox) findViewById(R.id.chkSecoundMarriage);
        chkGoForMovie=(CheckBox) findViewById(R.id.chkGoForMovie);
                chkDrinkTeaCoffee=(CheckBox) findViewById(R.id.chkDrinkTeaCoffee);



        spinner_edit_country=(Spinner)findViewById(R.id.spinner_edit_country);
        spinner_edit_state=(Spinner)findViewById(R.id.spinner_edit_state);
        spinner_edit_city=(Spinner)findViewById(R.id.spinner_edit_city);





    }


    private void getAllSearchUsers() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","Configs.GetAllUser========"+Configs.GetAllUser);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.GetAllQuickSearch,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","getAllSearchUsers=================="+response);
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            nextdata=jsonArray.length();
                            Log.i("","getAllUsersresponser============"+jsonArray.length());


                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    //  lastId= jsonObject.getString("id");

                                    //  Log.i("","ggggggggggggggg"+lastId);

                                    String  userId, uname,gender,birthday,city,profile_pic,looking,minage,maxage;

                                    userId=jsonObject.getString("id");
                                    uname=jsonObject.getString("name");
                                    gender=jsonObject.getString("gender");
                                    birthday=jsonObject.getString("birthday");
                                    city=jsonObject.getString("city");
                                    profile_pic=jsonObject.getString("profile_pic");


                                    recyclerModels.add(new GatterGetQuickUserList(userId, uname,gender,birthday,city,profile_pic));
                                    recyclerAdapter.notifyDataSetChanged();
                                }
                                catch (JSONException e)
                                {

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
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","profile error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("GetAllUser", "1");

                Log.i("","sharidsharidsharidsharidsharid========="+sharid);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    /*private void loadMoredta() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","convert in string gooooooooooooooo"+Integer.toString(nextdata));

        final String sendmore=Integer.toString(nextdata);
        itShouldLoadMore = false;
        Log.i("","loadmord datra"+sendmore);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.GetAllQuickSearch,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","quicksearchis======="+response);
                        progressBar.setVisibility(View.GONE);

                        if (response.equals("[]")) {
                            // we need to check this, to make sure, our dataStructure JSonArray contains
                            // something
                            Toast.makeText(getApplicationContext(), "no data available", Toast.LENGTH_SHORT).show();
                            return; // return will end the program at this point
                        }
                        if(response.equals("You Have No Friend Request"))
                        {
                            Toast.makeText(getApplicationContext(), "No New Friend Request", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            Log.i("","string responsesdasdg======="+jsonArray.length());

                            if (jsonArray.length()==0) {
                                // we need to check this, to make sure, our dataStructure JSonArray contains
                                // something
                                Toast.makeText(getApplicationContext(), "no data available", Toast.LENGTH_SHORT).show();
                                return; // return will end the program at this point
                            }

                            else if (jsonArray.length() <= 0) {
                                // we need to check this, to make sure, our dataStructure JSonArray contains
                                // something
                                Toast.makeText(getApplicationContext(), "no data available", Toast.LENGTH_SHORT).show();
                                return; // return will end the program at this point
                            }
                            else
                            {

                                int getNextLength= jsonArray.length();

                                Log.i("","loadMoredtaggggggggggggggg"+jsonArray.length());

                                nextdata=nextdata+getNextLength;


                                Log.i("","next data================"+nextdata);

                                itShouldLoadMore = true;



                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        //  lastId= jsonObject.getString("id");

                                        //  Log.i("","ggggggggggggggg"+lastId);

                                        String  userId, uname,gender,birthday,city,profile_pic,looking,minage,maxage;

                                        userId=jsonObject.getString("id");
                                        uname=jsonObject.getString("name");
                                        gender=jsonObject.getString("gender");
                                        birthday=jsonObject.getString("birthday");
                                        city=jsonObject.getString("city");
                                        profile_pic=jsonObject.getString("profile_pic");
                                        looking=jsonObject.getString("looking");
                                        minage=jsonObject.getString("minage");
                                        maxage=jsonObject.getString("maxage");


                                        recyclerModels.add(new GatterGetQuickUserList(userId, uname,gender,birthday,city,profile_pic,looking,0,maxage));
                                        recyclerAdapter.notifyDataSetChanged();
                                    }
                                    catch (JSONException e) {
                                        e.printStackTrace();
                                    }

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
                        progressBar.setVisibility(View.GONE);
                        itShouldLoadMore = true;
                        Toast.makeText(getApplicationContext(), "Failed to load more, network error", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                Log.i("","check sending data==============="+sendmore);

                params.put("moredta", "more");
                params.put("cuserid",sharid);
                params.put("mored",sendmore);

                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }*/

    private void loadMoredta() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","convert in string gooooooooooooooo"+Integer.toString(nextdata));

        final String sendmore=Integer.toString(nextdata);
        itShouldLoadMore = false;
        Log.i("","loadmord datra"+sendmore);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.GetAllQuickSearch,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","loadmordsdsastring responsesdasdg======="+response);
                        progressBar.setVisibility(View.GONE);

                        if (response.equals("[]")) {
                            // we need to check this, to make sure, our dataStructure JSonArray contains
                            // something
                            Toasty.info(getApplicationContext(), "no data available", Toast.LENGTH_SHORT).show();
                            return; // return will end the program at this point
                        }

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            Log.i("","string responsesdasdg======="+jsonArray.length());

                            if (jsonArray.length()==0) {
                                // we need to check this, to make sure, our dataStructure JSonArray contains
                                // something
                                Toasty.info(getApplicationContext(), "no data available", Toast.LENGTH_SHORT).show();
                                return; // return will end the program at this point
                            }

                            else if (jsonArray.length() <= 0) {
                                // we need to check this, to make sure, our dataStructure JSonArray contains
                                // something
                                Toasty.info(getApplicationContext(), "no data available", Toast.LENGTH_SHORT).show();
                                return; // return will end the program at this point
                            }
                            else
                            {

                                int getNextLength= jsonArray.length();

                                Log.i("","loadMoredtaggggggggggggggg"+jsonArray.length());

                                nextdata=nextdata+getNextLength;


                                Log.i("","next data================"+nextdata);

                                itShouldLoadMore = true;



                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        //  lastId= jsonObject.getString("id");

                                        //  Log.i("","ggggggggggggggg"+lastId);

                                        String  userId, uname,gender,birthday,city,profile_pic,looking,minage,maxage;

                                        userId=jsonObject.getString("id");
                                        uname=jsonObject.getString("name");
                                        gender=jsonObject.getString("gender");
                                        birthday=jsonObject.getString("birthday");
                                        city=jsonObject.getString("city");
                                        profile_pic=jsonObject.getString("profile_pic");

                                        recyclerModels.add(new GatterGetQuickUserList(userId, uname,gender,birthday,city,profile_pic));
                                        recyclerAdapter.notifyDataSetChanged();


                                    }
                                    catch (JSONException e) {
                                        e.printStackTrace();
                                    }

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
                        progressBar.setVisibility(View.GONE);
                        itShouldLoadMore = true;
                        Toasty.error(getApplicationContext(), "Failed to load more, network error", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                Log.i("","check sending data==============="+sendmore);

                params.put("moredta", "more");
                params.put("mored", sendmore);





                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void filter(final String scountry, final String sstate, final String scity, final String minage, final String maxage, final String seeking, final String upopenrel, final String uplivein, final String uponenight, final String upfriendship, final String uponlydating, final String upfood, final String uplongterm, final String upparty, final String uphiking, final String upsecmarrage, final String upmovie, final String uptea) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.GetAllQuickSearch,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","advanceUsers=================="+response);
                        recyclerModels.clear();
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            nextdata=jsonArray.length();
                            Log.i("","getAllUsersresponser============"+jsonArray.length());


                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    //  lastId= jsonObject.getString("id");

                                    //  Log.i("","ggggggggggggggg"+lastId);

                                    String  userId, uname,gender,birthday,city,profile_pic,looking,minage,maxage;

                                    userId=jsonObject.getString("id");
                                    uname=jsonObject.getString("name");
                                    gender=jsonObject.getString("gender");
                                    birthday=jsonObject.getString("birthday");
                                    city=jsonObject.getString("city");
                                    profile_pic=jsonObject.getString("profile_pic");


                                    recyclerModels.add(new GatterGetQuickUserList(userId, uname,gender,birthday,city,profile_pic));
                                    recyclerAdapter.notifyDataSetChanged();
                                    recyclerView.setVisibility(View.VISIBLE);
                                    layAdvSearch.setVisibility(View.GONE);
                                }
                                catch (JSONException e)
                                {

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
                      //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","profile error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("GetAdvanceFilterdata", "1");

                params.put("Getscountry", scountry);
                params.put("Getsstate", sstate);
                params.put("Getscity", scity);
                params.put("Getminage", minage);
                params.put("Getmaxage", maxage);
                params.put("Getseeking", seeking);
                params.put("Getupopenrel",upopenrel);
                params.put("Getuplivein", uplivein);
                params.put("Getuponenight",uponenight);
                params.put("Getupfriendship", upfriendship);
                params.put("Getuponlydating", uponlydating);
                params.put("Getupfood", upfood);
                params.put("Getuplongterm", uplongterm);
                params.put("Getupparty", upparty);
                params.put("Getuphiking", uphiking);
                params.put("Getupsecmarrage", upsecmarrage);
                params.put("Getupmovie", upmovie);
                params.put("Getuptea", uptea);





                Log.i("","sharidsharidsharidsharidsharid========="+sharid);

                return params;

            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



    private void GetCountryData() {
        cd=new ConnectDetector(getApplicationContext());
        isinternetpresent=cd.isConnectToInternet();

        if(isinternetpresent)
        {

            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_UserProfieGetUpdate,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // progressBar.setVisibility(View.GONE);
                            Log.i("","GetCountryData================"+response.toString());

                            JSONObject JSObj = null;

                            try {

                                if(response.equals("0"))
                                {
                                    Toast.makeText(getApplicationContext(),"In-Valid email or password",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                    JSObj = new JSONObject(response);

                                    resultCountry = JSObj.getJSONArray("country");
                                    countrys.clear();

                                    Log.i("","sign us response get country data response"+response);

                                    Log.i("","sign us response get country dataresult"+resultCountry);

                                    //Calling method getStudents to get the students from the JSON Array
                                    getCountry(resultCountry);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                            Log.i("","profile error========="+error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Getcountry", "yes");

                    return params;
                }
            };

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
        else {
            Toast.makeText(getApplicationContext(),"Connect To Internet",Toast.LENGTH_SHORT).show();

        }



    }


    private void getCountry(JSONArray arr) {
        //Traversing through all the items in the json array
        for(int i=0;i<arr.length();i++){
            try {
                JSONObject json = null;
                json = arr.getJSONObject(i);
                //Getting json object
                String  jname=json.getString("name");
                Log.i("","json response adfsdfsadfsd"+jname);
                //Adding the name of the student to array list

                countrys.add(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner_edit_country.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, countrys));


        if (strCountry != null) {
            int alreadySelectCountry =countrys.indexOf(strCountry);

            spinner_edit_country.setSelection(alreadySelectCountry);
        }





    }


    private void GetStateData(final String countryitem) {

        states.clear();
        cd=new ConnectDetector(getApplicationContext());
        isinternetpresent=cd.isConnectToInternet();

        if(isinternetpresent)
        {
            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_UserProfieGetUpdate,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // progressBar.setVisibility(View.GONE);
                            Log.i("","Getstatedata================"+response.toString());

                            JSONObject JSObj = null;

                            try {

                                if(response.equals("0"))
                                {
                                    Toast.makeText(getApplicationContext(),"In-Valid email or password",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                    JSObj = new JSONObject(response);
                                    resultState = JSObj.getJSONArray("state");


                                    Log.i("","sign us response get country data response"+response);

                                    Log.i("","sign us response get country dataresult"+resultState);

                                    //Calling method getStudents to get the students from the JSON Array
                                    getState(resultState);

                                }








                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                          //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                            Log.i("","profile error========="+error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("GetState", "yes");
                    params.put("sendCountryName", countryitem);

                    Log.i("","asdsadfcountryitem====="+countryitem);

                    return params;
                }
            };

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
        else {
            Toast.makeText(getApplicationContext(),"Connect To Internet",Toast.LENGTH_SHORT).show();

        }



    }

    private void getState(JSONArray arr) {

        for(int i=0;i<arr.length();i++){
            try {
                JSONObject json = null;
                json = arr.getJSONObject(i);
                //Getting json object
                String  jname=json.getString("name");
                Log.i("","json response adfsdfsadfsd"+jname);
                //Adding the name of the student to array list

                states.add(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //Setting adapter to show the items in the spinner
        spinner_edit_state.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, states));

        if (strState != null) {
            int alreadySelectState =states.indexOf(strState);

            spinner_edit_state.setSelection(alreadySelectState);
        }
    }


    private void GetCityData(final String stateitem) {
        citys.clear();
        cd=new ConnectDetector(getApplicationContext());
        isinternetpresent=cd.isConnectToInternet();

        if(isinternetpresent)
        {
            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_UserProfieGetUpdate,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // progressBar.setVisibility(View.GONE);
                            Log.i("","Getstatedata================"+response.toString());

                            JSONObject JSObj = null;

                            try {

                                if(response.equals("0"))
                                {
                                    Toast.makeText(getApplicationContext(),"In-Valid email or password",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                    JSObj = new JSONObject(response);
                                    resultCity = JSObj.getJSONArray("city");


                                    Log.i("","sign us response get country data response"+response);

                                    Log.i("","sign us response get country dataresult"+resultState);

                                    //Calling method getStudents to get the students from the JSON Array
                                    getCity(resultCity);

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
                    params.put("GetCity", "yes");
                    params.put("sendStateName", stateitem);

                    Log.i("","asdsadfcountryitem====="+stateitem);

                    return params;
                }
            };

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
        else {
            Toast.makeText(getApplicationContext(),"Connect To Internet",Toast.LENGTH_SHORT).show();

        }



    }

    private void getCity(JSONArray arr) {

        for(int i=0;i<arr.length();i++){
            try {
                JSONObject json = null;
                json = arr.getJSONObject(i);
                //Getting json object
                String  jname=json.getString("name");
                Log.i("","json response adfsdfsadfsd"+jname);
                //Adding the name of the student to array list

                citys.add(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner_edit_city.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, citys));
        if (strCity != null) {
            int alreadySelectcity =citys.indexOf(strCity);



            Log.i("","json response adfsdfsadfsd"+alreadySelectcity);

            spinner_edit_city.setSelection(alreadySelectcity);
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        // On selecting a spinner item



        Log.i("","postion of selected item======"+position);
        // Showing selected spinner item
        //   Toast.makeText(parent.getContext(), "Selected:" + item, Toast.LENGTH_LONG).show();




        switch(parent.getId()) {

         /*   spinner_edit_country=(Spinner)rootitem.findViewById(R.id.spinner_edit_country);
            spinner_edit_state=(Spinner)rootitem.findViewById(R.id.spinner_edit_state);
            spinner_edit_city=(
            */

            case R.id.spinner_edit_country:
                // Region r = (Region)sregions.getSelectedItem();

                String item = parent.getItemAtPosition(position).toString();
                Log.d("","regionid:country" + item);

                String countryitem=item;

                GetStateData(countryitem);
               /* selectPosition=position;
                if(selectPosition!=0)
                {
                    selectPosition=position+1;
                    GetStateData();

                    Log.d("form","regionid:country position is=====" + selectPosition);
                }*/
                Log.d("form","regionid:country position is=====" + position);
                break;
            case R.id.spinner_edit_state:
                Log.d("form","state id:");
                String items = parent.getItemAtPosition(position).toString();
                Log.d("form","regionid: state" + items);
                GetCityData(items);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }

}
