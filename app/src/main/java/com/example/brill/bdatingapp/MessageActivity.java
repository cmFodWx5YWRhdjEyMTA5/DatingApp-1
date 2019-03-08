package com.example.brill.bdatingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.brill.bdatingapp.adapter.AdapterGetAllChat;
import com.example.brill.bdatingapp.gattersatter.GatterGetAllChat;
import com.example.brill.bdatingapp.gattersatter.GatterGetAllUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class MessageActivity extends AppCompatActivity {

    SharedPreferences prefrance;

    static final int REQUEST_IMAGE_CAPTURE=1;

    ImageButton img_button;
    EditText edtMessage;
    FloatingActionButton btnSendMessage;
    String chattoUser,userid;

    ProgressBar progressBar;
    JsonArrayRequest jsonArrayReq;
    RequestQueue requestQueue;

    private AdapterGetAllChat recyclerAdapter;
    private ArrayList<GatterGetAllChat> recyclerModels;

    List<GatterGetAllUser> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerlayoutManager;



    private boolean itShouldLoadMore = true;

    Boolean isinternetpresent;
    ConnectDetector cd;

    GridView grid;
    String messageLength="0";
    Button dropView;
    int nextdata;

    Handler handler;


String selectuserid,selectusername,selectuserpicture,cuserid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefrance=getSharedPreferences("selectedUser", Context.MODE_PRIVATE);
        selectuserid=(prefrance.getString("selectuserid",""));
        selectusername=(prefrance.getString("selectusername",""));
        selectuserpicture=(prefrance.getString("selectuseridpic",""));

        final SharedPreferences msharedprefranceAdmin= getSharedPreferences(Configs.UserPrefrance,MODE_PRIVATE);
         cuserid=(msharedprefranceAdmin.getString("id",""));
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        dropView = (Button)findViewById(R.id.dropView);
        recyclerModels = new ArrayList<>();
        recyclerAdapter = new AdapterGetAllChat(recyclerModels,getApplicationContext());
        GetDataAdapter1 = new ArrayList<>();

        edtMessage= (EditText) findViewById(R.id.edtMessage);
        btnSendMessage= (FloatingActionButton) findViewById(R.id.btnSendMessage);

      //  Toast.makeText(this, selectusername.toString(), Toast.LENGTH_SHORT).show();
        setTitle(selectusername.toString());

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.loadmore_recycler_view);

        recyclerView.smoothScrollToPosition(recyclerModels.size() - 1);
        recyclerView.setHasFixedSize(true);
        recyclerlayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(recyclerlayoutManager);

        recyclerView.setAdapter(recyclerAdapter);

        GetDataAdapter1 = new ArrayList<>();
        GetOldMyChat();


        dropView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(recyclerModels.size() - 1);
            }
        });
       /* recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
        });*/







        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(edtMessage.getText().toString()==""))
                {
                    cd=new ConnectDetector(getApplicationContext());
                    isinternetpresent=cd.isConnectToInternet();

                    if(isinternetpresent) {

                        sendMsgChat(edtMessage.getText().toString());
                        recyclerView.smoothScrollToPosition(recyclerModels.size() - 1);

                    }
                    else {

                        Toasty.info(getApplicationContext(),"Internet not Present",Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toasty.error(getApplicationContext(),"Message Box is Empty",Toast.LENGTH_SHORT).show();
                }

            }
        });



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                String rtime="R";
                int sendto=Integer.parseInt(selectuserid);
                int sendby=Integer.parseInt(cuserid);
                int nxt=nextdata;
                RuntimeGetDataFromServer(rtime,sendto,sendby,nxt);
                handler.postDelayed(this, 2000);
            }
        }, 1500);


/*

         handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 20 seconds
                String rtime="R";
                int sendto=Integer.parseInt(selectuserid);
                int sendby=Integer.parseInt(cuserid);
                int nxt=nextdata;
                RuntimeGetDataFromServer(rtime,sendto,sendby,nxt);
                Log.i("","Runtime method called======"+rtime+sendto+sendby+nxt);

                handler.postDelayed(this, 1000);

            }
        }, 2000);
*/





       /* Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {


                RuntimeGetDataFromServer();
            }
        }, 0, 5000);
*/




    }
    private void GetOldMyChat() {
        nextdata=0;
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","GetOldMyChat====="+ Configs.URL_SENDCHAT);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_SENDCHAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","GetOldMyChatrrrrrrrrrrrrrrrrrrrrrrr"+response);
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            nextdata=nextdata+jsonArray.length();
                            Log.i("","GetOldMyChatlengthlength=length==========="+jsonArray.length());


                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    //  lastId= jsonObject.getString("id");

                                    //  Log.i("","ggggggggggggggg"+lastId);

                                    String  userId, uname,usenderid,recid,msg,readstatus,cdate;

                                    userId=jsonObject.getString("id");
                                    usenderid=jsonObject.getString("senderid");
                                    recid=jsonObject.getString("recvrid");
                                    msg=jsonObject.getString("message");
                                    readstatus=jsonObject.getString("read_status");
                                    cdate=jsonObject.getString("createdate");


                                    Log.i("","userId============"+userId);
                                    Log.i("","usenderid============"+usenderid);
                                    Log.i("","recid============"+recid);
                                    Log.i("","msg============"+msg);
                                    Log.i("","readstatus============"+readstatus);
                                    Log.i("","cdate============"+cdate);



                                    recyclerModels.add(new GatterGetAllChat(userId,usenderid,recid,msg,readstatus,cdate));
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

                        Log.i("","fffffffffffffffffffffffff"+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("GetOldChat", "G");
                params.put("sendto", selectuserid);
                params.put("sendby", cuserid);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void sendMsgChat(final String messg) {




        itShouldLoadMore = false;

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_SENDCHAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","message send ing response======="+response);


                        edtMessage.setText("");

                        if (response.equals("") || response.equals(null)) {
                            // we need to check this, to make sure, our dataStructure JSonArray contains
                            // something
                            Toasty.info(getApplicationContext(), "no data available", Toast.LENGTH_SHORT).show();
                            return; // return will end the program at this point
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("","get all data error"+error);
                        progressBar.setVisibility(View.GONE);
                        itShouldLoadMore = true;
                        Toasty.error(getApplicationContext(), "Failed to send", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("GetSendNewMsg", "S");
                params.put("sendto", selectuserid);
                params.put("sendby", cuserid);
                params.put("msg", messg);
                return params;
            }
        };

        int socketTimeout = 3000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    private void RuntimeGetDataFromServer(final String rtime, final int sendto, final int sendby, final int nxt) {

        progressBar.setClickable(false);


        Log.i("","nextnextnextnextnextnext"+nxt);

       // final String sendmore=Integer.toString(this.nextdata);
        itShouldLoadMore = false;

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_SENDCHAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {




                        // progressBar.setVisibility(View.GONE);
                        Log.i("","rrrrrrrrrrrrrrrrrrrrrrrrr"+response);
                        progressBar.setVisibility(View.GONE);

                      /*  if (response.equals("")) {
                            // we need to check this, to make sure, our dataStructure JSonArray contains
                            // something
                            Toast.makeText(getApplicationContext(), "no data available", Toast.LENGTH_SHORT).show();
                            return; // return will end the program at this point
                        }*/

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            Log.i("","string responsesdasdg======="+jsonArray.length());

                            if (jsonArray.length()==0) {
                                // we need to check this, to make sure, our dataStructure JSonArray contains
                                // something
                                Toasty.info(getApplicationContext(), "no data available", Toast.LENGTH_SHORT).show();
                                return; // return will end the program at this point
                            }

                           /* else if (jsonArray.length() <= 0) {
                                // we need to check this, to make sure, our dataStructure JSonArray contains
                                // something
                                Toast.makeText(getApplicationContext(), "no data available", Toast.LENGTH_SHORT).show();
                                return; // return will end the program at this point
                            }*/
                            else
                            {



                                Log.i("","loadMoredtaggggggggggggggg"+jsonArray.length());

                               // MessageActivity.this.nextdata = MessageActivity.this.nextdata +getNextLength;
                                nextdata=nextdata+jsonArray.length();




                                itShouldLoadMore = true;



                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        //  lastId= jsonObject.getString("id");

                                        //  Log.i("","ggggggggggggggg"+lastId);

                                        String  userId, uname,usenderid,recid,msg,readstatus,cdate;

                                        userId=jsonObject.getString("id");
                                        usenderid=jsonObject.getString("senderid");
                                        recid=jsonObject.getString("recvrid");
                                        msg=jsonObject.getString("message");
                                        readstatus=jsonObject.getString("read_status");
                                        cdate=jsonObject.getString("createdate");


                                        recyclerModels.add(new GatterGetAllChat(userId,usenderid,recid,msg,readstatus,cdate));
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
                       // Toast.makeText(getApplicationContext(), "Failed to load more, network error", Toast.LENGTH_SHORT).show();
                        Log.i("","ggggggggggggggg"+error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Runtime", rtime);
                params.put("sendto", Integer.toString(sendto));
                params.put("sendby", Integer.toString(sendby));
                params.put("getnext",Integer.toString(nextdata));

                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }


   /* private void sendMsgChat(final String messg) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        Log.i("","GetOldMyChat====="+Configs.URL_SENDCHAT);
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_SENDCHAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","sendmessagerrrrrrrrrrrrrrrrrrrr"+response);
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            nextdata=jsonArray.length();
                            Log.i("","GetOldMyChatlengthlength=length==========="+jsonArray.length());


                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    //  lastId= jsonObject.getString("id");

                                    //  Log.i("","ggggggggggggggg"+lastId);

                                    String  userId, uname,usenderid,recid,msg,readstatus,cdate;

                                    userId=jsonObject.getString("id");
                                    usenderid=jsonObject.getString("senderid");
                                    recid=jsonObject.getString("recvrid");
                                    msg=jsonObject.getString("message");
                                    readstatus=jsonObject.getString("read_status");
                                    cdate=jsonObject.getString("createdate");


                                    Log.i("","userId============"+userId);
                                    Log.i("","usenderid============"+usenderid);
                                    Log.i("","recid============"+recid);
                                    Log.i("","msg============"+msg);
                                    Log.i("","readstatus============"+readstatus);
                                    Log.i("","cdate============"+cdate);



                                    recyclerModels.add(new GatterGetAllChat(userId,usenderid,recid,msg,readstatus,cdate));
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
                params.put("GetSendNewMsg", "S");
                params.put("sendto", Integer.toString(12));
                params.put("sendby", Integer.toString(1));

                params.put("msg", messg);
                Log.i("","next data profile error========="+nextdata);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }*/

  /*  void GetAllMyChat() {

        Log.i("","Userid is======"+userid);

       // if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_SENDCHAT,
                 new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                        //converting the string to json array object
                        JSONArray array = new JSONArray(response);

                            Log.i("","asdfasdfresponse====="+response);
                            messageLength=Integer.toString(array.length()) ;

                            Log.i("","length of next message from first call====="+messageLength);
                        //traversing through all the object
                        for (int i = 0; i < array.length(); i++) {

                            //getting product object from json array
                            JSONObject obj = array.getJSONObject(i);

                          String sub = obj.getString("subject");

                            Log.i("","subject is====="+sub);

                            recyclerModels.add(new GatterGetAllChat(obj.getString("intId"),obj.getString("subject"),obj.getString("message"),obj.getString("sendingtime"),obj.getString("intSenderId")));
                            recyclerAdapter.notifyDataSetChanged();

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
                        error.printStackTrace();
                        Log.i("","error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String getchat="yes";
                params.put("userid", userid);  //userid
                params.put("ChatToUser", chattoUser); //chattoUser
                params.put("getOldChat", getchat);

                Log.i("","asdfasdfasfdasdfuserid"+userid);
                Log.i("","asdfasdfasfdasdfuserid"+chattoUser);
                Log.i("","asdfasdfasfdasdfuserid"+getchat);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    private void SendMessageToUser(final String isMessage, final String messageLimit, final String userid, final String chattoUser, final String message) {

       Log.i("","==========ismessage======"+ isMessage);
       Log.i("","==========ismessage======messageLimit"+ messageLimit);
       Log.i("","==========ismessage======userid"+ userid);
       Log.i("","==========ismessage======chattoUser"+ chattoUser);
       Log.i("","==========ismessage======message"+ message);

       progressBar.setVisibility(View.VISIBLE);
       progressBar.setClickable(false);

       itShouldLoadMore = false;

       //if everything is fine
       StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_SENDCHAT,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try {
                           Log.i("","4444444444444444444444444444444"+response);
                           progressBar.setVisibility(View.GONE);
                           itShouldLoadMore = true;

                           JSONArray array = new JSONArray(response);
                           Log.i("","Integer.parseInt(messageLength)"+array.length());
                           int len=Integer.parseInt(messageLength)+array.length();

                           messageLength=Integer.toString(len);
                           //converting the string to json array object
                           Log.i("","6666666666666666666666666666"+messageLength);

                         //  messageLength =Integer.toString(Integer.parseInt(messageLength)+array.length());

                         //  int length=array.length()+Integer.parseInt(messageLength);


                           int oldlength=array.length() ;
                         *//*  int len=Integer.parseInt(messageLength) +Integer.parseInt(oldlength);
                           messageLength= Integer.toString(len);
*//*
                           Log.i("","++++++++++++++++++++++++++++++++"+oldlength);
                          // messageLength=Integer.toString(len);

                           if (array.length() <= 0) {
                               // we need to check this, to make sure, our dataStructure JSonArray contains
                               // something
                               Toast.makeText(getApplicationContext(), "no data available", Toast.LENGTH_SHORT).show();
                               return; // return will end the program at this point
                           }
                           Log.i("","asdfasdfresponse====="+response);

                           //traversing through all the object
                           for (int i = 0; i < array.length(); i++) {

                               JSONObject obj = array.getJSONObject(i);

                               String sub = obj.getString("subject");

                               Log.i("","subject is====="+sub);

                               recyclerModels.add(new GatterGetAllChat(obj.getString("intId"),obj.getString("subject"),obj.getString("message"),obj.getString("sendingtime"),obj.getString("intSenderId")));
                               recyclerAdapter.notifyDataSetChanged();

                                   //  recyclerModels.add(new GatterAdminGetnews(lastId, up_id,up_datetime,title,details,news_photo,news_video,up_name));
                                   //   recyclerAdapter.notifyDataSetChanged();
                               edtMessage.setText("");
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

                       Log.i("","error========="+error.toString());
                   }
               }) {
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               String getchat="yes";

              // String isMessage, String messageLimit, String userid, String chattoUser, String message

               Map<String, String> params = new HashMap<>();
               params.put("isMessage", isMessage);
               params.put("MessageLimit", messageLimit);
               params.put("userid", userid);
               params.put("ChatToUser", chattoUser);
               params.put("Message", message);


               Log.i("","asdfasdfasfdasdfuserid"+ MessageActivity.this.userid);
               Log.i("","asdfasdfasfdasdfuserid"+ MessageActivity.this.chattoUser);
               Log.i("","asdfasdfasfdasdfuserid"+getchat);

               return params;
           }
       };

       VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



    private void RuntimeGetDataFromServer(final String isGetMessage, final String messageLimit, final String userid, final String chattoUser) {

        Log.i("","00000000000000000000==========ismessage======"+ isGetMessage);
        Log.i("","00000000000000000000==========ismessage======messageLimit"+ messageLimit);
        Log.i("","00000000000000000000==========ismessage======userid"+ userid);
        Log.i("","00000000000000000000==========ismessage======chattoUser"+ chattoUser);



        itShouldLoadMore = false;

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_SENDCHAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.i("","11111111111111111111111111111"+response);
                            progressBar.setVisibility(View.GONE);
                            itShouldLoadMore = true;


                            if(response.equals("0no"))
                            {
                                Log.i("","no data found call 11111111111111111111111111111"+response);
                            }
                            else
                            {
                                JSONArray array = new JSONArray(response);
                                int len=Integer.parseInt(messageLength)+array.length();
                                messageLength=Integer.toString(len);

                                //converting the string to json array object
                                Log.i("","222222222222222222222222222222"+messageLength);

                                //  messageLength =Integer.toString(Integer.parseInt(messageLength)+array.length());

                                //  int length=array.length()+Integer.parseInt(messageLength);


                                int oldlength=array.length() ;
                         *//*  int len=Integer.parseInt(messageLength) +Integer.parseInt(oldlength);
                           messageLength= Integer.toString(len);
*//*
                                Log.i("","++++++++++++++++++++++++++++++++"+oldlength);
                                // messageLength=Integer.toString(len);

                                if (array.length() <= 0) {
                                    // we need to check this, to make sure, our dataStructure JSonArray contains
                                    // something
                                    Toast.makeText(getApplicationContext(), "no data available", Toast.LENGTH_SHORT).show();
                                    return; // return will end the program at this point
                                }
                                Log.i("","asdfasdfresponse====="+response);

                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject obj = array.getJSONObject(i);

                                    String sub = obj.getString("subject");

                                    Log.i("","subject is====="+sub);

                                    recyclerModels.add(new GatterGetAllChat(obj.getString("intId"),obj.getString("subject"),obj.getString("message"),obj.getString("sendingtime"),obj.getString("intSenderId")));
                                    recyclerAdapter.notifyDataSetChanged();

                                    //  recyclerModels.add(new GatterAdminGetnews(lastId, up_id,up_datetime,title,details,news_photo,news_video,up_name));
                                    //   recyclerAdapter.notifyDataSetChanged();

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
                        Toast.makeText(getApplicationContext(),"No more data"+ error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String getchat="yes";

                // String isMessage, String messageLimit, String userid, String chattoUser, String message

                Map<String, String> params = new HashMap<>();
                params.put("isGetMessage", isGetMessage);
                params.put("MessageLimit", messageLimit);
                params.put("userid", userid);
                params.put("ChatToUser", chattoUser);


                Log.i("","333333333333333333333333"+userid);
                Log.i("","333333333333333333333333"+chattoUser);
                Log.i("","333333333333333333333333"+messageLimit);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
*/

    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
