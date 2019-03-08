package com.example.brill.bdatingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.brill.bdatingapp.Configs;
import com.example.brill.bdatingapp.ConnectDetector;
import com.example.brill.bdatingapp.ItemClickListener;
import com.example.brill.bdatingapp.R;
import com.example.brill.bdatingapp.VisitUserProfileActivity;
import com.example.brill.bdatingapp.gattersatter.GatterGetAllFriendList;
import com.example.brill.bdatingapp.gattersatter.GatterGetViewMessage;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by brill on 2/6/2018.
 */

public class AdapterViewAllMessage extends RecyclerView.Adapter<AdapterViewAllMessage.MyViewHolder> {

    SharedPreferences prefrance;

    public CircleImageView photouser;

    Boolean isinternetpresent;
    ConnectDetector cd;
    String SharedintId;

    String prefid="id",prefupid="upid",preftitle="title",prefdate="date",prefuploader="uploader",prefimages="images",prefdetails="details",prefvideo="video";
    private ArrayList<GatterGetViewMessage> recyclerModels; // this data structure carries our title and description
    Context context;
    public AdapterViewAllMessage(ArrayList<GatterGetViewMessage> recyclerModels, Context context) {
        this.recyclerModels = recyclerModels;
        this.context=context;
    }

    @Override
    public AdapterViewAllMessage.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate your custom row layout here
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_allmessage, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterViewAllMessage.MyViewHolder holder, int position) {
        // update your data here
        final GatterGetViewMessage getUserdtl = recyclerModels.get(position);
        //getUserdtl=getAda1;
        String senderid, username="",usergender,userDOB,userstate,usercity,userPic;

        final SharedPreferences msharedprefrance=context.getSharedPreferences(Configs.UserPrefrance,MODE_PRIVATE);
        SharedintId=(msharedprefrance.getString("id",""));

        senderid = getUserdtl.getSenderid();

        if(senderid.equals(SharedintId))
        {
            holder.laysend.setVisibility(View.VISIBLE);
            holder.text_sendmessage.setText(getUserdtl.getMessage());
        }
        else
        {
            holder.layrecived.setVisibility(View.VISIBLE);
            holder.text_recivedmessage.setText(getUserdtl.getMessage());
        }

           /* holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int pos) {

                    cd = new ConnectDetector(context);
                    isinternetpresent = cd.isConnectToInternet();

                    if (isinternetpresent) {

                        prefrance = context.getSharedPreferences("selectedUser", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = prefrance.edit();
                       // editor.putString("selectuserid", getUserdtl.getUserId());

                       // editor.putString("usertypeis", "FriendList");
                        editor.commit();




                    } else {
                        Toast.makeText(context, "internet Not Present", Toast.LENGTH_SHORT).show();
                    }


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
        //return super.getItemViewType(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        // view this our custom row layout, so intialize your variables here

        public LinearLayout laysend,layrecived;
        public TextView text_sendmessage,text_recivedmessage;
        public CircleImageView photouser,BtnViewProfile;
        ItemClickListener itemClickListener;





        MyViewHolder(View view) {
            super(view);
            laysend =(LinearLayout) itemView.findViewById(R.id.laysend);
            layrecived =(LinearLayout) itemView.findViewById(R.id.layrecived);
            text_sendmessage =(TextView) itemView.findViewById(R.id.text_sendmessage);
            text_recivedmessage =(TextView) itemView.findViewById(R.id.text_recivedmessage);


            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
    }




}
