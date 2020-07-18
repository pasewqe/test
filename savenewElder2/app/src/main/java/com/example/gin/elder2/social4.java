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
import android.widget.ImageButton;

public class social4 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();


        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.social4);
        View inflated = stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("經常運用之活動分類");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        ImageButton s1 = (ImageButton) findViewById(R.id.imageButton);
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(social4.this, social5.class);
                intent.putExtra("choice", "1");
                startActivity(intent);
            }
        });

        ImageButton s2 = (ImageButton) findViewById(R.id.imageButton6);
        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(social4.this, social5.class);
                intent.putExtra("choice", "2");
                startActivity(intent);
            }
        });

        ImageButton s3 = (ImageButton) findViewById(R.id.imageButton7);
        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(social4.this, social5.class);
                intent.putExtra("choice", "3");
                startActivity(intent);
            }
        });

        ImageButton s4 = (ImageButton) findViewById(R.id.imageButton8);
        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(social4.this, social5.class);
                intent.putExtra("choice", "4");
                startActivity(intent);
            }
        });

        ImageButton s5 = (ImageButton) findViewById(R.id.imageButton9);
        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(social4.this, social5.class);
                intent.putExtra("choice", "5");
                startActivity(intent);
            }
        });


        ImageButton s6 = (ImageButton) findViewById(R.id.imageButton10);
        s6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(social4.this, social5.class);
                intent.putExtra("choice", "6");
                startActivity(intent);
            }
        });


        ImageButton s7 = (ImageButton) findViewById(R.id.imageButton11);
        s7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(social4.this, social5.class);
                intent.putExtra("choice", "7");
                startActivity(intent);
            }
        });

        ImageButton s8 = (ImageButton) findViewById(R.id.imageButton12);
        s8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(social4.this, social5.class);
                intent.putExtra("choice", "8");
                startActivity(intent);
            }
        });

        ImageButton s9 = (ImageButton) findViewById(R.id.imageButton13);
        s9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(social4.this, social5.class);
                intent.putExtra("choice", "9");
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
