package com.example.brill.bdatingapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.brill.bdatingapp.fragments.AboutusFragment;
import com.example.brill.bdatingapp.fragments.EditProfileFragment;
import com.example.brill.bdatingapp.fragments.LookingforFragment;
import com.example.brill.bdatingapp.fragments.ProfilePhotosFragment;
import com.example.brill.bdatingapp.fragments.your_profileFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class UserProfileActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    CoordinatorLayout layTab;
    SharedPreferences prefrance;
    CircleImageView profile_pic;
    TextView text_userpf_username,text_userpf_city_state_country;
    String sharid,sharname,sharGender,sharCity, sharCountry,
    sharState,sharUserphoto,sharCoverphoto;

    ImageView cover_photo;

    Button bttn_edit_profile_pic,btnChangeCover;

    int PICK_IMAGE_REQUEST = 111;
    private static final int STORAGE_PERMISSION_CODE=2343;
    Uri filePath;
    Bitmap bitmap;
    String filename;

    String uploadImgOrCover="";

    Boolean isinternetpresent;
    ConnectDetector cd;

    ProgressBar ProgressCover;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        requestStoragePermission();
        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        profile_pic=(CircleImageView) findViewById(R.id.profile_pic);
        ProgressCover=(ProgressBar) findViewById(R.id.ProgressCover);
        cd=new ConnectDetector(getApplicationContext());
        isinternetpresent=cd.isConnectToInternet();
        File dir = new File("/sdcard/Wallpaper");
        try{
            if(dir.mkdir()) {
                System.out.println("Directory created");
                Log.i("","Directory created");
            } else {
                System.out.println("Directory is not created");
                Log.i("","Directory is not created");
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        text_userpf_username=(TextView) findViewById(R.id.text_userpf_username);
        cover_photo=(ImageView) findViewById(R.id.cover_photo);
        text_userpf_city_state_country=(TextView) findViewById(R.id.text_userpf_city_state_country);

        bttn_edit_profile_pic=(Button)findViewById(R.id.bttn_edit_profile_pic);
        btnChangeCover=(Button)findViewById(R.id.btnChangeCover);


        sharedPrefMethod();

        layTab=(CoordinatorLayout) findViewById(R.id.layTab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);

            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.tab_layout_tab, tabLayout, false);

            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);



            tab.select();

            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#e74c3c"));

        }


        bttn_edit_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImgOrCover="YesImage";
               // requestStoragePermission();
                showFileChooser();



            }
        });

        btnChangeCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImgOrCover="YesCover";
                showFileChooser();
            }
        });


    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

    }

    private void requestStoragePermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            return;

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode==STORAGE_PERMISSION_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toasty.info(this,"permission granted", Toast.LENGTH_LONG).show();

            }
            else {
                Toasty.error(this,"permission not granted", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("","onActivityResult bitmap is=======");
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            // filename = filePath.;

            Random rand = new Random();

            filename= String.format("%04d", rand.nextInt(10000));




            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);



                if(uploadImgOrCover.equals("YesImage"))
                {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 270,270, true);
                }

                if(uploadImgOrCover.equals("YesCover"))
                {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 1600,421, true);
                }





/*
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bitmap = BitmapFactory.decodeFile(filePath.toString(),options);*/

              //  getResizedBitmap(bitmap,120,100);
               // compressImage(filePath.toString());

                uploadImage(uploadImgOrCover);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    private void uploadImage(final String uploadImgOrCover) {
        ProgressCover.setVisibility(View.VISIBLE);
        Log.i("","uploadImage imageString-======");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        Log.i("","imageString-======"+imageString);

        StringRequest request = new StringRequest(Request.Method.POST, Configs.URL_UpdateUserProfile, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                Log.i("","responseis=========="+s);
                if(s.equals("1"))
                {
                   if(uploadImgOrCover.equals("YesImage"))
                   {
                       ProgressCover.setVisibility(View.GONE);
                       SharedPreferences.Editor editor=prefrance.edit();
                       editor.putString("profile_pic",filename+".jpg");
                       editor.putString("ProfileUpdate","yes");

                       editor.commit();
                       profile_pic.setImageBitmap(bitmap);
                       Toasty.info(getApplicationContext(),"Profile Change",Toast.LENGTH_SHORT).show();



                   }
                    if(uploadImgOrCover.equals("YesCover"))
                   {
                       ProgressCover.setVisibility(View.GONE);
                       SharedPreferences.Editor editor=prefrance.edit();
                       editor.putString("cover_pic",filename+".jpg");
                       editor.putString("ProfileUpdate","yes");
                       editor.commit();
                       cover_photo.setImageBitmap(bitmap);
                       Toasty.info(getApplicationContext(),"Cover photo change",Toast.LENGTH_SHORT).show();

                   }

                }
                if(s.equals("0"))
                {

                    Toast.makeText(getApplicationContext(),"Try after sometime",Toast.LENGTH_SHORT).show();

                }



                Log.i("","rrrrrrrrrrrrrrrrrrrr"+s);

              /* // progressDialog.dismiss();
                if(s.equals("true")){
                    Toast.makeText(MainActivity.this, "Uploaded Successful", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Some error occurred!", Toast.LENGTH_LONG).show();
                }*/
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               // Toast.makeText(MainActivity.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                Log.i("","rrrrrrrrvolleyError"+volleyError);



            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("imagefile", imageString);
                parameters.put("filetype", uploadImgOrCover);
                parameters.put("imageName", filename);
                parameters.put("userid", sharid);



                Log.i("","imageString========="+imageString);
                Log.i("","uploadImgOrCover========="+uploadImgOrCover);
                Log.i("","filename========="+filename);
                Log.i("","sharid========="+sharid);

                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(getApplicationContext());
        rQueue.add(request);
    }



    private void sharedPrefMethod() {
        prefrance=getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
        sharid= prefrance.getString("id","");
        sharname= prefrance.getString("name","");
        sharGender= prefrance.getString("gender","");
        sharCity= prefrance.getString("city","");
        sharCountry= prefrance.getString("country","");
        sharState= prefrance.getString("state","");
        sharUserphoto= prefrance.getString("profile_pic","");
        sharCoverphoto= prefrance.getString("cover_pic","");

        text_userpf_username=(TextView) findViewById(R.id.text_userpf_username);
        text_userpf_city_state_country=(TextView) findViewById(R.id.text_userpf_city_state_country);


        text_userpf_username.setText(sharname);
        text_userpf_city_state_country.setText(sharCity+" "+sharState+" "+sharCountry);
        if(!sharUserphoto.equals(""))
        {
            String url= Configs.profilephoto+sharUserphoto;
            Log.i("","photo from shared pref======="+url);
            Glide.with(getApplicationContext()).load(url).into(profile_pic);
        }

        Log.i("","sharCoverphoto"+sharCoverphoto);

        if(!sharCoverphoto.equals(""))
        {
            String url= Configs.coverphoto+sharCoverphoto;
            Log.i("","cover photo photo from shared pref======="+url);
            Glide.with(getApplicationContext()).load(url).into(cover_photo);
        }



    }


    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new EditProfileFragment(),"Edit Profile");
        adapter.addFragment(new AboutusFragment(),"About us");
        adapter.addFragment(new ProfilePhotosFragment(),"Photos");
        adapter.addFragment(new LookingforFragment(),"Looking for");
        adapter.addFragment(new your_profileFragment(),"Your Profile");

        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public boolean onSupportNavigateUp()
    {
        prefrance=getSharedPreferences(Configs.UserPrefrance, Context.MODE_PRIVATE);
        String profileupdate= prefrance.getString("ProfileUpdate","");

        Log.i("","ProfileUpdate======="+profileupdate);

        if(profileupdate.equals("yes") || !profileupdate.equals(""))
        {
            SharedPreferences.Editor editor=prefrance.edit();
            editor.putString("ProfileUpdate","");
            editor.commit();

            Intent intent=new Intent(getApplicationContext(),Dashboard.class);
            startActivity(intent);
            finish();
        }
        else {
            finish();
        }
        return true;

    }



}
