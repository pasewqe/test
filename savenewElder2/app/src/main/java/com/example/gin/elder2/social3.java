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

public class social3 extends AppCompatActivity {

    int flag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();


        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.social3);
        View inflated = stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("社會參與 活動種類 (2/1)");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_menu_send);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag ++;
                //flag%=3;
                ImageView content = (ImageView)findViewById(R.id.imageView4);
                if(flag==1){
                    content.setImageResource(R.drawable.type1);
                    getSupportActionBar().setTitle("社會參與 活動種類 (2/1)");
                } else if(flag==2){
                    content.setImageResource(R.drawable.type2);
                    getSupportActionBar().setTitle("社會參與 活動種類 (2/2)");
                } else if(flag>2){
                    Intent intent = new Intent();
                    intent.setClass(social3.this, social4.class);
                    startActivity(intent);
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
            flag--;
            if(flag==0)
                super.onBackPressed();
            else{
                ImageView content = (ImageView)findViewById(R.id.imageView4);
                content.setImageResource(R.drawable.type1);
                getSupportActionBar().setTitle("社會參與 活動種類 (2/1)");
            }
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
