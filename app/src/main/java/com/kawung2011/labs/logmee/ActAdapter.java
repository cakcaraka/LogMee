package com.kawung2011.labs.logmee;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;

import java.util.List;

/**
 * Created by Caraka Nur Azmi on 30/11/2014.
 */
public class ActAdapter extends RecyclerView.Adapter<ActAdapter.ActViewHolder> {

    private List<Activities> actList;
    private Context ctx;
    public ActAdapter(List<Activities> contactList,Context ctx) {
        this.actList = contactList;
        this.ctx = ctx;
    }

    @Override
    public int getItemCount() {
        return actList.size();
    }

    @Override
    public void onBindViewHolder(ActViewHolder actViewHolder, int i) {
        final Activities ci = actList.get(i);
        actViewHolder.vName.setText(ci.get_name());
        actViewHolder.vDate.setText(ci.get_dateTime());
        actViewHolder.vDescription.setText("Lorem Ipsum");
        Bitmap bm = ci.getBitmap();
        if(bm != null){
            actViewHolder.vImage.setImageBitmap(bm);
        }
        final int ii = i;
        actViewHolder.vView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ctx, ActViewActivity.class);
                Log.d("d", ii + ""+ci.get_id());
                intent.putExtra("_id", ci.get_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public ActViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.act_card, viewGroup, false);
        return new ActViewHolder(itemView);
    }

    public static class ActViewHolder extends RecyclerView.ViewHolder  {
        protected TextView vName;
        protected TextView vDescription;
        protected ImageView vImage;
        protected TextView vDate;
        protected View vView;
        public ActViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.textTitle);
            vDescription = (TextView)  v.findViewById(R.id.textDescription);
            vImage = (ImageView) v.findViewById(R.id.imageAct);
            vDate = (TextView) v.findViewById(R.id.textDate);
            vView =v;
        }
    }
}
