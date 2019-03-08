package com.example.brill.bdatingapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.brill.bdatingapp.Configs;
import com.example.brill.bdatingapp.R;
import com.example.brill.bdatingapp.gattersatter.GatterGetAllChat;
import com.github.library.bubbleview.BubbleTextView;

import java.util.ArrayList;

public class AdapterGetAllChat extends RecyclerView.Adapter<AdapterGetAllChat.MyViewHolder> {

    SharedPreferences forreciver;
    SharedPreferences forsender;

    String sendby,recby;



    String prefid="id",prefupid="upid",preftitle="title",prefdate="date",prefuploader="uploader",prefimages="images",prefdetails="details",prefvideo="video";
    private ArrayList<GatterGetAllChat> recyclerModels; // this data structure carries our title and description
    Context context;
    public AdapterGetAllChat(ArrayList<GatterGetAllChat> recyclerModels, Context context) {
        this.recyclerModels = recyclerModels;
        this.context=context;
    }

    @Override
    public AdapterGetAllChat.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        // inflate your custom row layout here
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_user_chatlist, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterGetAllChat.MyViewHolder holder, int position) {
        forreciver=context.getSharedPreferences("selectedUser", Context.MODE_PRIVATE);

        forsender=context.getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);

        recby=(forreciver.getString("selectuserid",""));
        sendby=(forsender.getString("id",""));

        // update your data here
        final GatterGetAllChat getUserdtl = recyclerModels.get(position);

        String userId,usenderid,recid,msg,readstatus,cdate;
        //getUserdtl=getAda1;
         usenderid=getUserdtl.getUsenderid();
         recid=getUserdtl.getRecid();
         msg=getUserdtl.getMsg();
         readstatus=getUserdtl.getUsenderid();
         cdate=getUserdtl.getUsenderid();



      Log.i("","message ============is"+getUserdtl.getMsg().toString());


        if(usenderid.equals(sendby))
        {

                holder.laysend.setVisibility(View.VISIBLE);
                holder.sendmessage.setText(msg.toString());

        }
        if(usenderid.equals(recby))
        {
                holder.layrecived.setVisibility(View.VISIBLE);
                holder.recivedmessage.setText(msg.toString());

        }








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



        public BubbleTextView recivedmessage,sendmessage;
        public LinearLayout layrecived,laysend;


        MyViewHolder(View view) {
            super(view);

            layrecived=(LinearLayout)itemView.findViewById(R.id.layrecived);
            laysend=(LinearLayout)itemView.findViewById(R.id.laysend);

            recivedmessage=(BubbleTextView)itemView.findViewById(R.id.text_recivedmessage);
            sendmessage=(BubbleTextView)itemView.findViewById(R.id.text_sendmessage);



            /*newsimage =(ImageView) itemView.findViewById(R.id.newsimages) ;*/

        }
    }
    @Override
    public int getItemViewType(int position) {
        return position;
        //return super.getItemViewType(position);
    }
}
