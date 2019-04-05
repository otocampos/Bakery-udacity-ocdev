package ocdev.com.br.bakery.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static ocdev.com.br.bakery.data.WidgetContract.WidgetEntry.TABLE_NAME;

/**
 * Created by Oto on 09/01/2018.
 */

public class WidgetContentProvider extends ContentProvider {

    private WidgetDbHelper mWidgetDbhelper;
    public static final int INGREDIENTES_WIDGET = 100;
    public static final int INGREDIENTES_WIDGET_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(WidgetContract.AUTHORITY, WidgetContract.PATH_WIDGET_INGREDIENTES, INGREDIENTES_WIDGET);
        uriMatcher.addURI(WidgetContract.AUTHORITY, WidgetContract.PATH_WIDGET_INGREDIENTES + "/#", INGREDIENTES_WIDGET_WITH_ID);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        mWidgetDbhelper = new WidgetDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mWidgetDbhelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor cursor;

        switch (match) {
            case INGREDIENTES_WIDGET:
                cursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            case INGREDIENTES_WIDGET_WITH_ID: {
                String normalizedUtcDateString = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{normalizedUtcDateString};
                cursor = mWidgetDbhelper.getReadableDatabase().query(
                        TABLE_NAME,
                        projection,
                        WidgetContract.WidgetEntry.COLUMN_ID_RECEITA + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);
                break;
            }
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mWidgetDbhelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case INGREDIENTES_WIDGET:
                long id = db.insert(TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(WidgetContract.WidgetEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = mWidgetDbhelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int ingredientedeletado = 0;
        switch (match) {
            case INGREDIENTES_WIDGET_WITH_ID:
                String id = uri.getPathSegments().get(1);
                ingredientedeletado = db.delete(TABLE_NAME, s, new String[]{id});
                break;

            case INGREDIENTES_WIDGET:

                ingredientedeletado = db.delete(TABLE_NAME, s, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (ingredientedeletado != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ingredientedeletado;


    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;

    }
}
