package com.example.brill.bdatingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

import es.dmoral.toasty.Toasty;

public class NotifiactionActivity extends AppCompatActivity {
    Button btnLogin;
    RadioGroup newMessege,interestedInMe,friendRequest,profileVisitors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiaction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        final Bundle bd = intent.getExtras();
        final String id = bd.getString("id");
        /*String name = bd.getString("name");
        Toast.makeText(NotifiactionActivity.this,id+name.toString(),Toast.LENGTH_SHORT).show();*/

        btnLogin=(Button) findViewById(R.id.btnLogin);
        newMessege = (RadioGroup)findViewById(R.id.newMessege);
        interestedInMe = (RadioGroup)findViewById(R.id.interestedInMe);
        friendRequest = (RadioGroup)findViewById(R.id.friendRequest);
        profileVisitors = (RadioGroup)findViewById(R.id.profileVisitors);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)     {

                String newmessage=((RadioButton)findViewById(newMessege.getCheckedRadioButtonId())).getText().toString();
                String interestedinme=((RadioButton)findViewById(interestedInMe.getCheckedRadioButtonId())).getText().toString();
                String friendrequest=((RadioButton)findViewById(friendRequest.getCheckedRadioButtonId())).getText().toString();
                String profilevisitors=((RadioButton)findViewById(profileVisitors.getCheckedRadioButtonId())).getText().toString();

                Toast.makeText(NotifiactionActivity.this, newmessage+interestedinme+friendrequest+profilevisitors, Toast.LENGTH_SHORT).show();

                String type = "notification";

               BackgroundWorker backgroundWorker = new BackgroundWorker(NotifiactionActivity.this);
               backgroundWorker.execute(type,newmessage,interestedinme,friendrequest,profilevisitors,id);

            }
        });


    }

    public class BackgroundWorker extends AsyncTask<String,Void,String>
    {
        Context context;
        ProgressBar progressBar;
        String json_url;
        AlertDialog alertDialog;


        BackgroundWorker(Context ctx)
        {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String complaint_url = "http://brilldating.com/android/notification.php";
            if (type.equals("notification"))
            {
                try
                {
                    String id1 = params[5];
                    String newmessage = params[1];
                    String interestedinme = params[2];
                    String friendrequest = params[3];
                    String profilevisitors = params[4];
                    URL url = new URL(complaint_url);

                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data=
                            URLEncoder.encode("newmessage","UTF-8")+"="+URLEncoder.encode(newmessage,"UTF-8")+"&"
                                    + URLEncoder.encode("interestedinme","UTF-8")+"="+URLEncoder.encode(interestedinme,"UTF-8")
                                    + "&" + URLEncoder.encode("friendrequest","UTF-8")+"="+URLEncoder.encode(friendrequest,"UTF-8")
                                    + "&" + URLEncoder.encode("profilevisitors","UTF-8")+"="+URLEncoder.encode(profilevisitors,"UTF-8")
                                    + "&" + URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id1,"UTF-8");



                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String result = "";
                    String line = "";

                    while ((line=bufferedReader.readLine())!=null)
                    {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Notification Status");
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject obj = null;

            Toasty.success(NotifiactionActivity.this,"Notifaction Preferences Set Succesfully",Toast.LENGTH_SHORT).show();
            finish();

        }
    }
}
