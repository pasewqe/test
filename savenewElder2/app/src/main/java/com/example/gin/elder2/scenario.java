package com.example.gin.elder2;

/**
 * Created by GIN on 2018/3/9.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;

import java.util.List;

public class scenario extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Detector.ImageListener{

    Button sc1;
    Button sc2;
    Button sc3;
    Button sc4;
    SurfaceView cameraPreview;
    CameraDetector detector;

    int valence=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.scenario);
        View inflated = stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //execution

        sc1=(Button)findViewById(R.id.button2);
        sc2=(Button)findViewById(R.id.button3);
        sc3=(Button)findViewById(R.id.button4);
        sc4=(Button)findViewById(R.id.button11);
        cameraPreview=(SurfaceView)findViewById(R.id.camera_view2);
        //cameraPreview.setLayoutParams(new ConstraintLayout.LayoutParams(20,20));


        detector=new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, (SurfaceView)findViewById(R.id.camera_view2));
        detector.setImageListener(this);
        //detector.setDetectSmile(true);
        detector.setDetectAllEmotions(true);
        //detector.setDetectAllAppearances(true);
        detector.setMaxProcessRate(50);
        detector.start();


        sc1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
                pref.edit()
                        .putString("TOPIC", "physically")
                        .commit();

                if(detector.isRunning()) {
                    detector.stop();
                }
                Intent intent = new Intent();
                intent.setClass(scenario.this, physical1.class);
                //intent.putExtra("url", "http://140.123.92.130/api/loadscenario1.php");
                //intent.putExtra("title", "活動力");
                //intent.putExtra("valence", String.valueOf(valence));
                startActivity(intent);

            }
        });

        sc2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
                pref.edit()
                        .putString("TOPIC", "activity")
                        .commit();

                if(detector.isRunning()) {
                    detector.stop();
                }
                Intent intent = new Intent();
                intent.setClass(scenario.this, social.class);
                //intent.putExtra("url", "http://140.123.92.130/api/loadscenario2.php");
                //intent.putExtra("title", "參與力");
                //intent.putExtra("valence", String.valueOf(valence));
                startActivity(intent);

            }
        });

        sc3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
                pref.edit()
                        .putString("TOPIC", "cognitive")
                        .commit();

                if(detector.isRunning()) {
                    detector.stop();
                }
                Intent intent = new Intent();
                intent.setClass(scenario.this, co1.class);
                //intent.putExtra("url", "http://140.123.92.130/api/loadscenario3.php");
                //intent.putExtra("title", "認知力");
                //intent.putExtra("valence", String.valueOf(valence));
                startActivity(intent);

            }
        });

        sc4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(detector.isRunning()) {
                    detector.stop();
                }
                Intent intent = new Intent();
                intent.setClass(scenario.this, comm.class);
                //intent.putExtra("url", "http://140.123.92.130/api/loadscenario3.php");
                //intent.putExtra("title", "認知力");
                //intent.putExtra("valence", String.valueOf(valence));
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(detector.isRunning()) {
                detector.stop();
            }
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            if(detector.isRunning()) {
                detector.stop();
            }
            Intent intent = new Intent();
            intent.setClass(scenario.this, cameraMain.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent();
            intent.setClass(scenario.this, tabmain.class);
            startActivity(intent);
            scenario.this.finish();
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onImageResults(List<Face> faces, Frame frame, float v) {
        if (faces.size()==0){

        } else {
            Button t1 = (Button) findViewById(R.id.button2);
            Face face = faces.get(0);
            valence+=face.emotions.getValence();
            t1.setText(String.format("%d", valence));
        }
    }

}