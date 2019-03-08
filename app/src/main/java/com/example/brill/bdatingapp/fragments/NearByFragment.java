package com.example.brill.bdatingapp.fragments;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.brill.bdatingapp.adapter.AdapterGetNearBy;
import com.example.brill.bdatingapp.gattersatter.GatterNearBy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearByFragment extends android.support.v4.app.Fragment {
    ProgressBar progressBar;
    JsonArrayRequest jsonArrayReq;
    RequestQueue requestQueue;

    private AdapterGetNearBy recyclerAdapter;
    private ArrayList<GatterNearBy> recyclerModels;


    List<GatterNearBy> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerlayoutManager;




    private static final int LOAD_LIMIT = 15;
    private String lastId = "0";

    private boolean itShouldLoadMore = true;

    Boolean isinternetpresent;
    ConnectDetector cd;

    GridView grid;
    String SharedintId,SharedPassword;

    int nextdata=0;
    String citycode;
    //EditText edtSearchuser;
    public NearByFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=  inflater.inflate(R.layout.fragment_near_by, container, false);

        final SharedPreferences msharedprefranceAdmin= getActivity().getSharedPreferences(Configs.UserPrefrance,MODE_PRIVATE);
        citycode =(msharedprefranceAdmin.getString("city_code",""));



        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        //edtSearchuser=(EditText) rootView.findViewById(R.id.edtSearchuser);

        // cd=new ConnectDetector(getActivity().getApplicationContext());
        //  isinternetpresent=cd.isConnectToInternet();
        //   if(isinternetpresent) {
        recyclerModels = new ArrayList<>();
        recyclerAdapter = new AdapterGetNearBy(recyclerModels,getActivity());
        GetDataAdapter1 = new ArrayList<>();


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.loadmore_recycler_view);

        recyclerlayoutManager=new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(recyclerlayoutManager);
        recyclerView.setHasFixedSize(true);
        //we can now set adapter to recyclerView;


        recyclerView.setAdapter(recyclerAdapter);

        GetDataAdapter1 = new ArrayList<>();

        getAllNearByUser();
        //   }
        //   else
        // {
        //   Toast.makeText(getActivity().getApplicationContext(),"Connect To Internet",Toast.LENGTH_SHORT).show();

        // }

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

    private void getAllNearByUser() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","nearby data ========"+ Configs.GetAllNearby);

        Log.i("","nearby data ========"+citycode);
        Log.i("","nearby data ========"+nextdata);

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.GetAllNearby,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","nearby data is===========response"+response);
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


                                    recyclerModels.add(new GatterNearBy(userId, uname,gender,birthday,city,profile_pic));
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
                        Toasty.error(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","nearby error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("GetNearby", "1");
                params.put("fromcity", citycode);



                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }



    private void loadMoredta() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","convert in string gooooooooooooooo"+Integer.toString(nextdata));

        final String sendmore=Integer.toString(nextdata);
        itShouldLoadMore = false;
        Log.i("","loadmord datra"+sendmore);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.GetAllNearby,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","string responsesdasdg======="+response);
                        progressBar.setVisibility(View.GONE);

                        if (response.equals("[]")) {
                            // we need to check this, to make sure, our dataStructure JSonArray contains
                            // something
                            Toasty.info(getActivity(), "no data available", Toast.LENGTH_SHORT).show();
                            return; // return will end the program at this point
                        }

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            Log.i("","string responsesdasdg======="+jsonArray.length());

                            if (jsonArray.length()==0) {
                                // we need to check this, to make sure, our dataStructure JSonArray contains
                                // something
                                Toasty.info(getActivity(), "no data available", Toast.LENGTH_SHORT).show();
                                return; // return will end the program at this point
                            }

                            else if (jsonArray.length() <= 0) {
                                // we need to check this, to make sure, our dataStructure JSonArray contains
                                // something
                                Toasty.info(getActivity(), "no data available", Toast.LENGTH_SHORT).show();
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



                                        recyclerModels.add(new GatterNearBy(userId, uname,gender,birthday,city,profile_pic));
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
                params.put("fromcity", citycode);
                params.put("mored", sendmore);





                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    private void filter(String text) {

        ArrayList<GatterNearBy> filteredname=new ArrayList<>();

        for (GatterNearBy s : recyclerModels) {
            if(s.getUname().toLowerCase().startsWith(text))
            {
                filteredname.add(s);
            }
        }
        recyclerAdapter.filterList(filteredname);

    }


}
