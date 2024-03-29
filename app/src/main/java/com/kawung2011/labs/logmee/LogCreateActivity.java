package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Logs;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Logs;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;
import com.kawung2011.labs.logmee.lib.LocationFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


public class LogCreateActivity extends ActionBarActivity {
    private Toolbar toolbar;
   private int REQ_SOUND = 0;
    private int REQ_CAM = 1;
    private int REQ_GAL = 2;
    private LinearLayout mediaLayout;
    private ImageView viewImage;
    private Button viewSound;
    private int type = -1;
    private int log_id;
    private int act_id;
    private LocationFinder  lf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_create_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mediaLayout = (LinearLayout) findViewById(R.id.mediaLayout);
        lf = new LocationFinder(getApplicationContext(),((TextView) findViewById(R.id.textLocation)));
        DBHandler db = new DBHandler(this,null);
        Intent intent = getIntent();
        log_id = intent.getIntExtra("log_id",-1);
        act_id = intent.getIntExtra("_id", -1);
        if(log_id != -1) {
            Logs log = db.findLog(log_id);
            EditText txt = (EditText) findViewById(R.id.editText_log_title);
            txt.setText(log.get_text());
            ((EditText) findViewById(R.id.editText_log_desc)).setText(log.get_description());
            if(log.get_image_bitmap() != null) {
                viewImage = new ImageView(this);
                viewImage.setImageBitmap(log.get_image_bitmap());
                mediaLayout.removeAllViews();
                mediaLayout.addView(viewImage);
            }else if(log.get_speech() != null && !log.get_speech().equals("")){
                viewSound = new Button(this);
                viewSound.setVisibility(View.VISIBLE);
                viewSound.setTag(false);
                viewSound.setText("Play");
                final LogAudioPlayer pl = new LogAudioPlayer(log.get_speech(),viewSound);
                viewSound.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(viewSound.getTag() == true){
                            viewSound.setTag(false);
                            viewSound.setText("play");
                            pl.stop();
                        }else{
                            viewSound.setTag(true);
                            viewSound.setText("stop");
                            pl.play();
                        }
                    }
                });
                viewImage.setTag(log.get_speech());
                mediaLayout.removeAllViews();
                mediaLayout.addView(viewSound);
                mediaLayout.addView(viewImage);
            }
        }

        if (toolbar != null) {
            toolbar.setTitle("Create Log");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        ImageButton button = (ImageButton) findViewById(R.id.btnAddImagesGallery);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_GAL);
            }
        });
        ImageButton button3 = (ImageButton) findViewById(R.id.btnAddImagesCam);
        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, REQ_CAM);
            }
        });

        ImageButton button2 = (ImageButton) findViewById(R.id.btnAddSound);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(LogCreateActivity.this, LogAddAudioActivity.class);
                startActivityForResult(i, REQ_SOUND);
            }
        });

        ImageButton button4 = (ImageButton) findViewById(R.id.btnAddLocation);
        if(log_id == -1) {
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lf.requestLocation();
                }
            });
        }else{
            button4.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onPause(){
        super.onPause();
        lf.stop();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
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
        } else if(id == R.id.action_submit){
            createLog();
        } else if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createLog() {
        EditText et = (EditText) findViewById(R.id.editText_log_title);
        EditText ed = (EditText) findViewById(R.id.editText_log_desc);

        if(et.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Text can't be empty", Toast.LENGTH_SHORT).show();
        }else {
            DBHandler db = new DBHandler(this, null);
            if (lf.is_requesting) {
                Toast.makeText(getApplicationContext(), "Still requesting location.. ", Toast.LENGTH_SHORT).show();
            }else {
                if (log_id != -1) {
                    Logs log = db.findLog(log_id);
                    log.set_text(et.getText().toString());
                    log.set_description(ed.getText().toString());
                    if (type == REQ_CAM || type == REQ_GAL) {
                        Bitmap bmp;
                        try {
                            bmp = ((BitmapDrawable) viewImage.getDrawable()).getBitmap();


                        } catch (Exception e) {
                            bmp = null;
                        }
                        log.set_image(bmp);
                        log.set_speech("");
                    } else if (type == REQ_SOUND) {
                        log.set_speech(viewImage.getTag().toString());
                        log.set_image("");
                    }
                    db.updateLog(log);
                } else {
                    Logs log = new Logs(act_id, et.getText().toString());
                    log.set_description(ed.getText().toString());
                    if (type == REQ_CAM || type == REQ_GAL) {
                        Bitmap bmp;
                        try {
                            bmp = ((BitmapDrawable) viewImage.getDrawable()).getBitmap();

                        } catch (Exception e) {
                            bmp = null;
                        }
                        log.set_image(bmp);
                    } else if (type == REQ_SOUND) {
                        log.set_speech(viewImage.getTag().toString());
                    }
                    String loc = ((TextView) findViewById(R.id.textLocation)).getText().toString();
                    if(!loc.equals("")){
                        String[] location = loc.split(",");
                        log.set_latitude(location[0]);
                        log.set_longitude(location[1]);
                    }
                    Log.d("d", log.toString());
                    db.addLogs(log);
                }
                finish();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            type = requestCode;
            viewImage = new ImageView(this);
            if (requestCode == REQ_CAM) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    viewImage.setImageBitmap(bitmap);
                    mediaLayout.removeAllViews();
                    mediaLayout.addView(viewImage);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Logmee" + File.separator + "activity";
                    OutputStream outFile = null;
                    new File(path).mkdirs();
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQ_GAL) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();

                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                viewImage.setImageBitmap(thumbnail);
                mediaLayout.removeAllViews();
                mediaLayout.addView(viewImage);
            } else if (requestCode == REQ_SOUND) {
                String sound = data.getStringExtra("path");
                viewSound = new Button(this);
                viewSound.setVisibility(View.VISIBLE);
                viewSound.setTag(false);
                viewSound.setText("Play");
                final LogAudioPlayer pl = new LogAudioPlayer(sound,viewSound);
                viewSound.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(viewSound.getTag() == true){
                            viewSound.setTag(false);
                            viewSound.setText("play");
                            pl.stop();
                        }else{
                            viewSound.setTag(true);
                            viewSound.setText("stop");
                            pl.play();
                        }
                    }
                });
                viewImage.setTag(sound);
                mediaLayout.removeAllViews();
                mediaLayout.addView(viewSound);
                mediaLayout.addView(viewImage);

            }
            Log.d("s",""+requestCode);
        }
    }
}
