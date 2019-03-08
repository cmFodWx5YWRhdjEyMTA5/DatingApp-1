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

import es.dmoral.toasty.Toasty;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText edtOldpass,edtnewPass,edtConfirm;
    Button btnChangePass;
    CheckBox chkRemamber;

    String JID = "intId";

    TextView signup;


    public static final String PREF_ID="intId";

    SharedPreferences prefrance;
    String userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtOldpass=(EditText)findViewById(R.id.edtOldpass);
        edtnewPass=(EditText)findViewById(R.id.edtnewPass);
        edtConfirm=(EditText)findViewById(R.id.edtConfirm);
        btnChangePass=(Button) findViewById(R.id.btnChangePass);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setPropertyOnFocousOnText();



        prefrance=getSharedPreferences(Configs.UserPrefrance,Context.MODE_PRIVATE);
        userid= prefrance.getString("id","");


        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)     {


               /* String strEmail=edtEmail.getText().toString();
                String strPass=edtPassword.getText().toString();
                LoginUserMethod(strEmail,strPass);*/
                String oldpass=edtOldpass.getText().toString();
                String newpass=edtnewPass.getText().toString();
                String confpass=edtConfirm.getText().toString();

                if(newpass.equals(confpass))
                {
                    ChangePassword(oldpass,newpass,userid);
                }
                else
                {
                    Toasty.warning(getApplicationContext(),"Confirm password Not metch",Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void setPropertyOnFocousOnText() {

        edtOldpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edtOldpass.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                   // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                   // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edtOldpass.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
                }

            }
        });

        edtnewPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edtnewPass.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                    // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                    // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edtnewPass.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
                }

            }
        });


        edtConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edtConfirm.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                    // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                    // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edtConfirm.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
                }

            }
        });


    }

    private void ChangePassword(final String oldpass, final String newpass , final String uid) {
        Log.i("","=====email====="+oldpass);
        Log.i("","=====password====="+newpass);

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_ChangePass,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // progressBar.setVisibility(View.GONE);
                        Log.i("","111111111111111111111"+response.toString());

                        Toasty.success(ChangePasswordActivity.this,"Password Changed",Toast.LENGTH_SHORT).show();
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ChangePass", "1");
                params.put("userid", uid);
                params.put("OldPassword", oldpass);
                params.put("NewPassword", newpass);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }




    public boolean onSupportNavigateUp()
    {
        //finish();
        return true;
    }
}
