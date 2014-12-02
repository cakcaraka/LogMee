package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Logs;

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
    public void onBindViewHolder(LogViewHolder logViewHolder, int i) {
        final Logs log = logList.get(i);
        logViewHolder.vName.setText(log.get_text());
        logViewHolder.vDate.setText(log.get_dateTime());

        logViewHolder.vView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                final CharSequence[] options = { "Update", "Delete", "Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(_activityContext);
                builder.setTitle("Action!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Update"))
                        {
                            Intent intent = new Intent(_activityContext, LogCreateActivity.class);
                            intent.putExtra("_id", log.get_activiy_id());
                            intent.putExtra("log_id", log.get_id());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(intent);
                        }
                        else if (options[item].equals("Delete"))
                        {
                            Intent intent = new Intent(_activityContext, ActViewActivity.class);
                            DBHandler db = new DBHandler(_activityContext, null);
                            db.deleteLog(log);
                            intent.putExtra("_id", log.get_activiy_id());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ctx.startActivity(intent);

                        }
                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_log, viewGroup, false);
        return new LogViewHolder(itemView);
    }

    public static class LogViewHolder extends RecyclerView.ViewHolder  {
        protected TextView vName;
        protected TextView vDate;

        protected View vView;
        public LogViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.textTitle);
            vDate =  (TextView) v.findViewById(R.id.textDate);

            vView =v;
        }


    }
}
