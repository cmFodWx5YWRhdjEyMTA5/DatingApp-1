package com.example.brill.bdatingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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


public class LoginActivity extends AppCompatActivity {

    EditText edtEmail,edtPassword;
    Button btnLogin;
    //CheckBox chkRemamber;

    String JID = "intId";

    TextView signup,fg_pass;


    public static final String PREF_ID="intId";

    SharedPreferences prefrance;

    Boolean isinternetpresent;
    ConnectDetector cd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtEmail=(EditText)findViewById(R.id.edtEmail);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        btnLogin=(Button) findViewById(R.id.btnLogin);
        //chkRemamber=(CheckBox)findViewById(R.id.chkRemamber);
        cd=new ConnectDetector(getApplicationContext());
        isinternetpresent=cd.isConnectToInternet();
        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setPropertyOnFocousOnText();

        signup=(TextView)findViewById(R.id.signup);
        fg_pass=(TextView)findViewById(R.id.fg_pass);

        prefrance=getSharedPreferences(Configs.UserPrefrance,Context.MODE_PRIVATE);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)     {



               /* String strEmail=edtEmail.getText().toString();
                String strPass=edtPassword.getText().toString();
                LoginUserMethod(strEmail,strPass);*/

                Boolean mail= isValidMail(edtEmail.getText().toString());
                int pass=edtPassword.getText().toString().length();



                if(!mail.equals(true))
                {
                    Toasty.error(getApplicationContext(),"InValid Email",Toast.LENGTH_SHORT).show();

                }
               /* else if (!(pass>=8))
                {
                    Toast.makeText(getApplicationContext(),"Enter Atlist 8 Digits Password !",Toast.LENGTH_SHORT).show();

                }*/
                else
                {
                    String strEmail=edtEmail.getText().toString();
                    String strPass=edtPassword.getText().toString();
                    if(isinternetpresent) {
                        LoginUserMethod(strEmail, strPass);
                    }
                    else {
                        Toasty.info(getApplicationContext(),"Internet Not Present",Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });

        fg_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(i);
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

        edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edtPassword.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                    // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                    // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edtPassword.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
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

                        Log.i("","login response========="+response);
                        try {

                            if(response.equals("0"))
                            {
                                Toasty.error(getApplicationContext(),"In-Valid email or password",Toast.LENGTH_SHORT).show();
                            }
                            else if(response.equals("blocked"))
                            {
                                Toasty.error(getApplicationContext(),"Your account blocked By Admin",Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                JSONObject obj = new JSONObject(response);
                                JSONObject userJson = obj.getJSONObject("0");

                                String id=userJson.getString("id");
                                String reg_sta=userJson.getString("reg_status");

                                SharedPreferences.Editor editor=prefrance.edit();
                                editor.putString("id",userJson.getString("id"));
                                editor.putString("reg_status",userJson.getString("reg_status"));
                                editor.putString("email",userJson.getString("email"));
                                editor.putString("password",userJson.getString("password"));
                                editor.commit();

                                String idss= prefrance.getString("id","");

                                Log.i("","ccccccccccccccccccc"+idss);

                                if(reg_sta.equals("2"))
                                {

                                    Intent i=new Intent(getApplicationContext(),Dashboard.class);
                                    startActivity(i);
                                    finish();
                                }
                                else if(reg_sta.equals("1"))
                                {
                                    Intent i=new Intent(getApplicationContext(),CompleteYourProfie.class);
                                    startActivity(i);
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
                params.put("UserLogin", strEmail);
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
}
