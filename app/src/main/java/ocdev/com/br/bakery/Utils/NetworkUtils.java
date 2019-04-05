package ocdev.com.br.bakery.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import ocdev.com.br.bakery.Constants.Constantes;
import ocdev.com.br.bakery.Model.Passos;
import ocdev.com.br.bakery.Model.Result;

/**
 * Created by Oto on 05/01/2018.
 */

public class NetworkUtils {
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(Constantes.URL_BASE).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static class Reversed<T> implements Iterable<T> {
        private final List<T> original;

        public Reversed(List<T> original) {
            this.original = original;
        }

        public Iterator<T> iterator() {
            final ListIterator<T> i = original.listIterator(original.size());

            return new Iterator<T>() {
                public boolean hasNext() {
                    return i.hasPrevious();
                }

                public T next() {
                    return i.previous();
                }

                public void remove() {
                    i.remove();
                }
            };
        }
    }

    public static String getThumb(Result result) {

        if (!result.getImage().isEmpty()) {
            return result.getImage();
        }

        for (Passos passo : new NetworkUtils.Reversed<>(result.getPassosArrayList())) {
            if (!passo.getThumbnailURL().isEmpty())
                return passo.getThumbnailURL();
        }

        for (Passos passos : new NetworkUtils.Reversed<>(result.getPassosArrayList())) {
            if (!passos.getVideoURL().isEmpty() && passos.getId() != 0)
                return passos.getVideoURL();
        }

        return null;
    }


    public static String TransformarListaemString(ArrayList<String> listadestrings) {
        StringBuilder sb = new StringBuilder();


        for (String str : listadestrings) {
            sb.append(str + "\n");
        }

        return sb.toString();
    }


}
