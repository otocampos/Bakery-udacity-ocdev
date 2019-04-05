package ocdev.com.br.bakery.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import ocdev.com.br.bakery.Constants.Constantes;

/**
 * Created by Oto on 05/01/2018.
 */

public class Passos implements Parcelable {

    @SerializedName(Constantes.ID)
    private int id;
    @SerializedName(Constantes.SHORTDESCRIPTION)
    private String shortDescription;
    @SerializedName(Constantes.DESCRIPTION)
    private String description;
    @SerializedName(Constantes.VIDEOURL)
    private String videoURL;
    @SerializedName(Constantes.THUMBNAILURL)
    private String thumbnailURL;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public Passos(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public Passos() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }

    protected Passos(Parcel in) {
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Creator<Passos> CREATOR = new Creator<Passos>() {
        @Override
        public Passos createFromParcel(Parcel source) {
            return new Passos(source);
        }

        @Override
        public Passos[] newArray(int size) {
            return new Passos[size];
        }
    };
}
