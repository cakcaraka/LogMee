package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Logs;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;

import java.util.List;


public class LogCreateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_create_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Button button = (Button) findViewById(R.id.btnAddImages);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(LogCreateActivity.this, LogAddImagesActivity.class);

                startActivity(i);
            }
        });

        Button button2 = (Button) findViewById(R.id.btnAddSound);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(LogCreateActivity.this, LogAddAudioActivity.class);
                //Intent i = new Intent(ctx, TestAudioRecord.class);

                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_view, menu);
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
        } else if(id == R.id.action_create_log){
            createLog();
        } else if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createLog() {
        EditText et = (EditText) findViewById(R.id.editText_log_text);
        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("_id"));

        if(et.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Text can't be empty", Toast.LENGTH_SHORT).show();
        }else{
            DBHandler db = new DBHandler(this, null);
            db.addLogs(new Logs(id, et.getText().toString(), "", "", "", "", "", ""));
            Toast.makeText(getApplicationContext(),et.getText(),Toast.LENGTH_SHORT).show();
        }
    }
}
