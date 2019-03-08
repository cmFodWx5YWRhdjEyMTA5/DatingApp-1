package com.example.brill.bdatingapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brill.bdatingapp.Configs;
import com.example.brill.bdatingapp.R;

import java.util.Calendar;


public class your_profileFragment extends Fragment {

    TextView txtGender,txtAgeCrit,txtAge,txtEdu,txtDob,txtKnowLang,txtCountry,txtIntrest,txtState,txtSmoking,txtCity,txtDrink,txtRelation,txtEyeColor,txtLookfor,txtSkinColor,txtWorkas;

    String strGender,strAgeCrit,strMinAge,strMaxAge,strAge,strEdu,strDob,strKnowLang,strCountry,strIntrest,strState,strSmoking,strCity,strDrink,strRelation,strEyeColor,strLookfor,strSkinColor,strWorkas;

    SharedPreferences prefrance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootitem= inflater.inflate(R.layout.fragment_your_profile, container, false);
        findViewByIdMethod(rootitem);

        addSharedData();

        return  rootitem;
    }

    private void addSharedData() {
        prefrance=getActivity().getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
       /* sharid= prefrance.getString("id","");
        sharname= prefrance.getString("name","");
        sharGender= prefrance.getString("gender","");
        sharCity= prefrance.getString("city","");
        sharUserphoto= prefrance.getString("profile_pic","");*/

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

        String yearfatch[]=strAge.split("-");

        int currentyear= Calendar.getInstance().get(Calendar.YEAR);

        int curAge=currentyear-Integer.parseInt(yearfatch[0]);


        txtGender.setText(strGender);
        txtAgeCrit.setText(strMinAge+" - "+strMaxAge);
        txtAge.setText(Integer.toString(curAge));
        txtEdu.setText(strEdu);
        txtDob.setText(strDob);
        txtKnowLang.setText(strKnowLang);
        txtCountry.setText(strCountry);
        txtIntrest.setText(strIntrest);
        txtState.setText(strState);
        txtSmoking.setText(strSmoking);
        txtCity.setText(strCity);
        txtDrink.setText(strDrink);
        txtRelation.setText(strRelation);
        txtEyeColor.setText(strEyeColor);
        txtLookfor.setText(strLookfor);
        txtSkinColor.setText(strSkinColor);
        txtWorkas.setText(strWorkas);



    }

    private void findViewByIdMethod(View rootitem) {
        txtGender=(TextView)rootitem.findViewById(R.id.txtGender);
        txtAgeCrit=(TextView)rootitem.findViewById(R.id.txtAgeCrit);
        txtAge=(TextView)rootitem.findViewById(R.id.txtAge);
        txtEdu=(TextView)rootitem.findViewById(R.id.txtEdu);
        txtDob=(TextView)rootitem.findViewById(R.id.txtDob);
        txtKnowLang=(TextView)rootitem.findViewById(R.id.txtKnowLang);
        txtCountry=(TextView)rootitem.findViewById(R.id.txtCountry);
        txtIntrest=(TextView)rootitem.findViewById(R.id.txtIntrest);
        txtState=(TextView)rootitem.findViewById(R.id.txtState);
        txtSmoking=(TextView)rootitem.findViewById(R.id.txtSmoking);
        txtCity=(TextView)rootitem.findViewById(R.id.txtCity);
        txtDrink=(TextView)rootitem.findViewById(R.id.txtDrink);
        txtRelation=(TextView)rootitem.findViewById(R.id.txtRelation);
        txtEyeColor=(TextView)rootitem.findViewById(R.id.txtEyeColor);
        txtLookfor=(TextView)rootitem.findViewById(R.id.txtLookfor);
        txtSkinColor=(TextView)rootitem.findViewById(R.id.txtSkinColor);
        txtWorkas=(TextView)rootitem.findViewById(R.id.txtWorkas);


    }

}
