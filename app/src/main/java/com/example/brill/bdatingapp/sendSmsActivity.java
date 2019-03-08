package com.example.brill.bdatingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

/**
 * Created by brill on 2/11/2018.
 */

public class sendSmsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView edtsms;
    Button btnSend;
    CheckBox chkRemamber;

    String JID = "intId";
    Boolean isinternetpresent;
    ConnectDetector cd;

    JSONArray resultCountry;

    public static final String PREF_ID="intId";

    SharedPreferences prefrance,pref;

    LinearLayout forgotlayForgot,forgotlayOtp,forgotlayNewPass;
    Spinner spinner_edit_country;
    private ArrayList<String> countrys;
    String userphone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendsms);
        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        edtsms=(TextView)findViewById(R.id.edtsms);

        btnSend=(Button) findViewById(R.id.btnsend);
        spinner_edit_country=(Spinner)findViewById(R.id.spinner_edit_country);
        spinner_edit_country.setOnItemSelectedListener(this);

        countrys=new ArrayList<String>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  setPropertyOnFocousOnText();



                pref = getSharedPreferences("selectedUserSMS", Context.MODE_PRIVATE);

        userphone= pref.getString("selectuserphone","");

        Log.i("","phone=========="+userphone);

        GetSMSData();

        forgotlayForgot=(LinearLayout) findViewById(R.id.forgotlayForgot);


        prefrance=getSharedPreferences(Configs.UserPrefrance,Context.MODE_PRIVATE);




        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)     {

               /* String strEmail=edtEmail.getText().toString();
                String strPass=edtPassword.getText().toString();
                LoginUserMethod(strEmail,strPass);*/

                String strsms=edtsms.getText().toString();

                if(!strsms.equals(""))
                {
                    SMSSENDUserMethod(userphone,strsms);
                    Toasty.success(getApplicationContext(),"SMS Sent",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toasty.error(getApplicationContext(),"SMS Sent Failed",Toast.LENGTH_SHORT).show();
                }

            }
        });




    }



    private void SMSSENDUserMethod(final String phoneno, final String strmsg) {
       final String strm="Brill Techno Pvt Ltd. Greetings you ! Best Of Luck For Your Exam.";
      //  final String p="8769847255";

        //if everything is fine

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_sendsmsurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // progressBar.setVisibility(View.GONE);
                        Log.i("","sms111111111111111111111"+response.toString());

                        /*    JSONObject obj = new JSONObject(response);
                            JSONObject userJson = obj.getJSONObject("0");
                            //Log.i("","111111111111111111111"+response.toString());
                            String jid=userJson.getString("intId");
                            SharedPreferences.Editor editor=prefrance.edit();
                            editor.putString("intId",userJson.getString("intId"));
                            editor.putString("fname",userJson.getString("fname"));
                            editor.putString("lname",userJson.getString("lname"));
                            editor.putString("email",userJson.getString("email"));
                            editor.putString("password",userJson.getString("password"));
                            editor.putString("sex",userJson.getString("sex"));
                            editor.putString("seeking",userJson.getString("seeking"));
                            editor.putString("dob",userJson.getString("dob"));
                            editor.putString("country_code",userJson.getString("country_code"));
                            editor.putString("state_code",userJson.getString("state_code"));
                            editor.putString("city_code",userJson.getString("city_code"));
                            editor.putString("plan_type",userJson.getString("plan_type"));
                            editor.putString("intPlanCatid",userJson.getString("intPlanCatid"));
                            editor.putString("intLookAgeId",userJson.getString("intLookAgeId"));
                            editor.putString("status",userJson.getString("status"));
                            editor.putString("auth_code",userJson.getString("auth_code"));
                            editor.putString("createdate",userJson.getString("createdate"));
                            editor.putString("plan_duration",userJson.getString("plan_duration"));
                            editor.putString("user_type",userJson.getString("user_type"));
                            editor.putString("online_status",userJson.getString("online_status"));
                            editor.putString("online_duration",userJson.getString("online_duration"));
                            editor.putString("profile_status",userJson.getString("profile_status"));
                            editor.putString("verify_status",userJson.getString("verify_status"));
                            editor.putString("has_pic",userJson.getString("has_pic"));
                            editor.putString("remark",userJson.getString("remark"));
                            editor.putString("phone",userJson.getString("phone"));
                            editor.putString("prefer",userJson.getString("prefer"));
                            editor.putString("country",userJson.getString("country"));
                            editor.putString("state",userJson.getString("state"));
                            editor.putString("city",userJson.getString("city"));
                            editor.putString("seekingAgestart",userJson.getString("seekingAgestart"));
                            editor.putString("seekingAgeend",userJson.getString("seekingAgeend"));
                            editor.commit();

                            Log.i("","jid111111111111111111111"+jid.toString());

                            Intent i=new Intent(getApplicationContext(),Dashboard.class);
                            startActivity(i);*/

                           /* Intent i=new Intent(getApplicationContext(),Dashboard.class);
                            startActivity(i);
*/
                        // JsonLoginResponse(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("message", strmsg);
                params.put("phone", phoneno);
               // params.put("loginPassword", strPass);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void GetSMSData() {
        cd=new ConnectDetector(getApplicationContext());
        isinternetpresent=cd.isConnectToInternet();

        if(isinternetpresent)
        {

            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_UserSMStemplete,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // progressBar.setVisibility(View.GONE);
                            Log.i("","GetCountryData================"+response.toString());

                            JSONObject JSObj = null;

                            try {

                                if(response.equals("0"))
                                {
                                    Toasty.error(getApplicationContext(),"In-Valid email or password",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                    JSObj = new JSONObject(response);

                                    resultCountry = JSObj.getJSONArray("templet");
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
            Toasty.info(getApplicationContext(),"Connect To Internet",Toast.LENGTH_SHORT).show();

        }



    }


    private void getCountry(JSONArray arr) {
        //Traversing through all the items in the json array
        for(int i=0;i<arr.length();i++){
            try {
                JSONObject json = null;
                json = arr.getJSONObject(i);
                //Getting json object
                String  jname=json.getString("title");
                Log.i("","json response adfsdfsadfsd"+jname);
                //Adding the name of the student to array list

                countrys.add(json.getString("title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner_edit_country.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, countrys));

    }



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

                getMessageContent(item);

                break;

        }
    }


    private void getMessageContent(final String item) {

        //if everything is fine

        Log.i("","itemis111111111111111111111"+item);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_UserSMStemplete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","content111111111111111111111"+response.toString());
                        edtsms.setText(response);

                        /*    JSONObject obj = new JSONObject(response);
                            JSONObject userJson = obj.getJSONObject("0");
                            //Log.i("","111111111111111111111"+response.toString());
                            String jid=userJson.getString("intId");
                            SharedPreferences.Editor editor=prefrance.edit();
                            editor.putString("intId",userJson.getString("intId"));
                            editor.putString("fname",userJson.getString("fname"));
                            editor.putString("lname",userJson.getString("lname"));
                            editor.putString("email",userJson.getString("email"));
                            editor.putString("password",userJson.getString("password"));
                            editor.putString("sex",userJson.getString("sex"));
                            editor.putString("seeking",userJson.getString("seeking"));
                            editor.putString("dob",userJson.getString("dob"));
                            editor.putString("country_code",userJson.getString("country_code"));
                            editor.putString("state_code",userJson.getString("state_code"));
                            editor.putString("city_code",userJson.getString("city_code"));
                            editor.putString("plan_type",userJson.getString("plan_type"));
                            editor.putString("intPlanCatid",userJson.getString("intPlanCatid"));
                            editor.putString("intLookAgeId",userJson.getString("intLookAgeId"));
                            editor.putString("status",userJson.getString("status"));
                            editor.putString("auth_code",userJson.getString("auth_code"));
                            editor.putString("createdate",userJson.getString("createdate"));
                            editor.putString("plan_duration",userJson.getString("plan_duration"));
                            editor.putString("user_type",userJson.getString("user_type"));
                            editor.putString("online_status",userJson.getString("online_status"));
                            editor.putString("online_duration",userJson.getString("online_duration"));
                            editor.putString("profile_status",userJson.getString("profile_status"));
                            editor.putString("verify_status",userJson.getString("verify_status"));
                            editor.putString("has_pic",userJson.getString("has_pic"));
                            editor.putString("remark",userJson.getString("remark"));
                            editor.putString("phone",userJson.getString("phone"));
                            editor.putString("prefer",userJson.getString("prefer"));
                            editor.putString("country",userJson.getString("country"));
                            editor.putString("state",userJson.getString("state"));
                            editor.putString("city",userJson.getString("city"));
                            editor.putString("seekingAgestart",userJson.getString("seekingAgestart"));
                            editor.putString("seekingAgeend",userJson.getString("seekingAgeend"));
                            editor.commit();

                            Log.i("","jid111111111111111111111"+jid.toString());

                            Intent i=new Intent(getApplicationContext(),Dashboard.class);
                            startActivity(i);*/

                           /* Intent i=new Intent(getApplicationContext(),Dashboard.class);
                            startActivity(i);
*/
                        // JsonLoginResponse(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("GetItemContent", item);
                // params.put("loginPassword", strPass);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }
}
