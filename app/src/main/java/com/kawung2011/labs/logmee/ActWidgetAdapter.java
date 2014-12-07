package com.kawung2011.labs.logmee;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;

import org.w3c.dom.Text;

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
        actViewHolder.vDescription.setText("Lorem Ipsum");
        Bitmap bm = activity.getBitmap();
        if(bm != null){
            actViewHolder.vImage.setImageBitmap(bm);
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

                PendingIntent pendingIntent = PendingIntent.getActivity(ctx, activity.get_id(), intent, 0);
                db.addAWidgetData(WidgetActivity.appWidgetId, activity.get_id());

                RemoteViews views = new RemoteViews(ctx.getPackageName(), R.layout.log_mee_app_widget);
                views.setTextViewText(R.id.appwidget_text, activity.get_name());
                if(activity.getBitmap() != null) views.setImageViewBitmap(R.id.imageWidget, activity.getBitmap());
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
