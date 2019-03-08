package com.example.brill.bdatingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.brill.bdatingapp.adapter.AdapterQuickSearchUserList;
import com.example.brill.bdatingapp.gattersatter.GatterGetQuickUserList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class SearchActivity extends AppCompatActivity {

    Spinner spinner_iam, spinner_seeking, spinner_from, spinner_to;

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

    Button edtSearchuser,btnQSearch,button_cancel;

    Boolean isinternetpresent;
    ConnectDetector cd;

    String reslength="0";

    GridView grid;
    int nextdata=0;
    String sharid;
    SharedPreferences prefrance;
    CardView searchcardview;
    public SearchActivity() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        prefrance=getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
        sharid= prefrance.getString("id","");

        addAllSpinner();
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        edtSearchuser=(Button) findViewById(R.id.edtSearchuser);
        searchcardview=(CardView) findViewById(R.id.searchcardview);
        btnQSearch=(Button) findViewById(R.id.btnQSearch);
        // cd=new ConnectDetector(getApplicationContext());
        //  isinternetpresent=cd.isConnectToInternet();
        //   if(isinternetpresent) {
        recyclerModels = new ArrayList<>();
        recyclerAdapter = new AdapterQuickSearchUserList(recyclerModels,this);
        GetDataAdapter1 = new ArrayList<>();


         recyclerView = (RecyclerView) findViewById(R.id.loadmore_recycler_view);
         button_cancel = (Button)findViewById(R.id.button_cancel);

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
                searchcardview.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });

        btnQSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String seeking= spinner_seeking.getSelectedItem().toString();
                String from= spinner_from.getSelectedItem().toString();
                String to= spinner_to.getSelectedItem().toString();

                if(seeking.equals("Gender?"))
                {
                    Toasty.info(getApplicationContext(),"Select You Seeking For",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    filter(seeking,from,to);
                }


            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(searchcardview.getVisibility()==View.VISIBLE)
                {
                    searchcardview.setVisibility(View.INVISIBLE);

                }
            }
        });
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


    private void addAllSpinner() {

        spinner_iam = (Spinner) findViewById(R.id.spinner_iam);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.list_item_name, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_iam.setAdapter(adapter1);


        spinner_seeking = (Spinner) findViewById(R.id.spinner_seeking);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.login_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_seeking.setAdapter(adapter2);


        spinner_from = (Spinner) findViewById(R.id.spinner_from);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.list_from, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_from.setAdapter(adapter3);


        spinner_to = (Spinner) findViewById(R.id.spinner_to);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.list_to, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_to.setAdapter(adapter4);


    }

    private void getAllSearchUsers() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","Configs.GetAllUser========"+ Configs.GetAllUser);
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

    private void filter(final String seeking, final String from, final String to) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","Configs.GetAllUser========"+ Configs.GetAllUser);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.GetAllQuickSearch,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","getAllSearchUsers=================="+response);
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
                                    searchcardview.setVisibility(View.GONE);
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
                params.put("GetFilterdata", "1");

                params.put("Getseeking", seeking);
                params.put("Getfrom", from);
                params.put("Getto", to);

                Log.i("","sharidsharidsharidsharidsharid========="+sharid);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }

}








