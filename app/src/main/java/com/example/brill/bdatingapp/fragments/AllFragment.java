package com.example.brill.bdatingapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.example.brill.bdatingapp.Configs;
import com.example.brill.bdatingapp.ConnectDetector;
import com.example.brill.bdatingapp.R;
import com.example.brill.bdatingapp.VolleySingleton;
import com.example.brill.bdatingapp.adapter.AdapterGetAllUserList;
import com.example.brill.bdatingapp.gattersatter.GatterGetAllUser;

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
public class AllFragment extends android.support.v4.app.Fragment {
    ProgressBar progressBar;
    JsonArrayRequest jsonArrayReq;
    RequestQueue requestQueue;

    private AdapterGetAllUserList recyclerAdapter;
    private ArrayList<GatterGetAllUser> recyclerModels;

    List<GatterGetAllUser> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerlayoutManager;

    private static final int LOAD_LIMIT = 15;
    private String lastId = "0";

    private boolean itShouldLoadMore = true;

   EditText edtSearchuser;

    Boolean isinternetpresent;
    ConnectDetector cd;

    String reslength="0";

    GridView grid;
    int nextdata=0;
    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_all, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        edtSearchuser=(EditText) rootView.findViewById(R.id.edtSearchuser);

       // cd=new ConnectDetector(getActivity().getApplicationContext());
      //  isinternetpresent=cd.isConnectToInternet();
     //   if(isinternetpresent) {
            recyclerModels = new ArrayList<>();
            recyclerAdapter = new AdapterGetAllUserList(recyclerModels,getActivity());
            GetDataAdapter1 = new ArrayList<>();


          RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.loadmore_recycler_view);

        recyclerlayoutManager=new GridLayoutManager(getActivity(),2);
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

        recyclerView = (RecyclerView) rootView.findViewById(R.id.loadmore_recycler_view);


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

        return rootView;
    }


    private void filter(String text) {

        ArrayList<GatterGetAllUser> filteredname=new ArrayList<>();

        for (GatterGetAllUser s : recyclerModels) {
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
        Log.i("","Configs.GetAllUser========"+ Configs.GetAllUser);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.GetAllUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","ggggggggggggggg"+response);
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

                                  String  userId, uname,gender,birthday,city,profile_pic,photo1,photo2,photo3,photo4,photo5,photo6,photo7,photo8,photo9;

                                    userId=jsonObject.getString("id");
                                    uname=jsonObject.getString("name");
                                    gender=jsonObject.getString("gender");
                                    birthday=jsonObject.getString("birthday");
                                    city=jsonObject.getString("city");
                                    profile_pic=jsonObject.getString("profile_pic");
                                    photo1=jsonObject.getString("photo1");
                                    photo2=jsonObject.getString("photo2");
                                    photo3=jsonObject.getString("photo3");
                                    photo4=jsonObject.getString("photo4");

                                    photo5=jsonObject.getString("photo5");

                                    photo6=jsonObject.getString("photo6");

                                    photo7=jsonObject.getString("photo7");

                                    photo8=jsonObject.getString("photo8");

                                    photo9=jsonObject.getString("photo9");

                                    recyclerModels.add(new GatterGetAllUser(userId, uname,gender,birthday,city,profile_pic,photo1,photo2,photo3,photo4,photo5,photo6,photo7,photo8,photo9));
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
                    //    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","profile error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("GetAllUser", "1");
                params.put("limit ",Integer.toString(nextdata) );

                Log.i("","next data profile error========="+nextdata);

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }




    /*private void getAllUsers() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);

        String JSON_URL=Configs.GetAllUser+"?limit=16&LoadData=yes";

        Log.i("","json links is======"+JSON_URL);

        jsonArrayReq = new JsonArrayRequest(JSON_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //   uploading.dismiss();
                        Log.i("", "00000000000response is --------------" + response);

                        reslength=Integer.toString(response.length());
                        progressBar.setVisibility(View.GONE);

                        if (response.length() <= 0) {
                            // no data available
                            Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                 lastId= jsonObject.getString("intId");
                                String fname= jsonObject.getString("fname");
                                String lname= jsonObject.getString("lname");
                                String sex= jsonObject.getString("sex");
                                String dob= jsonObject.getString("dob");
                                String country_code= jsonObject.getString("country");
                                String state_code= jsonObject.getString("state");
                                String city_code= jsonObject.getString("city");
                                String online_status= jsonObject.getString("online_status");

                                Log.i("","fname is=="+fname);


                                recyclerModels.add(new GatterGetAllUser(lastId, fname,lname,sex,dob,country_code,state_code,city_code,online_status));
                                recyclerAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        //    progressBar.setVisibility(View.GONE);

                        //  GetRecentNewsResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // progressBar.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        Log.i("","error============"+error);
                    }
                });

        requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonArrayReq);
    }*/
























    private void loadMoredta() {
         progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","convert in string gooooooooooooooo"+Integer.toString(nextdata));

        final String sendmore=Integer.toString(nextdata);
        itShouldLoadMore = false;
        Log.i("","loadmord datra"+sendmore);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.GetAllUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","string responsesdasdg======="+response);
                        progressBar.setVisibility(View.GONE);

                        if (response.equals("[]")) {
                            // we need to check this, to make sure, our dataStructure JSonArray contains
                            // something
                            Toasty.error(getActivity(), "no data available", Toast.LENGTH_SHORT).show();
                            return; // return will end the program at this point
                        }

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            Log.i("","string responsesdasdg======="+jsonArray.length());

                            if (jsonArray.length()==0) {
                                // we need to check this, to make sure, our dataStructure JSonArray contains
                                // something
                                Toasty.error(getActivity(), "no data available", Toast.LENGTH_SHORT).show();
                                return; // return will end the program at this point
                            }

                           else if (jsonArray.length() <= 0) {
                                // we need to check this, to make sure, our dataStructure JSonArray contains
                                // something
                                Toasty.error(getActivity(), "no data available", Toast.LENGTH_SHORT).show();
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

                                        String  userId, uname,gender,birthday,city,profile_pic,photo1,photo2,photo3,photo4,photo5,photo6,photo7,photo8,photo9;


                                        userId=jsonObject.getString("id");
                                        uname=jsonObject.getString("name");
                                        gender=jsonObject.getString("gender");
                                        birthday=jsonObject.getString("birthday");
                                        city=jsonObject.getString("city");
                                        profile_pic=jsonObject.getString("profile_pic");
                                        photo1=jsonObject.getString("photo1");
                                        photo2=jsonObject.getString("photo2");
                                        photo3=jsonObject.getString("photo3");
                                        photo4=jsonObject.getString("photo4");

                                        photo5=jsonObject.getString("photo5");

                                        photo6=jsonObject.getString("photo6");

                                        photo7=jsonObject.getString("photo7");

                                        photo8=jsonObject.getString("photo8");

                                        photo9=jsonObject.getString("photo9");

                                        recyclerModels.add(new GatterGetAllUser(userId, uname,gender,birthday,city,profile_pic,photo1,photo2,photo3,photo4,photo5,photo6,photo7,photo8,photo9));

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
                        Toasty.error(getActivity(), "Failed to load more, network error", Toast.LENGTH_SHORT).show();

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

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}
