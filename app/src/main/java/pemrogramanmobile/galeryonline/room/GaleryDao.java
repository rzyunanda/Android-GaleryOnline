package pemrogramanmobile.galeryonline.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface GaleryDao {

    @Query("SELECT * FROM galeri")
    List<RomGalery> getGalery();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertgalery(RomGalery romGalery);


}
