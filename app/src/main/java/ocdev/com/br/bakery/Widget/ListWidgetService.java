package ocdev.com.br.bakery.Widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import ocdev.com.br.bakery.R;
import ocdev.com.br.bakery.data.WidgetContract;

import static ocdev.com.br.bakery.data.WidgetContract.BASE_CONTENT_URI;
import static ocdev.com.br.bakery.data.WidgetContract.PATH_WIDGET_INGREDIENTES;

/**
 * Created by Oto on 12/01/2018.
 */

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListeRemoteViewFacotiry(this.getApplicationContext());
    }

}

class ListeRemoteViewFacotiry implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    Cursor mCursor;

    public ListeRemoteViewFacotiry(Context applicationContext) {
        mContext = applicationContext;

    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Uri PLANT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WIDGET_INGREDIENTES).build();
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                PLANT_URI,
                null,
                null,
                null,
                WidgetContract.WidgetEntry._ID
        );
    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() == 0) return null;
        mCursor.moveToPosition(position);

        int idIngredientes = mCursor.getColumnIndex(WidgetContract.WidgetEntry.COLUMN_INGREDIENTE);
        int idQuantidade = mCursor.getColumnIndex(WidgetContract.WidgetEntry.COLUMN_QUANTIDADE);

        String IngredientesText = mCursor.getString(idIngredientes);
        String QuantidadesText = mCursor.getString(idQuantidade);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredientes_widget);

        views.setTextViewText(R.id.appwidget_text,QuantidadesText+ " " +IngredientesText);


        return views;


    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}