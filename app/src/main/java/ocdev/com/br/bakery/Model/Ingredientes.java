package ocdev.com.br.bakery.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import ocdev.com.br.bakery.Constants.Constantes;

/**
 * Created by Oto on 05/01/2018.
 */

public class Ingredientes implements Parcelable{
    @SerializedName(Constantes.QUANTITY)
    private String quantity;
    @SerializedName(Constantes.MEASURE)
    private String measure;
    @SerializedName(Constantes.INGREDIENT)
    private String ingredient;

    public Ingredientes(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public Ingredientes() {
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    protected Ingredientes(Parcel in) {
        this.quantity = in.readString();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Creator<Ingredientes> CREATOR = new Creator<Ingredientes>() {
        @Override
        public Ingredientes createFromParcel(Parcel source) {
            return new Ingredientes(source);
        }

        @Override
        public Ingredientes[] newArray(int size) {
            return new Ingredientes[size];
        }
    };
}
