package com.example.brill.bdatingapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brill.bdatingapp.R;
import com.example.brill.bdatingapp.gattersatter.GatterGetWhoIntrestedInMeList;

import java.util.ArrayList;

/**
 * Created by brill on 2/6/2018.
 */

public class AdapterGetWhoIntrestedInMe extends RecyclerView.Adapter<AdapterGetWhoIntrestedInMe.MyViewHolder> {

    SharedPreferences prefrance;




    String prefid="id",prefupid="upid",preftitle="title",prefdate="date",prefuploader="uploader",prefimages="images",prefdetails="details",prefvideo="video";
    private ArrayList<GatterGetWhoIntrestedInMeList> recyclerModels; // this data structure carries our title and description
    Context context;
    public AdapterGetWhoIntrestedInMe(ArrayList<GatterGetWhoIntrestedInMeList> recyclerModels, Context context) {
        this.recyclerModels = recyclerModels;
        this.context=context;
    }

    @Override
    public AdapterGetWhoIntrestedInMe.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate your custom row layout here
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_user_list, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterGetWhoIntrestedInMe.MyViewHolder holder, int position) {
        // update your data here
        final GatterGetWhoIntrestedInMeList getUserdtl = recyclerModels.get(position);
        //getUserdtl=getAda1;
        String userid, username;



        userid = getUserdtl.getIntId();
        username = getUserdtl.getFname();

        holder.username.setText(username.toString());

        Log.i("","username adapter====="+username);


     /*   holder.btnChat.setOnClickListener(new View.OnClickListener() {
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
*/
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables here

        public TextView username,newsdatetime,newsuploader,newsudetail;
        public Button btnChat;

        public ImageView newsimage;
        Button btnviewmore;

        MyViewHolder(View view) {
            super(view);



            username =(TextView) itemView.findViewById(R.id.txtName) ;
         //   btnChat=(Button)itemView.findViewById(R.id.btnChat);

            /*newsimage =(ImageView) itemView.findViewById(R.id.newsimages) ;*/

        }
    }
}
