package pemrogramanmobile.galeryonline.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "galery")
public class galery {

    @PrimaryKey
    public  int id;

    @ColumnInfo(name="nama")
    public String nama;

    @ColumnInfo(name="deskripsi")
    public String deskripsi;

    @ColumnInfo(name="lat")
    public double lat;

    @ColumnInfo(name="lng")
    public  double lng;

    @ColumnInfo(name = "gambar")
    public String gambar;



}
