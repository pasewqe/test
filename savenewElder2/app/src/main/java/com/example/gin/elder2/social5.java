package com.example.gin.elder2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;

public class social5 extends AppCompatActivity {


    String choice = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();

        choice = intent.getStringExtra("choice");

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.social5);
        View inflated = stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("活動分類 暖身活動");

        ImageView content = (ImageView) findViewById(R.id.imageView5);

        if(choice.equals("2")){
            content.setImageResource(R.drawable.s52);
            getSupportActionBar().setTitle("活動分類 感官刺激");
        }else if(choice.equals("3")){
            content.setImageResource(R.drawable.s53);
            getSupportActionBar().setTitle("活動分類 運動");
        }else if(choice.equals("4")){
            content.setImageResource(R.drawable.s54);
            getSupportActionBar().setTitle("活動分類 規畫認知");
        }else if(choice.equals("5")){
            content.setImageResource(R.drawable.s55);
            getSupportActionBar().setTitle("活動分類 懷舊");
        }else if(choice.equals("6")){
            content.setImageResource(R.drawable.s56);
            getSupportActionBar().setTitle("活動分類 娛樂");
        }else if(choice.equals("7")){
            content.setImageResource(R.drawable.s57);
            getSupportActionBar().setTitle("活動分類 創作美勞");
        }else if(choice.equals("8")){
            content.setImageResource(R.drawable.s58);
            getSupportActionBar().setTitle("活動分類 日常生活");
        }else if(choice.equals("9")){
            content.setImageResource(R.drawable.s59);
            getSupportActionBar().setTitle("活動分類 節慶");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_menu_send);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(social5.this, scenario1.class);
                intent.putExtra("url", "http://140.123.92.130/api/loadscenario1.php");
                intent.putExtra("title", "如何規劃活動");
                startActivity(intent);
            }
        });










    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();

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
