package ocdev.com.br.bakery.Widget;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

import ocdev.com.br.bakery.R;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientesWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId,String receitaswidget) {


        RemoteViews rv = null;

        rv=getIngredientelistRemoteView(context);

        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        IngredientesWidgetService.startActionUpdateIngredientesWidgets(context);
    }

    public static void updateIngredientesWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,String receitaswidget) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,receitaswidget);
        }
    }

    private static RemoteViews getIngredientelistRemoteView(Context context){

        RemoteViews view = new RemoteViews(context.getPackageName(),R.layout.widget_list_view_ingredientes);
        Intent intent = new Intent(context, ListWidgetService.class);
        view.setRemoteAdapter(R.id.widget_list_view,intent);

        view.setEmptyView(R.id.widget_list_view,R.id.empty_view);
        return view;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        IngredientesWidgetService.startActionUpdateIngredientesWidgets(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

