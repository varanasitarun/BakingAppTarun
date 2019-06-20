package com.example.bakingappnano;
import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class BakingWidgetServices extends IntentService {

    public BakingWidgetServices()
    {
        super("baking");
    }
    public static void seeviceCall(Context context,String ingredient){
        Intent intent=new Intent(context,BakingWidgetServices.class);
        intent.putExtra("Ingredients",ingredient);
        intent.setAction("action");
        context.startService(intent);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent.getAction().equals("action")){
            String ingredients=intent.getStringExtra("Ingredients");
            AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(this);
            int[] appWidgetids=appWidgetManager.getAppWidgetIds(new ComponentName(this,BakingAppWidget.class));
            BakingAppWidget.onUpdateIngredients(this,appWidgetManager,appWidgetids,ingredients);


        }

    }
}
