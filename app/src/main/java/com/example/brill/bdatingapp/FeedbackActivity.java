package com.example.brill.bdatingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;


public class FeedbackActivity extends AppCompatActivity {

    EditText edtIssue,edtDescIssue;
    Button btnLogin;
    CheckBox chkIssue;

    String JID = "intId";
    String selectuserid;
    public static final String PREF_ID="intId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        //  hideSoftKeyboard();
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        edtIssue=(EditText)findViewById(R.id.edtIssue);
        edtDescIssue=(EditText)findViewById(R.id.edtDescIssue);
        btnLogin=(Button) findViewById(R.id.btnLogin);
        chkIssue=(CheckBox)findViewById(R.id.chkIssue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setPropertyOnFocousOnText();

        Intent intent=getIntent();
        final Bundle bd = intent.getExtras();
        final String id = bd.getString("id");
       //final String name = bd.getString("name");
        //Toast.makeText(FeedbackActivity.this,id+name.toString(),Toast.LENGTH_SHORT).show();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)     {

                    String title=edtIssue.getText().toString();
                    String message=edtDescIssue.getText().toString();

                    String type = "complaint";

               // Toasty.success(FeedbackActivity.this,"Sent",Toast.LENGTH_SHORT).show();
              //  finish();


                BackgroundWorker backgroundWorker = new BackgroundWorker(FeedbackActivity.this);
                backgroundWorker.execute(type,title,message,id);
                   // LoginUserMethod(strEmail,strPass);


            }
        });


    }

    private void setPropertyOnFocousOnText() {

        edtIssue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edtIssue.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                   // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                   // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edtIssue.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
                }

            }
        });

        edtDescIssue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {

                    edtDescIssue.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                    // edtEmail.setForeground(getResources().getDrawable(R.drawable.rounded_edittext1));

                    // edtEmail.setCompoundDrawableTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorvactor));
                }
                else {
                    edtDescIssue.setBackground(getResources().getDrawable(R.drawable.rounded_edittext1));
                }

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
            String complaint_url = "http://brilldating.com/android/FeedBack.php";
            if (type.equals("complaint"))
            {
                try
                {
                    //String id = params[3];
                    String id1 = params[3];
                    String title = params[1];
                    String message = params[2];
                    URL url = new URL(complaint_url);

                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data=
                            URLEncoder.encode("title","UTF-8")+"="+URLEncoder.encode(title,"UTF-8")+"&"
                                    + URLEncoder.encode("message","UTF-8")+"="+URLEncoder.encode(message,"UTF-8")
                            +"&" + URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id1,"UTF-8");
                                    //+ URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");



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
            alertDialog.setTitle("Complaint Status");
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject obj = null;

           Toasty.success(FeedbackActivity.this,"Feedback Sent",Toast.LENGTH_SHORT).show();
            finish();

        }
    }



    private boolean isValidMail(String email) {
        boolean check;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email);
        check = m.matches();

        if(!check) {

            // Toast.makeText(getApplicationContext(),"Not Valid Email",Toast.LENGTH_SHORT).show();

        }
        return check;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }
}
