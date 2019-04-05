package ocdev.com.br.bakery.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Oto on 09/01/2018.
 */

public class WidgetDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "receitas.db";
    private static final int VERSION = 1;

    public WidgetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " + WidgetContract.WidgetEntry.TABLE_NAME + " (" +
                WidgetContract.WidgetEntry._ID + " INTEGER PRIMARY KEY, " +
                WidgetContract.WidgetEntry.COLUMN_QUANTIDADE + " TEXT NOT NULL, " +
                WidgetContract.WidgetEntry.COLUMN_MEDIDA + " TEXT NOT NULL, " +
                WidgetContract.WidgetEntry.COLUMN_INGREDIENTE + " TEXT NOT NULL, " +
                WidgetContract.WidgetEntry.COLUMN_ID_RECEITA + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WidgetContract.WidgetEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
