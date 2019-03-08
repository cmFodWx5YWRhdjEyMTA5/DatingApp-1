package com.example.brill.bdatingapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.brill.bdatingapp.Configs;
import com.example.brill.bdatingapp.R;
import com.example.brill.bdatingapp.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ProfilePhotosFragment extends Fragment {
    ImageView immBtn1,immBtn2,immBtn3,immBtn4,immBtn5,immBtn6,immBtn7,immBtn8,immBtn9;
    SharedPreferences prefrance;
    String sharid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootitem= inflater.inflate(R.layout.fragment_profile_photos, container, false);

        findviewbyidmethod(rootitem);

        return rootitem;
    }

    private void findviewbyidmethod(View rootitem) {
        immBtn1=(ImageView)rootitem.findViewById(R.id.immBtn1);
        immBtn2=(ImageView)rootitem.findViewById(R.id.immBtn2);
        immBtn3=(ImageView)rootitem.findViewById(R.id.immBtn3);
        immBtn4=(ImageView)rootitem.findViewById(R.id.immBtn4);
        immBtn5=(ImageView)rootitem.findViewById(R.id.immBtn5);
        immBtn6=(ImageView)rootitem.findViewById(R.id.immBtn6);
        immBtn7=(ImageView)rootitem.findViewById(R.id.immBtn7);
        immBtn8=(ImageView)rootitem.findViewById(R.id.immBtn8);
        immBtn9=(ImageView)rootitem.findViewById(R.id.immBtn9);
        prefrance=getActivity().getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
        sharid= prefrance.getString("id","");
        LoginUserMethod();
    }


    private void LoginUserMethod() {


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","ooooooooooooooooo"+response.toString());

                        try {

                            if(response.equals("0"))
                            {
                                Toasty.error(getActivity(),"In-Valid email or password",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                JSONObject obj = new JSONObject(response);
                                JSONObject userJson = obj.getJSONObject("0");

                                String id=userJson.getString("id");
                                String photo1=userJson.getString("photo1");
                                String photo2=userJson.getString("photo2");
                                String photo3=userJson.getString("photo3");
                                String photo4=userJson.getString("photo4");
                                String photo5=userJson.getString("photo5");
                                String photo6=userJson.getString("photo6");
                                String photo7=userJson.getString("photo7");
                                String photo8=userJson.getString("photo8");
                                String photo9=userJson.getString("photo9");


                                Log.i("","photo1=============="+photo1);
                                Log.i("","photo1=============="+photo2);
                                Log.i("","photo1=============="+photo3);
                                Log.i("","photo1=============="+photo4);
                                Log.i("","photo1=============="+photo5);
                                Log.i("","photo1=============="+photo6);
                                Log.i("","photo1=============="+photo7);
                                Log.i("","photo1=============="+photo8);
                                Log.i("","photo1=============="+photo9);



        if(!photo1.equals(""))
        {
            immBtn1.setVisibility(View.VISIBLE);
            String url= Configs.userAddedphotos+photo1;
            Log.i("","=======photo from shared pref======="+url);
            Glide.with(getActivity()).load(url).into(immBtn1);
        }
         if(!photo2.equals(""))
        {
            immBtn2.setVisibility(View.VISIBLE);
            String url= Configs.userAddedphotos+photo2;
            Log.i("","=======photo from shared pref=======2222222"+url);
            Glide.with(getActivity()).load(url).into(immBtn2);
        }
                                if(!photo3.equals(""))
                                {
                                    immBtn3.setVisibility(View.VISIBLE);
                                    String url= Configs.userAddedphotos+photo3;
                                    Log.i("","=======photo from shared pref=======2222222"+url);
                                    Glide.with(getActivity()).load(url).into(immBtn3);
                                }
                                if(!photo4.equals(""))
                                {
                                    immBtn4.setVisibility(View.VISIBLE);
                                    String url= Configs.userAddedphotos+photo4;
                                    Log.i("","=======photo from shared pref=======2222222"+url);
                                    Glide.with(getActivity()).load(url).into(immBtn4);
                                }
                                if(!photo5.equals(""))
                                {
                                    immBtn5.setVisibility(View.VISIBLE);
                                    String url= Configs.userAddedphotos+photo5;
                                    Log.i("","=======photo from shared pref=======2222222"+url);
                                    Glide.with(getActivity()).load(url).into(immBtn5);
                                }
                                if(!photo6.equals(""))
                                {
                                    immBtn6.setVisibility(View.VISIBLE);
                                    String url= Configs.userAddedphotos+photo6;
                                    Log.i("","=======photo from shared pref=======2222222"+url);
                                    Glide.with(getActivity()).load(url).into(immBtn6);
                                }
                                if(!photo7.equals(""))
                                {
                                    immBtn7.setVisibility(View.VISIBLE);
                                    String url= Configs.userAddedphotos+photo7;
                                    Log.i("","=======photo from shared pref=======2222222"+url);
                                    Glide.with(getActivity()).load(url).into(immBtn7);
                                }
                                if(!photo8.equals(""))
                                {
                                    immBtn8.setVisibility(View.VISIBLE);
                                    String url= Configs.userAddedphotos+photo8;
                                    Log.i("","=======photo from shared pref=======2222222"+url);
                                    Glide.with(getActivity()).load(url).into(immBtn8);
                                }
                                if(!photo9.equals(""))
                                {
                                    immBtn2.setVisibility(View.VISIBLE);
                                    String url= Configs.userAddedphotos+photo9;
                                    Log.i("","=======photo from shared pref=======2222222"+url);
                                    Glide.with(getActivity()).load(url).into(immBtn9);
                                }


                               /* SharedPreferences.Editor editor=getActivity().prefrance.edit();
                                editor.putString("id",userJson.getString("id"));
                                editor.putString("name",userJson.getString("name"));
                                editor.putString("email",userJson.getString("email"));

                                editor.commit();*/




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
                params.put("UserPhotos", "yes");
                params.put("userid", sharid);

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


}
