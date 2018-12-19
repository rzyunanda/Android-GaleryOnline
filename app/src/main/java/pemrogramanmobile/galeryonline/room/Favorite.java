package pemrogramanmobile.galeryonline.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorites")
class Favorite {
    @PrimaryKey
    public Integer id;

    @ColumnInfo(name = "nama")
    public String nama;

    @ColumnInfo(name = "lokasi")
    public String lokasi;

    @ColumnInfo(name = "gambar")
    public String gambar;

    @ColumnInfo(name="deskripsi")
    public String deskripsi;

    @ColumnInfo(name="lat")
    public String lat;

    @ColumnInfo(name="lng")
    public String lng;
}
