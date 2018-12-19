package pemrogramanmobile.galeryonline.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Galery implements Parcelable {


    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String nama;

    public String getGambar_url() {
        return gambar_url;
    }

    public void setGambar_url(String gambar_url) {
        this.gambar_url = gambar_url;
    }

    public String gambar_url;
    public String lokasi;
    public String tanggal;
    public String deskripsi;
    public String lat;
    public String lng;

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Galery(int id, String s, String nama, String gambar_url, String lokasi, String deskripsi, String cursorString, String string) {
        this.id = id;
        this.nama = nama;
        this.gambar_url = gambar_url;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
    }


    protected Galery(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        lokasi = in.readString();
        gambar_url = in.readString();
        tanggal = in.readString();
        deskripsi = in.readString();

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
        parcel.writeString(nama);
        parcel.writeString(lokasi);
        parcel.writeString(gambar_url);
        parcel.writeString(tanggal);
        parcel.writeString(deskripsi);
    }
}
