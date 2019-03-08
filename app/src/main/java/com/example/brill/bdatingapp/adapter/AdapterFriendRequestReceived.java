package com.example.brill.bdatingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.brill.bdatingapp.R;
import com.example.brill.bdatingapp.VisitUserProfileActivity;
import com.example.brill.bdatingapp.gattersatter.GatterGetAllFriendRequestReceived;
import com.example.brill.bdatingapp.gattersatter.GatterGetAllFriendRequestSended;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

/**
 * Created by brill on 2/6/2018.
 */

public class AdapterFriendRequestReceived extends RecyclerView.Adapter<AdapterFriendRequestReceived.MyViewHolder> {

    SharedPreferences prefrance;

    public CircleImageView photouser;

    Boolean isinternetpresent;
    ConnectDetector cd;
    String SharedintId;

    String prefid="id",prefupid="upid",preftitle="title",prefdate="date",prefuploader="uploader",prefimages="images",prefdetails="details",prefvideo="video";
    private ArrayList<GatterGetAllFriendRequestReceived> recyclerModels; // this data structure carries our title and description
    Context context;
    public AdapterFriendRequestReceived(ArrayList<GatterGetAllFriendRequestReceived> recyclerModels, Context context) {
        this.recyclerModels = recyclerModels;
        this.context=context;
    }

    @Override
    public AdapterFriendRequestReceived.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate your custom row layout here
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_alluser_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterFriendRequestReceived.MyViewHolder holder, int position) {
        // update your data here
        final GatterGetAllFriendRequestReceived getUserdtl = recyclerModels.get(position);
        //getUserdtl=getAda1;
        String userid, username="",usergender,userDOB,userstate,usercity,userPic;

        userid = getUserdtl.getUserId();

            username = getUserdtl.getUname();
            usergender = getUserdtl.getGender();
            userDOB = getUserdtl.getBirthday();
            usercity = getUserdtl.getCity();
            userPic = getUserdtl.getProfile();
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
                        editor.putString("selectusergender", getUserdtl.getGender());

                        editor.putString("selecttype",getUserdtl.getUserId()+"FriendRequest");

                        editor.commit();

                        Intent intent = new Intent(context, VisitUserProfileActivity.class);
                        context.startActivity(intent);

                    } else {
                        Toasty.error(context, "internet Not Present", Toast.LENGTH_SHORT).show();
                    }


                }
            });





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
        return super.getItemViewType(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        // view this our custom row layout, so intialize your variables here


        public TextView txtusername,txtgenAgeCity;
        public Button btnacceptfr;
        public CircleImageView photouser,BtnViewProfile;

        ItemClickListener itemClickListener;




        MyViewHolder(View view) {
            super(view);
            txtusername =(TextView) itemView.findViewById(R.id.txtusername);
            btnacceptfr =(Button) itemView.findViewById(R.id.btnacceptfr);
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

    public void filterList(ArrayList<GatterGetAllFriendRequestReceived> filterdNames) {
        this.recyclerModels = filterdNames;
        notifyDataSetChanged();
    }


}
