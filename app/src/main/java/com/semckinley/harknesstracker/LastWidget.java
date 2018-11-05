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

    static String mClassList;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String classList, int appWidgetId) {

        //CharSequence widgetText = message;

        CharSequence widgetText = context.getString(R.string.appwidget_text);



        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.last_widget);


        Intent intent = new Intent(context, ClassInformationService.class);
        intent.setAction(ClassInformationService.ACTION_UPDATE_CLASS);

        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        String message = "this is a widget";


        if(classList ==null) {
            views.setTextViewText(R.id.appwidget_text, widgetText);
        } else {views.setTextViewText(R.id.appwidget_text, classList );}
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ClassInformationService.startActionUpdateClass(context);
        // There may be multiple widgets active, so update all of them
      //String classList = "no class data";
        //for (int appWidgetId : appWidgetIds) {
          //  updateAppWidget(context, appWidgetManager, appWidgetId);
       // }
    }
    public static void updateClassWidget(Context context, AppWidgetManager appWidgetManager, String classList){
        mClassList = classList;
        updateAppWidget(context, appWidgetManager, classList,0);

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

