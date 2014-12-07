package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Logs;
import com.kawung2011.labs.logmee.lib.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


public class ActViewActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private Activities act;
    private RecyclerView recList;
    private int id;
    private DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        db = new DBHandler(this,null);
        Intent intent = getIntent();
        id = intent.getIntExtra("_id", 0);
        act = db.findActivity(id);
        if(act == null){
            Toast.makeText(this,"activity missing, may have been deleted",Toast.LENGTH_SHORT).show();
            finish();
        }
        if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        FloatingActionButton fabButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.float_add))
                .withButtonColor(Color.rgb(233, 30, 99))
                .withGravity(Gravity.TOP | Gravity.LEFT)
                .withButtonSize(64)
                .withMargins(123, 0, 0, 8)
                .create();

        final int ii = id;
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActViewActivity.this, LogCreateActivity.class);
                Log.d("d", "" + ii);
                i.putExtra("_id", ii);

                startActivity(i);
            }
        });

        ((TextView) findViewById(R.id.textActTitle)).setText(act.get_name());
        ((TextView) findViewById(R.id.textActDate)).setText(act.get_dateTime());

        recList = (RecyclerView) findViewById(R.id.logsCardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        updateList("");

    }

    private void updateList(String query){
        List<Logs> logs = db.fetchAllLogsById(id,query);
        LogAdapter actAdapter = new LogAdapter(logs, getApplicationContext(), ActViewActivity.this);
        recList.setAdapter(actAdapter);
        registerForContextMenu(recList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recList != null) {
            DBHandler db = new DBHandler(this, null);
            List<Logs> logs = db.fetchAllLogsById(id);
            LogAdapter actAdapter = new LogAdapter(logs, getApplicationContext(), ActViewActivity.this);
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
            case R.id.action_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("*/*");
                String shareBody = "My log on \"" + act.get_name() + "\" #LogMeeApp";
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "LogMee Activity:" + act.get_name());
                Uri uri = Uri.fromFile(new File(takeScreenShot()));
                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

                startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        return super.onOptionsItemSelected(item);
    }

    private String takeScreenShot()
    {
        View view = this.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height  - statusBarHeight);
        view.destroyDrawingCache();
        return savePic(b);
    }
    private String savePic(Bitmap b)
    {
        String path = android.os.Environment
                .getExternalStorageDirectory()
                + File.separator
                + "Logmee" + File.separator + "screenshot";
        OutputStream outFile = null;
        new File(path).mkdirs();
        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
            if (null != fos)
            {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                return file.getAbsolutePath();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act_view, menu);
        SearchView sv = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        sv.setQueryHint("search..");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String query) {
                updateList(query);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
