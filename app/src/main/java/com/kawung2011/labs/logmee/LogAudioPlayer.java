package com.kawung2011.labs.logmee;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by Caraka Nur Azmi on 3/12/2014.
 */
public class LogAudioPlayer{
    MediaPlayer mp;
    String path;
    Button btn;
    public LogAudioPlayer(String path,Button btn){
        this.path = path;
        this.btn = btn;
    }

    public void play(){
        mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                btn.setText("play");
                btn.setTag(false);
            }

        });
        try {
            mp.setDataSource(this.path);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            Log.e("d", "prepare() failed");
        }
    }

    public void stop(){
        mp.release();
        mp = null;
    }


}
