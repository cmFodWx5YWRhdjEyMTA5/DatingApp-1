package com.example.brill.bdatingapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brill.bdatingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatingFragment extends android.support.v4.app.Fragment {


    public DatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_dating, container, false);

        return rootView;
    }

}
