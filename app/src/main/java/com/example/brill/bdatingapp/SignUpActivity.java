package com.example.brill.bdatingapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    Spinner spinnerGender;
    EditText edtEmail,edtFullName,edtPhone,edtPassword,edtdob;
    Button btnSignup;
    private DatePickerDialog.OnDateSetListener mDatesetListener;

    SharedPreferences prefrance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefrance=getSharedPreferences(Configs.UserPrefrance,Context.MODE_PRIVATE);


        edtEmail=(EditText)findViewById(R.id.edtEmail);
        edtFullName=(EditText)findViewById(R.id.edtFullName);
        edtPhone=(EditText)findViewById(R.id.edtPhone);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        edtdob=(EditText)findViewById(R.id.edtdob);

        btnSignup=(Button)findViewById(R.id.btnSignup);

        spinnerGender= (Spinner) findViewById(R.id.spinnerGender);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.login_array,
                        R.layout.gender_array);


        staticAdapter
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinnerGender.setAdapter(staticAdapter);

        edtdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(SignUpActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDatesetListener,year,month,day);


                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDatesetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int month1=month+1;
                Log.i("","Data from Date picker=====year"+year);
                Log.i("","Data from Date picker=====month"+month);
                Log.i("","Data from Date picker=====dayOfMonth"+dayOfMonth);

                edtdob.setText(year+"-"+month1+"-"+dayOfMonth);
            }
        };


        setPropertyOnFocousOnText();


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uemail,ufname,uphone,upass,ugender,udob;
                uemail=edtEmail.getText().toString();
                ufname=edtFullName.getText().toString();
                uphone=edtPhone.getText().toString();
                upass=edtPassword.getText().toString();
                udob=edtdob.getText().toString();
                ugender=spinnerGender.getSelectedItem().toString();

                Boolean mail= isValidMail(uemail);
                int pass=upass.length();

                if(uemail.equals("") || ufname.equals("") || uphone.equals("") || upass.equals("") || ugender.equals("") || udob.equals(""))
                {
                    Toasty.info(getApplicationContext(),"Please fill all form",Toast.LENGTH_SHORT).show();
                }
                else if(!mail.equals(true))
                {
                    Toasty.error(getApplicationContext(),"InValid Email",Toast.LENGTH_SHORT).show();
                }
                else if (!(pass>=8))
                {
                    Toasty.error(getApplicationContext(),"Enter Atleast 8 Digits Password !",Toast.LENGTH_SHORT).show();

                }
                else if (ugender.equals("Gender?"))
                {
                    Toasty.info(getApplicationContext(),"Select Your Gender",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    CreateAccountMethod(ufname,uemail,uphone,upass,udob,ugender);

                }



            }
        });

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

    private void CreateAccountMethod(final String ufname, final String uemail, final String uphone, final String upass, final String udob, final String ugender) {



            //if everything is fine
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.urlsignup,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // progressBar.setVisibility(View.GONE);


                            SharedPreferences.Editor editor=prefrance.edit();
                            editor.putString("id",response);
                            editor.putString("email",uemail);
                            editor.putString("password",upass);
                            editor.commit();


                        Toast.makeText(getApplicationContext(),"Created",Toast.LENGTH_SHORT).show();


                            Intent i=new Intent(getApplicationContext(),CompleteYourProfie.class);
                            startActivity(i);

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
                    params.put("CreateAccount", "1");
                    params.put("usname", ufname);
                    params.put("usemail", uemail);
                    params.put("usphone", uphone);
                    params.put("uspass", upass);
                    params.put("usdob", udob);
                    params.put("usgender", ugender);

                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void setOnClickItemComponent() {

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

        edtFullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edtFullName.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                    // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                    // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edtFullName.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
                }

            }
        });
        edtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edtPhone.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                    // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                    // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edtPhone.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
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
 /*       edtdob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edtdob.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                    // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                    // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edtdob.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
                }

            }
        });*/

    }

    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }
}
