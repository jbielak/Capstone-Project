package com.jbielak.wordsguide.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.jbielak.wordsguide.R;
import com.jbielak.wordsguide.TrackActivity;
import com.jbielak.wordsguide.adapter.TrackAdapter;
import com.jbielak.wordsguide.model.Track;

public class WordsGuideWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Track track, int appWidgetId) {

        Intent intent = new Intent(context, TrackActivity.class);
        intent.putExtra(TrackAdapter.EXTRA_TRACK, track);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.words_guide_widget);
        views.setTextViewText(R.id.tv_title, track.getTrackName());
        views.setTextViewText(R.id.tv_artist, track.getArtistName());
        views.setOnClickPendingIntent(R.id.words_guide_widget_container, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    public static void updateTrackWidgets(Context context, AppWidgetManager appWidgetManager,
                                           Track track, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, track, appWidgetId);
        }
    }

}
