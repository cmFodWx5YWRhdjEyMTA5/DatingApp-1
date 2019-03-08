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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;


public class ForgotPassword extends AppCompatActivity {

    EditText edtEmail,edtPassword;
    Button btnContinue,btnOtp,btnSubmit;
    CheckBox chkRemamber;

    String JID = "intId";




    public static final String PREF_ID="intId";

    SharedPreferences prefrance;

    LinearLayout forgotlayForgot,forgotlayOtp,forgotlayNewPass,fotp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        edtEmail=(EditText)findViewById(R.id.edtEmail);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        btnContinue=(Button) findViewById(R.id.btnContinue);
        btnOtp=(Button) findViewById(R.id.btnOtp);
        btnSubmit=(Button) findViewById(R.id.btnSubmit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setPropertyOnFocousOnText();

        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        forgotlayForgot=(LinearLayout) findViewById(R.id.forgotlayForgot);
        forgotlayOtp=(LinearLayout)findViewById(R.id.forgotlayOtp);
        forgotlayNewPass=(LinearLayout)findViewById(R.id.forgotlayNewPass);

        prefrance=getSharedPreferences(Configs.UserPrefrance,Context.MODE_PRIVATE);



        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)     {

               /* String strEmail=edtEmail.getText().toString();
                String strPass=edtPassword.getText().toString();
                LoginUserMethod(strEmail,strPass);*/

                Boolean mail= isValidMail(edtEmail.getText().toString());



                if(!mail.equals(true))
                {
                    Toasty.error(getApplicationContext(),"InValid Email",Toast.LENGTH_SHORT).show();

                }

                else
                {
                    String strEmail=edtEmail.getText().toString();


                   // forgotlayOtp.setVisibility(View.VISIBLE);
                   // forgotlayForgot.setVisibility(View.GONE);
                   // forgotlayNewPass.setVisibility(View.GONE);


                   // LoginUserMethod(strEmail,strPass);

                    Toasty.info(ForgotPassword.this,"Check your registered email account",Toast.LENGTH_SHORT).show();
                    Toasty.info(ForgotPassword.this,"We Have sent your Password",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });


        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)     {

                forgotlayOtp.setVisibility(View.GONE);
                forgotlayForgot.setVisibility(View.GONE);
                forgotlayNewPass.setVisibility(View.VISIBLE);

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)     {
                    Toasty.info(ForgotPassword.this,"Check your registered email account",Toast.LENGTH_SHORT).show();
                    Toasty.info(ForgotPassword.this,"We Have sent your Password",Toast.LENGTH_SHORT).show();
                    finish();
            }
        });


    }

    private void setPropertyOnFocousOnText() {

        edtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edtEmail.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                   // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                   // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edtEmail.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
                }

            }
        });


    }

    private void LoginUserMethod(final String strEmail, final String strPass) {
        Log.i("","=====email====="+strEmail);
        Log.i("","=====password====="+strPass);

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // progressBar.setVisibility(View.GONE);
                        Log.i("","111111111111111111111"+response.toString());

                        try {
                            JSONObject obj = new JSONObject(response);
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
                            startActivity(i);

                           /* Intent i=new Intent(getApplicationContext(),Dashboard.class);
                            startActivity(i);
*/
                            // JsonLoginResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                params.put("loginEmail", strEmail);
                params.put("loginPassword", strPass);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }




    private boolean isValidMail(String email) {
        boolean check;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email);
        check = m.matches();

        if(!check) {

            // Toast.makeText(getApplicationContext(),"Not Valid Email",Toast.LENGTH_SHORT).show();

        }
        return check;
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
