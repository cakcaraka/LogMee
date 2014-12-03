/*
 * The application needs to have the permission to write to external storage
 * if the output file is written to the external storage, and also the
 * permission to record audio. These permissions must be set in the
 * application's AndroidManifest.xml file, with something like:
 *
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.RECORD_AUDIO" />
 *
 */
package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class LogAddAudioActivity extends Activity
{
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    private ImageButton mRecordButton = null;
    private MediaRecorder mRecorder = null;
    private boolean mStartRecording = false;

    private Button   mPlayButton = null;
    private MediaPlayer   mPlayer = null;
    private boolean mStartPlaying = false;
    private TextView textHint = null;

    private Button mSubmitButton = null;

    public LogAddAudioActivity() {
        mFileName = Environment.getExternalStorageDirectory().toString() + File.separator + "Logmee" + File.separator ;
        mFileName += System.currentTimeMillis()+".mp3";
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.log_add_sound);

        textHint = (TextView) findViewById(R.id.textHint);
        mRecordButton = (ImageButton) findViewById(R.id.startRecord);
        mRecordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mStartPlaying) return;
                mStartRecording = !mStartRecording;
                if (mStartRecording) {
                    startRecording();
                    ((ImageButton) findViewById(R.id.startRecord)).setImageResource(R.drawable.stop_record);
                    textHint.setText("Touch the button to stop recording");
                } else {
                    stopRecording();
                    ((ImageButton) findViewById(R.id.startRecord)).setImageResource(R.drawable.start_record);
                    mPlayButton.setVisibility(View.VISIBLE);
                    mSubmitButton.setVisibility(View.VISIBLE);

                    textHint.setText("Touch the button to rerecord again");
                }

            }
        });

        mPlayButton = (Button) findViewById(R.id.playSound);
        mPlayButton.setVisibility(View.INVISIBLE);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartRecording) return;

                mStartPlaying = !mStartPlaying;
                if (mStartPlaying) {
                    startPlaying();
                    ((Button) v).setText("Stop playing");
                } else {
                    stopPlaying();
                    ((Button) v).setText("Start playing");
                }

            }
        });

        mSubmitButton = (Button) findViewById(R.id.submitSound);
        mSubmitButton.setVisibility(View.INVISIBLE);
        Button mSaveButton = (Button) findViewById(R.id.submitSound);

        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveAudio();

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
    private void startPlaying() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mStartPlaying = false;
                mPlayButton.setText("Start Playing");
            }

        });
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }



    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
    public void saveAudio(){
        /* to do

         */
        Intent i = new Intent();
        i.putExtra("path",mFileName);
        setResult(RESULT_OK,i);
        finish();

    }

    @Override
    public void onBackPressed()
    {
        File myFile = new File(mFileName);
        if(myFile.exists())
            myFile.delete();

        super.onBackPressed();  // optional depending on your needs
    }

}