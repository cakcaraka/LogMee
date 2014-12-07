package com.kawung2011.labs.logmee;

import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Logs;

import java.io.IOException;
import java.util.List;

/**
 * Created by Caraka Nur Azmi on 30/11/2014.
 */
public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    private List<Logs> logList;
    private Context ctx;
    private Context _activityContext;
    
    public LogAdapter(List<Logs> contactList,Context ctx, Context activityContext) {
        this.logList = contactList;
        this.ctx = ctx;
        _activityContext = activityContext;
    }

    @Override
    public int getItemCount() {
        return logList.size();
    }

    @Override
    public void onBindViewHolder(LogViewHolder actViewHolder, int i) {
        final Logs ci = logList.get(i);
        actViewHolder.vName.setText(ci.get_text());
        actViewHolder.vDate.setText(ci.get_dateTime());
        if(ci.get_description() == null || ci.get_description().equals("")){
            actViewHolder.vDescription.setVisibility(View.GONE);
        }else{
            actViewHolder.vDescription.setText(ci.get_description());
        }
        if(ci.hasLocation()){
            actViewHolder.vLocation.setText("from " + ci.get_latitude() + "," + ci.get_longitude());
        }else{
            actViewHolder.vLocation.setVisibility(View.GONE);
        }
        actViewHolder.vView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                final CharSequence[] options = {"Update", "Delete", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(_activityContext);
                builder.setTitle("Action!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Update")) {
                            Intent intent = new Intent(_activityContext, LogCreateActivity.class);
                            intent.putExtra("_id", ci.get_activiy_id());
                            intent.putExtra("log_id", ci.get_id());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);
                        } else if (options[item].equals("Delete")) {
                            Intent intent = new Intent(_activityContext, ActViewActivity.class);
                            DBHandler db = new DBHandler(_activityContext, null);
                            db.deleteLog(ci);
                            intent.putExtra("_id", ci.get_activiy_id());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ctx.startActivity(intent);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

            ImageView iv = (ImageView) actViewHolder.vView.findViewById(R.id.logImage);
            final Button btn = (Button) actViewHolder.vView.findViewById(R.id.logSound);
            iv.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
            Bitmap bm = ci.get_image_bitmap();
            if(bm != null){
                iv.setImageBitmap(bm);
                iv.setVisibility(View.VISIBLE);
                actViewHolder.vImage.setImageResource(R.drawable.mark_picture);
            }else if(!ci.get_speech().equals("")){
                actViewHolder.vImage.setImageResource(R.drawable.mark_sound);

                btn.setVisibility(View.VISIBLE);
                btn.setTag(false);
                btn.setText("Play");
                final LogAudioPlayer pl = new LogAudioPlayer(ci.get_speech(),btn);
                btn.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(btn.getTag() == true){
                            btn.setTag(false);
                            btn.setText("play");
                            pl.stop();
                        }else{
                            btn.setTag(true);
                            btn.setText("stop");
                            pl.play();
                        }
                    }
                });
            }else{
                actViewHolder.vImage.setImageResource(R.drawable.mark_note);
            }
    }

    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.log_card, viewGroup, false);
        return new LogViewHolder(itemView);
    }

    public static class LogViewHolder extends RecyclerView.ViewHolder  {
        protected TextView vName;
        protected TextView vDate;
        protected TextView vLocation;
        protected TextView vDescription;
        protected View vView;
        protected LinearLayout vLayout;
        protected ImageView vImage;
        public LogViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.textTitle);
            vDate =  (TextView) v.findViewById(R.id.textDate);
            vView =v;
            vLayout = (LinearLayout) v.findViewById(R.id.logLayout);
            vImage = (ImageView) v.findViewById(R.id.imageMark);
            vLocation = (TextView) v.findViewById(R.id.textLocation);
            vDescription = (TextView) v.findViewById(R.id.textDescription);

        }


    }
}
