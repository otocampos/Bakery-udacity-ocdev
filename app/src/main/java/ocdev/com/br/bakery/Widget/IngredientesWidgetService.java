package ocdev.com.br.bakery.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import ocdev.com.br.bakery.R;
import ocdev.com.br.bakery.Utils.NetworkUtils;
import ocdev.com.br.bakery.data.WidgetContract;

import static ocdev.com.br.bakery.data.WidgetContract.BASE_CONTENT_URI;
import static ocdev.com.br.bakery.data.WidgetContract.PATH_WIDGET_INGREDIENTES;

/**
 * Created by Oto on 11/01/2018.
 */

public class IngredientesWidgetService extends IntentService {
    public static final String ACTION_UPDATE_INGREDIENTE_WIDGETS = "ocdev.com.br.bakery.action.update_ingredientes_widgets";


    public IngredientesWidgetService() {
        super("IngredientesWidgetService");

    }

    public static void startActionUpdateIngredientesWidgets(Context context) {
        Intent intent = new Intent(context, IngredientesWidgetService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTE_WIDGETS);
        context.startService(intent);

    }


    private void UpdateIngredienteWidget() {
        Uri PLANT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WIDGET_INGREDIENTES).build();
        Cursor cursor = getContentResolver().query(
                PLANT_URI,
                null,
                null,
                null,
                WidgetContract.WidgetEntry._ID
        );
        String ReceitasWidget = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            int ingredientesIndex = cursor.getColumnIndex(WidgetContract.WidgetEntry.COLUMN_INGREDIENTE);
            int idreceitaIndex = cursor.getColumnIndex(WidgetContract.WidgetEntry.COLUMN_ID_RECEITA);
            cursor.moveToNext();
            ArrayList<String> listadeingrediente = new ArrayList<>();

            for (int i = 0; i < cursor.getCount() - 1; i++) {
                String Ingrediente = cursor.getString(ingredientesIndex);
                listadeingrediente.add(Ingrediente);
                cursor.moveToNext();
            }
            cursor.close();
            ReceitasWidget = NetworkUtils.TransformarListaemString(listadeingrediente);
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientesWidget.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);

        IngredientesWidget.updateIngredientesWidgets(this, appWidgetManager, appWidgetIds, ReceitasWidget);


    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTE_WIDGETS.equals(action)) {
                UpdateIngredienteWidget();
            }

        }

    }
}
