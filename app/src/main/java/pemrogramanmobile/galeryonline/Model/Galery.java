package pemrogramanmobile.galeryonline.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Galery implements Parcelable {
    public int id;
    public String title;
    public String gambar_url;
    public String lokasi;
    public String lat;
    public String lng;

    public Galery(int id, String title, String image_path, String overview, String lat, String lng) {
        this.id = id;
        this.title = title;
        this.gambar_url = image_path;
        this.lokasi = lokasi;
        this.lat = lat;
        this.lng = lng;
    }

    protected Galery(Parcel in) {
        id = in.readInt();
        title = in.readString();
        gambar_url = in.readString();
        lokasi = in.readString();
        lat = in.readString();
        lng = in.readString();
    }

    public static final Creator<Galery> CREATOR = new Creator<Galery>() {
        @Override
        public Galery createFromParcel(Parcel in) {
            return new Galery(in);
        }

        @Override
        public Galery[] newArray(int size) {
            return new Galery[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(gambar_url);
        parcel.writeString(lokasi);
        parcel.writeString(lat);
        parcel.writeString(lng);
    }
}
