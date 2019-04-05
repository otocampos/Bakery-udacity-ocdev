package ocdev.com.br.bakery.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ocdev.com.br.bakery.Constants.Constantes;

/**
 * Created by Oto on 05/01/2018.
 */

public class Result implements Parcelable {

    @SerializedName(Constantes.ID_RESULT)
    private int id;
    @SerializedName(Constantes.NAME_RECEITA_RESULTS)
    private String name;
    @SerializedName(Constantes.ARRAY_LIST_INGREDIENTS)
    private ArrayList<Ingredientes> ingredientesArrayList;
    @SerializedName(Constantes.STEPS_RESULTS)
    private ArrayList<Passos> passosArrayList;
    @SerializedName(Constantes.SERVINGS_RESULT)
    private int servings;
    @SerializedName(Constantes.IMAGE_RESULTS)
    private String image;


    public Result(int id, String name, ArrayList<Ingredientes> ingredientesArrayList, ArrayList<Passos> passosArrayList, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredientesArrayList = ingredientesArrayList;
        this.passosArrayList = passosArrayList;
        this.servings = servings;
        this.image = image;
    }

    public Result() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredientes> getIngredientesArrayList() {
        return ingredientesArrayList;
    }

    public void setIngredientesArrayList(ArrayList<Ingredientes> ingredientesArrayList) {
        this.ingredientesArrayList = ingredientesArrayList;
    }

    public ArrayList<Passos> getPassosArrayList() {
        return passosArrayList;
    }

    public void setPassosArrayList(ArrayList<Passos> passosArrayList) {
        this.passosArrayList = passosArrayList;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeList(this.ingredientesArrayList);
        dest.writeList(this.passosArrayList);
        dest.writeInt(this.servings);
        dest.writeString(this.image);
    }

    protected Result(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredientesArrayList = new ArrayList<Ingredientes>();
        in.readList(this.ingredientesArrayList, Ingredientes.class.getClassLoader());
        this.passosArrayList = new ArrayList<Passos>();
        in.readList(this.passosArrayList, Passos.class.getClassLoader());
        this.servings = in.readInt();
        this.image = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
