package com.example.brill.bdatingapp;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.brill.bdatingapp.adapter.AdapterFriendList;
import com.example.brill.bdatingapp.gattersatter.GatterGetAllFriendList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendList extends AppCompatActivity {
    ProgressBar progressBar;
    JsonArrayRequest jsonArrayReq;
    RequestQueue requestQueue;

    private AdapterFriendList recyclerAdapter;
    private ArrayList<GatterGetAllFriendList> recyclerModels;

    List<GatterGetAllFriendList> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerlayoutManager;

    private static final int LOAD_LIMIT = 15;
    private String lastId = "0";

    private boolean itShouldLoadMore = true;

    //EditText edtSearchuser;

    Boolean isinternetpresent;
    ConnectDetector cd;

    String reslength="0";

    GridView grid;
    int nextdata=0;
    String sharid;
    SharedPreferences prefrance;
    public FriendList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        setContentView(R.layout.activity_friend);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefrance=getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
        sharid= prefrance.getString("id","");
        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        //edtSearchuser=(EditText) findViewById(R.id.edtSearchuser);

       // cd=new ConnectDetector(getApplicationContext());
      //  isinternetpresent=cd.isConnectToInternet();
     //   if(isinternetpresent) {
            recyclerModels = new ArrayList<>();
            recyclerAdapter = new AdapterFriendList(recyclerModels,this);
            GetDataAdapter1 = new ArrayList<>();


          RecyclerView recyclerView = (RecyclerView) findViewById(R.id.loadmore_recycler_view);

        recyclerlayoutManager=new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(recyclerlayoutManager);
            recyclerView.setHasFixedSize(true);
            //we can now set adapter to recyclerView;


            recyclerView.setAdapter(recyclerAdapter);

            GetDataAdapter1 = new ArrayList<>();

            getAllUsers();


        /*edtSearchuser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String username=editable.toString();

                filter(username.toLowerCase());
            }
        });*/



     //   }
     //   else
       // {
         //   Toast.makeText(getActivity().getApplicationContext(),"Connect To Internet",Toast.LENGTH_SHORT).show();

       // }



/*
        recyclerModels = new ArrayList<>();
        recyclerAdapter = new AdapterGetAllUserList(recyclerModels,getActivity());



        GetDataAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.loadmore_recycler_view);


        recyclerView.setHasFixedSize(true);

        recyclerlayoutManager = new LinearLayoutManager(getActivity());

        GetDataAdapter1 = new ArrayList<>();*/



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


    }


    private void filter(String text) {

        ArrayList<GatterGetAllFriendList> filteredname=new ArrayList<>();

        for (GatterGetAllFriendList s : recyclerModels) {
            if(s.getUname().toLowerCase().startsWith(text))
            {
                filteredname.add(s);
            }
        }
        recyclerAdapter.filterList(filteredname);

    }


    private void getAllUsers() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","Configs.GetAllUser========"+ Configs.GetAllFriend);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.GetAllMyFriend,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","ffffffffff=================="+response);
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

                                  String  userId, uname,gender,birthday,city,profile_pic;

                                    userId=jsonObject.getString("id");
                                    uname=jsonObject.getString("name");
                                    gender=jsonObject.getString("gender");
                                    birthday=jsonObject.getString("birthday");
                                    city=jsonObject.getString("city");
                                    profile_pic=jsonObject.getString("profile_pic");


                                    recyclerModels.add(new GatterGetAllFriendList(userId, userId, uname,gender,birthday,city,profile_pic));
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
                      //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","profile error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("GetFriend", "1");
                    params.put("cuserid",sharid);
                Log.i("","next data profile error========="+nextdata);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loadMoredta() {
         progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","convert in string gooooooooooooooo"+Integer.toString(nextdata));

        final String sendmore=Integer.toString(nextdata);
        itShouldLoadMore = false;
        Log.i("","loadmord datra"+sendmore);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.GetAllMyFriend,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","string responsesdasdg======="+response);
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

                                        String  userId, uname,gender,birthday,city,profile_pic;

                                        userId=jsonObject.getString("id");
                                        uname=jsonObject.getString("name");
                                        gender=jsonObject.getString("gender");
                                        birthday=jsonObject.getString("birthday");
                                        city=jsonObject.getString("city");
                                        profile_pic=jsonObject.getString("profile_pic");



                                        recyclerModels.add(new GatterGetAllFriendList(userId, userId, uname,gender,birthday,city,profile_pic));
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
                        Toasty.info(getApplicationContext(), "Failed to load more, network error", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                Log.i("","check sending data==============="+sendmore);

                params.put("moredta", "more");
                params.put("cuserid",sharid);
                params.put("next",Integer.toString(nextdata));
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }

}
