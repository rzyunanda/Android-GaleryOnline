package pemrogramanmobile.galeryonline.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "galeri")
public class RomGalery {

    @PrimaryKey
    public Integer id;

    @ColumnInfo(name = "nama")
    public String nama;

    @ColumnInfo(name = "lokasi")
    public String lokasi;

    @ColumnInfo(name = "gambar_url")
    public String gambar_url;

    @ColumnInfo(name = "deskripsi")
    public String deskripsi;

    @ColumnInfo(name = "lat")
    public String lat;

    @ColumnInfo(name = "lng")
    public String lng;


}
