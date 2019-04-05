package ocdev.com.br.bakery.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import ocdev.com.br.bakery.Model.Result;

/**
 * Created by Oto on 05/01/2018.
 */

public class OpenJsonUtils {

    public Gson gson;

    public OpenJsonUtils() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    public List<Result> getListaResultados(String Jsonstring){
        List<Result> results = Arrays.asList(gson.fromJson(Jsonstring, Result[].class));
        return results;

    }


}
