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
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

public class materials extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GridView gridView;
    Button finish;
    String flag="1";
    String url;
    String titlestr;
    String[] AfterSplit;
    private int[] image = new int[20];
    private String[] imgText = new String[20];
    private String[] video = new String[20];
    int flagimgText=0;
    int flagvideo=0;
    int flagimage=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        flag = intent.getStringExtra("flag");
        titlestr = intent.getStringExtra("title");

        if(titlestr.equals("活動力")){
            url="http://140.123.92.130/api/loadmaterial1.php";
        }else if(titlestr.equals("參與力")){
            url="http://140.123.92.130/api/loadmaterial2.php";
        }else{
            url="http://140.123.92.130/api/loadmaterial3.php";
        }


        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.materials);
        View inflated = stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Elder2");

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
                Toast toast = Toast.makeText(materials.this,
                        AfterSplit[1] , Toast.LENGTH_LONG);
                //顯示Toast
                toast.show();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        //execution
        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", image[i]);
            item.put("text", imgText[i]);
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                items, R.layout.grid_item, new String[]{"image", "text"},
                new int[]{R.id.image, R.id.text});
        gridView = (GridView)findViewById(R.id.gridView2);
        gridView.setNumColumns(3);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position<AfterSplit.length/2) {
                    Toast.makeText(materials.this, "你選擇了" + imgText[position], Toast.LENGTH_SHORT).show();
                    //navigate to video

                    Intent intent = new Intent();
                    intent.setClass(materials.this, video.class);
                    intent.putExtra("url", video[position]);
                    //intent.putExtra("valence", String.valueOf(valence));
                    intent.putExtra("title", titlestr);
                    startActivity(intent);
                    materials.this.finish();
                }
            }
        });


        finish = (Button)findViewById(R.id.button99);
        if(flag.equals("2")){
            finish.setText("繼續其他主題學習");
        }
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "請稍候", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.setClass(materials.this, emotionresult.class);
                intent.putExtra("flag", "2");
                startActivity(intent);
                materials.this.finish();
            }
        });


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
            intent.setClass(materials.this, CameraActivity.class);
            startActivity(intent);
            materials.this.finish();
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent();
            intent.setClass(materials.this, scenario.class);
            startActivity(intent);
            materials.this.finish();
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent();
            intent.setClass(materials.this, materials.class);
            startActivity(intent);
            materials.this.finish();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent();
            intent.setClass(materials.this, tabmain.class);
            startActivity(intent);
            materials.this.finish();
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
