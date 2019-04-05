package ocdev.com.br.bakery.Utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ocdev.com.br.bakery.Constants.Constantes;

/**
 * Created by Oto on 07/01/2018.
 */

public class ConfigTela {

    public static RecyclerView.LayoutManager RecuperarEstilodoRecyclerview(Activity activity) {
        int value = activity.getResources().getConfiguration().orientation;
        RecyclerView.LayoutManager layoutManager = null;

        if (value == Configuration.ORIENTATION_PORTRAIT) {

            layoutManager
                    = (RecyclerView.LayoutManager) new LinearLayoutManager(activity.getApplicationContext());

        }
        if (value == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager
                    = (RecyclerView.LayoutManager) new GridLayoutManager(activity.getApplicationContext(), 2);

        }
        return layoutManager;
    }

    public static String OrientacaoTela(Activity activity) {
        int value = activity.getResources().getConfiguration().orientation;
        String orientacao = null;
        if (value == Configuration.ORIENTATION_PORTRAIT) {
            orientacao = Constantes.PORTRAIT;
        }

        if (value == Configuration.ORIENTATION_LANDSCAPE) {

            orientacao = Constantes.LANDSCAPE;
        }
        return orientacao;
    }

    public static int GetRecyclerviewColunasListadePassoa(Activity activity) {
        int value = activity.getResources().getConfiguration().orientation;
        int colunas = 0;
        if (value == Configuration.ORIENTATION_PORTRAIT) {
            colunas = 4;
        }
        if (value == Configuration.ORIENTATION_LANDSCAPE) {
            colunas = 3;
        }
        return colunas;
    }



}
