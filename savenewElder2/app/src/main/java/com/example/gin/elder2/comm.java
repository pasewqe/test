package com.example.gin.elder2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;

public class comm extends AppCompatActivity {


    int flag=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();


        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.comm);
        View inflated = stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("學習大綱");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_menu_send);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag ++;
                flag%=8;
                ImageView content = (ImageView)findViewById(R.id.imageView8);
                if(flag==1){
                    content.setImageResource(R.drawable.c1);
                    getSupportActionBar().setTitle("如何與老人溝通的原則");
                } else if(flag==2){
                    content.setImageResource(R.drawable.c2);
                    getSupportActionBar().setTitle("(一)正確判斷或克服溝通上的障礙");
                } else if(flag==3){
                    content.setImageResource(R.drawable.c3);
                    getSupportActionBar().setTitle("(二)營造良好的溝通環境");
                } else if(flag==4){
                    content.setImageResource(R.drawable.c4);
                    getSupportActionBar().setTitle("(三)信任、接納、開放、自然的互動關係-1");
                } else if(flag==5){
                    content.setImageResource(R.drawable.c5);
                    getSupportActionBar().setTitle("(三)信任、接納、開放、自然的互動關係-2");
                } else if(flag==6){
                    content.setImageResource(R.drawable.c6);
                    getSupportActionBar().setTitle("(三)信任、接納、開放、自然的互動關係-3");
                } else if(flag==7){
                    content.setImageResource(R.drawable.c7);
                    getSupportActionBar().setTitle("(四)培養能提高人性化溝通的互動技巧");
                } else {
                    content.setImageResource(R.drawable.c8);
                    getSupportActionBar().setTitle("LEARN的溝通技巧");
                }


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


}
