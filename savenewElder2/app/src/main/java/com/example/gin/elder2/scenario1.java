package com.example.gin.elder2;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class scenario1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Detector.ImageListener {

    //parameters
    private GridView gridView;
    private TextView title;
    private int[] image = new int[20];
    private String[] imgText = new String[20];
    private String[] video = new String[20];
    String url;
    String titlestr;
    String[] AfterSplit;
    int valence=0;
    int flagimgText=0;
    int flagvideo=0;
    int flagimage=0;
    private VideoView vidView;
    private MediaController vidControl;
    SurfaceView cameraPreview;
    CameraDetector detector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        url = intent.getStringExtra("url");
        titlestr = intent.getStringExtra("title");
        //valence = Integer.valueOf(intent.getStringExtra("valence"));

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.senario1);
        View inflated = stub.inflate();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Elder2");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, String.valueOf(valence), Snackbar.LENGTH_LONG)
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

        //http request
        StrictMode
                .setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .detectDiskReads()
                        .detectDiskWrites()
                        .detectNetwork()   // or .detectAll() for all detectable problems
                        .penaltyLog()
                        .build());
        StrictMode
                .setVmPolicy(new StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects()
                        .detectLeakedClosableObjects()
                        .penaltyLog()
                        .penaltyDeath()
                        .build());

        HttpPost method = new HttpPost(url);
        ArrayList<NameValuePair> variable = new ArrayList<NameValuePair>();


        //variable.add(new BasicNameValuePair("id",Id));
        //variable.add(new BasicNameValuePair("pw",Pw));

        try {
            method.setEntity(new UrlEncodedFormEntity(variable, HTTP.UTF_8));
            HttpResponse response = new DefaultHttpClient().execute(method);
            if(response.getStatusLine().getStatusCode()==200)
            {
                String rlt= EntityUtils.toString(response.getEntity());

                AfterSplit = rlt.split(" ");
                for(int i=0;i<AfterSplit.length;i++){
                    if(i%2==0){
                        imgText[flagimgText++]=AfterSplit[i];
                        image[flagimage++]=R.drawable.video;
                    } else{
                        video[flagvideo++]=AfterSplit[i];
                    }
                }
                Toast toast = Toast.makeText(scenario1.this,
                        AfterSplit[1] , Toast.LENGTH_LONG);
                //顯示Toast
                toast.show();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //set title
        title=(TextView)findViewById(R.id.textView9);
        title.setText(titlestr);
        //execution gridView
        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", image[i]);
            item.put("text", imgText[i]);
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                items, R.layout.grid_item2, new String[]{"image", "text"},
                new int[]{R.id.image, R.id.text});
        gridView = (GridView)findViewById(R.id.gridView4);
        gridView.setNumColumns(3);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position<AfterSplit.length/2) {
                    Toast.makeText(scenario1.this, "你選擇了" + imgText[position], Toast.LENGTH_SHORT).show();
                    //navigate to video
                    detector.stop();
                    Intent intent = new Intent();
                    intent.setClass(scenario1.this, video.class);
                    intent.putExtra("url", video[position]);
                    intent.putExtra("valence", String.valueOf(valence));
                    intent.putExtra("title", titlestr);
                    startActivity(intent);
                    scenario1.this.finish();
                }
            }
        });

        detector=new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, (SurfaceView)findViewById(R.id.camera_view2));
        detector.setImageListener(this);
        //detector.setDetectSmile(true);
        detector.setDetectAllEmotions(true);
        //detector.setDetectAllAppearances(true);
        detector.setMaxProcessRate(50);
        detector.start();




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
            intent.setClass(scenario1.this, cameraMain.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {
            if(detector.isRunning()) {
                detector.stop();
            }
            Intent intent = new Intent();
            intent.setClass(scenario1.this, scenario.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent();
            intent.setClass(scenario1.this, tabmain.class);
            startActivity(intent);
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
