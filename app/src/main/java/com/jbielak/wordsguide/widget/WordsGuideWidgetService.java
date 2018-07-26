package com.jbielak.wordsguide.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.jbielak.wordsguide.model.Track;

public class WordsGuideWidgetService  extends IntentService {

    public static final String WORDS_GUIDE_WIDGET_ACTION_UPDATE = "update_track";
    private static final String BUNDLE_WIDGET_DATA = "track_data";

    public WordsGuideWidgetService() {
        super("WordsGuideWidgetService");
    }

    public static void startActionUpdateTrackWidgets(Context context, Track track) {
        Intent intent = new Intent(context, WordsGuideWidgetService.class);
        intent.setAction(WORDS_GUIDE_WIDGET_ACTION_UPDATE);
        intent.putExtra(BUNDLE_WIDGET_DATA, track);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (WORDS_GUIDE_WIDGET_ACTION_UPDATE.equals(action) &&
                    intent.getParcelableExtra(BUNDLE_WIDGET_DATA) != null) {
                handleActionUpdateWidgets((Track)intent.getParcelableExtra(BUNDLE_WIDGET_DATA));
            }
        }
    }

    private void handleActionUpdateWidgets(Track track) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, WordsGuideWidgetProvider.class));
        WordsGuideWidgetProvider.updateTrackWidgets(this, appWidgetManager, track, appWidgetIds);
    }
}
