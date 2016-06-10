package androidfanatic.todo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import androidfanatic.todo.R;
import androidfanatic.todo.main.TodoActivity;
import androidfanatic.todo.models.Task;
import androidfanatic.todo.utils.ColorUtil;

public class TaskWidgetProvider extends AppWidgetProvider {


    public static void updateWidgets(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, TaskWidgetProvider.class));
        if (appWidgetIds.length > 0) {
            new TaskWidgetProvider().onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int N = appWidgetIds.length;
        SharedPreferences prefMgr = PreferenceManager.getDefaultSharedPreferences(context);
        String fgColorStr = prefMgr.getString(context.getString(R.string.pref_key_fg), ColorUtil.defaultColorHex);
        String bgColorStr = prefMgr.getString(context.getString(R.string.pref_key_bg), ColorUtil.defaultColorHex);

        int fgColor = ColorUtil.hex2int(fgColorStr);
        int bgColor = ColorUtil.hex2int(bgColorStr);

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {

            StringBuilder tasks = new StringBuilder();

            for (Task task : Task.listAll(Task.class, "id DESC")) {
                tasks.append(task.getWidgetString());
            }

            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_home);
            views.setTextViewText(R.id.textView_widget, tasks.toString());

            views.setInt(R.id.layout_widget, "setBackgroundColor", bgColor);
            views.setInt(R.id.textView_widget, "setTextColor", fgColor);

            // Set onclick
            Intent configIntent = new Intent(context, TodoActivity.class);
            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
            views.setOnClickPendingIntent(R.id.layout_widget, configPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}