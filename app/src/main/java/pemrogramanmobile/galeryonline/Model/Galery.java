package pemrogramanmobile.galeryonline.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Galery implements Parcelable {

    public int getId(String aDatum) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama(String aDatum) {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar_url(String aDatum) {
        return gambar_url;
    }

    public void setGambar_url(String gambar_url) {
        this.gambar_url = gambar_url;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public int id;
    public String nama;
    public String gambar_url;
    public String lokasi;
    public String tanggal;
    public String deskripsi;

    public Galery(int id, String nama, String gambar_url, String lokasi,String tanggal, String deskripsi) {
        this.id = id;
        this.nama = nama;
        this.gambar_url = gambar_url;
        this.lokasi = lokasi;
        this.tanggal = tanggal;
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
