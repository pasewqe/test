package com.example.gin.elder2;

/**
 * Created by GIN on 2018/3/8.
 */

import android.content.Intent;
import android.net.Uri;
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
import android.widget.MediaController;
import android.widget.VideoView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;

import java.util.List;

public class video extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Detector.ImageListener{

    //video parameters
    private VideoView vidView;
    private MediaController vidControl;
    String vidAddress = "";
    SurfaceView cameraPreview;
    CameraDetector detector;
    int valence;
    int beforevalence;
    String titlestr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        vidAddress = intent.getStringExtra("url");
        titlestr = intent.getStringExtra("title");
        valence = Integer.valueOf(intent.getStringExtra("valence"));
        beforevalence=valence;

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.video);
        View inflated = stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_menu_send);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "請稍候", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                detector.stop();
                Intent intent = new Intent();
                intent.setClass(video.this, emotionresult.class);
                intent.putExtra("flag", "1");
                intent.putExtra("valence", String.valueOf(valence));
                intent.putExtra("beforevalence", String.valueOf(beforevalence));
                intent.putExtra("title", titlestr);
                startActivity(intent);
                video.this.finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        detector=new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, (SurfaceView)findViewById(R.id.camera_view2));
        detector.setImageListener(this);
        //detector.setDetectSmile(true);
        detector.setDetectAllEmotions(true);
        //detector.setDetectAllAppearances(true);
        detector.setMaxProcessRate(50);
        detector.start();

        vidView = (VideoView) findViewById(R.id.myVideo);
        vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);

        Uri vidUri = Uri.parse(vidAddress);
        vidView.setVideoURI(vidUri);
        vidView.start();








    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
            Intent intent = new Intent();
            intent.setClass(video.this, CameraActivity.class);
            startActivity(intent);
            video.this.finish();
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent();
            intent.setClass(video.this, scenario.class);
            startActivity(intent);
            video.this.finish();
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent();
            intent.setClass(video.this, materials.class);
            startActivity(intent);
            video.this.finish();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent();
            intent.setClass(video.this, tabmain.class);
            startActivity(intent);
            video.this.finish();
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
            Face face = faces.get(0);
            valence+=face.emotions.getValence();
        }
    }
}

