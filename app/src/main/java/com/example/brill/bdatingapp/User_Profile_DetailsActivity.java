package com.example.brill.bdatingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

public class User_Profile_DetailsActivity extends AppCompatActivity {

    String viewUserId,viewUserfname,viewUserlname,viewUsersex,viewUserdob,viewUsercountry,viewUserstate,viewUsercity;

    Button bttn_addfaviourate,bttn_block,bttn_send_msg,bttn_interest;

    TextView text_username,text_gender,text_sate_country_profile,text_dob;

    TextView my_pf_hair_color_text,my_pf_hair_length_text,my_pf_hair_type_text,my_pf_eye_color_text,my_pf_eye_wear_text,my_pf_height_text,my_pf_weight_text,my_pf_body_type_text,my_pf_ethncty_text,my_pf_cmplxn_text,my_pf_facial_color_text,my_pf_my_app_text,my_pf_hlth_sts_text;
    TextView my_pf_do_you_drink_text,my_pf_do_you_smoke_text,my_pf_etng_hbts_text,my_pf_mrtl_sts_text,my_pf_do_you_hv_chld_text,my_pf_want_more_child_text,my_pf_occptn_text,my_pf_emp_sts_text,my_pf_annual_icm_text,my_pf_home_type_text,my_pf_lvng_stn_text,my_pf_resdncy_text,my_pf_wlng_rlct_text;
    TextView my_pf_nationality_text,my_pf_education_text,my_pf_lang_text,my_pf_relegion_text,my_pf_born_text,my_pf_relg_vl_text,my_pf_rlgs_srvc_text,my_pf_read_qu_text,my_pf_plgmy_text,my_pf_fmly_vl_text,my_pf_creatr_text;
    TextView my_pf_profile_heading_text,my_pf_abt_yourself_text,my_pf_lkng_for_text;

    Button btnBasicInfo,btnappearance_info,btnlifestyle_info,btncultural_info,btnownwords_info;
    LinearLayout layline_basic_info,layline_your_appearance,layline_lifestyle,layline_cultural,layline_ownwords_info;

    ScrollView scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile__details);

        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        findViewbyidMethod();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final SharedPreferences pref=getSharedPreferences(Configs.ChatingToPrefrance,MODE_PRIVATE);
        viewUserId=(pref.getString("ChatToId",""));
        viewUserfname=(pref.getString("fname",""));
        viewUserlname=(pref.getString("lname",""));
        viewUsersex=(pref.getString("sex",""));
        viewUserdob=(pref.getString("dob",""));

        viewUsercountry=(pref.getString("country",""));
        viewUserstate=(pref.getString("state",""));
        viewUsercity=(pref.getString("city",""));

        setTitle(viewUserfname);

        bttn_addfaviourate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFaviourate();
            }
        });


        btnappearance_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layline_your_appearance.getVisibility()==View.VISIBLE)
                {
                    layline_your_appearance.setVisibility(View.GONE);
                }
                else
                {
                    layline_your_appearance.setVisibility(View.VISIBLE);
                }

            }
        });
        btnlifestyle_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layline_lifestyle.getVisibility()==View.VISIBLE)
                {
                    layline_lifestyle.setVisibility(View.GONE);
                }
                else
                {
                    layline_lifestyle.setVisibility(View.VISIBLE);
                }

            }
        });

        btncultural_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(layline_cultural.getVisibility()==View.VISIBLE)
                {
                    layline_cultural.setVisibility(View.GONE);
                }
                else
                {
                    layline_cultural.setVisibility(View.VISIBLE);
                }

            }
        });
        btnownwords_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layline_ownwords_info.getVisibility()==View.VISIBLE)
                {
                    layline_ownwords_info.setVisibility(View.GONE);
                    scroll.fullScroll(View.FOCUS_DOWN);

                }
                else
                {
                    layline_ownwords_info.setVisibility(View.VISIBLE);
                    scroll.fullScroll(View.FOCUS_DOWN);

                }

            }
        });





        getViewProfileUser();




                if(viewUsersex.equals("M"))
                {
                    text_gender.setText("Male");
                }
                else
                {
                    text_gender.setText("Female");
                }


        if(!viewUserdob.equals(""))
        {
            String strMonth="";
            String[] temp1;
            temp1=viewUserdob.split("-");
            String monthis=temp1[1];

            if(monthis.equals("1"))
            {
                strMonth="January";
            }
            else if(monthis.equals("2"))
            {
                strMonth="February";
            }
            else if(monthis.equals("3"))
            {
                strMonth="March";
            }
            else if(monthis.equals("4"))
            {
                strMonth="April";
            }
            else if(monthis.equals("5"))
            {
                strMonth="May";
            }
            else if(monthis.equals("6"))
            {
                strMonth="June";
            }
            else if(monthis.equals("7"))
            {
                strMonth="July";
            } else if(monthis.equals("8"))
            {
                strMonth="August";
            }
            else if(monthis.equals("9"))
            {
                strMonth="September";
            }
            else if(monthis.equals("10"))
            {
                strMonth="October";
            }
            else if(monthis.equals("11"))
            {
                strMonth="November";
            }
            else if(monthis.equals("12"))
            {
                strMonth="December";
            }
            else
            {

            }


            text_dob.setText(temp1[2]+" "+strMonth+" "+temp1[0]);

        }





        text_username.setText(viewUserfname+" "+viewUserlname);
        text_sate_country_profile.setText(viewUsercountry+", "+viewUserstate+", "+viewUsercity);

    }

    private void findViewbyidMethod() {

        scroll=(ScrollView)findViewById(R.id.scroll);


        bttn_addfaviourate=(Button)findViewById(R.id.bttn_addfaviourate);
        bttn_block=(Button)findViewById(R.id.bttn_block);
        bttn_send_msg=(Button)findViewById(R.id.bttn_send_msg);
        bttn_interest=(Button)findViewById(R.id.bttn_interest);
        text_username=(TextView)findViewById(R.id.text_username);
        text_gender=(TextView)findViewById(R.id.text_gen_age_id_profile);
        text_sate_country_profile=(TextView)findViewById(R.id.text_sate_country_profile);
        text_dob=(TextView)findViewById(R.id.text_dob);


                my_pf_hair_color_text=(TextView)findViewById(R.id.my_pf_hair_color_text);
                my_pf_hair_length_text=(TextView)findViewById(R.id.my_pf_hair_length_text);
                my_pf_hair_type_text=(TextView)findViewById(R.id.my_pf_hair_type_text);
                my_pf_eye_color_text=(TextView)findViewById(R.id.my_pf_eye_color_text);
                my_pf_eye_wear_text=(TextView)findViewById(R.id.my_pf_eye_wear_text);
                my_pf_height_text=(TextView)findViewById(R.id.my_pf_height_text);
                my_pf_weight_text=(TextView)findViewById(R.id.my_pf_weight_text);
                my_pf_body_type_text=(TextView)findViewById(R.id.my_pf_body_type_text);
                my_pf_ethncty_text=(TextView)findViewById(R.id.my_pf_ethncty_text);
                my_pf_cmplxn_text=(TextView)findViewById(R.id.my_pf_cmplxn_text);
                my_pf_facial_color_text=(TextView)findViewById(R.id.my_pf_facial_color_text);
                my_pf_my_app_text=(TextView)findViewById(R.id.my_pf_my_app_text);
                my_pf_hlth_sts_text=(TextView)findViewById(R.id.my_pf_hlth_sts_text);
                my_pf_do_you_drink_text=(TextView)findViewById(R.id.my_pf_do_you_drink_text);
                my_pf_do_you_smoke_text=(TextView)findViewById(R.id.my_pf_do_you_smoke_text);
                my_pf_etng_hbts_text=(TextView)findViewById(R.id.my_pf_etng_hbts_text);
                my_pf_mrtl_sts_text=(TextView)findViewById(R.id.my_pf_mrtl_sts_text);
                my_pf_do_you_hv_chld_text=(TextView)findViewById(R.id.my_pf_do_you_hv_chld_text);
                my_pf_want_more_child_text=(TextView)findViewById(R.id.my_pf_want_more_child_text);
                my_pf_occptn_text=(TextView)findViewById(R.id.my_pf_occptn_text);
                my_pf_emp_sts_text=(TextView)findViewById(R.id.my_pf_emp_sts_text);
                my_pf_annual_icm_text=(TextView)findViewById(R.id.my_pf_annual_icm_text);
                my_pf_home_type_text=(TextView)findViewById(R.id.my_pf_home_type_text);
                my_pf_lvng_stn_text=(TextView)findViewById(R.id.my_pf_lvng_stn_text);
                my_pf_resdncy_text=(TextView)findViewById(R.id.my_pf_resdncy_text);
                my_pf_wlng_rlct_text=(TextView)findViewById(R.id.my_pf_wlng_rlct_text);
                my_pf_nationality_text=(TextView)findViewById(R.id.my_pf_nationality_text);
                my_pf_education_text=(TextView)findViewById(R.id.my_pf_education_text);
                my_pf_lang_text=(TextView)findViewById(R.id.my_pf_lang_text);
                my_pf_relegion_text=(TextView)findViewById(R.id.my_pf_relegion_text);
                my_pf_born_text=(TextView)findViewById(R.id.my_pf_born_text);
                my_pf_relg_vl_text=(TextView)findViewById(R.id.my_pf_relg_vl_text);
                my_pf_rlgs_srvc_text=(TextView)findViewById(R.id.my_pf_rlgs_srvc_text);
                my_pf_read_qu_text=(TextView)findViewById(R.id.my_pf_read_qu_text);
                my_pf_plgmy_text=(TextView)findViewById(R.id.my_pf_plgmy_text);
                my_pf_fmly_vl_text=(TextView)findViewById(R.id.my_pf_fmly_vl_text);
                my_pf_creatr_text=(TextView)findViewById(R.id.my_pf_creatr_text);
        my_pf_profile_heading_text=(TextView)findViewById(R.id.my_pf_profile_heading_text);
                my_pf_abt_yourself_text=(TextView)findViewById(R.id.my_pf_abt_yourself_text);
                my_pf_lkng_for_text=(TextView)findViewById(R.id.my_pf_lkng_for_text);



     //   layline_basic_info=(LinearLayout)findViewById(R.id.layline_basic_info);
        layline_your_appearance=(LinearLayout)findViewById(R.id.layline_your_appearance);
        layline_lifestyle=(LinearLayout)findViewById(R.id.layline_lifestyle);
        layline_cultural=(LinearLayout)findViewById(R.id.layline_cultural);
        layline_ownwords_info=(LinearLayout)findViewById(R.id.layline_ownwords_info);


      //  btnBasicInfo=(Button)findViewById(R.id.btnbasic_info);
        btnappearance_info=(Button)findViewById(R.id.btnappearance_info);
        btnlifestyle_info=(Button)findViewById(R.id.btnlifestyle_info);
        btncultural_info=(Button)findViewById(R.id.btncultural_info);
        btnownwords_info=(Button)findViewById(R.id.btnownwords_info);






    }

    private void AddFaviourate() {

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_ViewUserProfile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","view111111111111111111111"+response.toString());

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject userJson = obj.getJSONObject("0");
                            //Log.i("","111111111111111111111"+response.toString());
                            String jid=userJson.getString("intId");
                            Log.i("","1fgsdg11111111111111111111"+jid);



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
                params.put("UserId", viewUserId);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    public void getViewProfileUser()
    {

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_ViewUserProfile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar.setVisibility(View.GONE);
                        Log.i("","view111111111111111111111is===="+response.toString());

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject userJson = obj.getJSONObject("0");
                            //Log.i("","111111111111111111111"+response.toString());
                            String jid=userJson.getString("intId");
                            Log.i("","1fgsdg11111111111111111111sadf=="+jid);


                            my_pf_hair_color_text.setText(userJson.getString("hair_color"));
                            my_pf_hair_length_text.setText(userJson.getString("hair_length"));
                            my_pf_hair_type_text.setText(userJson.getString("hair_type"));
                            my_pf_eye_color_text.setText(userJson.getString("eye_color"));
                            my_pf_eye_wear_text.setText(userJson.getString("eye_wear"));
                            my_pf_height_text.setText(userJson.getString("height"));
                            my_pf_weight_text.setText(userJson.getString("weight"));
                            my_pf_body_type_text.setText(userJson.getString("body_type"));
                            my_pf_ethncty_text.setText(userJson.getString("ethnicity"));
                            my_pf_cmplxn_text.setText(userJson.getString("complexion"));
                            // my_pf_facial_color_text.setText(userJson.getString(""));
                            my_pf_my_app_text.setText(userJson.getString("appearance"));
                            my_pf_hlth_sts_text.setText(userJson.getString("health_status"));



                                    my_pf_do_you_drink_text.setText(userJson.getString("drink"));
                                    my_pf_do_you_smoke_text.setText(userJson.getString("smoke"));
                                    my_pf_etng_hbts_text.setText(userJson.getString("eating_habbit"));
                                    my_pf_mrtl_sts_text.setText(userJson.getString("material_status"));
                                    my_pf_do_you_hv_chld_text.setText(userJson.getString("children"));
                                    my_pf_want_more_child_text.setText(userJson.getString("more_children"));
                                    my_pf_occptn_text.setText(userJson.getString("occuption"));
                                    my_pf_emp_sts_text.setText(userJson.getString("emp_status"));
                                    my_pf_annual_icm_text.setText(userJson.getString("income"));
                                    my_pf_home_type_text.setText(userJson.getString("home_type"));
                                    my_pf_lvng_stn_text.setText(userJson.getString("live_status"));
                                    my_pf_resdncy_text.setText(userJson.getString("residancy_status"));
                                 //   my_pf_wlng_rlct_text.setText(userJson.getString("health_status"));
                                    my_pf_nationality_text.setText(userJson.getString("nationality"));
                                    my_pf_education_text.setText(userJson.getString("education"));
                                    my_pf_lang_text.setText(userJson.getString("lang_spoken"));
                                    my_pf_relegion_text.setText(userJson.getString("religion_values"));
                                    my_pf_born_text.setText(userJson.getString("born"));
                                    my_pf_relg_vl_text.setText(userJson.getString("religion_values"));
                                   // my_pf_rlgs_srvc_text.setText(userJson.getString("health_status"));
                                    my_pf_read_qu_text.setText(userJson.getString("read_quran"));
                                    my_pf_plgmy_text.setText(userJson.getString("polygamy"));
                                    my_pf_fmly_vl_text.setText(userJson.getString("family_values"));
                                    my_pf_creatr_text.setText(userJson.getString("profile_creator"));
                                    my_pf_profile_heading_text.setText(userJson.getString("profile_heading"));
                                 //   my_pf_abt_yourself_text.setText(userJson.getString("health_status"));
                                    my_pf_lkng_for_text.setText(userJson.getString("looking_state"));




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","profile error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId", viewUserId);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }

}
