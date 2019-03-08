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

import com.example.brill.bdatingapp.R;

import java.util.Calendar;


public class VisitUserProfileFragment extends Fragment {

    SharedPreferences pref;

    TextView text_user_gen,text_user_agecriteria,text_user_age,text_user_qualiftn,text_user_dob,text_user_know,text_user_country,text_user_interest,text_user_state,text_user_smoking,text_user_city,text_user_drinking,text_user_relationship,text_user_eyecolor,text_user_lookingfor,text_user_skincolor,text_user_workas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootitem= inflater.inflate(R.layout.fragment_visit_user_profile, container, false);

        pref=getActivity().getSharedPreferences("selectedUser", Context.MODE_PRIVATE);
        findviewbyidMethod(rootitem);

        addUserProfileDataMethod();

        return rootitem;
    }

    private void addUserProfileDataMethod() {

        try{

            String strAge=pref.getString("birthday","");

            if(!strAge.equals(""))
            {
                String yearfatch[]=strAge.split("-");

                int currentyear= Calendar.getInstance().get(Calendar.YEAR);

                int curAge=currentyear-Integer.parseInt(yearfatch[0]);

                Log.i("","curAge========"+curAge+"aaa"+currentyear);
                text_user_age.setText(Integer.toString(curAge)  );
            }


        }catch(NumberFormatException ex){ // handle your exception


        }




        text_user_gen.setText(pref.getString("gender",""));
                text_user_agecriteria.setText(pref.getString("minage","")+"-"+pref.getString("maxage",""));

                text_user_qualiftn.setText(pref.getString("education",""));
        text_user_dob.setText(pref.getString("birthday",""));
                text_user_know.setText(pref.getString("language",""));
        text_user_country.setText(pref.getString("country",""));
                text_user_interest.setText(pref.getString("interest",""));
        text_user_state.setText(pref.getString("state",""));
                text_user_smoking.setText(pref.getString("smoke",""));
        text_user_city.setText(pref.getString("city",""));
                text_user_drinking.setText(pref.getString("drink",""));
        text_user_relationship.setText(pref.getString("rel_status",""));
                text_user_eyecolor.setText(pref.getString("eye",""));
        text_user_lookingfor.setText(pref.getString("looking",""));
                text_user_skincolor.setText(pref.getString("skin",""));
        text_user_workas.setText(pref.getString("work",""));



    }

    private void findviewbyidMethod(View rootitem) {

       text_user_gen=(TextView)rootitem.findViewById(R.id.text_user_gen);
               text_user_agecriteria=(TextView)rootitem.findViewById(R.id.text_user_agecriteria);
        text_user_age=(TextView)rootitem.findViewById(R.id.text_user_age);
                text_user_qualiftn=(TextView)rootitem.findViewById(R.id.text_user_qualiftn);
        text_user_dob=(TextView)rootitem.findViewById(R.id.text_user_dob);
                text_user_know=(TextView)rootitem.findViewById(R.id.text_user_know);
        text_user_country=(TextView)rootitem.findViewById(R.id.text_user_country);
                text_user_interest=(TextView)rootitem.findViewById(R.id.text_user_interest);
        text_user_state=(TextView)rootitem.findViewById(R.id.text_user_state);
                text_user_smoking=(TextView)rootitem.findViewById(R.id.text_user_smoking);
        text_user_city=(TextView)rootitem.findViewById(R.id.text_user_city);
                text_user_drinking=(TextView)rootitem.findViewById(R.id.text_user_drinking);
        text_user_relationship=(TextView)rootitem.findViewById(R.id.text_user_relationship);
                text_user_eyecolor=(TextView)rootitem.findViewById(R.id.text_user_eyecolor);
        text_user_lookingfor=(TextView)rootitem.findViewById(R.id.text_user_lookingfor);
                text_user_skincolor=(TextView)rootitem.findViewById(R.id.text_user_skincolor);
        text_user_workas=(TextView)rootitem.findViewById(R.id.text_user_workas);
    }

}
