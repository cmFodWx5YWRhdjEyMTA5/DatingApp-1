package com.example.brill.bdatingapp.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.brill.bdatingapp.Configs;
import com.example.brill.bdatingapp.ConnectDetector;
import com.example.brill.bdatingapp.R;
import com.example.brill.bdatingapp.adapter.AdapterGetIAmThereFavourites;
import com.example.brill.bdatingapp.gattersatter.GatterGetIAmTheirFavourites;
import com.example.brill.bdatingapp.gattersatter.GatterNearBy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImTheirFavoriteFragment extends android.support.v4.app.Fragment {
    ProgressBar progressBar;
    JsonArrayRequest jsonArrayReq;
    RequestQueue requestQueue;

    private AdapterGetIAmThereFavourites recyclerAdapter;
    private ArrayList<GatterGetIAmTheirFavourites> recyclerModels;


    List<GatterNearBy> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerlayoutManager;




    private static final int LOAD_LIMIT = 15;
    private String lastId = "0";

    private boolean itShouldLoadMore = true;
    int LastGetDataLength=0;

    Boolean isinternetpresent;
    ConnectDetector cd;

    GridView grid;

    SharedPreferences prefrance;

    String SharedintId,SharedPassword;

    public ImTheirFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_im_their_favorite, container, false);

        prefrance=getActivity().getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
        SharedintId = prefrance.getString("intId","");

       /* final SharedPreferences msharedprefrance=getActivity().getSharedPreferences(Configs.UserPrefrance,MODE_PRIVATE);
        SharedintId=(msharedprefrance.getString("intId",""));
*/






        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar1);


        // cd=new ConnectDetector(getActivity().getApplicationContext());
        //  isinternetpresent=cd.isConnectToInternet();
        //   if(isinternetpresent) {
        recyclerModels = new ArrayList<>();
        recyclerAdapter = new AdapterGetIAmThereFavourites(recyclerModels,getActivity());
        GetDataAdapter1 = new ArrayList<>();


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.loadmore_recycler_view);

        recyclerlayoutManager=new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(recyclerlayoutManager);
        recyclerView.setHasFixedSize(true);
        //we can now set adapter to recyclerView;


        recyclerView.setAdapter(recyclerAdapter);

        GetDataAdapter1 = new ArrayList<>();

        getAllMyFavourites();
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
                            loadMore();
                        }
                    }

                }
            }
        });







        return rootView;
    }
    private void getAllMyFavourites() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);

        String JSON_URL= Configs.GetAllIAmTheirFavouritesList+"?limit=16&LoadData=yes&cuserid="+SharedintId;

        Log.i("","GetAllIAmTheirFavouritesList json links is======"+JSON_URL);

        jsonArrayReq = new JsonArrayRequest(JSON_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //   uploading.dismiss();
                        Log.i("", "GetAllIAmTheirFavouritesList response is --------------" + response);
                        LastGetDataLength=LastGetDataLength+response.length();

                        progressBar.setVisibility(View.GONE);

                        if (response.length() <= 0) {
                            // no data available
                            Toasty.info(getActivity(), "No data available", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                // please note this last id how we have updated it
                                // if there are 4 items for example, and we are ordering in descending order,
                                // then last id will be 1. This is because outside a loop, we will get the last
                                // value [Thanks to JAVA]


                                lastId= jsonObject.getString("intId");
                                String fname= jsonObject.getString("fname");

                                Log.i("","fname is=="+fname);


                                recyclerModels.add(new GatterGetIAmTheirFavourites(lastId, fname));
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
    }



    private void loadMore() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);

        itShouldLoadMore = false; // lock this until volley completes processing

        // progressWheel is just a loading spinner, please see the content_main.xml
      /*  final ProgressWheel progressWheel = (ProgressWheel) this.findViewById(R.id.progress_wheel);
        progressWheel.setVisibility(View.VISIBLE);

        http://www.ankalan.com/rescue/AdminPanel/AdminRecentNews.php?lastids=89&MoreData=yes&limit=15

*/
        //  uploading = ProgressDialog.show(AdminAddNews.this, "fatching data", "Please wait...", false, false);

        String urls= Configs.GetAllIAmTheirFavouritesList+"?lastGetDataLength="+lastId+"&MoreData=yes&limit=16&cuserid="+SharedintId;

        Log.i("","nfgasdgsdgLoad more urlis"+urls);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urls, (String)null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //  progressWheel.setVisibility(View.GONE);

                // since volley has completed and it has our response, now let's update
                // itShouldLoadMore

                Log.i("","88888888888loadmord data is==========="+response.length());
                LastGetDataLength=LastGetDataLength+response.length();

                progressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    // we need to check this, to make sure, our dataStructure JSonArray contains
                    // something
                    Toasty.info(getActivity(), "no data available", Toast.LENGTH_SHORT).show();
                    return; // return will end the program at this point
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        //    uploading.dismiss();

                        // please note how we have updated the lastId variable
                        // if there are 4 items for example, and we are ordering in descending order,
                        // then last id will be 1. This is because outside a loop, we will get the last
                        // value

                        lastId= jsonObject.getString("intId");
                        String fname= jsonObject.getString("fname");

                        Log.i("","fname is=="+fname);
                        recyclerModels.add(new GatterGetIAmTheirFavourites(lastId, fname));
                        recyclerAdapter.notifyDataSetChanged();

                        //  recyclerModels.add(new GatterAdminGetnews(lastId, up_id,up_datetime,title,details,news_photo,news_video,up_name));
                        //   recyclerAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressWheel.setVisibility(View.GONE);
                // volley finished and returned network error, update and unlock  itShouldLoadMore
                progressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;
                Toasty.error(getActivity(), "Failed to load more, network error", Toast.LENGTH_SHORT).show();

            }
        });

        Volley.newRequestQueue(getActivity()).add(jsonArrayRequest);

    }


}
