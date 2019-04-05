package ocdev.com.br.bakery.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Oto on 09/01/2018.
 */

public class WidgetContract {

    public static final String AUTHORITY = "ocdev.com.br.bakery";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_WIDGET_INGREDIENTES = "ingredientes";

    public static final class WidgetEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WIDGET_INGREDIENTES).build();
        public static final String TABLE_NAME = "ingredientes";
        public static final String COLUMN_QUANTIDADE = "quantidade";
        public static final String COLUMN_MEDIDA = "medida";
        public static final String COLUMN_INGREDIENTE = "ingrediente";
        public static final String COLUMN_ID_RECEITA = "id_receita";
    }








}
