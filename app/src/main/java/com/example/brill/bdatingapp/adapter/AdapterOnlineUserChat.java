package com.example.brill.bdatingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.brill.bdatingapp.Configs;
import com.example.brill.bdatingapp.ConnectDetector;
import com.example.brill.bdatingapp.ItemClickListener;
import com.example.brill.bdatingapp.MessageActivity;
import com.example.brill.bdatingapp.R;
import com.example.brill.bdatingapp.VisitUserProfileActivity;
import com.example.brill.bdatingapp.gattersatter.GatterOnlineUser;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

/**
 * Created by brill on 2/6/2018.
 */

public class AdapterOnlineUserChat extends RecyclerView.Adapter<AdapterOnlineUserChat.MyViewHolder> {

    SharedPreferences prefrance,sharedPreferences2;

    Boolean isinternetpresent;
    ConnectDetector cd;
    String SharedintId;

    String prefid="id",prefupid="upid",preftitle="title",prefdate="date",prefuploader="uploader",prefimages="images",prefdetails="details",prefvideo="video";
    private ArrayList<GatterOnlineUser> recyclerModels; // this data structure carries our title and description
    Context context;
    public AdapterOnlineUserChat(ArrayList<GatterOnlineUser> recyclerModels, Context context) {
        this.recyclerModels = recyclerModels;
        this.context=context;
    }

    @Override
    public AdapterOnlineUserChat.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate your custom row layout here
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_alluser_item_2, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterOnlineUserChat.MyViewHolder holder, int position) {
        // update your data here
        final GatterOnlineUser getUserdtl = recyclerModels.get(position);
        //getUserdtl=getAda1;
        String userid, username="",usergender,userDOB,userstate,usercity,userPic;



        sharedPreferences2=context.getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
        SharedintId=(sharedPreferences2.getString("id",""));

        Log.i("", "mmmmmmmmmmmmmmmmmmmmmmmmm" + SharedintId);


        userid = getUserdtl.getUserId();

        if(!SharedintId.equals(userid)) {


            username = getUserdtl.getUname();
            usergender = getUserdtl.getGender();
            userDOB = getUserdtl.getBirthday();
            usercity = getUserdtl.getCity();
            userPic = getUserdtl.getProfile_pic();
            String sortGender = "";


            if (usergender.equals("male") || usergender.equals("Male")) {
                sortGender = "M";
            } else if (usergender.equals("female") || usergender.equals("Female")) {
                sortGender = "F";
            } else {


            }

            if (!(userPic.equals("null"))) {
                String url = Configs.profilephoto + userPic;
                Log.i("", "vvvvvvvvvvvvvvvvvvvv======" + userPic);

                Glide.with(context).load(url).into(holder.photouser);


            } else if (userPic.equals("null")) {


                if (sortGender.equals("M")) {
                    String url = Configs.profilephoto + "nophoto-men.jpg";
                    Log.i("", "vvvvvvvvvvvvvvvvvvvv======" + url);

                    Glide.with(context).load(url).into(holder.photouser);
                    url = "";
                } else if (sortGender.equals("F")) {
                    String url = Configs.profilephoto + "nophoto-women.jpg";
                    Log.i("", "vvvvvvvvvvvvvvvvvvvv======" + url);

                    Glide.with(context).load(url).into(holder.photouser);
                }


            }

            try {
                String yearfatch[] = userDOB.split("-");

                int currentyear = Calendar.getInstance().get(Calendar.YEAR);

                int curAge = currentyear - Integer.parseInt(yearfatch[0]);

                holder.txtgenAgeCity.setText(sortGender + "/" + curAge + " " + usercity);


            } catch (NumberFormatException ex) { // handle your exception


            }

            holder.txtusername.setText(username);

            if (usercity.equals(null)) {
                usercity = "";
            }

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    cd = new ConnectDetector(context);
                    isinternetpresent = cd.isConnectToInternet();

                    if (isinternetpresent) {

                        prefrance = context.getSharedPreferences("selectedUser", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = prefrance.edit();
                        editor.putString("selectuserid", getUserdtl.getUserId());
                        editor.putString("selectusername", getUserdtl.getUname());
                        editor.putString("selectusercity", getUserdtl.getCity());
                        editor.commit();

                        Intent i = new Intent(context, MessageActivity.class);

                        context.startActivity(i);
                    } else {
                        Toasty.error(context, "internet Not Present", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else
        {
            holder.card.setVisibility(View.GONE);
        }
/*


        String yearfatch[]=userDOB.split("-");

        int currentyear= Calendar.getInstance().get(Calendar.YEAR);

        int curAge=currentyear-Integer.parseInt(yearfatch[0]);





        holder.username.setText(userfname+" "+uselname);


            holder.txtGender.setText(getUserdtl.getSex()+"/"+curAge+" "+usercity);

*/



       /* if(!userDOB.equals(""))
        {
            String strMonth="";
            String[] temp1;
            temp1=userDOB.split("-");
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


            holder.txtDOB.setText(temp1[2]+" "+strMonth+" "+temp1[0]);


        }

        holder.txtAddress.setText(usercountry+" "+userstate+" "+usercity);

*/


      /*  holder.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("","getUserdtl adapter====="+getUserdtl.getIntId());

                prefrance=context.getSharedPreferences(Configs.ChatingToPrefrance,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=prefrance.edit();
                editor.putString("ChatToId",getUserdtl.getIntId());
                editor.commit();

                Intent intent=new Intent(context.getApplicationContext(), MessageActivity.class);
                context.startActivity(intent);
            }
        });



        holder.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefrance=context.getSharedPreferences(Configs.ChatingToPrefrance,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=prefrance.edit();
                editor.putString("ChatToId",getUserdtl.getIntId());
                editor.putString("fname",getUserdtl.getFname());
                editor.putString("lname",getUserdtl.getLname());
                editor.putString("sex",getUserdtl.getSex());
                editor.putString("dob",getUserdtl.getDob());

                editor.putString("country",getUserdtl.getCountry_code());
                editor.putString("state",getUserdtl.getState_code());
                editor.putString("city",getUserdtl.getCity_code());





                editor.commit();

                Intent intent=new Intent(context.getApplicationContext(), User_Profile_DetailsActivity.class);
                context.startActivity(intent);
            }
        });*/

       /* holder.btnviewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newstitle,newsdate,newsuploader,newsimages,newsdetails,newsid,newsupid,newsvideo,profilestatus;


                preferences=context.getSharedPreferences(Configs.adminSingleNewsDetail, Context.MODE_PRIVATE);

                String userid,username;

                newstitle=getAda1.getTitle();
                newsdate=getAda1.getUp_datetime();
                newsuploader=getAda1.getUp_name();
                newsimages=getAda1.getNews_photo();
                newsdetails=getAda1.getDetails();

                newsid=getAda1.getNewsid();
                newsupid=getAda1.getUp_id();
                newsvideo=getAda1.getNews_video();


                Log.i("","user id is============="+newstitle);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString(prefid,newsid);
                editor.putString(prefupid,newsupid);
                editor.putString(preftitle,newstitle);
                editor.putString(prefdate,newsdate);
                editor.putString(prefuploader,newsuploader);
                editor.putString(prefimages,newsimages);
                editor.putString(prefdetails,newsdetails);
                editor.putString(prefvideo,newsvideo);

                editor.commit();


                Intent i=new Intent(context, NewsDetails.class);
                context.startActivity(i);


            }
        });
        */

    }

    @Override
    public int getItemCount() {
        return recyclerModels.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
        // return super.getItemViewType(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        // view this our custom row layout, so intialize your variables here
        public TextView txtusername,txtgenAgeCity;
        public CircleImageView photouser,BtnViewProfile;
        ItemClickListener itemClickListener;
        public CardView card;
        MyViewHolder(View view) {
            super(view);

            txtusername =(TextView) itemView.findViewById(R.id.txtusername);
            txtgenAgeCity =(TextView) itemView.findViewById(R.id.txtgenAgeCity);
            photouser =(CircleImageView) itemView.findViewById(R.id.photouser) ;
            card =(CardView) itemView.findViewById(R.id.cardview);
            itemView.setOnClickListener(this);



            /*newsimage =(ImageView) itemView.findViewById(R.id.newsimages) ;*/

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
    }

    public void filterList(ArrayList<GatterOnlineUser> filterdNames) {
        this.recyclerModels = filterdNames;
        notifyDataSetChanged();
    }


}
