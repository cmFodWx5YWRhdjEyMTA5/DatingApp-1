package com.example.brill.bdatingapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class CompleteYourProfie extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CheckBox edit_chkOpenrelation,edit_chkLivein,edit_chkOneNight,
            edit_chkFriendShip,edit_chkOnlyDating,edit_chkGetFood,
            edit_chkLongTerm,edit_chkGoOutParty,edit_chkGoHiking,
            edit_chkSecoundMarriage,edit_chkGoForMovie,edit_chkDrinkTeaCoffee;
    SharedPreferences prefrance;

    String sharid,sharname,strGender,sharphone,sharUserphoto,strAgeCrit,strMinAge,strMaxAge,strAge,strEdu,strDob,strKnowLang,strCountry,strIntrest,strState,strSmoking,strCity,strDrink,strRelation,strEyeColor,strLookfor,strSkinColor,strLookRel,strWorkas,strAbout,strParent;


    private ArrayList<String> countrys,states,citys;


    EditText edit_name,edit_number,edit_dob,edit_about,edit_Partners;
    Spinner spinner_edit_gen,spinner_edit_country,spinner_edit_state,spinner_edit_city,spinner_edit_lookingfor,spinner_edit_relationship,spinner_edit_smoking,spinner_edit_drinking,spinner_edit_qualification,spinner_edit_language,spinner_edit_minage,spinner_edit_maxage,spinner_edit_eyecolor,spinner_edit_skincolor,spinner_edit_workas,spinner_edit_interests;

    Boolean isinternetpresent;
    ConnectDetector cd;

    JSONArray resultCountry,resultState,resultCity;
    Button btnUpdate;

    private DatePickerDialog.OnDateSetListener mDatesetListener;
    String shareid;
   String countryName="",stateName="",cityName="";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Inflate the layout for this fragment
        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        prefrance=getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);


        String useremail=(prefrance.getString("email",""));
        String userpass=(prefrance.getString("password",""));
        shareid=(prefrance.getString("id",""));

Log.i("","emailttttttttt====useremail"+useremail);
        Log.i("","emailttttttttt====userpass"+userpass);


        findviewbyidmethod();
        addAlreadyData();

        GetCountryData();

        countrys=new ArrayList<String>();
        states=new ArrayList<String>();
        citys=new ArrayList<String>();


    }

    private void addAlreadyData() {

        final SharedPreferences msharedprefranceAdmin= getSharedPreferences(Configs.UserPrefrance,MODE_PRIVATE);
        final String useremail=(msharedprefranceAdmin.getString("email",""));
        final String userpass=(msharedprefranceAdmin.getString("password",""));

        Log.i("","emaillliiillliii=========="+useremail);
        Log.i("","passllliiillliii=========="+userpass);

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_CompleteProfile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","req================="+response.toString());

                        try {

                            if(response.equals("0"))
                            {
                                Toasty.info(getApplicationContext(),"In-Valid email or password",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    SharedPreferences.Editor editor=prefrance.edit();

                                    editor.putString("id",jsonObject.getString("id"));
                                    editor.putString("name",jsonObject.getString("name"));
                                    editor.putString("email",jsonObject.getString("email"));
                                    editor.putString("phone",jsonObject.getString("phone"));
                                    editor.putString("gender",jsonObject.getString("gender"));
                                    editor.putString("birthday",jsonObject.getString("birthday"));
                                    editor.putString("profile_pic",jsonObject.getString("profile_pic"));
                                    editor.putString("cover_pic",jsonObject.getString("cover_pic"));
                                    editor.putString("login",jsonObject.getString("login"));
                                    editor.putString("status",jsonObject.getString("status"));
                                    editor.putString("creation",jsonObject.getString("creation"));
                                    editor.putString("reg_status",jsonObject.getString("reg_status"));

                                    editor.commit();



                                    setDataInSubSignUp();

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

                        Log.i("","profile error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("CompleteProfile", "yes");
                params.put("loginEmail", useremail);
                params.put("loginPassword", userpass);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);









    }

    private void setDataInSubSignUp() {
        prefrance=getApplicationContext().getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
        sharid= prefrance.getString("id","");
        sharname= prefrance.getString("name","");
        sharphone=prefrance.getString("phone","");
        strGender=prefrance.getString("gender","");
        strAge=prefrance.getString("birthday","");

        strDob=prefrance.getString("birthday","");

        if(!strAge.equals("") || !strAge.equals("null"))
        {
            String yearfatch[]=strAge.split("-");

            int currentyear= Calendar.getInstance().get(Calendar.YEAR);

            int curAge=currentyear-Integer.parseInt(yearfatch[0]);
            edit_dob.setText(strDob);
        }





        edit_name.setText(sharname);
        edit_number.setText(sharphone);

        ArrayAdapter<CharSequence> AdpGender = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.gender_array,
                        R.layout.gender_array);
        AdpGender
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_gen.setAdapter(AdpGender);


        if (strGender != null) {
            int spinnerPosition = AdpGender.getPosition(strGender.substring(0,1).toUpperCase() + strGender.substring(1));
            spinner_edit_gen.setSelection(spinnerPosition);
        }



        ArrayAdapter<CharSequence> AdpLookFor = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.lookingfor_array,
                        R.layout.gender_array);
        AdpLookFor
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_lookingfor.setAdapter(AdpLookFor);


        Log.i("","strLookformanohar====="+strLookfor);


        if (strLookfor != null) {
            int spinnerPosition = AdpLookFor.getPosition(strLookfor.substring(0,1).toUpperCase() + strLookfor.substring(1));
            spinner_edit_lookingfor.setSelection(spinnerPosition);
        }


        ArrayAdapter<CharSequence> AdpRelStatus = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.relationStatus_array,
                        R.layout.gender_array);
        AdpRelStatus
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_relationship.setAdapter(AdpRelStatus);

        if (strRelation != null) {


            int spinnerPosition = AdpRelStatus.getPosition(strRelation.substring(0,1).toUpperCase() + strRelation.substring(1));
            spinner_edit_relationship.setSelection(spinnerPosition);
        }





        ArrayAdapter<CharSequence> AdpSmoke = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.smoking_array,
                        R.layout.gender_array);
        AdpSmoke
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_smoking.setAdapter(AdpSmoke);

        if (strSmoking != null) {
            int spinnerPosition = AdpSmoke.getPosition(strSmoking.substring(0,1).toUpperCase() + strSmoking.substring(1));
            spinner_edit_smoking.setSelection(spinnerPosition);
        }


        ArrayAdapter<CharSequence> AdpDrink = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.drink_array,
                        R.layout.gender_array);
        AdpDrink
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_drinking.setAdapter(AdpDrink);
        if (strDrink != null) {
            int spinnerPosition = AdpDrink.getPosition(strDrink.substring(0,1).toUpperCase() + strDrink.substring(1));
            spinner_edit_drinking.setSelection(spinnerPosition);
        }







        ArrayAdapter<CharSequence> Adpedu = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.education_array,
                        R.layout.gender_array);
        Adpedu
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_qualification.setAdapter(Adpedu);

        if (strEdu != null) {
            int spinnerPosition = Adpedu.getPosition(strEdu.substring(0,1).toUpperCase() + strEdu.substring(1));
            spinner_edit_qualification.setSelection(spinnerPosition);
        }







        ArrayAdapter<CharSequence> AdpLang = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.knowlang_array,
                        R.layout.gender_array);
        AdpLang
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_language.setAdapter(AdpLang);
        if (strKnowLang != null) {
            int spinnerPosition = AdpLang.getPosition(strKnowLang.substring(0,1).toUpperCase() + strKnowLang.substring(1));
            spinner_edit_language.setSelection(spinnerPosition);
        }








        ArrayAdapter<CharSequence> AdpMinAge = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.minage_array,
                        R.layout.gender_array);
        AdpMinAge
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_minage.setAdapter(AdpMinAge);
        if (strMinAge != null) {
            int spinnerPosition = AdpMinAge.getPosition(strMinAge.substring(0,1).toUpperCase() + strMinAge.substring(1));
            spinner_edit_minage.setSelection(spinnerPosition);
        }











        ArrayAdapter<CharSequence> AdpMaxAge = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.maxage_array,
                        R.layout.gender_array);
        AdpMaxAge
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_maxage.setAdapter(AdpMaxAge);
        if (strMaxAge != null) {
            int spinnerPosition = AdpMaxAge.getPosition(strMaxAge.substring(0,1).toUpperCase() + strMaxAge.substring(1));
            spinner_edit_maxage.setSelection(spinnerPosition);
        }











        ArrayAdapter<CharSequence> Adpeye = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.eyecolor_array,
                        R.layout.gender_array);
        Adpeye
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_eyecolor.setAdapter(Adpeye);
        if (strEyeColor != null) {
            int spinnerPosition = Adpeye.getPosition(strEyeColor.substring(0,1).toUpperCase() + strEyeColor.substring(1));
            spinner_edit_eyecolor.setSelection(spinnerPosition);
        }














        ArrayAdapter<CharSequence> AdpSkin = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.skincolor_array,
                        R.layout.gender_array);
        AdpSkin
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_skincolor.setAdapter(AdpSkin);
        if (strSkinColor != null) {
            int spinnerPosition = AdpSkin.getPosition(strSkinColor.substring(0,1).toUpperCase() + strSkinColor.substring(1));
            spinner_edit_skincolor.setSelection(spinnerPosition);
        }













        ArrayAdapter<CharSequence> AdpWork = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.workas_array,
                        R.layout.gender_array);
        AdpWork
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_workas.setAdapter(AdpWork);

        if (strWorkas != null) {
            int spinnerPosition = AdpWork.getPosition(strWorkas.substring(0,1).toUpperCase() + strWorkas.substring(1));
            spinner_edit_workas.setSelection(spinnerPosition);
        }












        ArrayAdapter<CharSequence> AdpIntrest = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.interast_array,
                        R.layout.gender_array);
        AdpIntrest
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_interests.setAdapter(AdpIntrest);
        if (strIntrest != null) {
            int spinnerPosition = AdpIntrest.getPosition(strIntrest.substring(0,1).toUpperCase() + strIntrest.substring(1));
            spinner_edit_interests.setSelection(spinnerPosition);
        }




    }

    private void findviewbyidmethod() {




        edit_chkOpenrelation= (CheckBox) findViewById(R.id.edit_chkOpenrelation);
        edit_chkLivein= (CheckBox) findViewById(R.id.edit_chkLivein);
        edit_chkOneNight= (CheckBox) findViewById(R.id.edit_chkOneNight);
        edit_chkFriendShip= (CheckBox) findViewById(R.id.edit_chkFriendShip);
        edit_chkOnlyDating= (CheckBox) findViewById(R.id.edit_chkOnlyDating);
        edit_chkGetFood= (CheckBox) findViewById(R.id.edit_chkGetFood);
        edit_chkLongTerm= (CheckBox) findViewById(R.id.edit_chkLongTerm);
        edit_chkGoOutParty= (CheckBox) findViewById(R.id.edit_chkGoOutParty);
        edit_chkGoHiking= (CheckBox) findViewById(R.id.edit_chkGoHiking);
        edit_chkSecoundMarriage= (CheckBox) findViewById(R.id.edit_chkSecoundMarriage);
        edit_chkGoForMovie= (CheckBox) findViewById(R.id.edit_chkGoForMovie);
        edit_chkDrinkTeaCoffee= (CheckBox) findViewById(R.id.edit_chkDrinkTeaCoffee);

        edit_name=(EditText) findViewById(R.id.edit_name);
        edit_number=(EditText)findViewById(R.id.edit_number);
        edit_dob=(EditText)findViewById(R.id.edit_dob);

        edit_about=(EditText) findViewById(R.id.edit_about);
        edit_Partners=(EditText) findViewById(R.id.edit_Partners);


        spinner_edit_gen=(Spinner)findViewById(R.id.spinner_edit_gen);
        spinner_edit_country=(Spinner)findViewById(R.id.spinner_edit_country);
        spinner_edit_state=(Spinner)findViewById(R.id.spinner_edit_state);
        spinner_edit_city=(Spinner)findViewById(R.id.spinner_edit_city);
        spinner_edit_lookingfor=(Spinner)findViewById(R.id.spinner_edit_lookingfor);
        spinner_edit_relationship=(Spinner)findViewById(R.id.spinner_edit_relationship);
        spinner_edit_smoking=(Spinner)findViewById(R.id.spinner_edit_smoking);
        spinner_edit_drinking=(Spinner)findViewById(R.id.spinner_edit_drinking);
        spinner_edit_qualification=(Spinner)findViewById(R.id.spinner_edit_qualification);
        spinner_edit_language=(Spinner)findViewById(R.id.spinner_edit_language);
        spinner_edit_minage=(Spinner)findViewById(R.id.spinner_edit_minage);
        spinner_edit_maxage=(Spinner)findViewById(R.id.spinner_edit_maxage);
        spinner_edit_eyecolor=(Spinner)findViewById(R.id.spinner_edit_eyecolor);
        spinner_edit_skincolor=(Spinner)findViewById(R.id.spinner_edit_skincolor);
        spinner_edit_workas=(Spinner)findViewById(R.id.spinner_edit_workas);
        spinner_edit_interests=(Spinner)findViewById(R.id.spinner_edit_interests);





        spinner_edit_country.setOnItemSelectedListener(this);

        spinner_edit_state.setOnItemSelectedListener(this);

        spinner_edit_city.setOnItemSelectedListener(this);

        btnUpdate=(Button) findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Upname=edit_name.getText().toString();
                String Uphone=edit_number.getText().toString();
                String Updob=edit_dob.getText().toString();
                String UpAbout = edit_about.getText().toString();
                String UpParents= edit_Partners.getText().toString();


                String upgender=spinner_edit_gen.getSelectedItem().toString();
                String upcountry=spinner_edit_country.getSelectedItem().toString();
                String upstate=spinner_edit_state.getSelectedItem().toString();
                String upcity=spinner_edit_city.getSelectedItem().toString();
                String uplookfor=spinner_edit_lookingfor.getSelectedItem().toString();
                String uprelship=spinner_edit_relationship.getSelectedItem().toString();
                String upsmoke=spinner_edit_smoking.getSelectedItem().toString();
                String updrink=spinner_edit_drinking.getSelectedItem().toString();
                String upedu=spinner_edit_qualification.getSelectedItem().toString();
                String uplang=spinner_edit_language.getSelectedItem().toString();
                String upminage=spinner_edit_minage.getSelectedItem().toString();
                String upmaxage=spinner_edit_maxage.getSelectedItem().toString();
                String upeyecolor=spinner_edit_eyecolor.getSelectedItem().toString();
                String upskincolor=spinner_edit_skincolor.getSelectedItem().toString();
                String upwork=spinner_edit_workas.getSelectedItem().toString();
                String upintrest=spinner_edit_interests.getSelectedItem().toString();



                String upopenrel,uplivein,uponlydating,upfriendship,uplongterm,uponenight,uptea,upfood,upparty,upsecmarrage,upmovie,uphiking;

                if(edit_chkOpenrelation.isChecked())
                {
                    upopenrel="openrelationship,";
                }
                else{
                    upopenrel="";
                }

                if(edit_chkLivein.isChecked())
                {
                    uplivein="livein,";
                }
                else {
                    uplivein="";
                }
                if(edit_chkOneNight.isChecked())
                {
                    uponenight="onenightstand,";
                }
                else {
                    uponenight="";
                }
                if(edit_chkFriendShip.isChecked())
                {
                    upfriendship="friendship,";
                }
                else {
                    upfriendship="";
                }
                if(edit_chkOnlyDating.isChecked())
                {
                    uponlydating="onlydating,";
                }
                else {
                    uponlydating="";
                }
                if(edit_chkGetFood.isChecked())
                {
                    upfood="food,";
                }
                else {
                    upfood="";
                }
                if(edit_chkLongTerm.isChecked())
                {
                    uplongterm="longterm,";
                }
                else {
                    uplongterm="";
                }
                if(edit_chkGoOutParty.isChecked())
                {
                    upparty="party,";
                }
                else {
                    upparty="";
                }
                if(edit_chkGoHiking.isChecked())
                {
                   uphiking="hiking,";
                }
                else {
                    uphiking="";
                }
                if(edit_chkSecoundMarriage.isChecked())
                {
                    upsecmarrage="secondmarriage,";
                }
                else {
                    upsecmarrage="";
                }
                if(edit_chkGoForMovie.isChecked())
                {
                    upmovie="movie,";
                }
                else {
                    upmovie="";
                }
                if(edit_chkDrinkTeaCoffee.isChecked())
                {
                    uptea="tea";
                }
                else {
                    uptea="";
                }



                if(upgender.equals("Gender ?"))
                {
                    upgender="";
                }
                else if(uplookfor.equals("Looking for ?"))
                {
                    uplookfor="";
                }

                else if(uprelship.equals("Relationship Status ?"))
                {
                    uprelship="";
                }
                else if(upsmoke.equals("Smoking ?"))
                {
                    upsmoke="";
                }
                else if(updrink.equals("Drinking ?"))
                {
                    updrink="";
                }
                else if(upedu.equals("Education ?"))
                {
                    upedu="";
                }
                else if(uplang.equals("Know Language ?"))
                {
                    uplang="";
                }
                else if(upminage.equals("Min Age ?"))
                {
                    upminage="";
                }
                else if(upmaxage.equals("Max Age ?"))
                {
                    upmaxage="";
                }
                else if(upeyecolor.equals("Eye Color ?"))
                {
                    upeyecolor="";
                }

                else if(upskincolor.equals("Skin Color ?"))
                {
                    upskincolor="";
                }
                else if(upwork.equals("Work As ?"))
                {
                    upwork="";
                }
                else if(upintrest.equals("Interests ?"))
                {
                    upintrest="";
                }


                if(Upname.equals(""))
                {
                    Toasty.info(getApplicationContext(),"Enter Name",Toast.LENGTH_SHORT).show();
                }
                else if(Uphone.equals("")) {
                    Toasty.info(getApplicationContext(),"Enter phone number",Toast.LENGTH_SHORT).show();

                }
                else if(Updob.equals("")) {
                    Toasty.info(getApplicationContext(),"select dob",Toast.LENGTH_SHORT).show();

                }
                else if(upgender.equals("")) {
                    Toasty.info(getApplicationContext(),"select gender",Toast.LENGTH_SHORT).show();

                }
                else if(uplookfor.equals("")) {
                    Toasty.info(getApplicationContext(),"select Looking for",Toast.LENGTH_SHORT).show();

                }
                else if(uprelship.equals("")) {
                    Toasty.info(getApplicationContext(),"select relationship",Toast.LENGTH_SHORT).show();

                }
                else if(upsmoke.equals("")) {
                    Toasty.info(getApplicationContext(),"select smoking?",Toast.LENGTH_SHORT).show();

                }
                else if(updrink.equals("")) {
                    Toasty.info(getApplicationContext(),"select drinking",Toast.LENGTH_SHORT).show();

                }
                else if(upedu.equals("")) {
                    Toasty.info(getApplicationContext(),"select education",Toast.LENGTH_SHORT).show();

                }
                else if(uplang.equals("")) {
                    Toasty.info(getApplicationContext(),"select language",Toast.LENGTH_SHORT).show();

                }
                else if(upminage.equals("")) {
                    Toasty.info(getApplicationContext(),"select minimum age",Toast.LENGTH_SHORT).show();

                }
                else if(upmaxage.equals("")) {
                    Toasty.info(getApplicationContext(),"select maximum age",Toast.LENGTH_SHORT).show();

                }
                else if(upeyecolor.equals("")) {
                    Toasty.info(getApplicationContext(),"select eyes color",Toast.LENGTH_SHORT).show();

                }
                else if(upskincolor.equals("")) {
                    Toasty.info(getApplicationContext(),"select skin color",Toast.LENGTH_SHORT).show();

                }
                else {

                  String look_rel= upopenrel+uplivein+uponlydating+upfriendship+uplongterm+uponenight+uptea+upfood+upparty+upsecmarrage+upmovie+uphiking;

                    UpdateUserDatails(Upname,Uphone, Updob, UpAbout, UpParents, upgender, upcountry, upstate, upcity, uplookfor, uprelship, upsmoke, updrink, upedu, uplang, upminage, upmaxage, upeyecolor, upskincolor, upwork, upintrest,look_rel);

                }
            }
        });


        edit_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(CompleteYourProfie.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDatesetListener,year,month,day);

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

                String dateString = String.format("%04d-%02d-%2d", year, month1, dayOfMonth);


                edit_dob.setText(dateString);
            }
        };




        onclickmethod();

    }

    private void UpdateUserDatails(final String upname, final String uphone, final String updob, final String upAbout, final String upParents, final String upgender, final String upcountry, final String upstate, final String upcity, final String uplookfor, final String uprelship, final String upsmoke, final String updrink, final String upedu, final String uplang, final String upminage, final String upmaxage, final String upeyecolor, final String upskincolor, final String upwork, final String upintrest, final String look_rel) {

        cd=new ConnectDetector(getApplicationContext());
        isinternetpresent=cd.isConnectToInternet();

        if(isinternetpresent)
        {

            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_UpdateUserProfile,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // progressBar.setVisibility(View.GONE);

                            Log.i("","profile updated response======"+response);

                            if(response.equals("updateDtl"))
                            {

                                Toasty.success(getApplicationContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
                                prefrance.edit().clear().commit();

                                SharedPreferences.Editor editor=prefrance.edit();

                                editor.putString("id",shareid);
                                editor.commit();

                                Intent i=new Intent(getApplicationContext(),Dashboard.class);
                                startActivity(i);
                            }
                            else
                            {


                            }


                               /* SharedPreferences.Editor editor=prefrance.edit();

                                editor.putString("name",upname);
                                editor.putString("phone",uphone);
                                editor.putString("gender",upgender);
                                editor.putString("birthday",updob);
                                editor.putString("country",upcountry);
                                editor.putString("state",upstate);
                                editor.putString("city",upcity);
                                editor.putString("looking",uplookfor);
                                editor.putString("rel_status",uprelship);
                                editor.putString("smoke",upsmoke);
                                editor.putString("drink",updrink);
                                editor.putString("education",upedu);
                                editor.putString("language",uplang);
                                editor.putString("minage",upminage);
                                editor.putString("maxage",upmaxage);
                                editor.putString("eye",upeyecolor);
                                editor.putString("skin",upskincolor);
                                editor.putString("work",upwork);
                                editor.putString("interest",upintrest);
                                editor.putString("look_rel",look_rel);
                                editor.putString("about",upAbout);
                                editor.putString("partner",upParents);


                                editor.putString("ProfileUpdate","yes");

                                editor.commit();

                               UserProfileActivity userProfileActivity=new UserProfileActivity();

                                Intent i=new Intent(getApplicationContext(),UserProfileActivity.class);
                                startActivity(i);*/

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
                    params.put("SubmitProfileDta", "yes");
                    params.put("upuserid", sharid);
                    params.put("upname", upname);
                    params.put("uphone", uphone);
                    params.put("updob", updob);
                    params.put("upAbout", upAbout);
                    params.put("upParents", upParents);
                    params.put("upgender", upgender);
                    params.put("upcountry", upcountry);
                    params.put("upstate", upstate);
                    params.put("upcity", upcity);
                    params.put("uplookfor", uplookfor);
                    params.put("uprelship", uprelship);
                    params.put("upsmoke", upsmoke);
                    params.put("updrink", updrink);
                    params.put("upedu", upedu);
                    params.put("uplang", uplang);
                    params.put("upminage", upminage);
                    params.put("upmaxage", upmaxage);
                    params.put("upeyecolor", upeyecolor);
                    params.put("upskincolor", upskincolor);
                    params.put("upwork", upwork);
                    params.put("upintrest", upintrest);
                    params.put("upLookRel", look_rel);
                    return params;
                }
            };

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
        else {
            Toasty.info(getApplicationContext(),"Connect To Internet",Toast.LENGTH_SHORT).show();

        }





    }

    private void GetCountryData() {
        cd=new ConnectDetector(getApplicationContext());
        isinternetpresent=cd.isConnectToInternet();

        if(isinternetpresent)
        {

            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_UserProfieGetUpdate,
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

                                    resultCountry = JSObj.getJSONArray("country");
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
            Toasty.error(getApplicationContext(),"Connect To Internet",Toast.LENGTH_SHORT).show();

        }



    }


        private void getCountry(JSONArray arr) {
            //Traversing through all the items in the json array
            for(int i=0;i<arr.length();i++){
                try {
                    JSONObject json = null;
                    json = arr.getJSONObject(i);
                    //Getting json object
                    String  jname=json.getString("name");
                    Log.i("","json response adfsdfsadfsd"+jname);
                    //Adding the name of the student to array list

                    countrys.add(json.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //Setting adapter to show the items in the spinner
            spinner_edit_country.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, countrys));


            if (strCountry != null) {
                int alreadySelectCountry =countrys.indexOf(strCountry);

                spinner_edit_country.setSelection(alreadySelectCountry);
            }





        }


    private void GetStateData(final String countryitem) {

        states.clear();
        cd=new ConnectDetector(getApplicationContext());
        isinternetpresent=cd.isConnectToInternet();

        if(isinternetpresent)
        {
            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_UserProfieGetUpdate,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // progressBar.setVisibility(View.GONE);
                            Log.i("","Getstatedata================"+response.toString());

                            JSONObject JSObj = null;

                            try {

                                if(response.equals("0"))
                                {
                                    Toasty.error(getApplicationContext(),"In-Valid email or password",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                    JSObj = new JSONObject(response);
                                    resultState = JSObj.getJSONArray("state");


                                    Log.i("","sign us response get country data response"+response);

                                    Log.i("","sign us response get country dataresult"+resultState);

                                    //Calling method getStudents to get the students from the JSON Array
                                    getState(resultState);

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
                    params.put("GetState", "yes");
                    params.put("sendCountryName", countryitem);

                    Log.i("","asdsadfcountryitem====="+countryitem);

                    return params;
                }
            };

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
        else {
            Toasty.info(getApplicationContext(),"Connect To Internet",Toast.LENGTH_SHORT).show();

        }



    }

    private void getState(JSONArray arr) {

        for(int i=0;i<arr.length();i++){
            try {
                JSONObject json = null;
                json = arr.getJSONObject(i);
                //Getting json object
                String  jname=json.getString("name");
                Log.i("","json response adfsdfsadfsd"+jname);
                //Adding the name of the student to array list

                states.add(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //Setting adapter to show the items in the spinner
        spinner_edit_state.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, states));

        if (strState != null) {
            int alreadySelectState =states.indexOf(strState);

            spinner_edit_state.setSelection(alreadySelectState);
        }
    }


    private void GetCityData(final String stateitem) {
        citys.clear();
        cd=new ConnectDetector(getApplicationContext());
        isinternetpresent=cd.isConnectToInternet();

        if(isinternetpresent)
        {
            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_UserProfieGetUpdate,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // progressBar.setVisibility(View.GONE);
                            Log.i("","Getstatedata================"+response.toString());

                            JSONObject JSObj = null;

                            try {

                                if(response.equals("0"))
                                {
                                    Toasty.error(getApplicationContext(),"In-Valid email or password",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                    JSObj = new JSONObject(response);
                                    resultCity = JSObj.getJSONArray("city");


                                    Log.i("","sign us response get country data response"+response);

                                    Log.i("","sign us response get country dataresult"+resultState);

                                    //Calling method getStudents to get the students from the JSON Array
                                    getCity(resultCity);

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

                            Log.i("","profile error========="+error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("GetCity", "yes");
                    params.put("sendStateName", stateitem);

                    Log.i("","asdsadfcountryitem====="+stateitem);

                    return params;
                }
            };

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
        else {
            Toasty.info(getApplicationContext(),"Connect To Internet",Toast.LENGTH_SHORT).show();

        }



    }

    private void getCity(JSONArray arr) {

        for(int i=0;i<arr.length();i++){
            try {
                JSONObject json = null;
                json = arr.getJSONObject(i);
                //Getting json object
                String  jname=json.getString("name");
                Log.i("","json response adfsdfsadfsd"+jname);
                //Adding the name of the student to array list

                citys.add(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner_edit_city.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, citys));
        if (strCity != null) {
            int alreadySelectcity =citys.indexOf(strCity);



            Log.i("","json response adfsdfsadfsd"+alreadySelectcity);

            spinner_edit_city.setSelection(alreadySelectcity);
        }

    }



    public void onclickmethod()
    {
        edit_chkOpenrelation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    edit_chkOpenrelation.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    edit_chkOpenrelation.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    edit_chkOpenrelation.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    edit_chkOpenrelation.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        edit_chkLivein.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    edit_chkLivein.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    edit_chkLivein.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    edit_chkLivein.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    edit_chkLivein.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });

        edit_chkOneNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    edit_chkOneNight.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    edit_chkOneNight.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    edit_chkOneNight.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    edit_chkOneNight.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });

        edit_chkFriendShip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    edit_chkFriendShip.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    edit_chkFriendShip.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    edit_chkFriendShip.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    edit_chkFriendShip.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });

        edit_chkOnlyDating.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    edit_chkOnlyDating.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    edit_chkOnlyDating.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    edit_chkOnlyDating.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    edit_chkOnlyDating.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });

        edit_chkGetFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    edit_chkGetFood.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    edit_chkGetFood.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    edit_chkGetFood.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    edit_chkGetFood.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });

        edit_chkLongTerm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    edit_chkLongTerm.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    edit_chkLongTerm.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    edit_chkLongTerm.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    edit_chkLongTerm.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        edit_chkGoOutParty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    edit_chkGoOutParty.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    edit_chkGoOutParty.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    edit_chkGoOutParty.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    edit_chkGoOutParty.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        edit_chkGoHiking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    edit_chkGoHiking.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    edit_chkGoHiking.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    edit_chkGoHiking.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    edit_chkGoHiking.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        edit_chkSecoundMarriage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    edit_chkSecoundMarriage.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    edit_chkSecoundMarriage.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    edit_chkSecoundMarriage.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    edit_chkSecoundMarriage.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });


        edit_chkGoForMovie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    edit_chkGoForMovie.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    edit_chkGoForMovie.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    edit_chkGoForMovie.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    edit_chkGoForMovie.setTextColor(getResources().getColor(R.color.colorPrimary));

                }

            }
        });

        edit_chkDrinkTeaCoffee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    edit_chkDrinkTeaCoffee.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn));
                    edit_chkDrinkTeaCoffee.setTextColor(getResources().getColor(R.color.colorWhite));

                }
                else {
                    edit_chkDrinkTeaCoffee.setBackground(getResources().getDrawable(R.drawable.rounded_bbtn_actn2));
                    edit_chkDrinkTeaCoffee.setTextColor(getResources().getColor(R.color.colorPrimary));

                }
            }
        });


        edit_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edit_name.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                    // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                    // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edit_name.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
                }

            }
        });

        edit_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edit_number.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                    // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                    // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edit_number.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
                }

            }
        });

        edit_dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edit_dob.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                    // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                    // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edit_dob.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
                }

            }
        });


        edit_about.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edit_about.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                    // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                    // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edit_about.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
                }

            }
        });


        edit_Partners.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edit_Partners.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                    // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                    // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edit_Partners.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
                }

            }
        });




    }


    @Override
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
                Log.d("","regionid:country" + item);

               String countryitem=item;

                GetStateData(countryitem);
               /* selectPosition=position;
                if(selectPosition!=0)
                {
                    selectPosition=position+1;
                    GetStateData();

                    Log.d("form","regionid:country position is=====" + selectPosition);
                }*/

                Log.d("form","regionid:country position is=====" + position);
                break;
            case R.id.spinner_edit_state:
                Log.d("form","state id:");

                String items = parent.getItemAtPosition(position).toString();
                Log.d("form","regionid: state" + items);

                GetCityData(items);

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
