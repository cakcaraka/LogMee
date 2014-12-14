package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;

import java.util.List;

/**
 * Created by Numan on 06/12/2014.
 */
public class ActWidgetAdapter extends RecyclerView.Adapter<ActWidgetAdapter.ActViewHolder> {

    private List<Activities> actList;
    private Activity act;
    private Context ctx;
    private Activity widgetActivity;
    public ActWidgetAdapter(List<Activities> contactList,Activity act, WidgetActivity wdgt) {
        this.actList = contactList;
        this.act = act;
        this.ctx = act.getApplicationContext();
        this.widgetActivity = wdgt;
    }

    @Override
    public int getItemCount() {
        return actList.size();
    }

    @Override
    public void onBindViewHolder(ActViewHolder actViewHolder, int i) {
        final Activities activity = actList.get(i);
        actViewHolder.vName.setText(activity.get_name());
        actViewHolder.vDate.setText(activity.get_dateTime());
        actViewHolder.vTextDoc.setText(""+activity.get_count_logs_text());
        actViewHolder.vTextImage.setText(""+activity.get_count_logs_image());
        actViewHolder.vTextSound.setText(""+activity.get_count_logs_speech());
        Log.d("d",activity.toString());
        Bitmap bm = activity.getBitmap();
        if(bm != null){
            actViewHolder.vImage.setImageBitmap(bm);
        }else{
            int jj = activity.get_id() % 3;
            int drawable = 0;
            if(jj == 0){
                drawable = R.drawable.default_1;
            }else if(jj == 1){
                drawable = R.drawable.default_2;
            }else{
                drawable = R.drawable.default_3;
            }
            Drawable myDraw = act.getResources().getDrawable(drawable);
            actViewHolder.vImage.setImageDrawable(myDraw);
        }

        final int ii = i;
        actViewHolder.vView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler db = new DBHandler(ctx, null);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ctx);
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, WidgetActivity.appWidgetId);

                Intent intent;
                intent = new Intent(ctx, ActViewActivity.class);
                intent.putExtra("_id", activity.get_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                db.addAWidgetData(WidgetActivity.appWidgetId, activity.get_id());

                PendingIntent pendingIntent = PendingIntent.getActivity(ctx, activity.get_id(), intent, 0);


                RemoteViews views = new RemoteViews(ctx.getPackageName(), R.layout.widget_app);
                views.setTextViewText(R.id.appwidget_text,  activity.get_name());
                Bitmap bm = activity.getBitmap();
                if(bm == null){
                    int jj = activity.get_id() % 3;
                    int drawable = 0;
                    if(jj == 0){
                        drawable = R.drawable.default_1;
                    }else if(jj == 1){
                        drawable = R.drawable.default_2;
                    }else{
                        drawable = R.drawable.default_3;
                    }
                    Drawable myDraw = act.getResources().getDrawable(drawable);
                    Bitmap bitmap = ((BitmapDrawable) myDraw).getBitmap();
                    views.setImageViewBitmap(R.id.imageWidget,bitmap);
                }else{
                    views.setImageViewBitmap(R.id.imageWidget, activity.getBitmap());
                }
                views.setOnClickPendingIntent(R.id.imageWidget, pendingIntent);
                views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

                appWidgetManager.updateAppWidget(WidgetActivity.appWidgetId, views);
                widgetActivity.setResult(widgetActivity.RESULT_OK, resultValue);
                widgetActivity.finish();


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
        protected TextView vTextDoc;
        protected TextView vTextImage;
        protected TextView vTextSound;

        protected ImageView vImage;
        protected TextView vDate;
        protected View vView;
        public ActViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.textTitle);
            vTextDoc = (TextView)  v.findViewById(R.id.textDocument);
            vTextImage = (TextView)  v.findViewById(R.id.textImage);
            vTextSound = (TextView)  v.findViewById(R.id.textSound);

            vImage = (ImageView) v.findViewById(R.id.imageAct);
            vDate = (TextView) v.findViewById(R.id.textDate);
            vView =v;
        }
    }
}
