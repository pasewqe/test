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
import android.widget.Button;

public class co2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();


        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.co2);
        View inflated = stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("如何提升老人認知功能");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        Button c1 = (Button) findViewById(R.id.button6);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(co2.this, scenario1.class);
                intent.putExtra("url", "http://140.123.92.130/api/loadscenario1.php");
                intent.putExtra("title", "如何增強老人定向感");
                //intent.putExtra("valence", String.valueOf(valence));
                startActivity(intent);
            }
        });

        Button c2 = (Button) findViewById(R.id.button8);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(co2.this, scenario1.class);
                intent.putExtra("url", "http://140.123.92.130/api/loadscenario2.php");
                intent.putExtra("title", "如何訓練老人的語言能力");
                //intent.putExtra("valence", String.valueOf(valence));
                startActivity(intent);
            }
        });

        Button c3 = (Button) findViewById(R.id.button9);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(co2.this, scenario1.class);
                intent.putExtra("url", "http://140.123.92.130/api/loadscenario3.php");
                intent.putExtra("title", "如何訓練老人的注意力");
                //intent.putExtra("valence", String.valueOf(valence));
                startActivity(intent);
            }
        });

        Button c4 = (Button) findViewById(R.id.button10);
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(co2.this, scenario1.class);
                intent.putExtra("url", "http://140.123.92.130/api/loadscenario3.php");
                intent.putExtra("title", "如何訓練老人的記憶");
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
