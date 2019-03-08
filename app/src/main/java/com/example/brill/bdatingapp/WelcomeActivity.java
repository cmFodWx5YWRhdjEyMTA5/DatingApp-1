package com.example.brill.bdatingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import es.dmoral.toasty.Toasty;

public class  WelcomeActivity extends AwesomeSplash {
    Button btndashboard;
    Boolean isinternetpresent;
    ConnectDetector cd;

    ImageView imgSignal;


    //@Override
   /* protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        imgSignal=(ImageView)findViewById(R.id.imgSignal);
*/





        public void initSplash(ConfigSplash configSplash) {

requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            /* you don't have to override every property */

            //Customize Circular Reveal
            configSplash.setBackgroundColor(R.color.back2); //any color you want form colors.xml
            configSplash.setAnimCircularRevealDuration(2000); //int ms
            configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
            configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

            //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

            //Customize Logo
            configSplash.setLogoSplash(R.drawable.bdlogo); //or any other drawable
            configSplash.setAnimLogoSplashDuration(2000); //int ms
            configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


            //Customize Path
            // configSplash.setPathSplash(SyncStateContract.Constants.DROID_LOGO); //set path String
            configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
            configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
            configSplash.setAnimPathStrokeDrawingDuration(3000);
            configSplash.setPathSplashStrokeSize(3); //I advise value be <5
            configSplash.setPathSplashStrokeColor(R.color.colorAccent); //any color you want form colors.xml
            configSplash.setAnimPathFillingDuration(3000);
            configSplash.setPathSplashFillColor(R.color.Wheat); //path object filling color


            //Customize Title
            configSplash.setTitleSplash("BrillDating"); //change your app name here
            configSplash.setTitleTextColor(R.color.Wheat);
            configSplash.setTitleTextSize(30f); //float value
            configSplash.setAnimTitleDuration(3000);
            configSplash.setAnimTitleTechnique(Techniques.FlipInX);
           // configSplash.setTitleFont("fonts/Pacifico.ttf"); //provide string to your font located in assets/fonts/







            //  hideSoftKeyboard();
            getWindow().setSoftInputMode(WindowManager.
                    LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            cd=new ConnectDetector(getApplicationContext());
            isinternetpresent=cd.isConnectToInternet();

            if(isinternetpresent) {

                Thread timerThread=new Thread()
                {
                    public void run()
                    {
                        try {
                            sleep(3000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finally {
                            final SharedPreferences msharedprefranceAdmin= getSharedPreferences(Configs.UserPrefrance,MODE_PRIVATE);
                            String useremail=(msharedprefranceAdmin.getString("email",""));
                            String userpass=(msharedprefranceAdmin.getString("password",""));

                            String id=(msharedprefranceAdmin.getString("id",""));

                            Log.i("","emailUser login data=======shared"+useremail);
                            Log.i("","passUser login data=======shared"+userpass);
                            Log.i("","idUser login data=======shared"+id);


                            if(!(id.equals("")))
                            {
                                Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                };
                timerThread.start();



            }
            else {
                Toasty.info(getApplicationContext(), "Please Connect TO Internet", Toast.LENGTH_SHORT).show();
                imgSignal.setVisibility(View.VISIBLE);
            }







        }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }






        public void animationsFinished() {


               /* Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                startActivity(intent);*/

            //transit to another activity here
            //or do whatever you want
        }











}
