package com.example.weatherable.app_widgets

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import com.example.weatherable.DetailYanActivity
import com.example.weatherable.R
import com.example.weatherable.utilites.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


/**
 * Implementation of App Widget functionality.
 */
class Yandex : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            CoroutineScope(Dispatchers.Default).launch {
                updateYanAppWidget(
                    context,
                    appWidgetManager,
                    appWidgetId
                )
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action == "update") {
            val watchYanWidget = ComponentName(context!!, Yandex::class.java)
            val watchGisWidget = ComponentName(context, Gismeteo::class.java)
            val watchHydWidget = ComponentName(context, Hydro::class.java)
            CoroutineScope(Dispatchers.Default).launch {
                updateGisViews(context) {
                    AppWidgetManager.getInstance(context).updateAppWidget(watchGisWidget, it)
                }
            }
            CoroutineScope(Dispatchers.Default).launch {
                updateYanViews(context) {
                    AppWidgetManager.getInstance(context).updateAppWidget(watchYanWidget, it)
                }
            }
            CoroutineScope(Dispatchers.Default).launch {
                updateHydViews(context) {
                    AppWidgetManager.getInstance(context).updateAppWidget(watchHydWidget, it)
                }
            }
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(
            context,
            appWidgetManager,
            appWidgetId, newOptions
        )
        // appWidgetManager?.updateAppWidget(appWidgetId, getView(context, newOptions))
        CoroutineScope(Dispatchers.Default).launch {
            updateYanAppWidget(context!!, appWidgetManager!!, appWidgetId)
        }
    }
}

@SuppressLint("SimpleDateFormat")
internal suspend fun updateYanAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    updateYanViews(context) {
        appWidgetManager.updateAppWidget(appWidgetId, it)
    }
}

@SuppressLint("SimpleDateFormat", "UnspecifiedImmutableFlag")
suspend fun updateYanViews(context: Context?, function: (RemoteViews) -> Unit) {
    val nowTime = SimpleDateFormat("H:mm").format(Calendar.getInstance().time)
    val views = RemoteViews(context?.packageName, R.layout.yandex)
    val value = getOnSitesTemps(YAN_URL, YAN_RAIN, 0)!!
    val nowTimeInt = nowTime.rep
    val sunUp = getOnSitesTemps(KRM_URL, GIS_SUN_UP, 0)!!.rep
    val sunDown = getOnSitesTemps(KRM_URL, GIS_SUN_UP, 1)!!.rep
    views.apply {
        setOnClickPendingIntent(R.id.image_yan, getPendingSelfIntent(context,
            "update", Yandex::class.java))
        setOnClickPendingIntent(R.id.image_now_yan,
            PendingIntent.getActivity(context, 0,
                Intent(context, DetailYanActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 0))
        setTextViewText(R.id.yan_text, getOnSitesTemps(YAN_URL,
            YAN_TEMP, 1)?.replace("+", "") + " °C")
        setTextViewText(R.id.yan_time, nowTime)
    }
    if (nowTimeInt in sunUp..sunDown || nowTimeInt in sunDown..sunUp
    ) {
        views.setImageViewResource(R.id.image_now_yan, getIconDayYan(value))
        function(views)
    } else {
        views.setImageViewResource(R.id.image_now_yan, getIconNightYan(value))
        function(views)
    }
}
