package com.kawung2011.labs.logmee;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;
import com.kawung2011.labs.logmee.lib.FloatingActionButton;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private RecyclerView recList;
    String title = "Logmee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        if (toolbar != null) {
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
        }
        initDrawer();

        FloatingActionButton fabButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.float_add))
                .withButtonColor(Color.rgb(233, 30, 99))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withButtonSize(64)
                .withMargins(0, 16, 16, 0)
                .create();

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ActCreateActivity.class);
                startActivity(i);
            }
        });
        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        DBHandler db = new DBHandler(this,null);
        List<Activities> acts = db.fetchAllActivities();

        ActAdapter actAdapter = new ActAdapter(acts,getApplicationContext());
        recList.setAdapter(actAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(recList != null){
            DBHandler db = new DBHandler(this,null);
            List<Activities> acts = db.fetchAllActivities();
            ActAdapter actAdapter = new ActAdapter(acts,getApplicationContext());
            recList.setAdapter(actAdapter);
            Log.d("s","berubah");

        }
    }

    /**
         * init navigation drawer thing
         */
    private void initDrawer() {
        //setup navigation drawer
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.txt_open, R.string.txt_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // when drawer closed
                toolbar.setTitle(title);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // when drawer open
                toolbar.setTitle("Nav menu");
            }
        };

        // setDrawerlisterner
        drawerLayout.setDrawerListener(drawerToggle);

        LinearLayout set = (LinearLayout) findViewById(R.id.drawerSettings);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"abc",Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawers();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}