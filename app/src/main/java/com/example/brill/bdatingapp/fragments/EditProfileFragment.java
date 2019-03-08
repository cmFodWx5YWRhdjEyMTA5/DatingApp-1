package com.example.brill.bdatingapp.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.brill.bdatingapp.Configs;
import com.example.brill.bdatingapp.ConnectDetector;
import com.example.brill.bdatingapp.R;
import com.example.brill.bdatingapp.UserProfileActivity;
import com.example.brill.bdatingapp.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class EditProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {

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

    String countryName="",stateName="",cityName="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootitem;
        rootitem=inflater.inflate(R.layout.fragment_edit_profile, container, false);


        findviewbyidmethod(rootitem);
        addAlreadyData();

        GetCountryData();

        countrys=new ArrayList<String>();
        states=new ArrayList<String>();
        citys=new ArrayList<String>();

        return rootitem;
    }

    private void addAlreadyData() {

        prefrance=getActivity().getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
        sharid= prefrance.getString("id","");
        sharname= prefrance.getString("name","");
        sharUserphoto= prefrance.getString("profile_pic","");
        sharphone=prefrance.getString("phone","");

        strMinAge= prefrance.getString("minage","");
        strMaxAge= prefrance.getString("maxage","");

        strGender=prefrance.getString("gender","");

        strAge=prefrance.getString("birthday","");

        Log.i("","user age is====="+strAge);

        strEdu=prefrance.getString("education","");
        strDob=prefrance.getString("birthday","");
        strKnowLang=prefrance.getString("language","");
        strCountry=prefrance.getString("country","");
        strIntrest=prefrance.getString("interest","");
        strState=prefrance.getString("state","");
        strSmoking=prefrance.getString("smoke","");
        strCity=prefrance.getString("city","");
        strDrink=prefrance.getString("drink","");
        strRelation=prefrance.getString("rel_status","");
        strEyeColor=prefrance.getString("eye","");
        strLookfor=prefrance.getString("looking","");
        strSkinColor=prefrance.getString("skin","");
        strWorkas=prefrance.getString("work","");
        strLookRel=prefrance.getString("look_rel","");
        strAbout=prefrance.getString("about","");
        strParent=prefrance.getString("partner","");

        if(!strAge.equals("") || !strAge.equals("null"))
        {
            String yearfatch[]=strAge.split("-");

            int currentyear= Calendar.getInstance().get(Calendar.YEAR);

            int curAge=currentyear- Integer.parseInt(yearfatch[0]);
            edit_dob.setText(strDob);
        }


        edit_name.setText(sharname);
        edit_number.setText(sharphone);



        edit_about.setText(strAbout);
        edit_Partners.setText(strParent);



        ArrayAdapter<CharSequence> AdpGender = ArrayAdapter
                .createFromResource(getActivity(), R.array.gender_array,
                        R.layout.gender_array);
        AdpGender
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_gen.setAdapter(AdpGender);


        if (strGender != null) {

            int spinnerPosition = AdpGender.getPosition(strGender.substring(0,1).toUpperCase() + strGender.substring(1));
            spinner_edit_gen.setSelection(spinnerPosition);
        }



        ArrayAdapter<CharSequence> AdpLookFor = ArrayAdapter
                .createFromResource(getActivity(), R.array.lookingfor_array,
                        R.layout.gender_array);
        AdpLookFor
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_lookingfor.setAdapter(AdpLookFor);





        if (strLookfor != null) {
            int spinnerPosition = AdpLookFor.getPosition(strLookfor.substring(0,1).toUpperCase() + strLookfor.substring(1));
            spinner_edit_lookingfor.setSelection(spinnerPosition);
        }


        ArrayAdapter<CharSequence> AdpRelStatus = ArrayAdapter
                .createFromResource(getActivity(), R.array.relationStatus_array,
                        R.layout.gender_array);
        AdpRelStatus
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_relationship.setAdapter(AdpRelStatus);

        if (strRelation != null) {


            int spinnerPosition = AdpRelStatus.getPosition(strRelation.substring(0,1).toUpperCase() + strRelation.substring(1));
            spinner_edit_relationship.setSelection(spinnerPosition);
        }





        ArrayAdapter<CharSequence> AdpSmoke = ArrayAdapter
                .createFromResource(getActivity(), R.array.smoking_array,
                        R.layout.gender_array);
        AdpSmoke
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_smoking.setAdapter(AdpSmoke);

        if (strSmoking != null) {
            int spinnerPosition = AdpSmoke.getPosition(strSmoking.substring(0,1).toUpperCase() + strSmoking.substring(1));
            spinner_edit_smoking.setSelection(spinnerPosition);
        }








        ArrayAdapter<CharSequence> AdpDrink = ArrayAdapter
                .createFromResource(getActivity(), R.array.drink_array,
                        R.layout.gender_array);
        AdpDrink
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_drinking.setAdapter(AdpDrink);
        if (strDrink != null) {
            int spinnerPosition = AdpDrink.getPosition(strDrink.substring(0,1).toUpperCase() + strDrink.substring(1));
            spinner_edit_drinking.setSelection(spinnerPosition);
        }







        ArrayAdapter<CharSequence> Adpedu = ArrayAdapter
                .createFromResource(getActivity(), R.array.education_array,
                        R.layout.gender_array);
        Adpedu
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_qualification.setAdapter(Adpedu);

        if (strEdu != null) {
            int spinnerPosition = Adpedu.getPosition(strEdu.substring(0,1).toUpperCase() + strEdu.substring(1));
            spinner_edit_qualification.setSelection(spinnerPosition);
        }








        ArrayAdapter<CharSequence> AdpLang = ArrayAdapter
                .createFromResource(getActivity(), R.array.knowlang_array,
                        R.layout.gender_array);
        AdpLang
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);
        Log.i("","strLookformanohar====="+strKnowLang);
        spinner_edit_language.setAdapter(AdpLang);
        if (strKnowLang != null) {
            int spinnerPosition = AdpLang.getPosition(strKnowLang.substring(0,1).toUpperCase() + strKnowLang.substring(1));
            spinner_edit_language.setSelection(spinnerPosition);
        }









        ArrayAdapter<CharSequence> AdpMinAge = ArrayAdapter
                .createFromResource(getActivity(), R.array.minage_array,
                        R.layout.gender_array);
        AdpMinAge
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_minage.setAdapter(AdpMinAge);
        if (strMinAge != null) {
            int spinnerPosition = AdpMinAge.getPosition(strMinAge.substring(0,1).toUpperCase() + strMinAge.substring(1));
            spinner_edit_minage.setSelection(spinnerPosition);
        }











        ArrayAdapter<CharSequence> AdpMaxAge = ArrayAdapter
                .createFromResource(getActivity(), R.array.maxage_array,
                        R.layout.gender_array);
        AdpMaxAge
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_maxage.setAdapter(AdpMaxAge);
        if (strMaxAge != null) {
            int spinnerPosition = AdpMaxAge.getPosition(strMaxAge.substring(0,1).toUpperCase() + strMaxAge.substring(1));
            spinner_edit_maxage.setSelection(spinnerPosition);
        }











        ArrayAdapter<CharSequence> Adpeye = ArrayAdapter
                .createFromResource(getActivity(), R.array.eyecolor_array,
                        R.layout.gender_array);
        Adpeye
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_eyecolor.setAdapter(Adpeye);
        if (strEyeColor != null) {
            int spinnerPosition = Adpeye.getPosition(strEyeColor.substring(0,1).toUpperCase() + strEyeColor.substring(1));
            spinner_edit_eyecolor.setSelection(spinnerPosition);
        }














        ArrayAdapter<CharSequence> AdpSkin = ArrayAdapter
                .createFromResource(getActivity(), R.array.skincolor_array,
                        R.layout.gender_array);
        AdpSkin
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_skincolor.setAdapter(AdpSkin);
        if (strSkinColor != null) {
            int spinnerPosition = AdpSkin.getPosition(strSkinColor.substring(0,1).toUpperCase() + strSkinColor.substring(1));
            spinner_edit_skincolor.setSelection(spinnerPosition);
        }













        ArrayAdapter<CharSequence> AdpWork = ArrayAdapter
                .createFromResource(getActivity(), R.array.workas_array,
                        R.layout.gender_array);
        AdpWork
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_workas.setAdapter(AdpWork);

        if (strWorkas != null) {
            int spinnerPosition = AdpWork.getPosition(strWorkas.substring(0,1).toUpperCase() + strWorkas.substring(1));
            spinner_edit_workas.setSelection(spinnerPosition);
        }












        ArrayAdapter<CharSequence> AdpIntrest = ArrayAdapter
                .createFromResource(getActivity(), R.array.interast_array,
                        R.layout.gender_array);
        AdpIntrest
                .setDropDownViewResource(R.layout.spinner_dropdownitemitem);

        spinner_edit_interests.setAdapter(AdpIntrest);
        if (strIntrest != null) {
            int spinnerPosition = AdpIntrest.getPosition(strIntrest.substring(0,1).toUpperCase() + strIntrest.substring(1));
            spinner_edit_interests.setSelection(spinnerPosition);
        }


        String[] look=strLookRel.split(",");

        Log.i("","strLookRel==============="+strLookRel);

        for(int i=0;i<=look.length-1;i++)
        {
            String itemOnPosition=look[i];

            Log.i("","strLookRelplace==============="+edit_chkOpenrelation.getText().toString().toLowerCase().replace(" ",""));

            if(itemOnPosition.equals("openrelationship"))
            {

                edit_chkOpenrelation.setChecked(true);
            }
            else if(itemOnPosition.equals("livein"))
            {
                edit_chkLivein.setChecked(true);
            }
            else if(itemOnPosition.equals("onenightstand"))
            {
                edit_chkOneNight.setChecked(true);
            }
            else if(itemOnPosition.equals("friendship"))
            {
                edit_chkFriendShip.setChecked(true);
            }
            else if(itemOnPosition.equals("onlydating"))
            {
                edit_chkOnlyDating.setChecked(true);
            }
            else if(itemOnPosition.equals("food"))
            {
                edit_chkGetFood.setChecked(true);
            }
            else if(itemOnPosition.equals("longterm"))
            {
                edit_chkLongTerm.setChecked(true);
            }
            else if(itemOnPosition.equals("party"))
            {
                edit_chkGoOutParty.setChecked(true);
            }
            else if(itemOnPosition.equals("hiking"))
            {
                edit_chkGoHiking.setChecked(true);
            }
            else if(itemOnPosition.equals("secondmarriage"))
            {
                edit_chkSecoundMarriage.setChecked(true);
            }
            else if(itemOnPosition.equals("movie"))
            {
                edit_chkGoForMovie.setChecked(true);
            }
            else if(itemOnPosition.equals("tea"))
            {
                edit_chkDrinkTeaCoffee.setChecked(true);
            }


            edit_dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal= Calendar.getInstance();
                    int year=cal.get(Calendar.YEAR);
                    int month=cal.get(Calendar.MONTH);
                    int day=cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog=new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDatesetListener,year,month,day);

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


        }






    }

    private void findviewbyidmethod(View rootitem) {

        edit_chkOpenrelation=rootitem.findViewById(R.id.edit_chkOpenrelation);
        edit_chkLivein=rootitem.findViewById(R.id.edit_chkLivein);
        edit_chkOneNight=rootitem.findViewById(R.id.edit_chkOneNight);
        edit_chkFriendShip=rootitem.findViewById(R.id.edit_chkFriendShip);
        edit_chkOnlyDating=rootitem.findViewById(R.id.edit_chkOnlyDating);
        edit_chkGetFood=rootitem.findViewById(R.id.edit_chkGetFood);
        edit_chkLongTerm=rootitem.findViewById(R.id.edit_chkLongTerm);
        edit_chkGoOutParty=rootitem.findViewById(R.id.edit_chkGoOutParty);
        edit_chkGoHiking=rootitem.findViewById(R.id.edit_chkGoHiking);
        edit_chkSecoundMarriage=rootitem.findViewById(R.id.edit_chkSecoundMarriage);
        edit_chkGoForMovie=rootitem.findViewById(R.id.edit_chkGoForMovie);
        edit_chkDrinkTeaCoffee=rootitem.findViewById(R.id.edit_chkDrinkTeaCoffee);

        edit_name=(EditText) rootitem.findViewById(R.id.edit_name);
        edit_number=(EditText)rootitem.findViewById(R.id.edit_number);
        edit_dob=(EditText)rootitem.findViewById(R.id.edit_dob);

        edit_about=(EditText) rootitem.findViewById(R.id.edit_about);
        edit_Partners=(EditText) rootitem.findViewById(R.id.edit_Partners);


        spinner_edit_gen=(Spinner)rootitem.findViewById(R.id.spinner_edit_gen);
        spinner_edit_country=(Spinner)rootitem.findViewById(R.id.spinner_edit_country);
        spinner_edit_state=(Spinner)rootitem.findViewById(R.id.spinner_edit_state);
        spinner_edit_city=(Spinner)rootitem.findViewById(R.id.spinner_edit_city);
        spinner_edit_lookingfor=(Spinner)rootitem.findViewById(R.id.spinner_edit_lookingfor);
        spinner_edit_relationship=(Spinner)rootitem.findViewById(R.id.spinner_edit_relationship);
        spinner_edit_smoking=(Spinner)rootitem.findViewById(R.id.spinner_edit_smoking);
        spinner_edit_drinking=(Spinner)rootitem.findViewById(R.id.spinner_edit_drinking);
        spinner_edit_qualification=(Spinner)rootitem.findViewById(R.id.spinner_edit_qualification);
        spinner_edit_language=(Spinner)rootitem.findViewById(R.id.spinner_edit_language);
        spinner_edit_minage=(Spinner)rootitem.findViewById(R.id.spinner_edit_minage);
        spinner_edit_maxage=(Spinner)rootitem.findViewById(R.id.spinner_edit_maxage);
        spinner_edit_eyecolor=(Spinner)rootitem.findViewById(R.id.spinner_edit_eyecolor);
        spinner_edit_skincolor=(Spinner)rootitem.findViewById(R.id.spinner_edit_skincolor);
        spinner_edit_workas=(Spinner)rootitem.findViewById(R.id.spinner_edit_workas);
        spinner_edit_interests=(Spinner)rootitem.findViewById(R.id.spinner_edit_interests);





        spinner_edit_country.setOnItemSelectedListener(this);

        spinner_edit_state.setOnItemSelectedListener(this);

        spinner_edit_city.setOnItemSelectedListener(this);

        btnUpdate=(Button) rootitem.findViewById(R.id.btnUpdate);

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
                    Toasty.info(getActivity(),"Enter Your Name", Toast.LENGTH_SHORT).show();
                }
                else if(Uphone.equals("")) {
                    Toasty.info(getActivity(),"Enter Your phone number", Toast.LENGTH_SHORT).show();

                }
                else if(Updob.equals("")) {
                    Toasty.info(getActivity(),"Enter Your dob", Toast.LENGTH_SHORT).show();

                }
                else {

                    String look_rel= upopenrel+uplivein+uponlydating+upfriendship+uplongterm+uponenight+uptea+upfood+upparty+upsecmarrage+upmovie+uphiking;

                    UpdateUserDatails(Upname,Uphone, Updob, UpAbout, UpParents, upgender, upcountry, upstate, upcity, uplookfor, uprelship, upsmoke, updrink, upedu, uplang, upminage, upmaxage, upeyecolor, upskincolor, upwork, upintrest,look_rel);

                }








            }
        });

        onclickmethod();


    }

    private void UpdateUserDatails(final String upname, final String uphone, final String updob, final String upAbout, final String upParents, final String upgender, final String upcountry, final String upstate, final String upcity, final String uplookfor, final String uprelship, final String upsmoke, final String updrink, final String upedu, final String uplang, final String upminage, final String upmaxage, final String upeyecolor, final String upskincolor, final String upwork, final String upintrest, final String look_rel) {

        cd=new ConnectDetector(getActivity());
        isinternetpresent=cd.isConnectToInternet();

        if(isinternetpresent)
        {

            //Creating a string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_UpdateUserProfile,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // progressBar.setVisibility(View.GONE);
                            Log.i("","GetCountryData================"+response.toString());

                            String[] temp=response.split(",");

                            String strResp=temp[0];

                            if(strResp.equals("1"))
                            {
                                Toasty.success(getActivity(),"Profile Updated", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor=prefrance.edit();
                                editor.putString("name",upname);
                                editor.putString("phone",uphone);
                                editor.putString("gender",upgender);
                                editor.putString("birthday",updob);
                                editor.putString("country_code",temp[1]);
                                editor.putString("state_code",temp[2]);
                                editor.putString("city_code",temp[3]);
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

                                Intent i=new Intent(getActivity(),UserProfileActivity.class);
                                getActivity().startActivity(i);


                            }
                            else {
                                Toasty.error(getActivity(),"Profile Updation failed ! try again", Toast.LENGTH_SHORT).show();

                            }




                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toasty.error(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                            Log.i("","profile error========="+error.toString());
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("UpdateMyProfile", "yes");
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

            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }
        else {
            Toasty.info(getActivity(),"Connect To Internet", Toast.LENGTH_SHORT).show();

        }





    }

    private void GetCountryData() {
        cd=new ConnectDetector(getActivity());
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
                                    Toasty.error(getActivity(),"In-Valid email or password", Toast.LENGTH_SHORT).show();
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
                            Toasty.error(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

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

            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }
        else {
            Toasty.info(getActivity(),"Connect To Internet", Toast.LENGTH_SHORT).show();

        }



    }


    private void getCountry(JSONArray arr) {
        //Traversing through all the items in the json array
        for(int i=0;i<arr.length();i++){
            try {
                JSONObject json = null;
                json = arr.getJSONObject(i);
                //Getting json object
                String jname=json.getString("name");
                Log.i("","json response adfsdfsadfsd"+jname);
                //Adding the name of the student to array list

                countrys.add(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner_edit_country.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, countrys));


        if (strCountry != null) {
            int alreadySelectCountry =countrys.indexOf(strCountry);

            spinner_edit_country.setSelection(alreadySelectCountry);
        }





    }


    private void GetStateData(final String countryitem) {

        states.clear();
        cd=new ConnectDetector(getActivity());
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
                                    Toasty.error(getActivity(),"In-Valid email or password", Toast.LENGTH_SHORT).show();
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
                            Toasty.error(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

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

            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }
        else {
            Toasty.error(getActivity(),"Connect To Internet", Toast.LENGTH_SHORT).show();

        }



    }

    private void getState(JSONArray arr) {

        for(int i=0;i<arr.length();i++){
            try {
                JSONObject json = null;
                json = arr.getJSONObject(i);
                //Getting json object
                String jname=json.getString("name");
                Log.i("","json response adfsdfsadfsd"+jname);
                //Adding the name of the student to array list

                states.add(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //Setting adapter to show the items in the spinner
        spinner_edit_state.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, states));

        if (strState != null) {
            int alreadySelectState =states.indexOf(strState);

            spinner_edit_state.setSelection(alreadySelectState);
        }
    }


    private void GetCityData(final String stateitem) {
        citys.clear();
        cd=new ConnectDetector(getActivity());
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
                                    Toasty.info(getActivity(),"In-Valid email or password", Toast.LENGTH_SHORT).show();
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
                            Toasty.error(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

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

            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }
        else {
            Toasty.info(getActivity(),"Connect To Internet", Toast.LENGTH_SHORT).show();

        }



    }

    private void getCity(JSONArray arr) {

        for(int i=0;i<arr.length();i++){
            try {
                JSONObject json = null;
                json = arr.getJSONObject(i);
                //Getting json object
                String jname=json.getString("name");
                Log.i("","json response adfsdfsadfsd"+jname);
                //Adding the name of the student to array list

                citys.add(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner_edit_city.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, citys));
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
