package com.example.brill.bdatingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class AddPhotos extends AppCompatActivity {

    ImageView photo1,photo2,photo3,photo4,photo5,photo6,photo7,photo8,photo9;
    String uploadImgOrCover="";
    String imagenumber="";
    Bitmap bitmap;
    String filename;
    Uri filePath;
    Button bttn_edit_profile_pic,btnChangeCover,b3,b4,b5,b6,b7,b8,b9;
    ImageView imgview;
    EditText editText;

    CircleImageView c1,c2,c3,c4,c5,c6,c7,c8,c9;
    String id;
    private String uploadurl="http://brilldating.com/android/imgtest";
   private final int PICK_IMAGE_REQUEST = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photos);
        Intent intent=getIntent();
        final Bundle bd = intent.getExtras();
             id = bd.getString("id");

        c1=(CircleImageView) findViewById(R.id.cover_photo);
        c2=(CircleImageView) findViewById(R.id.profile_pic);
        c3=(CircleImageView) findViewById(R.id.imageview3);
        c4=(CircleImageView) findViewById(R.id.imageview4);
        c5=(CircleImageView) findViewById(R.id.imageview5);
        c6=(CircleImageView) findViewById(R.id.imageview6);
        c7=(CircleImageView) findViewById(R.id.imageview7);
        c8=(CircleImageView) findViewById(R.id.imageview8);
        c9=(CircleImageView) findViewById(R.id.imageview9);

        bttn_edit_profile_pic=(Button)findViewById(R.id.b1);
        btnChangeCover=(Button)findViewById(R.id.b2);
        b3=(Button)findViewById(R.id.b3);
        b4=(Button)findViewById(R.id.b4);
        b5=(Button)findViewById(R.id.b5);
        b6=(Button)findViewById(R.id.b6);
        b7=(Button)findViewById(R.id.b7);
        b8=(Button)findViewById(R.id.b8);
        b9=(Button)findViewById(R.id.b9);


        VisitUserPhotos();

        bttn_edit_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImgOrCover="YesImage1";
                imagenumber="photo1";
                // requestStoragePermission();
                showFileChooser();



            }
        });

        btnChangeCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenumber="photo2";
                uploadImgOrCover="YesImage2";
                showFileChooser();
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenumber="photo3";
                uploadImgOrCover="YesImage3";
                // requestStoragePermission();
                showFileChooser();



            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImgOrCover="YesImage4";
                imagenumber="photo4";
                // requestStoragePermission();
                showFileChooser();



            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImgOrCover="YesImage5";
                imagenumber="photo5";
                // requestStoragePermission();
                showFileChooser();



            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImgOrCover="YesImage6";
                imagenumber="photo6";
                // requestStoragePermission();
                showFileChooser();



            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImgOrCover="YesImage7";
                imagenumber="photo7";
                // requestStoragePermission();
                showFileChooser();




            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImgOrCover="YesImage8";
                imagenumber="photo8";
                // requestStoragePermission();
                showFileChooser();



            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenumber="photo9";
                uploadImgOrCover="YesImage9";
                // requestStoragePermission();
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



                if(uploadImgOrCover.equals("YesImage1"))
                {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 270,270, true);
                    c1.setImageBitmap(bitmap);
                    uploadImage();

                }

                if(uploadImgOrCover.equals("YesImage2"))
                {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 270,270, true);
                    c2.setImageBitmap(bitmap);
                    uploadImage();
                }


                if(uploadImgOrCover.equals("YesImage3"))
                {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 270,270, true);
                    c3.setImageBitmap(bitmap);
                    uploadImage();
                }
                if(uploadImgOrCover.equals("YesImage4"))
                {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 270,270, true);
                    c4.setImageBitmap(bitmap);
                    uploadImage();
                }
                if(uploadImgOrCover.equals("YesImage5"))
                {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 270,270, true);
                    c5.setImageBitmap(bitmap);
                    uploadImage();
                }
                if(uploadImgOrCover.equals("YesImage6"))
                {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 270,270, true);
                    c6.setImageBitmap(bitmap);
                    uploadImage();
                }
                if(uploadImgOrCover.equals("YesImage7"))
                {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 270,270, true);
                    c7.setImageBitmap(bitmap);
                    uploadImage();
                }
                if(uploadImgOrCover.equals("YesImage8"))
                {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 270,270, true);
                    c8.setImageBitmap(bitmap);
                    uploadImage();
                }
                if(uploadImgOrCover.equals("YesImage9"))
                {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 270,270, true);
                    c9.setImageBitmap(bitmap);
                    uploadImage();
                }


/*
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bitmap = BitmapFactory.decodeFile(filePath.toString(),options);*/

                //  getResizedBitmap(bitmap,120,100);
                // compressImage(filePath.toString());


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    private void uploadImage() {

        Log.i("","uploadImage imageString-======");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        Log.i("asdfsdf","imageString-======"+imageString);

        StringRequest request = new StringRequest(Request.Method.POST, Configs.URL_UpdateUserProfile1, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                Log.i("sdf","imageString-======response ====="+s);

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Toast.makeText(MainActivity.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                Log.i("","rrrrrrrrvolleyError"+volleyError);
            Toast.makeText(AddPhotos.this,volleyError.toString(),Toast.LENGTH_SHORT).show();


            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("imagefile", imageString);
                parameters.put("filetype", "YesImage");
                parameters.put("imageName", filename);
                parameters.put("userid", id);
                parameters.put("imageNumber", imagenumber);

                Log.i("","rrrrrrrrvolleyError========="+filename);
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(getApplicationContext());
        rQueue.add(request);
    }

    private void VisitUserPhotos() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Configs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            if(response.equals("0"))
                            {
                                Toasty.error(AddPhotos.this,"In-Valid email or password",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                JSONObject obj = new JSONObject(response);
                                JSONObject userJson = obj.getJSONObject("0");

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
                                    String url="http://www.brilldating.com/img/photo/"+photo1;
                                    Glide.with(getApplicationContext()).load(url).into(c1);

                                }
                                if(!photo2.equals(""))
                                {
                                    String url="http://www.brilldating.com/img/photo/"+photo2;
                                    Glide.with(getApplicationContext()).load(url).into(c2);
                                }
                                if(!photo3.equals(""))
                                {
                                    String url="http://www.brilldating.com/img/photo/"+photo3;
                                    Glide.with(getApplicationContext()).load(url).into(c3);
                                }
                                if(!photo4.equals(""))
                                {
                                    String url="http://www.brilldating.com/img/photo/"+photo4;
                                    Glide.with(getApplicationContext()).load(url).into(c4);
                                }
                                if(!photo5.equals(""))
                                {
                                    String url="http://www.brilldating.com/img/photo/"+photo5;
                                    Glide.with(getApplicationContext()).load(url).into(c5);
                                }
                                if(!photo6.equals(""))
                                {
                                    String url="http://www.brilldating.com/img/photo/"+photo6;
                                    Glide.with(getApplicationContext()).load(url).into(c6);
                                }
                                if(!photo7.equals(""))
                                {
                                    String url="http://www.brilldating.com/img/photo/"+photo7;
                                    Glide.with(getApplicationContext()).load(url).into(c7);
                                }
                                if(!photo8.equals(""))
                                {
                                    String url="http://www.brilldating.com/img/photo/"+photo8;
                                    Glide.with(getApplicationContext()).load(url).into(c8);
                                }
                                if(!photo9.equals(""))
                                {
                                    String url="http://www.brilldating.com/img/photo/"+photo9;
                                    Glide.with(getApplicationContext()).load(url).into(c9);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(AddPhotos.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("","profile error========="+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserPhotos", "yes");
                params.put("userid", id);

                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}
