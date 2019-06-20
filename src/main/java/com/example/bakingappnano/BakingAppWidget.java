package com.example.bakingappnano;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId,String ingredient) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        Intent intent=new Intent(context,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(context,0,intent,0);
        views.setTextViewText(R.id.appwidget_text, ingredient);
        views.setOnClickPendingIntent(R.id.appwidget_text,pi);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        BakingWidgetServices.seeviceCall(context,"Tap to see ingredients");

    }
    static void onUpdateIngredients(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,String ingre){
        for(int appWidgetId:appWidgetIds ){
            updateAppWidget(context,appWidgetManager,appWidgetId,ingre);
        }
    }

    @Override
    public void onEnabled(Context c) {
    }

    @Override
    public void onDisabled(Context c) {
    }
}

