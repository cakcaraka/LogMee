package com.kawung2011.labs.logmee;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Logs;

import java.util.List;


public class ActViewActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private Activities act;
    private RecyclerView recList;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        DBHandler db = new DBHandler(this,null);

        Intent intent = getIntent();
        id = intent.getIntExtra("_id",0);
        act = db.findActivity(id);

        if (toolbar != null) {
            toolbar.setTitle(act.get_name());
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        final int ii = id;
        Button button = (Button) findViewById(R.id.btnAddLog);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(ActViewActivity.this, LogCreateActivity.class);
                Log.d("d", ""+ii);
                i.putExtra("_id", ii);
                startActivity(i);
            }
        });

        recList = (RecyclerView) findViewById(R.id.logsCardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        List<Logs> logs = db.fetchAllLogsById(id);

        LogAdapter actAdapter = new LogAdapter(logs,getApplicationContext());
        recList.setAdapter(actAdapter);

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(recList != null){
            DBHandler db = new DBHandler(this,null);
            List<Logs> logs = db.fetchAllLogsById(id);
            LogAdapter actAdapter = new LogAdapter(logs,getApplicationContext());
            recList.setAdapter(actAdapter);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
