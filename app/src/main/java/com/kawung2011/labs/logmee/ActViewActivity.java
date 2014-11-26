package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Logs;

import java.util.List;


public class ActViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_view_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("_id");
        displayListViewLogs(Integer.parseInt((id)));

        Button button = (Button) findViewById(R.id.btnAddLog);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(ActViewActivity.this, LogCreateActivity.class);
                i.putExtra("_id", id);
                startActivity(i);
            }
        });

        /*
        DBHandler db = new DBHandler(this, null);
        List<Logs> list = db.getAllLogs();
        String logs = "";
        for(int i=0; i < list.size(); i++) {
         logs += list.get(i).toString() +"\n";
        }
        if(logs.equals("")) logs = "belum ada log";

        TextView text = (TextView) findViewById(R.id.logs);
        text.setText(logs);
        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_view, menu);
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

    private void displayListViewLogs(int id) {
        DBHandler db = new DBHandler(this, null);
        Cursor cursor = db.fetchAllLogsById(id);

        // The desired columns to be bound
        String[] columns = new String[] {
                DBHandler.getlId(),
                DBHandler.getlActivity(),
                DBHandler.getlText(),
                DBHandler.getlImg(),
                DBHandler.getlSpeech(),
                DBHandler.getlLocation(),
                DBHandler.getlLocationLatitude(),
                DBHandler.getlLocationLongitude(),
                DBHandler.getaDateTime()
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.textViewLogId,
                R.id.textViewLogActivity,
                R.id.textViewLogText,
                R.id.textViewLogImage,
                R.id.textViewLogSpeech,
                R.id.textViewLogLocation,
                R.id.textViewLogLatitude,
                R.id.textViewLogLatitude,
                R.id.textViewLogDateTime
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(
                this, R.layout.log_info,
                cursor,
                columns,
                to,
                0);
        ListView listView = (ListView) findViewById(R.id.listViewLogs);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        /*
        final View onClickView = rootView;
        Activity ctx = getActivity();
        final Intent intent = new Intent(ctx, ActViewActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                // Get the state's capital from this row in the database.
                String name = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                intent.putExtra("key", name);
                startActivity(intent);
            }
        });
        */
}
}
