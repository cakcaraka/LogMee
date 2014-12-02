package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Logs;

import java.util.List;

/**
 * Created by Caraka Nur Azmi on 30/11/2014.
 */
public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    private List<Logs> logList;
    private Context ctx;
    public LogAdapter(List<Logs> contactList,Context ctx) {
        this.logList = contactList;
        this.ctx = ctx;
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
        Bitmap bm = ci.get_image_bitmap();
        if(bm != null){
            ImageView iv = new ImageView(ctx);
            iv.setImageBitmap(bm);
            actViewHolder.vLayout.addView(iv);
        }else{
            Log.d("d",ci.get_text() + " " + ci.get_image());
        }
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
        protected LinearLayout vLayout;
        protected View vView;
        public LogViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.textTitle);
            vDate =  (TextView) v.findViewById(R.id.textDate);
            vLayout = (LinearLayout) v.findViewById(R.id.log_content);
            vView =v;
        }
    }
}
