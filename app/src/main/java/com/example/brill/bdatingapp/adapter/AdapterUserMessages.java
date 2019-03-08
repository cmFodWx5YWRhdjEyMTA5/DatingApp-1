package com.example.brill.bdatingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.brill.bdatingapp.Configs;
import com.example.brill.bdatingapp.ConnectDetector;
import com.example.brill.bdatingapp.ItemClickListener;
import com.example.brill.bdatingapp.R;
import com.example.brill.bdatingapp.ViewUserMessage;
import com.example.brill.bdatingapp.VisitUserProfileActivity;
import com.example.brill.bdatingapp.gattersatter.GatterGetAllFriendList;
import com.example.brill.bdatingapp.gattersatter.GatterGetUserMessage;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;


public class AdapterUserMessages extends RecyclerView.Adapter<AdapterUserMessages.MyViewHolder> {

    SharedPreferences prefrance;

    public CircleImageView photouser;

    Boolean isinternetpresent;
    ConnectDetector cd;
    String SharedintId;

    String prefid="id",prefupid="upid",preftitle="title",prefdate="date",prefuploader="uploader",prefimages="images",prefdetails="details",prefvideo="video";
    private ArrayList<GatterGetUserMessage> recyclerModels; // this data structure carries our title and description
    Context context;
    public AdapterUserMessages(ArrayList<GatterGetUserMessage> recyclerModels, Context context) {
        this.recyclerModels = recyclerModels;
        this.context=context;
    }

    @Override
    public AdapterUserMessages.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate your custom row layout here
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_alluser_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterUserMessages.MyViewHolder holder, int position) {
        // update your data here
        final GatterGetUserMessage getUserdtl = recyclerModels.get(position);
        //getUserdtl=getAda1;
        final String name;
        String sender="";
        final String receiver;
        final String gender;
        final String profilepic;
        final String message;
        final String birthday;

        final SharedPreferences msharedprefrance=context.getSharedPreferences(Configs.UserPrefrance,MODE_PRIVATE);
        SharedintId=(msharedprefrance.getString("id",""));

        name=getUserdtl.getName();
        birthday=getUserdtl.getBirthday();
        gender=getUserdtl.getGender();
        profilepic=getUserdtl.getProfile_pic();
        //  receiver=getUserdtl.getReceiverid();
        sender=getUserdtl.getSenderid();
        //  message=getUserdtl.getMessage();
        holder.txtusername.setText(name);

        if (!(profilepic.equals("null"))) {
            String url = Configs.profilephoto + profilepic;
            Log.i("", "vvvvvvvvvvvvvvvvvvvv======" + profilepic);
            Glide.with(context).load(url).into(holder.photouser);
        }
        else
        {
            // String url = Configs.profilephoto + profilepic;
            // Log.i("", "vvvvvvvvvvvvvvvvvvvv======" + profilepic);
            String url = Configs.profilephoto + "nophoto-men.jpg";

            Glide.with(context).load(url).into(holder.photouser);
        }



        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                cd = new ConnectDetector(context);
                isinternetpresent = cd.isConnectToInternet();

                if (isinternetpresent) {

                    prefrance = context.getSharedPreferences("GetMessage", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = prefrance.edit();
                    editor.putString("selectuserid", getUserdtl.getSenderid());
                    // editor.putString("selectusername", getUserdtl.get());
                    editor.commit();
                   // Toast.makeText(context, name.toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, ViewUserMessage.class);
                    intent.putExtra("username",name);
                    context.startActivity(intent);


                } else {
                    Toast.makeText(context, "internet Not Present", Toast.LENGTH_SHORT).show();
                }


            }
        });



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


        public TextView txtusername,txtgenAgeCity;
        public CircleImageView photouser,BtnViewProfile;

        ItemClickListener itemClickListener;




        MyViewHolder(View view) {
            super(view);
            txtusername =(TextView) itemView.findViewById(R.id.txtusername);
            txtgenAgeCity =(TextView) itemView.findViewById(R.id.txtgenAgeCity);
            photouser =(CircleImageView) itemView.findViewById(R.id.photouser) ;

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

    public void filterList(ArrayList<GatterGetUserMessage> filterdNames) {
        this.recyclerModels = filterdNames;
        notifyDataSetChanged();
    }


}
