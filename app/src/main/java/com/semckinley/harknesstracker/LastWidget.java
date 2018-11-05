package com.semckinley.harknesstracker;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.RemoteViews;
import com.semckinley.harknesstracker.ClassInformationService;

/**
 * Implementation of App Widget functionality.
 */
public class LastWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //CharSequence widgetText = message;
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.last_widget);

       // LocalBroadcastManager.getInstance(context).registerReceiver();

       //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        Intent intent = new Intent(context, ClassInformationService.class);
        intent.setAction(ClassInformationService.ACTION_UPDATE_CLASS);

        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        String message = "this is a widget";


        
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static RemoteViews getRemoteView(Context context){

        Intent intent = new Intent(context, ClassInformationService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        String message = "this is a widget";
        CharSequence widgetText = message;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.last_widget);
          views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        return views;
    }
}

