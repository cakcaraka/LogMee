package com.kawung2011.labs.logmee;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Activities;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.DBHandler;
import com.kawung2011.labs.logmee.com.kawung2011.labs.logmee.datamodel.Widgets;


/**
 * Implementation of App Widget functionality.
 */
public class LogMeeAppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
    }


    @Override
    public void onEnabled(Context context) {
        try {
            // Enter relevant functionality for when the first widget is created
            int[] appWidgetIds = AppWidgetManager
                    .getInstance(context)
                    .getAppWidgetIds(new ComponentName(context, LogMeeAppWidget.class));

            final int N = appWidgetIds.length;
            DBHandler db = new DBHandler(context, null);

            for (int i = 0; i < N; i++) {
                int appWidgetId = appWidgetIds[i];
                Widgets widget = db.findWidget(appWidgetId);
                // Create an Intent to launch ExampleActivity
                Intent intent;
                intent = new Intent(context, ActViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                Activities activity = db.findActivity(widget.get_activityId());
                intent.putExtra("_id", activity.get_id());
                PendingIntent pendingIntent = PendingIntent.getActivity(context, activity.get_id(), intent, 0);

                // Get the layout for the App Widget and attach an on-click listener
                // to the button
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.log_mee_app_widget);
                views.setTextViewText(R.id.appwidget_text, activity.get_name());
                if(activity.getBitmap() != null) views.setImageViewBitmap(R.id.imageWidget, activity.getBitmap());
                views.setOnClickPendingIntent(R.id.imageWidget, pendingIntent);
                views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
                views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

                AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, views);
            }
        } catch (Exception ex) {

        }



    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

 /*       CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.log_mee_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);*/


    }
}


