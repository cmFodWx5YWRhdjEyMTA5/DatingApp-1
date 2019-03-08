package com.example.brill.bdatingapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brill.bdatingapp.R;

public class UserAboutFragment extends Fragment {
TextView txtAboutUs;
    SharedPreferences prefrance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootitem= inflater.inflate(R.layout.fragment_user_about, container, false);

        txtAboutUs=(TextView) rootitem.findViewById(R.id.txtAboutUs);
        prefrance=getActivity().getSharedPreferences("selectedUser", Context.MODE_PRIVATE);
        String about= prefrance.getString("about","");
        txtAboutUs.setText(about);
        return rootitem;
    }

}
