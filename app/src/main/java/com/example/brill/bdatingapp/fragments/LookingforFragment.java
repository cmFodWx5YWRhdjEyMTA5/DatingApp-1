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


public class LookingforFragment extends Fragment {

    TextView txtopenrel, txtlivein, txtonenightstand, txtfriendship, txtonlydating, txtlongterm, txtfood, txtparty, txthiking, txtsecmarriage, txtmovie, txttea;
    SharedPreferences prefrance;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootitem = inflater.inflate(R.layout.fragment_lookingfor, container, false);
        prefrance = getActivity().getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
        String Lookfor = prefrance.getString("look_rel", "");

        Log.i("","loolfor sdfasdfasdf==="+Lookfor);



        txtopenrel = (TextView) rootitem.findViewById(R.id.txtopenrel);
        txtlivein = (TextView) rootitem.findViewById(R.id.txtlivein);
        txtonenightstand = (TextView) rootitem.findViewById(R.id.txtonenightstand);
        txtfriendship = (TextView) rootitem.findViewById(R.id.txtfriendship);
        txtonlydating = (TextView) rootitem.findViewById(R.id.txtonlydating);
        txtlongterm = (TextView) rootitem.findViewById(R.id.txtlongterm);
        txtfood = (TextView) rootitem.findViewById(R.id.txtfood);
        txtparty = (TextView) rootitem.findViewById(R.id.txtparty);
        txthiking = (TextView) rootitem.findViewById(R.id.txthiking);
        txtsecmarriage = (TextView) rootitem.findViewById(R.id.txtsecmarriage);
        txtmovie = (TextView) rootitem.findViewById(R.id.txtmovie);
        txttea = (TextView) rootitem.findViewById(R.id.txttea);



        String[] look=Lookfor.split(",");

        for (int i = 0; i <= look.length - 1; i++) {
            String itemOnPosition = look[i];

            Log.i("","loolfor iiiiiiiiiiiiif==="+look[i]);

            if (itemOnPosition.equals("openrelationship")) {
                txtopenrel.setVisibility(View.VISIBLE);
                txtopenrel.setText("Open Relationship");
            } else if (itemOnPosition.equals("livein")) {
                txtlivein.setVisibility(View.VISIBLE);
                txtlivein.setText("Live-In");
            } else if (itemOnPosition.equals("onenightstand")) {
                txtonenightstand.setVisibility(View.VISIBLE);
                txtonenightstand.setText("One Night Stand");
            } else if (itemOnPosition.equals("friendship")) {
                txtfriendship.setVisibility(View.VISIBLE);
                txtfriendship.setText("Friendship");
            } else if (itemOnPosition.equals("onlydating")) {
                txtonlydating.setVisibility(View.VISIBLE);
                txtonlydating.setText("Only Dating");
            } else if (itemOnPosition.equals("food")) {
                txtlongterm.setVisibility(View.VISIBLE);
                txtlongterm.setText("Get Some Food");
            } else if (itemOnPosition.equals("longterm")) {
                txtfood.setVisibility(View.VISIBLE);
                txtfood.setText("Long-Term");
            } else if (itemOnPosition.equals("party")) {
                txtparty.setVisibility(View.VISIBLE);
                txtparty.setText("Go Out And Party");
            } else if (itemOnPosition.equals("hiking")) {
                txthiking.setVisibility(View.VISIBLE);
                txthiking.setText("Go Hiking");
            } else if (itemOnPosition.equals("secondmarriage")) {
                txtsecmarriage.setVisibility(View.VISIBLE);
                txtsecmarriage.setText("Secound Marriage");
            } else if (itemOnPosition.equals("movie")) {
                txtmovie.setVisibility(View.VISIBLE);
                txtmovie.setText("Go For Movie");
            } else if (itemOnPosition.equals("tea")) {
                txttea.setVisibility(View.VISIBLE);
                txttea.setText("Drink Tea or Coffee");
            }


        }

        return rootitem;

    }

}
