package com.example.brill.bdatingapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import com.example.brill.bdatingapp.adapter.AdapterFriendList;
import com.example.brill.bdatingapp.adapter.AdapterViewAllMessage;
import com.example.brill.bdatingapp.gattersatter.GatterGetAllFriendList;
import com.example.brill.bdatingapp.gattersatter.GatterGetUserMessage;
import com.example.brill.bdatingapp.gattersatter.GatterGetViewMessage;

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
public class ViewUserMessage extends AppCompatActivity {
    ProgressBar progressBar;
    JsonArrayRequest jsonArrayReq;
    RequestQueue requestQueue;

    private AdapterViewAllMessage recyclerAdapter;
    private ArrayList<GatterGetViewMessage> recyclerModels;

    List<GatterGetViewMessage> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerlayoutManager;

    private static final int LOAD_LIMIT = 15;
    private String lastId = "0";

    private boolean itShouldLoadMore = true;

    EditText edtMessage;
    FloatingActionButton btnSendMessage;

    Boolean isinternetpresent;
    ConnectDetector cd;

    String reslength="0";
    String selectusername;
    GridView grid;
    int nextdata=0;
    String sharid;
    String senderid;
    String receiverid;
    SharedPreferences prefrance;
    SharedPreferences ViewMessage;
    String messagereceiver;
    public ViewUserMessage() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        setContentView(R.layout.activity_viewusermessage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        prefrance=getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
        sharid= prefrance.getString("id","");
        selectusername=(prefrance.getString("selectusername",""));

        Intent intent=getIntent();
        final Bundle bd = intent.getExtras();
        final String id = bd.getString("username");
        setTitle(id);

        ViewMessage=getSharedPreferences("GetMessage", Context.MODE_PRIVATE);
        senderid= ViewMessage.getString("selectuserid","");
        // receiverid= ViewMessage.getString("selectusername","");



        if(sharid.equals(messagereceiver))
        {
            messagereceiver=receiverid;
        }
        else
        {
            messagereceiver=senderid;
        }

        Log.i("","sender and reciver=="+"sender=="+senderid+"receiver==="+receiverid);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        edtMessage = (EditText) findViewById(R.id.edtMessage);
        btnSendMessage = (FloatingActionButton) findViewById(R.id.btnSendMessage);


        // edtSearchuser=(EditText) findViewById(R.id.edtSearchuser);

        // cd=new ConnectDetector(getApplicationContext());
        //  isinternetpresent=cd.isConnectToInternet();
        //   if(isinternetpresent) {
        recyclerModels = new ArrayList<>();
        recyclerAdapter = new AdapterViewAllMessage(recyclerModels,this);
        GetDataAdapter1 = new ArrayList<>();


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.loadmore_recycler_view);

        recyclerlayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerlayoutManager);
        recyclerView.setHasFixedSize(true);

        //we can now set adapter to recyclerView;


        recyclerView.setAdapter(recyclerAdapter);

        GetDataAdapter1 = new ArrayList<>();

        getAllUsers();

        Button dropView1 = (Button) findViewById(R.id.dropView1);
        dropView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(recyclerModels.size()-1);
            }
        });

     /*   edtSearchuser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String username=editable.toString();

              //  filter(username.toLowerCase());
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



      /*  recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
        });*/


        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mesg= edtMessage.getText().toString();
                if(mesg.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Message is empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendMessageToUser(mesg);
                    edtMessage.setText("");
                    recyclerView.smoothScrollToPosition(recyclerModels.size()-1);
                }
            }
        });


    }

    private void sendMessageToUser(final String mesg) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","Configs.GetAllUser========"+ Configs.GetAllViewMessage);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.sendMessage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","ffffffffff=================="+response);
                        Toast.makeText(getApplicationContext(),"message send", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);


                            nextdata=jsonArray.length();
                            Log.i("","getAllUsersresponser============"+jsonArray.length());


                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String senderid, receiverid,gender,name,birthday,message,profile_pic;
                                    senderid=jsonObject.getString("sender");
                                    receiverid=jsonObject.getString("reciver");
                                    name=jsonObject.getString("name");
                                    gender=jsonObject.getString("gender");
                                    birthday=jsonObject.getString("birthday");
                                    message=jsonObject.getString("message");
                                    profile_pic=jsonObject.getString("profile_pic");



                                    recyclerModels.add(new GatterGetViewMessage(senderid,receiverid,message,profile_pic));



                                    recyclerAdapter.notifyDataSetChanged();
                                    //  recyclerAdapter.hideItem(0);
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","profile error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("SendMessage", "1");
                params.put("senderid",sharid);
                params.put("receiverid",messagereceiver);
                params.put("messageis",mesg);

                Log.i("","next data profile error========="+nextdata);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }





   /* private void filter(String text) {

        ArrayList<GatterGetViewMessage> filteredname=new ArrayList<>();

        for (GatterGetViewMessage s : recyclerModels) {
            if(s.getUname().toLowerCase().startsWith(text))
            {
                filteredname.add(s);
            }
        }
        recyclerAdapter.filterList(filteredname);

    }*/

    private void getAllUsers() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","Configs.GetAllUser========"+ Configs.GetAllViewMessage);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.GetAllViewMessage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","usermessagesffffffffff=================="+response);
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

                                    String senderid, receiverid,gender,name,birthday,message,profile_pic;
                                    senderid=jsonObject.getString("sender");
                                    receiverid=jsonObject.getString("reciver");
                                    name=jsonObject.getString("name");
                                    gender=jsonObject.getString("gender");
                                    birthday=jsonObject.getString("birthday");
                                    message=jsonObject.getString("message");
                                    profile_pic=jsonObject.getString("profile_pic");

                                    recyclerModels.add(new GatterGetViewMessage(senderid,receiverid,message,profile_pic));

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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","profile error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("GetViewMessage", "1");
                params.put("userid",sharid);
                params.put("senderid",senderid);
                params.put("receiverid",sharid);


                Log.i("","message sender id data profile error========="+senderid);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
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
























   /* private void loadMore() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);

        itShouldLoadMore = false; // lock this until volley completes processing

        // progressWheel is just a loading spinner, please see the content_main.xml
      *//**//*  final ProgressWheel progressWheel = (ProgressWheel) this.findViewById(R.id.progress_wheel);
        progressWheel.setVisibility(View.VISIBLE);

        http://www.ankalan.com/rescue/AdminPanel/AdminRecentNews.php?lastids=89&MoreData=yes&limit=15

*//**//*
        //  uploading = ProgressDialog.show(AdminAddNews.this, "fatching data", "Please wait...", false, false);

        //String urls=Configs.GetAllUser+"?lastid="+lastId+"&MoreData=yes&limit=16";

        String urls= Configs.GetAllUser+"?limit=16&MoreData=yes&lastid="+reslength;
        Log.i("","nfgasdgsdgLoad more urlis "+urls);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urls, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //  progressWheel.setVisibility(View.GONE);

                // since volley has completed and it has our response, now let's update
                // itShouldLoadMore

                Log.i("","88888888888loadmord data is==========="+response);

              reslength= Integer.toString(Integer.parseInt(reslength)+response.length());

                Log.i("","88888888888loadmord data is===========length"+reslength);
                progressBar.setVisibility(View.GONE);
                itShouldLoadMore = true;

                if (response.length() <= 0) {
                    // we need to check this, to make sure, our dataStructure JSonArray contains
                    // something
                    Toast.makeText(getActivity(), "no data available", Toast.LENGTH_SHORT).show();
                    return; // return will end the program at this point
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


                        recyclerModels.add(new GatterGetAllUser(lastId, fname,lname,sex,dob,country_code));
                        recyclerAdapter.notifyDataSetChanged();

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
                Toast.makeText(getActivity(), "Failed to load more, network error", Toast.LENGTH_SHORT).show();

            }
        });

        Volley.newRequestQueue(getActivity()).add(jsonArrayRequest);

    }*/



    private void loadMoredta() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","convert in string gooooooooooooooo"+ Integer.toString(nextdata));

        final String sendmore= Integer.toString(nextdata);
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
                            Toast.makeText(getApplicationContext(), "no data available", Toast.LENGTH_SHORT).show();
                            return; // return will end the program at this point
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

                                        String senderid, receiverid,gender,name,birthday,message,profile_pic;
                                        senderid=jsonObject.getString("sender");
                                        receiverid=jsonObject.getString("reciver");
                                        name=jsonObject.getString("name");
                                        gender=jsonObject.getString("gender");
                                        birthday=jsonObject.getString("birthday");
                                        message=jsonObject.getString("message");
                                        profile_pic=jsonObject.getString("profile_pic");

                                        recyclerModels.add(new GatterGetViewMessage(senderid,receiverid,message,profile_pic));
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
                params.put("next", Integer.toString(nextdata));
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
